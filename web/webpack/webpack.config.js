/**
 * Created by xuechao on 2016/12/9.
 */
var webpack = require('webpack');
var path = require('path');
var ExtractTextPlugin = require('extract-text-webpack-plugin'); //css单独打包
var HtmlWebpack = require('./HtmlWebpack');
var Tool = require('./tool');
var os = require('os');

module.exports = function(moduleName) {

  var DIST_PATH = "dist/" + moduleName + "/";

  console.log(path.resolve(__dirname, '../' + DIST_PATH));

  // 获取指定路径下的入口文件
  var entries = Tool.getEntries('src/' + moduleName + '/**/index.jsx');

  var entry = Object.assign({}, {
    common: ["react", 'react-dom', 'react-router', 'whatwg-fetch', 'react-tap-event-plugin']
  }, entries);

  return {
    entry: entry,
    output: {
      path: path.resolve(__dirname, '../' + DIST_PATH),
      publicPath: "../",
      filename: '[name]/[name].js',
      chunkFilename: "[name].js"
    },
    module: {
      loaders: [

        {
          test: /\.jsx?$/,
          exclude: /(node_modules)/,
          loader: 'babel-loader',
          query: {
            presets: [
              'react', 'es2015',"stage-1"
            ],
            plugins: [
              [
                "import", {
                  libraryName: "antd",
                  style: "css"
                }
              ] // `style: true` 会加载 less 文件
            ]
          }
        }, {
          test: /\.less$/,
          loader: ExtractTextPlugin.extract('style', ['css', 'less'])
        }, {
          test: /\.css$/,
          loader: 'style-loader!css-loader?localIdentName=[name]_[local]_[hash:base64:5]'
          // [path][name]_[local]_[hash:base64:5]也可以
        }, {
          test: /\.(jpe?g|png|eot|woff|svg|ttf|woff2|gif|ico)$/i,
          exclude: /^node_modules$/,
          //注意后面那个limit的参数，当你图片大小小于这个限制的时候，会自动启用base64编码图片
          loader: 'url?limit=10000&name=assets/img/[hash:8].[ext]'
        }
      ]
    },
    plugins: [
      new ExtractTextPlugin('[name]/[name].css'),
      new webpack.optimize.UglifyJsPlugin({
        output: {
          comments: false
        },
        compress: {
          warnings: false
        }
      }),
      new webpack.DefinePlugin({
        "process.env": {
          NODE_ENV: JSON.stringify("production")
        }
      }),
      new webpack.optimize.CommonsChunkPlugin("common", "base.js")
    ]
  };
};
