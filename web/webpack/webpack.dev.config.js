var webpackConfig = require('./webpack.config');
var HtmlWebpack = require('./HtmlWebpack');
var propertiesLoader = require("./properties.loader.js");
var CopyWebpackPlugin = require('copy-webpack-plugin');

const TYPE = 'develop';

module.exports = ((moduleName)=>{
    var webpackConfigDev = webpackConfig(moduleName);
    webpackConfigDev.devtool = "source-map";
    webpackConfigDev.plugins = webpackConfigDev.plugins.concat(HtmlWebpack(TYPE,moduleName));
    webpackConfigDev.module.loaders = webpackConfigDev.module.loaders.concat(propertiesLoader(TYPE,moduleName));

    return webpackConfigDev;
});
