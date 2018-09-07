import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { CONTEXT_PATH } from '../common/Constants'

export default class NavBar extends Component {
    render() {
        let linkId = "/" + CONTEXT_PATH;
        return (
        <div>
            <nav className="navbar navbar-default">
                <div className="container-fluid">
                    <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul className="nav navbar-nav">
                            {/* TODO: fix the activeClassName not working */}
                            <li><Link to={linkId} className="active">Session Summary</Link></li>
                            {/* <li><Link to="/logs" activeClassName="active">Logs</Link></li> */}
                            {/* <li><Link to="errors" activeClassName="active">Errors</Link></li> */}
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        )
    }
}