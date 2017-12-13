var propertiesConfig = require("./properties.config");

/**
 * 转换config数据
 * @param dataConfig
 */
function getLoaderStr(dataConfig) {
    var Arr = [];
    for (var key in dataConfig) {
        Arr.push(key + "=>'" + dataConfig[key]+"'");
    }
    return Arr.join(",");
}

module.exports = function(tesk,moduleName){
    var loaderStr = getLoaderStr(propertiesConfig[tesk+"Config"]);

    return [
        {
            test: require.resolve('../src/common/constant/serverConfig.js'),
            loader: "imports-loader?" + loaderStr
        }
    ];
};
