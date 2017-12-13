/**
 * Created by xuechao on 2016/12/11.
 */
var webpack = require("webpack");
var webpackDistConfig = require("./webpack.dist.config");
var manifest = require("./manifest");

const moduleName = process.argv.pop().substring(2);

function build(){
    webpack(webpackDistConfig(moduleName),function(){
        manifest(moduleName);
        console.log("项目构建完成");
    });
}
build();
