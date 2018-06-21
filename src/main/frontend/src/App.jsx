import React from 'react'
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom'

import Banner from './pages/common/Banner'
import NavBar from './pages/common/NavBar'
import SessionSummary from './pages/session/SessionSummary'
import Map from './pages/map/Map';
import Logs from './pages/logs/Logs';
import Errors from './pages/errors/Errors';

import style from '../style/style.css';

const App = () =>
  <Router>
    <div className="app">
      <NavBar />
      <Banner />    
      
      {/* <Route path="/map" component={Map} /> */}
      <Route path="/map/:id" component={Map} />
      
      <Route path="/logs/:id" component={Logs} />
      <Route path="/errors/:id" component={Errors} />
      <Route exact path="/" component={SessionSummary}/>
    </div>
  </Router>

export default App