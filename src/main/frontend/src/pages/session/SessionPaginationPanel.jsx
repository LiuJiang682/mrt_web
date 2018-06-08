import React, { Component } from 'react'
import Pagination from 'react-js-pagination'
import 'jquery'
import "bootstrap-less"

export default class SessionPaginationPanel extends Component {
    constructor(props) {
        super(props);
        this.handlePageNoClicked = this.handlePageNoClicked.bind(this);
    }

    handlePageNoClicked(pageNumber) {
        // console.log(pageNumber);
        this.props.onPageNoClicked(pageNumber);
    }

    render() {
        // let paginationPanel;
        // if ((undefined !== this.props.totalPageNo)
        //     && (null !== this.props.totalPageNo)) {
        //         const len = (this.props.totalPageNo < 10) ? this.props.totalPageNo : 10;
        //         const pageNumbers = [];
        //         for (let i = 1; i <= len; i++) {
        //             pageNumbers.push(i);
        //         }

        //         const renderPageNumbers = pageNumbers.map(number => {
        //             return(
        //                 <li
        //                     key={number}
        //                     id={number} onClick={this.handlePageNoClicked}
        //                 >
        //                     {number}
        //                 </li>    
        //             );
        //         });
        //         paginationPanel = <ul id="page-numbers">
        //                             {renderPageNumbers} 
        //                             &emsp; 
        //                             <li>
        //                                 Next
        //                             </li>
        //                         </ul>
        // }
        
        // return (
        //     <div className="pagination_center">
        //         {paginationPanel}
        //     </div>
        // );

        return(
            <div className="pagination_center">
                <Pagination 
                    hideDisabled
                    prevPageText='prev'
                    nextPageText='next'
                    firstPageText='first'
                    lastPageText='last'
                    pageRangeDisplayed={10}
                    activePage={this.props.pageNo}
                    itemsCountPerPage={20}
                    totalItemsCount={this.props.totalItemsCount}
                    onChange={this.handlePageNoClicked}
                />
            </div>
        );
    }
}