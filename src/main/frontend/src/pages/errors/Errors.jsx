import React, { Component } from "react";

export default class Errors extends Component {
    render() {
        return (
            <div>
                <h2>Errors: {this.props.match.params.id}</h2>
            </div>
        )
    }
}