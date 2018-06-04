import React, { Component } from 'react'

export default class SearchBar extends Component {
    constructor(props) {
        super(props);
        this.handleSearchTextChange= this.handleSearchTextChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSearchTextChange(e) {
        console.log(e.target.value);
        this.props.onSearchTextChange(e.target.value);
    }

    handleSubmit(e) {
        // e.preventDefault();
        console.log('inside handle submit');
        console.log('ref', this.refs.searchText.value);
    }

    render() {
        const searchText = this.props.searchText;

        return (
            <div className="pos_center">
                <form onSubmit={this.handleSubmit} >
                    <p>
                        <b>Batch Id:</b>&emsp;
                        <input type="text" ref="searchText" placeholder="Search Session ID..." value={searchText} onChange={this.handleSearchTextChange} />
                    </p>
                </form>
            </div>
        );
    }
}