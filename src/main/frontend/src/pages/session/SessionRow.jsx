import React, { Component } from 'react'

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
        return (
            <tr className="tr_height">
                <td className="pos_center">{checkBox}</td>
                <td className="pos_left_middle">{session.batchId}</td>
                <td className="pos_left_middle">{session.fileName}</td>
                <td className="pos_left_middle">{status}</td>
                <td className="pos_left_middle">{session.dateRun}</td>
            </tr>
        );
    }
}