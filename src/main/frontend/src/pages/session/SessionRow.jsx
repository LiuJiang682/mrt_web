import React, { Component } from 'react'

export default class SessionRow extends Component {
    render() {
        const session = this.props.session;
        const status = (session.status === 'Fail') ? <span style={{color:'red'}}>{session.status}</span> : session.status;
        return (
            <tr className="tr_height">
                <td className="pos_center"><input type="checkbox" name={session.batchId}/></td>
                <td className="pos_left_middle">{session.batchId}</td>
                <td className="pos_left_middle">{session.fileName}</td>
                <td className="pos_left_middle">{status}</td>
                <td className="pos_left_middle">{session.dateRun}</td>
            </tr>
        );
    }
}