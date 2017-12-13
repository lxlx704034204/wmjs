/**
 * 功能描述
 */
define(function (require, exports, module) {
	var Grid = require("grid");
	var GridProps = require("../constant/messageProps");
	var GridEvents = require("../handler/messageGridEvents");
	var activeUser = require("store").get("user");

    var instance = {
        initComponent: function (params, widget) {
        	var component = new Grid({
                parentNode: ".J-grid", 
                url : "api/webnews/query",
                params :function(){
                	var subnav = widget.get("subnav");
                	var date = subnav.getValue("date");
                	var status = subnav.getValue("status");
                	var obj = {
            			createDate : date.start,
                		createDateEnd : date.end,
                        organization: activeUser.organization.pkOrganization,
                		orderString : "createDate:desc",
                		fetchProperties : GridProps.gridFetchProperties
                	}
                	if(status == "Opened"){
                		obj["seal"] = false; 
                	}else if(status == "Closed"){
                		obj["seal"] = true; 
                	}else if(status == "Released"){
                		obj["releasedStatus"] = "Released"; 
                	}
                	return obj;
                },
                model : {
                    columns : [{
        				name:"title",
        				label:"标题",
        				format:"detail",
	    				formatparams:{
							key:"title",
	                        handler:function(index,data,rowEle){
	                        	GridEvents.editAction(data,widget,true);
	                        }
	                   }
        			},{
                        name:"classify.value",
                        label:"新闻类型",
                    },{
        				name:"releaseDate",
        				label:"发布日期",
        				format:"date",
						formatparams:{
							mode:"YYYY-MM-DD HH:mm"
						}
					},{
						name:"createDate",
						label:"设置日期",
						format:"date",
						formatparams:{
							mode:"YYYY-MM-DD HH:mm"
						}
					},{						
						name:"creator.name",
        				label:"设置人",
					},{
						name:"status",
						label:"状态",
						format:function(value,row){
							if(row.seal){
								return "停用"
							}else{
								if(row.releasedStatus.key == "UnReleased"){
									return "启用"
								}else{
									return "发布"
								}
							}
						}
					},{
                        name : "operate",
                        label : "操作",
                        format : "button",
                        formatparams : [{
							id:"open",
							text:"启用",
							show:function(value,row){
								if(activeUser.organization.pkOrganization != row.organization.pkOrganization){
									return false;
								}
								if(row.seal){
									return true;
								}else{
									return false;
								}
							},
							handler:function(index,data,rowEle){
								GridEvents.openAction(data,widget);
							}
						},{
							id:"close",
							text:"停用",
							show:function(value,row){
								if(activeUser.organization.pkOrganization != row.organization.pkOrganization){
									return false;
								}
								if(row.releasedStatus.key == "Released" || !row.seal){
									return true;
								}else{
									return false;
								}
							},
							handler:function(index,data,rowEle){
								GridEvents.closeAction(data,widget);
							}
						},{
							id:"release",
							text:"发布",
							show:function(value,row){
								if(activeUser.organization.pkOrganization != row.organization.pkOrganization){
									return false;
								}
								if(row.releasedStatus.key == "UnReleased" && !row.seal){
									return true;
								}else{
									return false;
								}
							},
							handler:function(index,data,rowEle){
								GridEvents.releaseAction(data,widget);
							}
						},{
                            id : "edit",
                            icon: "icon-edit",
                            show:function(value,row){
                            	if(activeUser.organization.pkOrganization != row.organization.pkOrganization){
									return false;
								}
								if(row.releasedStatus.key == "UnReleased" && !row.seal){
									return true;
								}else{
									return false;
								}
							},
                            handler : function(index,data,rowEL){
                            	GridEvents.editAction(data,widget,false);
                            }
                        },{
                        	key:"delete",
							icon:"icon-remove",
							show:function(value,row){
								if(activeUser.organization.pkOrganization != row.organization.pkOrganization){
									return false;
								}
								if(row.releasedStatus.key == "Released"){
									return false;
								}else{
									return true;
								}
							},
                            handler : function(index,data,rowEL){
                            	GridEvents.deleteAction(data,widget);
 							}
                        }]
                           				
					}]
                }
            });
            widget.set("grid",component);
        }
    };
    module.exports = instance;
});
