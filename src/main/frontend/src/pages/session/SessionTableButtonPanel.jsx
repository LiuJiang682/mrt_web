import React, { Component } from 'react'

import SelectButton from './SelectButton'

export default class SessionTableButtonPanel extends Component {
    constructor(props) {
        super(props);
        this.handleButtonClick=this.handleButtonClick.bind(this);
    }

    handleButtonClick(command) {
        // e.preventDefault();
        console.log(command);
    }

    // render() {
    //     return (
    //         <div className="bottom_center">
    //             <button className="green_button">Approve selected items</button>
    //             &emsp;&emsp;&emsp;&emsp;
    //             <button className="red_button">Reject selected items</button>
    //         </div>
    //     );
    // }

    render() {
        return (
            <div className="bottom_center">
                <SelectButton className="green_button" command="Approve selected items" onButtonClicked={this.props.onButtonClicked}/>
                &emsp;&emsp;&emsp;&emsp;
                <SelectButton className="red_button" command="Reject selected itmes" onButtonClicked={this.props.onButtonClicked}/>
            </div>
        );
    }
}