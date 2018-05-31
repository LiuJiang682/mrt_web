import React, { Component } from 'react'

import SessionRow from './SessionRow'
import SessionPaginationPanel from './SessionPaginationPanel'

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
                        <tr style={{paddingBottom: '50px'}}>
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
                <SessionPaginationPanel totalPages={this.props.totalPages} pageNo={this.props.pageNo} /> 
            </div>
        );
    }
}