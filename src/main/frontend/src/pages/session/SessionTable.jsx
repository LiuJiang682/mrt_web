import React, { Component } from 'react'

import SessionTablePanel from './SessionTablePanel'
import SessionTableButtonPanel from './SessionTableButtonPanel'

export default class SessionTable extends Component {
    render() {
        return (
            <div>
                <SessionTablePanel sessions={this.props.sessions}/>
                <SessionTableButtonPanel />
            </div>
        );
    }
}