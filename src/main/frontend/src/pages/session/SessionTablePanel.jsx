import React, { Component } from 'react'

import SessionRow from './SessionRow'

export default class SessionTablePanel extends Component {
    render() {
        const rows = [];
        
        this.props.sessions.forEach((session) => {
            rows.push(
                <SessionRow session={session} key={session.batchId} />
            );
        });
        return (
            <div>
                <table width="100%">
                    <thead>
                        <tr>
                            {/** TODO: Refactor this to a seperate class*/}
                            <th>
                                <label>
                                    <input name="selectAll" type="checkbox" />
                                </label>
                            </th>
                            <th>Batch Id</th>
                            <th>File Name</th>
                            <th>Status</th>
                            <th>Date Run</th>
                        </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>    
            </div>
        );
    }
}