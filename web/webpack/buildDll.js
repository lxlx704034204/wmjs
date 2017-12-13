/**
 * Created by xuechao on 2017/4/20.
 */
var webpack = require("webpack");
var webpackDllConfig = require("./webpack.dll.config");

webpack(webpackDllConfig,function(err,stats){
    if(!err){
        console.log(stats.toString({
            /*
             http://webpack.github.io/docs/node.js-api.html
             请自己查看配置参数
             * */
            colors: true
        }));
    }
});