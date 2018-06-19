import React, { Component } from "react";

import TemplateDataRecordList from './TemplateDataRecordList';

export default class ReportTemplate extends Component {
    constructor(props) {
        super(props);
        this.state = {
            templateName: this.props.templateName,
            recordList: this.props.recordList,
        }
    }
    render() {
        const rows = [];
        const headers = this.extractColumnHeaders(this.state.recordList[0]);
        const headerRow = this.buildTableHeader(headers);
        rows.push(headerRow);
        const length = this.state.recordList.length;
        for (var index = 1; index < length; index++) {
            rows.push(<TemplateDataRecordList headers={headers} recordList={this.state.recordList[index]} />);
        }
        
        return (
            <table width="100%">
                <thead>
                    <tr>
                        <th>{this.state.templateName}</th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
        );
    }

    extractColumnHeaders(headers) {
        // console.log(headers);
        let columnHeaders = Object.keys(headers).map(function(keyName, keyIndex) {
            console.log('key: ' + keyName, 'value: ' + headers[keyName], 'index: ' + keyIndex);
            return headers[keyName];
        });
        // console.log(columnHeaders);
        return columnHeaders;
    }

    buildTableHeader(headers) {
        // console.log('headers', headers);
        const headerRows = [];
        var index = 0;
        headers.forEach((header) => {
            const td = <td key={index}>{header}</td>
            headerRows.push(td);
            ++index;
        });
        const tr = <tr className="tr_height">{headerRows}</tr>
        // console.log('tr', tr);
        return tr;
    }
}