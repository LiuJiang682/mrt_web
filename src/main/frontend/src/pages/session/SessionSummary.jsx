import React, { Component } from 'react'

// import Mrt from './Mrt'

// const Home = () => <div>Hello World! <Mrt /></div>

// export default Home
import SearchBar from '../common/SearchBar'
import SessionTable from './SessionTable'

// import serverUrl from 'Config'

export default class SessionSummary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sessions: [],
            searchText: '',
            selectAll: false,
            selectedBatch: [],
            totalItemsCount: 0,
            currentPage: 0,
        };

        this.handleSearchTextChange = this.handleSearchTextChange.bind(this);
        this.handleSelectAllChange = this.handleSelectAllChange.bind(this);
        this.handleSelectedBatchChange = this.handleSelectedBatchChange.bind(this);
        this.handleTotalPageNoChange = this.handleTotalPageNoChange.bind(this);
        this.handleCurrentPageChange = this.handleCurrentPageChange.bind(this);
        this.handleButtonClicked = this.handleButtonClicked.bind(this);
        this.handlePageNoClicked = this.handlePageNoClicked.bind(this);
        this.handleSearchSubmit = this.handleSearchSubmit.bind(this);
    }

    handleSearchSubmit(searchText) {
        const url = "http://localhost:8090/sessionHeader/" + searchText;
        const newSessions =[];
        fetch(url)
            .then(results => {
                return results.json();
            })
            .then(data => {
                console.log(data);
                const batchId = this.extractBatchId(data._links.self.href);
                const fileName = data.fileName;
                const status = data.status;
                const dateRun = this.exttractTime(data.created);
                const sessionDto = {batchId: batchId, fileName: fileName, status: status, dateRun: dateRun};
                newSessions.push(sessionDto);
                   
                this.setState({
                    sessions: newSessions,
                });
            });
    }

    handleSearchTextChange(searchText) {
        this.setState({
            searchText: searchText
        });
    }

    handleSelectAllChange(selectAll) {
        console.log(selectAll);
        if (selectAll) {
            // console.log('About to selected all session');
            const selectedSessions = [];
            for (const session of this.state.sessions) {
                selectedSessions.push(session.batchId);
            }
            this.setState({
                selectedBatch: selectedSessions
            });
        }
        this.setState({
            selectAll: selectAll
        });
    }

    handleSelectedBatchChange(selectedBatch) {
        // console.log(this.state.selectedBatch);
        // console.log(selectedBatch);
        let newSelectedBatch = [];
        if ((undefined === this.state.selectedBatch) 
            || (null === this.state.selectedBatch)
            || (0 === this.state.selectedBatch.length)) {
                //Empty array case
                newSelectedBatch.push(selectedBatch);
            } else {
                let index = this.state.selectedBatch.indexOf(selectedBatch);
                // console.log(index);
                if (-1 === index) {
                    //No contain case
                    newSelectedBatch = this.state.selectedBatch;
                    newSelectedBatch.push(selectedBatch);
                } else {
                    //Contain and replaces case
                    newSelectedBatch = this.state.selectedBatch;
                    // console.log('newSelectedBatch before delete', newSelectedBatch);  
                    if (1 === newSelectedBatch.length) {
                        newSelectedBatch = [];
                    } else {
                        newSelectedBatch.splice(index, 1);
                    }
                    // console.log('newSelectedBatch after delete', newSelectedBatch);  
                }
            }
        // console.log('newSelectedBatch', newSelectedBatch);    
        this.setState({
            selectedBatch: newSelectedBatch
        });
        console.log(this.state.selectedBatch);
    }

    handleTotalPageNoChange(totalItemsCount) {
        this.setState({
            totalItemsCount: totalItemsCount
        });
    }

    handleCurrentPageChange(currentPage) {
        this.setState({
            currentPage: currentPage
        });
    }

    handleButtonClicked(command) {
        // console.log(command);
        let commandString = '';

        if (command.startsWith('Approve')) {
            commandString = 'approve';
        } else if (command.startsWith('Reject')) {
            commandString = 'reject';
        }
        if ('' === commandString) {
            alert("Unknown command: " + command);
        } else if (0 === this.state.selectedBatch.length) {
            alert("Please select at least one file before you " + commandString);
        } else {
            var url = "http://localhost:8090/sessionHeader/search/" + commandString + "?sessionId=" + this.state.selectedBatch.join(",");
            console.log(url);
            fetch(url)
                .then(
                    (response) => {
                        if (response.ok) {
                            // console.log("Updated");
                            const newSessions =[];
                            const refreshUrl = "http://localhost:8090/sessionHeader/search/display?page=" + this.state.currentPage + "&size=20";
                            fetch(refreshUrl)
                                .then(results => {
                                    return results.json();
                                })
                                .then(data => {
                                    // console.log(data);
                                    for (const session of data._embedded.sessionHeader) {
                                        // console.log(session._links.self.href);
                                        const batchId = this.extractBatchId(session._links.self.href);
                                        const fileName = session.fileName;
                                        const status = session.status;
                                        const dateRun = this.exttractTime(session.created);
                                        const sessionDto = {batchId: batchId, fileName: fileName, status: status, dateRun: dateRun};
                                        newSessions.push(sessionDto);
                                    }
                                    // console.log("newSessions", newSessions);    
                                    this.setState({
                                        sessions: newSessions,
                                        totalItemsCount: data.page.totalElements,
                                        currentPage: ++data.page.number,
                                    });
                                });
         
                             this.setState({
                                sessions: newSessions,
                                selectedBatch: [],
                                selectAll: false,
                                searchText: ''
                            });
                        } else {
                            alert('Fail to Update the session!');
                        }
                    }
                );
        }
    }

    handlePageNoClicked(pageNo) {
        // console.log(pageNo);
        const newSessions =[];
        const refreshUrl = "http://localhost:8090/sessionHeader/search/display?page=" + pageNo + "&size=20";
        fetch(refreshUrl)
            .then(results => {
                return results.json();
            })
            .then(data => {
                // console.log(data);
                for (const session of data._embedded.sessionHeader) {
                    // console.log(session._links.self.href);
                    const batchId = this.extractBatchId(session._links.self.href);
                    const fileName = session.fileName;
                    const status = session.status;
                    const dateRun = this.exttractTime(session.created);
                    const sessionDto = {batchId: batchId, fileName: fileName, status: status, dateRun: dateRun};
                    newSessions.push(sessionDto);
                }
                // console.log("newSessions", newSessions);    
                this.setState({
                    sessions: newSessions,
                    totalItemsCount: data.page.totalElements,
                    currentPage: pageNo
                });
            });
         
        this.setState({
            sessions: newSessions,
            selectedBatch: []
        });
    }

    render() {
        return (
            <div>
                <SearchBar searchText={this.state.searchText} onSearchTextChange={this.handleSearchTextChange} onSearchSubmit={this.handleSearchSubmit}/>
                <SessionTable sessions={this.state.sessions} searchText={this.state.searchText} selectAll={this.state.selectAll} 
                    selectedBatch={this.state.selectedBatch} totalItemsCount={this.state.totalItemsCount} currentPage={this.state.currentPage}
                    onSelectAllChange={this.handleSelectAllChange} onSelectedBatchChange={this.handleSelectedBatchChange} 
                    onButtonClicked={this.handleButtonClicked} onPageNoClicked={this.handlePageNoClicked}/>
            </div>
        )
    }

     componentWillMount() {
         console.log('insider componentWillMount');
         const newSessions =[];
         fetch("http://localhost:8090/sessionHeader/search/display?page=0&size=20")
            .then(results => {
                return results.json();
            })
            .then(data => {
                // console.log(data);
                for (const session of data._embedded.sessionHeader) {
                    // console.log(session._links.self.href);
                    const batchId = this.extractBatchId(session._links.self.href);
                    const fileName = session.fileName;
                    const status = session.status;
                    const dateRun = this.exttractTime(session.created);
                    const sessionDto = {batchId: batchId, fileName: fileName, status: status, dateRun: dateRun};
                    newSessions.push(sessionDto);
                }
                // console.log("newSessions", newSessions);    
                this.setState({
                    sessions: newSessions,
                    totalItemsCount: data.page.totalElements,
                    currentPage: ++data.page.number,
                });
            });
         
         this.setState({
             sessions: newSessions
         });
     }   

     extractBatchId(url) {
        //  console.log(url);
        if ((undefined !== url) 
            && (null !== url)) {
                let index = url.lastIndexOf("/");
                return url.substring(++index);
            }
        return '';
    }

    exttractTime(timeString) {
        if ((undefined !== timeString) 
            && (null !== timeString)) {
                let index = timeString.lastIndexOf("+");
                var newTimeString = timeString.slice(0, index);
                return newTimeString.replace('T', ' ');
            }
        return '';    
    }
}



const SESSIONS = [
    {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'},
    {batchId: '789012', fileName: 'MRT_EL98', status: 'Success', dateRun: '06/05/2018'},
    {batchId: '345678', fileName: 'MRT_EL97', status: 'Fail', dateRun: '05/05/2018'},
    {batchId: '901234', fileName: 'MRT_EL96', status: 'Success', dateRun: '04/05/2018'},
    {batchId: '567890', fileName: 'MRT_EL95', status: 'Fail', dateRun: '03/05/2018'},
    {batchId: '112345', fileName: 'MRT_EL92', status: 'Success', dateRun: '02/05/2018'}
];