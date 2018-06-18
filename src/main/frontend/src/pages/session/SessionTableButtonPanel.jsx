import React, { Component } from 'react'

import SelectButton from './SelectButton'

export default class SessionTableButtonPanel extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="bottom_center">
                <SelectButton className="green_button" command="Approve selected items" onButtonClicked={this.props.onButtonClicked} buttonDisabled={this.props.buttonDisabled}/>
                <SelectButton className="red_button" command="Reject selected itmes" onButtonClicked={this.props.onButtonClicked} buttonDisabled={this.props.buttonDisabled}/>
            </div>
        );
    }
}