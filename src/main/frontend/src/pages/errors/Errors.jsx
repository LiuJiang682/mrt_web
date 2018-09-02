import React, { Component } from "react";

import ReportTemplate from './ReportTemplate';

import {SERVER_HOST, SERVER_PORT, CORS_HEADERS} from '../common/Constants';
import { error } from "util";

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
                    var value = data[keyName];
                    let details = Object.keys(value).map(function(newkeyName, newkeyIndex) {
                        // console.log('newkey: ' + newkeyName, 'newvalue: ' + value[newkeyName]);
                        var myValue = value[newkeyName];
                        return (
                            <div key={newkeyIndex}>
                                <ReportTemplate templateName={keyName} fileName={newkeyName} recordList={value[newkeyName]} />
                            </div>
                        );
                    });
                    return details;
                });
                this.setState({
                    records: records
                });
            })
            .catch(error => alert(error));
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