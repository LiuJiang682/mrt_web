import React, { Component } from 'react'

export default class LogButtonPanel extends Component {
    constructor(props) {
        super(props);
        this.handleApprove=this.handleApprove.bind(this);
        this.handleReject=this.handleReject.bind(this);
        this.handleDisplay=this.handleDisplay.bind(this);
    }

    handleApprove() {
        this.props.onApprove();
    }

    handleReject() {
        this.props.onReject();
    }

    handleDisplay() {
        this.props.onDisplay();
    }

    render() {
        return (
            <div>
                <div className="bottom_center">
                    <div>
                        <button className="green_button" onClick={this.handleApprove}>Approve</button>
                    </div>
                    <div>
                        <button className="red_button" onClick={this.handleReject}>Reject</button>
                    </div>
                    <div>
                        <button className="green_button" onClick={this.handleDisplay}>Display Error Records</button>
                    </div>
                </div>
            </div>
        );
    }
}