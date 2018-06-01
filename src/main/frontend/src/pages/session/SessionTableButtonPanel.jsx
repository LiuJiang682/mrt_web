import React, { Component } from 'react'

export default class SessionTableButtonPanel extends Component {
    render() {
        return (
            <div className="bottom_center">
                <button className="green_button">Approve selected items</button>
                &emsp;&emsp;&emsp;&emsp;
                <button className="red_button">Reject selected items</button>
            </div>
        );
    }
}