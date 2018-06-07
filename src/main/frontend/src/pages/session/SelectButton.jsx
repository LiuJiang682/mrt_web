import React, { Component } from 'react'

export default class SelectButton extends Component {
    constructor(props) {
        super(props);
        this.handleClick=this.handleClick.bind(this);
    }

    handleClick(e) {
        console.log(e.target.id);
        this.props.onButtonClicked(e.target.id);
    }

    render() {
        return (
            <div>
                <button id={this.props.command} className={this.props.className} onClick={this.handleClick}>{this.props.command}</button>
            </div>
        );
    }
    
}