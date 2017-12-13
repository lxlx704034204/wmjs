var customConfig = require("./custom.config.js");

var Properties = {
    //开发环境地址，webpack开发专用
    developConfig: {
        baseUrl: customConfig.proxy.target,
        token: "XgdT0DhEEoVAJd4D10WaSlxSgFAhVeCgpY4CAkC7CgFbDwI6QwxbEQI"
    },
    //生产环境地址，非webpack开发使用，勿动
    productConfig: {
        baseUrl: customConfig.proxy.distTarget,
        token: "XgdT0DhEEoVAJd4D10WaSlxSgFAhVeCgpY4CAkC7CgFbDwI6QwxbEQI"
    }
};

module.exports = Properties;