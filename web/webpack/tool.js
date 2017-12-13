/**
 * Created by xuechao on 2016/12/27.
 */
var path = require('path');
var glob = require('glob');

module.exports = {
    //获取文件列表
    getEntries(globPath){
        var files = glob.sync(globPath),
            entries = {};

        files.forEach(function(filepath) {
            // 取倒数第二层(view下面的文件夹)做包名
            var split = filepath.split('/');
            var name = split[split.length - 2];

            entries[name] = path.resolve(__dirname, '../' + filepath);
        });

        return entries;
    }
}