import React, { Component } from "react";
import { withRouter } from "react-router-dom";

import LogButtonPanel from './LogButtonPanel';

import {SERVER_HOST, SERVER_PORT, CORS_HEADERS} from '../common/Constants';

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
            host: null,
            port: -1,
            headers: null,
        }
        this.handleApprove = this.handleApprove.bind(this);
        this.handleDisplay = this.handleDisplay.bind(this);
        this.handelReject = this.handelReject.bind(this);
    }

    handleApprove() {
        // console.log('handle approve');
        var url = "http://" + this.state.host + ":" + this.state.port + "/sessionHeader/search/approve?sessionId=" + this.state.batchId;
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
        var url = "http://" + this.state.host + ":" + this.state.port + "/sessionHeader/search/reject?sessionId=" + this.state.batchId;
        this.updateSession(url);
    }

    updateSession(url) {
        // console.log('updateSession ' + url);
        fetch(url, {
            headers: this.state.headers
        })
            .then(response => {
                // console.log(response);
                if (response.ok) {
                    //Session reject -- redirect back to first page.
                    this.props.history.push("/");
                } else { 
                    // console.log('about to alert');
                    // alert('Fail to Update the session!');
                    // console.log('just complated alert');
                    const errorMessage = response.status + ' ' + response.statusText + ' ' + response.body;
                    throw new Error(errorMessage);
                } 
            })
            .catch(error => alert(error));
    }

    componentWillMount() {
        // console.log('componentWillMount');
        this.setState({
            host: SERVER_HOST,
            port: SERVER_PORT,
            headers: CORS_HEADERS,
        });
        const fileErrorLogDTOs = [];
        const fileWarningLogDTOs = [];
        const fileInfoLogDTOs = [];
        const url = "http://" + SERVER_HOST + ":" + SERVER_PORT + "/fileLogs/search/get?sessionId=" + this.state.batchId;
        fetch(url, {
            headers: CORS_HEADERS
        })
            .then(response => {
                if (response.ok) {
                    return response.json()
                } else {
                    const errorMessage = response.status + ' ' + response.statusText + ' ' + response.body;
                    throw new Error(errorMessage);
                }
            })
            .then(data => {
                // console.log(data);
                if ((data._embedded) 
                    && (data._embedded.fileLogs)) {
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
                } else {
                    console.log('No fileLogs!');
                }
            })
            .catch(error => alert(error));            
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