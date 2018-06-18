import React, { Component } from "react";

export default class Logs extends Component {
    constructor(props) {
        super(props);
        var batchId = this.props.match.params.id;
        batchId = batchId.replace(":", "");
        this.state = {
            batchId: batchId,
            fileErrorLogDTOs: [],
            fileWarningLogDTOs: [],
            fileInfoLogDTOs: [],
        }
    }

    componentWillMount() {
        console.log('componentWillMount');
        const fileErrorLogDTOs = [];
        const fileWarningLogDTOs = [];
        const fileInfoLogDTOs = [];
        const url = "http://localhost:8090/fileLogs/search/get?loaderId=" + this.state.batchId;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log(data);
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
                fileInfoLogDTOs.push("Test");
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
                </div>
            </div>
        )
    }
}