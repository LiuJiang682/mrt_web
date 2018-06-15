import React, { Component } from "react";

export default class Logs extends Component {
    constructor(props) {
        super(props);
        var batchId = this.props.match.params.id;
        batchId = batchId.replace(":", "");
        this.state = {
            batchId: batchId,
        }
    }
    render() {
        return (
            <div>
                <h2>Logs of Batch: {this.state.batchId}</h2>
            </div>
        )
    }
}