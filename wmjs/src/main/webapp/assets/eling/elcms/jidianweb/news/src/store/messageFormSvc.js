/**
 * 功能描述
 */

define(function (require, exports, module) {
    var aw = require("ajaxwrapper");

    module.exports = {
    	saveSvc : function(data,sCb){
    		aw.saveOrUpdate("api/webnews/save",data,function(data){
    			sCb(data);
			});
    	},
    };
});
