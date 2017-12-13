import "whatwg-fetch";
import "es6-promise";
import 'fetch-detector';
import {TOKEN} from "../constant/serverConfig";

const ELFetch = {
    getWhatwgFetch: function(){
        return fetch;
    },
    fetch: function(url,cParams){
        let defParams = {
            credentials: "include"
        };
        Object.assign(defParams,cParams);

        let cUrl = url;
        if(cUrl.indexOf("?") !== -1){
            if(cUrl.lastIndexOf("&")==(cUrl.length-1)){
                cUrl += "_=" + new Date().getTime();
            }else{
                cUrl += "&_=" + new Date().getTime();
            }

        }else{
            cUrl += "?_=" + new Date().getTime();
        }

        if(TOKEN){
            cUrl += "&token="+TOKEN;
        }
        return fetch(cUrl,defParams).then(function(response){
            return response;
        }).catch(function(ex) {
            console.log(url + ":" + ex);
        });
    },
    save: function(url,cParams){
        let defParams = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            credentials: "include"
        };
        Object.assign(defParams,cParams);

        return fetch(url,defParams).then(function(response){
            return response.json();
        }).catch(function(ex) {
            //不可修复的异常
            console.log(url + ":" + ex);
        });
    },

    convertJson2Url: function(jsonObject){
        let ret = "";
        for(var i in jsonObject){
            ret += (i + "=" + ((jsonObject[i]===undefined ||  jsonObject[i]===null) ? "" : jsonObject[i]) + "&");
        }
        return ret.substring(0,ret.length-1);
    },

    getQueryString(name){
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
        var context = "";
        if (r != null)
            context = r[2];
        reg = null;
        r = null;
        return context == null || context == "" || context == "undefined" ? "" : context;
    },
    //提交数据格式化方法
    customParam(jsonObject, parentKey){
        var ret = "";
        if (typeof jsonObject === "object" && jsonObject.constructor === Array) {
            for (var a = 0; a < jsonObject.length; a++) {
                ret += jsonObject[a].name + "=" + jsonObject[a].value + "&";
            }
            return ret;
        }
        if (typeof jsonObject === "object") {
            for (var i in jsonObject) {
                var value = jsonObject[i];
                if (value === 0) {
                    value = "0";
                }
                if (value === false) {
                    value = "false";
                }
                if (value && typeof value == 'object' && value.constructor == Array) {
                    //数组
                    var temp = parentKey ? parentKey + "." + i : i;
                    for (var j = 0; j < value.length; j++) {
                        if (typeof value[j] === "object") {
                            ret += ELFetch.customParam(value[j], i + "[" + j + "]");
                        } else {
                            ret += ELFetch.customParam(value[j], temp);
                        }
                    }
                } else if (value && typeof value == 'object') {
                    //对象
                    var temp = parentKey ? parentKey + "." + i : i;
                    ret += ELFetch.customParam(value, temp);
                } else if (value) {
                    //普通类型
                    var temp = parentKey ? parentKey + "." + i : i;
                    ret += temp + "=" + value + "&";
                } else {
                    var temp = parentKey ? parentKey + "." + i : i;
                    ret += temp + "=" + "" + "&";
                }
            }
            return ret;
        } else if (!jsonObject && !parentKey) {
            return "";
        } else {
            return parentKey + "=" + jsonObject + "&";
        }
    }
};

module.exports = ELFetch;
