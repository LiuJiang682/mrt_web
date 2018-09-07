const path = require('path');
const merge = require('webpack-merge');
const webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

let hostParam;
let portParam;
let contextPathParam;

for(const arg of process.argv) {
    if (/^process.env.HOST/.test(arg)) {
        const envHostArray = arg.split("=");
        hostParam = envHostArray[1];
    } else if (/^process.env.PORT/.test(arg)) {
        const envPortArray = arg.split("=");
        portParam = envPortArray[1];
    } else if(/^process.env.CONTEXT_PATH/.test(arg)) {
    	const envCPArray = arg.split("=");
    	contextPathParam = envCPArray[1];
    }
}

const HOST = process.env.HOST || hostParam || 'WDAUD7210FGY.internal.vic.gov.au';
const PORT = process.env.PORT || portParam || 8090;
const CONTEXT_PATH = process.env.CONTEXT_PATH || contextPathParam || 'tloader';

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
            host: HOST,
            port: PORT,
            CONTEXT_PATH: CONTEXT_PATH
          }),
          new HtmlWebpackPlugin({
              template: 'pages/index.html',
              templateParameters: {
                  'contextPath': CONTEXT_PATH
              },
        	  hash: false,
              filename: 'index.html',
              inject: 'body'
        }),
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
