import React, { Component } from 'react'

import SessionTableHeader from './SessionTableHeader'
import SessionRow from './SessionRow'
import SessionPaginationPanel from './SessionPaginationPanel'

export default class SessionTablePanel extends Component {
    render() {
        const rows = [];
        
        this.props.sessions.forEach((session) => {
            rows.push(
                <SessionRow session={session} key={session.batchId}  selectedBatch={this.props.selectedBatch} onSelectedBatchChange={this.props.onSelectedBatchChange}/>
            );
        });
        return (
            <div>
                <table width="100%">
                    <thead>
                       <SessionTableHeader selectAll={this.props.selectAll} onSelectAllChange={this.props.onSelectAllChange}/>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>    
                <SessionPaginationPanel totalPages={this.props.totalPages} pageNo={this.props.pageNo} /> 
            </div>
        );
    }
}