/**
 * 功能描述:APP消息
 */
define(function (require, exports, module) {
    //引入视图基类
    var ELView = require("elview");

    //assets
    var template = require("./assets/tpl/message_main.tpl");
    require("./assets/css/message_main.css");

    //business

    //component
    var gridComp = require("./component/messageGridComp");
    var subnavComp = require("./component/messageSubnavComp");
    var formComp = require("./component/messageFormComp");
    
    //constant

    //handler

    //store

    var App = ELView.extend({
        attrs: {
            template: template
        },
        events: {
        	"click #edui9_body":function(e){
        		setTimeout(function(){$("iframe").contents().find("#centerAlign").click()},500);
        	}
        },
        initComponent: function (params, widget) {
        	subnavComp.initComponent(params, widget);
        	gridComp.initComponent(params, widget);
        	formComp.initComponent(params, widget);
        },
        afterInitComponent: function (params, widget) {
            
        }
    });

    module.exports = App;
});
