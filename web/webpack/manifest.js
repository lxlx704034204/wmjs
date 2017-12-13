/**
 * Created by xuechao on 2017/1/12.
 */
var fs = require("fs");
var path = require("path");

//检测文件或者文件夹存在 nodeJS
function fsExistsSync(path) {
    try{
        fs.accessSync(path,fs.F_OK);
    }catch(e){
        return false;
    }
    return true;
}

var DIST_PATH = "../dist/";

var manifest = function(moduleName){
    const srcPath = path.join(__dirname, "../src/"+moduleName+"/");
    fs.readdirSync(srcPath).map(function (name) {
        var iPath = path.join(srcPath, name, name+".manifest");

        if(fsExistsSync(iPath)){
            var indexContent = fs.readFileSync(iPath).toString();
            indexContent = indexContent + "\n#ts_"+new Date().getTime();
            fs.writeFileSync(path.join(__dirname, DIST_PATH + moduleName+"/"+name, name+".manifest"), indexContent);

            console.log(name+"缓存已刷新");
        }
    });
}

module.exports = manifest;
