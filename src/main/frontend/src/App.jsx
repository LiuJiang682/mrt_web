import React from 'react'
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom'

import Home from './pages/Home'
import Books from './pages/Books'
import Banner from './pages/common/Banner'

const App = () =>
  <Router>
    <div>
    <Banner />
    <Route exact path="/" component={Home}/>
    <Route path="/books" component={Books}/>
    
    <ul>
        <li><Link to="/">Home</Link></li>
        <li><Link to="/books">Books</Link></li>
      </ul>

    </div>
  </Router>

export default App