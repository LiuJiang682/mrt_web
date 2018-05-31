import React, { Component } from 'react'

export default class SearchBar extends Component {
    render() {
        return (
            <div className="pos_center">
                <form>
                    <p>
                        <b>Batch Id:</b>&emsp;
                        <input type="text" placeholder="Search Session ID..." />
                    </p>
                </form>
            </div>
        );
    }
}