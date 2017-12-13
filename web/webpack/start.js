var webpack = require("webpack");
var WebpackDevServer = require("webpack-dev-server");
var webpackConfig = require("./webpack.dev.config.js");
var customConfig = require("./custom.config.js");
var request = require("request");


const moduleName = process.argv.pop().substring(2);

var webpackConfigDev = webpackConfig(moduleName);

var server = new WebpackDevServer(webpack(webpackConfigDev),{
    hot: true,
    watchOptions: {
        aggregateTimeout: 300,
        poll: 1000
    },
    publicPath: "/",
    profile:true,
    stats: {
        /*
         http://webpack.github.io/docs/node.js-api.html
         请自己查看配置参数
         * */
        chunkModules: false,
        chunks: false,
        children: false,
        colors: true
    }
});

server.listen(customConfig.server.port, customConfig.server.domain, function () {
    console.log("模块构建完成后，请访问：http://" + customConfig.server.domain + ":"
        + customConfig.server.port + "/模块名");
});
