import React, { Component } from 'react'

import SessionTableHeader from './SessionTableHeader'
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
                       <SessionTableHeader selectAll={this.props.selectAll}/>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>    
                <SessionPaginationPanel totalPages={this.props.totalPages} pageNo={this.props.pageNo} /> 
            </div>
        );
    }
}