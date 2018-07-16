const path = require('path');
const merge = require('webpack-merge');
const webpack = require('webpack');

const HOST = process.env.HOST || 'WDAUD7210FGY.internal.vic.gov.au';
const PORT = process.env.PORT || 8090;

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, 'src'),
    output: path.join(__dirname, '../../../target/classes/static')
};

externals: {
    Config: JSON.stringify('production' === process.env.NODE_ENV ? {
        serverUrl : "http://52.65.91.200:8080"
    } : {
        serverUrl : "http://localhost:8090"
    })
};

const common = {
    entry: [
        PATHS.source
    ],
    output: {
        path: PATHS.output,
        publicPath: '',
        filename: 'bundle.js'
    },
    module: {
        rules: [{
        	test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: 'babel-loader',
            query: {
                presets: ['es2015', 'react']
              }
        }, {
            test: /\.css$/,
            loader: 'style-loader!css-loader'
        }]
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jquery: "jquery",
            "window.jQuery": "jquery",
            jQuery:"jquery"
          }), 
          new webpack.EnvironmentPlugin({
            NODE_ENV: 'development',
            DEBUG: true,
            host: HOST,
            port: PORT
          })          
    ],
    resolve: {
        extensions: ['.js', '.jsx', '.css']
    }
};

if (TARGET === 'start' || !TARGET) {
    module.exports = merge(common, {
        devServer: {
            port: 9090,
            proxy: {
                '/': {
                    target: 'http://localhost:8080',
                    secure: false,
                    prependPath: false
                }
            },
            publicPath: 'http://localhost:9090/',
            historyApiFallback: true
        },
        devtool: 'source-map'
    });
}

if (TARGET === 'build') {
    module.exports = merge(common, {});
}
