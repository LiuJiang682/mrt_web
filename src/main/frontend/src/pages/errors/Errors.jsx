import React, { Component } from "react";

import ReportTemplate from './ReportTemplate';

export default class Errors extends Component {
    constructor(props) {
        super(props);
        this.state = {
            records: [],
            batchId: this.props.match.params.id,
        };
    }

    componentWillMount() {
        let headers = new Headers({
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
			'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
		    'Content-Type': 'multipart/form-data'
        });
        //TODO: -- Replace with correct URL
        var url = 'http://localhost:8090/template/' + this.state.batchId;
        fetch(url, {
            headers: headers
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