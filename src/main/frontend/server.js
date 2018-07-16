const express = require('express')
const path = require('path')
const webpack = require('webpack')

const app = express()

const webpackMiddleware = require("webpack-dev-middleware")
// const webpackConfig = require('./webpack.config.js')
let webpackConfig;
if (process.env.NODE_ENV === 'production') {
  webpackConfig = require('./webpack.config.production')
} else {
  webpackConfig = require('./webpack.config.development')
}

app.use(webpackMiddleware(
  webpack(webpackConfig),
  { publicPath: '/' }
))

app.get('/about', (req, res) => {
  res.sendFile(path.join(__dirname, 'pages', 'about.html'))
})

app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'pages', 'index.html'))
})

app.listen(9090, () => console.log('Server listening on 9090'))
