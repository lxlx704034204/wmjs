/**
 * 功能描述
 */
define(function (require, exports, module) {
	var Subnav = require("subnav");
	var enmu = require("enums");
	var SubnavEvents = require("../handler/messageSubnavEvents");
	
    var instance = {
        initComponent: function (params, widget) {
        	var subnav = new Subnav({
        		parentNode:".J-subnav",
				model : {
					title:"祭奠网站新闻后台",
					items : [{ 
						id : "search",
						type : "search",						
						placeholder : "标题",
						handler : function(data){
							SubnavEvents.searchAction(data,widget);			
						}
					},{
						id : "date",
						type : "daterange",
						tip:"设置日期",
						ranges : {
							"本年": [moment().startOf("year"),moment().endOf("year")]
						},
						defaultRange : "本年",
						handler : function(time){
							widget.get("grid").refresh();
						},
					},{
						id : "status",
						type : "buttongroup",
						tip:"状态",
						all:{
							show:true,
						},
						items : [{
		                    key:"Opened",
		                    value:"启用"
						},{
							key:"Closed", 
		                    value:"停用"
						},{
							key:"Released",
							value:"发布"
						}],
						handler : function(key,text){
							widget.get("grid").refresh();
						},
					},{
						id : "add",
						text : "新增",
						type : "button",
						handler : function(){
							SubnavEvents.addAction(widget);
						}
					},{
						id : "return",
						text : "返回",
						type : "button",
						show : false,
						handler : function(){
							SubnavEvents.returnAction(widget);
						}
					}]
				}
        	});
        	widget.set("subnav",subnav);
        }
    };
    module.exports = instance;
});
