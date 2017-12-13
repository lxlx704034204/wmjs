/**
 * 功能描述
 */

define(function (require, exports, module) {
    var aw = require("ajaxwrapper");

    module.exports = {
    	openSvc : function(data,sCb){
    		aw.saveOrUpdate("api/webnews/"+data.pkWebNews+"/open",data,function(data){
    			sCb(data);
			});
    	},
    	closeSvc : function(data,sCb){
    		aw.saveOrUpdate("api/webnews/"+data.pkWebNews+"/close",data,function(data){
    			sCb(data);
			});
    	},
    	deleteSvc : function(data,sCb){
    		aw.del("api/webnews/"+data.pkWebNews+"/delete",function() {
    			sCb();
			});	
    	},
    	releaseSvc : function(data,sCb){
    		aw.saveOrUpdate("api/webnews/"+data.pkWebNews+"/release",data,function(data) {
    			sCb(data);
			});	
    	}
    };
});
