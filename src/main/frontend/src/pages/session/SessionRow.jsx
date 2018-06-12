import React, { Component } from 'react'
import { Link } from 'react-router-dom';

export default class SessionRow extends Component {
    constructor(props) {
        super(props);
        this.handleSelectBatchChang=this.handleSelectBatchChang.bind(this);
    }

    handleSelectBatchChang(e) {
        // console.log(e.target.name);
        this.props.onSelectedBatchChange(e.target.name);
    }

    render() {
        console.log("SessionRow render");
        const session = this.props.session; 
        const selectedBatch = this.props.selectedBatch; 
        const status = ("FAILED" === session.status.toUpperCase()) ? <span style={{color:'red'}}>{session.status}</span> : session.status;
        let checkBox;
        if ((undefined !== selectedBatch)
            && (null !== selectedBatch)
            && (selectedBatch.includes(session.batchId))) {
                checkBox = <input type="checkbox" name={session.batchId} checked onChange={this.handleSelectBatchChang}/>; 
            } else {
                checkBox = <input type="checkbox" name={session.batchId} onChange={this.handleSelectBatchChang}/>; 
            }
        let fileName;
        if ((undefined === session.actionStatus) 
            || (null === session.actionStatus)) {
                fileName = session.fileName;
            } else if ("APPROVED" === session.actionStatus.toUpperCase()) {
                fileName = <span style={{color:'green'}}>{session.fileName}&ensp;--&ensp;<strong>Approved</strong></span> ;
            } else if ("REJECTED" === session.actionStatus.toUpperCase()) {
                fileName = <span style={{color:'red'}}>{session.fileName}&ensp;--&ensp;<strong>Rejected</strong></span> ;
            } else {
                fileName = session.fileName;
            }   
        return (
            <tr className="tr_height">
                <td className="pos_center">{checkBox}</td>
                <td className="pos_left_middle"><Link to={"/map/" + session.batchId}>{session.batchId}</Link></td>
                <td className="pos_left_middle">{fileName}</td>
                <td className="pos_left_middle">{status}</td>
                <td className="pos_left_middle">{session.dateRun}</td>
            </tr>
        );
    }
}