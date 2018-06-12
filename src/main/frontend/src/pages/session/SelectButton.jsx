import React, { Component } from 'react'

export default class SelectButton extends Component {
    constructor(props) {
        super(props);
        this.handleClick=this.handleClick.bind(this);
    }

    handleClick(e) {
        // console.log(e.target.id);
        this.props.onButtonClicked(e.target.id);
    }

    render() {
        let myButton;
        if (this.props.buttonDisabled) {
            let className = this.props.className + "_disabled";
            myButton = <button id={this.props.command} className={className} onClick={this.handleClick} disabled>{this.props.command}</button>
        } else {
            myButton = <button id={this.props.command} className={this.props.className} onClick={this.handleClick}>{this.props.command}</button>;
        }
        return (
            <div>
                {myButton}
            </div>
        );
    }
    
}