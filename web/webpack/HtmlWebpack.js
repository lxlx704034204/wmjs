/**
 * Created by xuechao on 2016/12/27.
 */
var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var Tool = require('./tool');

module.exports = (type,moduleName)=>{

    // 获取指定路径下的入口文件
    var entries = Tool.getEntries('src/'+moduleName+'/**/index.jsx');

    var config = require('../src/'+moduleName+'/scriptConfig.json');

    var hash = false;
    if(type == 'develop'){
        hash = false;
    }else if(type == 'product'){
        hash = true;
    }

    var htmlArr = [];
    Object.keys(entries).forEach(function(name) {

        var iConfig = config[name];
        var scriptPath = [];
        if(iConfig && iConfig["path"]){
            scriptPath = iConfig["path"];
        }

        // 每个页面生成一个html
        var plugin = new HtmlWebpackPlugin({
            // 生成出来的html文件名
            filename: name + '/index.html',
            // 每个html的模版，这里多个页面使用同一个模版
            template: path.resolve(__dirname, '../src/'+moduleName+'/template/index.html'),
            // 自动将引用插入html
            inject: true,
            hash: hash,
            // 每个html引用的js模块，也可以在这里加上vendor等公用模块
            chunks: [name],
            scriptPath:scriptPath.join(""),
            manifest: '../'+name+'/'+name+'.manifest'
        });
        htmlArr.push(plugin);
    });
    return htmlArr;
};