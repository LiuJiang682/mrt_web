import React, { Component } from 'react'

export default class SessionPaginationPanel extends Component {
    render() {
        let paginationPanel;
        if ((undefined !== this.props.totalPageNo)
            && (null !== this.props.totalPageNo)) {
                const len = (this.props.totalPageNo < 10) ? this.props.totalPageNo : 10;
                const pageNumbers = [];
                for (let i = 1; i <= len; i++) {
                    pageNumbers.push(i);
                }

                const renderPageNumbers = pageNumbers.map(number => {
                    return(
                        <li
                            key={number}
                            id={number}
                        >
                            {number}
                        </li>    
                    );
                });
                paginationPanel = <ul id="page-numbers">
                                    {renderPageNumbers} 
                                    &emsp; 
                                    <li>
                                        Next
                                    </li>
                                </ul>
        }
        
        return (
            <div className="pagination_center">
                {paginationPanel}
            </div>
        );
    }
}