import React, { Component } from "react";

import ReportTemplate from './ReportTemplate';

import {SERVER_HOST, SERVER_PORT, CORS_HEADERS} from '../common/Constants';

export default class Errors extends Component {
    constructor(props) {
        super(props);
        this.state = {
            records: [],
            batchId: this.props.match.params.id,
        };
    }

    componentWillMount() {
        var url = 'http://' + SERVER_HOST + ':' + SERVER_PORT + '/template/' + this.state.batchId;
        fetch(url, {
            headers: CORS_HEADERS
        })
            .then(response => response.json())
            .then(data => {
                // console.log('data', data);
                let records = Object.keys(data).map(function(keyName, keyIndex) {
                    // console.log('key: ' + keyName, 'value: ' + data[keyName]);
                    return (
                        <div key={keyIndex}>
                            <ReportTemplate templateName={keyName} recordList={data[keyName]} />
                        </div>
                    );
                });
                this.setState({
                    records: records
                });
            });
    }
    render() {
        return (
            <div>
                <h2>Errors: {this.state.batchId}</h2>
                <div>
                    {this.state.records}
                </div>
            </div>
        )
    }
}