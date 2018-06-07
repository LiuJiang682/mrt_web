import React, { Component } from 'react'

import SessionTablePanel from './SessionTablePanel'
import SessionTableButtonPanel from './SessionTableButtonPanel'

export default class SessionTable extends Component {
    render() {
        return (
            <div>
                <SessionTablePanel sessions={this.props.sessions} searchText={this.props.searchText} selectAll={this.props.selectAll} 
                    selectedBatch={this.props.selectedBatch} totalPageNo={this.props.totalPageNo} currentPage={this.props.currentPage}
                    onSelectAllChange={this.props.onSelectAllChange} onSelectedBatchChange={this.props.onSelectedBatchChange}/>
                <SessionTableButtonPanel onButtonClicked={this.props.onButtonClicked} />
            </div>
        );
    }
}