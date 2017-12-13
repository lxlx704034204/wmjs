/**
 * 功能描述
 */
define(function (require, exports, module) {
	var Form = require("form");
	var FormEvents = require("../handler/messageFormEvents");
	var activeUser = require("store").get("user");
	var classifyType = [{
        value:"通知公告",key:"Tzgg"
    },{
        value:"民政要闻",key:"Mzyw"
    },{
        value:"本地动态",key:"Bddt"
    }];
    var instance = {
        initComponent: function (params, widget) {
        	var form = new Form({
                parentNode: ".J-form",
                model: {
                    id: "form",
                    saveaction: function() {
                        var data = form.getData();
                        FormEvents.saveAction(data,widget);
                    },
                    cancelaction: function() {
                    	widget.show([".J-grid"]).hide([".J-form"]);
        				widget.get("subnav").hide(["return"]).show(["add","date","search","status"]);
                    },
                    items: [{
                        name: "pkWebNews",
                        type: "hidden"
                    },{
                    	 name: "version",
                         type: "hidden"
                    },{
	                   	 name: "creator",
	                     type: "hidden",
	                     defaultValue:activeUser.pkUser
                    },{
	                   	 name: "releasedStatus",
	                     type: "hidden",
	                     defaultValue:"UnReleased"
                    },{
	                   	 name: "seal",
	                     type: "hidden",
	                     defaultValue:false
                    },{
	                   	 name: "likeNum",
	                     type: "hidden",
	                     defaultValue:0
                    },{
	                   	 name: "shareNum",
	                     type: "hidden",
	                     defaultValue:0
                    },{
						name:"title",
						label:"标题",
						validate:["required"]
					},{
                        name:"classify",
                        label:"所属类型",
                        type:"select",
                        options:[{
                            key:"News",
                            value:"新闻中心"
                        },{
                            key:"Law",
                            value:"政策法规"
                        }],
                        validate:["required"],
                        defaultValue:"News",
                        events: {
                            "click": function(e) {
                                var newsType = form.getValue("classify");
                                if(newsType == "News"){
                                    classifyType = [{
                                        value:"通知公告",key:"Tzgg"
                                    },{
                                        value:"民政要闻",key:"Mzyw"
                                    },{
                                        value:"本地动态",key:"Bddt"
                                    }];
								}else{
                                    classifyType = [{
                                        value:"国家政策法规文件",key:"CountryPolicy"
                                    },{
                                        value:"省政策法规文件",key:"ProvincePolicy"
                                    },{
                                        value:"市政策法规文件",key:"CityPolicy"
                                    }];
								}
                                form.setData("classifyType", classifyType);
                            }
                        }
                    },{
                        name:"classifyType",
                        label:"所属条目",
                        type:"select",
                        options:classifyType,
                        validate:["required"]
                    },{
				    	name:"content",
                        label : "正文",
                        type: "richtexteditor",
                        options:{
            				initialFrameHeight:"500",  //设置富文本高度
            				elementPathEnabled : false,
            				toolbars: [['insertimage']]
            			},
                    },{
                    	name:"createDate",
						label:"设置日期",
						type:"date",
						mode:"YYYY-MM-DD HH:mm:ss",
						defaultValue:moment().valueOf(),
						readonly:true
                    },{
                    	name : "creatorName",
						label : "设置人",
						readonly : true,
						defaultValue : activeUser.name
                    }]
                }
            });
            widget.set("form", form);
        }
    };
    module.exports = instance;
});
