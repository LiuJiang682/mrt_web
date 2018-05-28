import React from 'react'
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom'

import Home from './pages/Home'
import Books from './pages/Books'

const App = () =>
  <Router>
    <div>
    <Route exact path="/" component={Home}/>
    <Route path="/books" component={Books}/>
    
    <ul>
        <li><Link to="/">Home</Link></li>
        <li><a href="/about">About</a></li> {/* Note that this is still an anchor */}
        <li><Link to="/books">Books</Link></li>
      </ul>

    </div>
  </Router>

export default App
