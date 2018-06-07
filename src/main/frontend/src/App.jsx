import React from 'react'
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom'

import Banner from './pages/common/Banner'
import NavBar from './pages/common/NavBar'
import SessionSummary from './pages/session/SessionSummary'
import Map from './pages/Map';
import Logs from './pages/Logs';
import Errors from './pages/Errors';

import style from '../style/style.css';

const App = () =>
  <Router>
    <div>
      <NavBar />
      <Banner />    
      <Route exact path="/" component={SessionSummary}/>
      <Route path="/map" component={Map} />
      <Route path="/map/:id" component={Map} />
      <Route path="/logs" component={Logs} />
      <Route path="/errors" component={Errors} />
    </div>
  </Router>

export default App
