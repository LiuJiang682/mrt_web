const path = require('path');
const merge = require('webpack-merge');
const webpack = require('webpack');
console.log(process.env.HOST);
const HOST = process.env.HOST || 'WDAUD7210FGY.internal.vic.gov.au';
const PORT = process.env.PORT || 8090;
console.log(process.env.HOST);
console.log(HOST);
const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, 'src'),
    output: path.join(__dirname, '../../../target/classes/static')
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
            NODE_ENV: 'production',
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
                    target: 'http://' + HOST + ':' + PORT,
                    secure: false,
                    prependPath: false
                }
            },
            publicPath: 'http://' + HOST + ':' + PORT,
            historyApiFallback: true
        },
        devtool: 'source-map'
    });
}

if (TARGET === 'build') {
    module.exports = merge(common, {});
}
