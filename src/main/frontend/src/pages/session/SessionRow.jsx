import React, { Component } from 'react'

export default class SessionRow extends Component {
    render() {
        const session = this.props.session;
        const status = (session.status === 'Fail') ? <span style={{color:'red'}}>{session.status}</span> : session.status;
        return (
            <tr>
                <td><input type="checkbox" name={session.batchId}/></td>
                <td>{session.batchId}</td>
                <td>{session.fileName}</td>
                <td>{status}</td>
                <td>{session.dateRun}</td>
            </tr>
        );
    }
}