import React, { Component } from "react";

export default class Logs extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                <h2>Logs -- {this.props.match.params.id}</h2>
            </div>
        )
    }
}