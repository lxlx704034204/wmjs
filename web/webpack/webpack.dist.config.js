var webpackConfig = require('./webpack.config');
var HtmlWebpack = require('./HtmlWebpack');
var propertiesLoader = require("./properties.loader.js");
var CopyWebpackPlugin = require("copy-webpack-plugin");

const TYPE = 'product';

module.exports = ((moduleName)=>{
    var webpackConfigDist = webpackConfig(moduleName);
    webpackConfigDist.devtool = "source-map";
    webpackConfigDist.plugins = webpackConfigDist.plugins.concat(HtmlWebpack(TYPE,moduleName));
    webpackConfigDist.module.loaders = webpackConfigDist.module.loaders.concat(propertiesLoader(TYPE,moduleName));

    //if(moduleName == "mobile"){
        var copyPlugin = new CopyWebpackPlugin([
            {
                from: '../src/'+moduleName+'/**/*.manifest',
                to: '[name]/[name].manifest',
                force:true
            },
        ]);

        webpackConfigDist.plugins.push(copyPlugin);
    //}

    return webpackConfigDist;
});