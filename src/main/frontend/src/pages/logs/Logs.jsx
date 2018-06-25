import React, { Component } from "react";
import { withRouter } from "react-router-dom";

import LogButtonPanel from './LogButtonPanel'

class Logs extends Component {
    constructor(props) {
        super(props);
        var batchId = this.props.match.params.id;
        batchId = (batchId) ? batchId.replace(":", "") : '';
        this.state = {
            batchId: batchId,
            fileErrorLogDTOs: [],
            fileWarningLogDTOs: [],
            fileInfoLogDTOs: [],
        }
        this.handleApprove = this.handleApprove.bind(this);
        this.handleDisplay = this.handleDisplay.bind(this);
        this.handelReject = this.handelReject.bind(this);
    }

    handleApprove() {
        // console.log('handle approve');
        var url = "http://localhost:8090/sessionHeader/search/approve?sessionId=" + this.state.batchId;
        this.updateSession(url);
    }

    handleDisplay() {
        // console.log('handle display');
        //Redirect to display error page
        var url = "/errors/" + this.state.batchId;
        this.props.history.push(url);
    }

    handelReject() {
        // console.log('handle reject');
        var url = "http://localhost:8090/sessionHeader/search/reject?sessionId=" + this.state.batchId;
        this.updateSession(url);
    }

    updateSession(url) {
        fetch(url)
            .then(response => {
                if (response.ok) {
                    //Session reject -- redirect back to first page.
                    this.props.history.push("/");
                } else {
                    alert('Fail to Update the session!');
                } 
            });
    }

    componentWillMount() {
        // console.log('componentWillMount');
        const fileErrorLogDTOs = [];
        const fileWarningLogDTOs = [];
        const fileInfoLogDTOs = [];
        const url = "http://localhost:8090/fileLogs/search/get?loaderId=" + this.state.batchId;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                // console.log(data);
                for (const fileLog of data._embedded.fileLogs) {
                    const severity = fileLog.severity;
                    const message = fileLog.message;
                    if ('ERROR' === severity.toUpperCase()) {
                        fileErrorLogDTOs.push(message);
                    } else if ('WARNING' === severity.toUpperCase()) {
                        fileWarningLogDTOs.push(message);
                    } else {
                        fileInfoLogDTOs.push(message);
                    }
                }
                // fileInfoLogDTOs.push("Test");
                
                this.setState({
                    fileErrorLogDTOs: fileErrorLogDTOs,
                    fileWarningLogDTOs: fileWarningLogDTOs,
                    fileInfoLogDTOs: fileInfoLogDTOs,
                });
            });
            
    }

    render() {
        const rows = [];
        var key = 0;
        const disableApprove = (0 === this.state.fileErrorLogDTOs.length) ? false : true;
        this.state.fileErrorLogDTOs.forEach((dto) => {
            const error = <tr className="tr_height" key={++key}><td className="log_error">ERROR: {dto}</td></tr>
            rows.push(error);
        });
        this.state.fileWarningLogDTOs.forEach((dto) => {
            const warning = <tr className="tr_height" key={++key}><td className="log_warning">WARNING: {dto}</td></tr>
            rows.push(warning);
        });
        this.state.fileInfoLogDTOs.forEach((dto) => {
            const info = <tr className="tr_height" key={++key}><td className="log_info">INFO: {dto}</td></tr>
            rows.push(info);
        });
        return (
            <div>
                <h2>Logs of Batch: {this.state.batchId}</h2>
                <div>
                    <table width="100%">
                        <tbody>{rows}</tbody>
                    </table>
                    <LogButtonPanel disableApprove={disableApprove} onApprove={this.handleApprove} onReject={this.handelReject} onDisplay={this.handleDisplay}/>
                </div>
            </div>
        )
    }
}

export default withRouter(Logs);