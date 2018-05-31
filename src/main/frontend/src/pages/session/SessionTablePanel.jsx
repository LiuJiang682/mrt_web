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
                        <tr className="tr_height">
                            {/** TODO: Refactor this to a seperate class*/}
                            <th className="pos_center">
                                <label>
                                    <input name="selectAll" type="checkbox" />
                                </label>
                            </th>
                            <th className="pos_left_middle">Batch Id</th>
                            <th className="pos_left_middle">File Name</th>
                            <th className="pos_left_middle">Status</th>
                            <th className="pos_left_middle">Date Run</th>
                        </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>    
                <SessionPaginationPanel totalPages={this.props.totalPages} pageNo={this.props.pageNo} /> 
            </div>
        );
    }
}