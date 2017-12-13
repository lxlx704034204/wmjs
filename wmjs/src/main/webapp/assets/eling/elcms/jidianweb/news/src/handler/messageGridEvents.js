/**
 * 功能描述
 */

define(function (require, exports, module) {
	var GridSvc = require("../store/messageGridSvc"); 
	
    module.exports = {
		openAction : function(data,widget){
			GridSvc.openSvc(data,function(ret){
				 widget.get("grid").refresh();
			});
		},
		closeAction : function(data,widget){
			GridSvc.closeSvc(data,function(ret){
				 widget.get("grid").refresh();
			});
		},
		deleteAction : function(data,widget){
			GridSvc.deleteSvc(data,function(){
				widget.get("grid").refresh();
			});
		},
		releaseAction : function(data,widget){
			GridSvc.releaseSvc(data,function(ret){
				widget.get("grid").refresh();
			});
		},
		editAction : function(data,widget,disabled){
			widget.show([".J-form"]).hide([".J-grid"]);
			var form = widget.get("form");
			form.reset();
			form.setData(data);
			var classifyType = [{
                value:"通知公告",key:"Tzgg"
            },{
                value:"民政要闻",key:"Mzyw"
            },{
                value:"本地动态",key:"Bddt"
            }];
			if(data.classify.key=="Law"){
                classifyType = [{
                    value:"国家政策法规文件",key:"CountryPolicy"
                },{
                    value:"省政策法规文件",key:"ProvincePolicy"
                },{
                    value:"市政策法规文件",key:"CityPolicy"
                }];
			}
            form.setData("classifyType", classifyType);
            form.setValue("classifyType",data.classifyType.key);
			form.setValue("creator",data.creator.pkUser);
			form.setValue("creatorName",data.creator.name);
			form.setValue("releasedStatus",data.releasedStatus.key);
			form.setDisabled(disabled);
			if(disabled){
				widget.get("subnav").hide(["search","date","status","add"]).show(["return"]);
			}else{
				widget.get("subnav").hide(["search","date","status","add"]).show(["return"]);
			}
			
		}
    };
});
