/**
 * 功能描述
 */

define(function (require, exports, module) {
	var FormSvc = require("../store/messageFormSvc"); 	
	var activeUser = require("store").get("user");
	
    module.exports = {
		saveAction : function(data,widget){
        	if(!data.organization){
        		data.organization = activeUser.organization.pkOrganization;
        	}
        	data.createDate = moment().valueOf();
			FormSvc.saveSvc(data,function(ret){
				widget.get("grid").refresh();
				widget.show([".J-grid"]).hide([".J-form"]);
				widget.get("subnav").hide(["return"]).show(["add","date","search","status"]);
			});
		},
    };
});
