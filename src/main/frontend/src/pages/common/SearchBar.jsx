import React, { Component } from 'react'

export default class SearchBar extends Component {
    render() {
        const searchText = this.props.searchText;

        return (
            <div className="pos_center">
                <form>
                    <p>
                        <b>Batch Id:</b>&emsp;
                        <input type="text" placeholder="Search Session ID..." value={searchText} />
                    </p>
                </form>
            </div>
        );
    }
}