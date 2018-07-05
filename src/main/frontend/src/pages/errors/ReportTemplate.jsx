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
        if ((null === this.state.recordList)) {
            const headerRow = <tr key={0} className="tr_height"><td className="log_error"><strong>No Data</strong></td></tr>
            rows.push(headerRow);
        } else {
            const headers = this.extractColumnHeaders(this.state.recordList[0]);
            const dataHeaders = this.extractDataHeaders(this.state.recordList[1]);
            const headerRow = this.buildTableHeader(headers, dataHeaders);
            const headersUC = this.buildUCHeaders(headers);
            rows.push(headerRow);
            const length = this.state.recordList.length;
            for (var index = 1; index < length; index++) {
                rows.push(<TemplateDataRecordList key={index} currentIndex={index} headers={headersUC} recordList={this.state.recordList[index]} />);
            }
        }
        
        return (
            <table width="100%" className="data_table">
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

    extractDataHeaders(dataRow) {
        // console.log(dataRow);
        let dataHeaders = Object.keys(dataRow).map(function(keyName, keyIndex) {
            // console.log(keyName + ' ' + keyIndex);
            return keyName;
        });
        // console.log(dataHeaders);
        return dataHeaders;
    }

    extractColumnHeaders(headers) {
        // console.log(headers);
        if ((undefined === headers) 
            || (null === headers)) {
                return null;
            }
        let columnHeaders = Object.keys(headers).map(function(keyName, keyIndex) {
            // console.log('key: ' + keyName, 'value: ' + headers[keyName], 'index: ' + keyIndex);
            return headers[keyName];
        });
        // console.log(columnHeaders);
        return columnHeaders;
    }

    buildTableHeader(headers, dataHeaders) {
        // console.log('headers', headers);
        const headerRows = [];
        var index = 0;
        headers.forEach((header) => {
            const dataHeaderPos = dataHeaders.indexOf(header);
            const className = (-1 < dataHeaderPos) ? "data_table_td" : "data_table_td_warn";
            const td = <td key={index} className={className}>{header}</td>
            headerRows.push(td);
            ++index;
        });

        const tr = <tr key={0} className="tr_height">{headerRows}</tr>
        // console.log('tr', tr);
        return tr;
    }

    buildUCHeaders(headers) {
        const ucHeaders = [];
        var index = 0;
        headers.forEach((header) => {
            const ucHeader = header.toUpperCase();
            ucHeaders.push(ucHeader);
        });
        return ucHeaders;
    }
}