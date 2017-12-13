/**
 * 功能描述
 */

define(function (require, exports, module) {
	var Handlebars = require("handlebars");
	var GridProps = require("../constant/messageProps");
	var Dialog = require("dialog");
	var FormSvc = require("../store/messageFormSvc");
	var activeUser = require("store").get("user");
	var aw = require("ajaxwrapper");
	
    module.exports = {
		previewAction : function(widget){
			var that = this;
			if(!widget.get("form").valid()){
				return false;
			}
			var img = $(".J-form-form-file-coverPic img").size();
        	if(img == 0){
				Dialog.alert({
					title:"提示",
					content:"未选择图片！"
				});
				return false;
        	}
        	var data = widget.get("form").getData();
        	if(!data.organization){
        		data.organization = activeUser.organization.pkOrganization;
        	}
        	data.createDate = moment().valueOf();
        	data.fetchProperties = GridProps.gridFetchProperties;
			FormSvc.saveSvc(data,function(ret){
				var form = widget.get("form");
				form.upload("coverPic","api/attachment/webnews/webnews"+"_"+ret.pkWebNews);
				form.setData(ret);
				form.setValue("creator",ret.creator.pkUser);
				form.setValue("creatorName",ret.creator.name);
				form.setValue("releasedStatus",ret.releasedStatus.key);
				that.showPreviewDialog(ret.pkWebNews);
			});
		},
		addAction: function(widget){
			widget.get("form").reset();
			widget.get("subnav").hide(["add","date","search","status"]).show(["return"]);
			widget.hide([".J-grid"]).show([".J-form"]);
		},
		returnAction: function(widget){
			widget.get("grid").refresh();
			widget.show([".J-grid"]).hide([".J-form"]);
			widget.get("subnav").hide(["return"]).show(["add","date","search","status"]);
		},
		previewWithoutSaveAction: function(widget){
			this.showPreviewDialog(widget.get("form").getValue("pkWebNews"));
		},
		searchAction: function(str,widget){
			var grid = widget.get("grid");
			var obj={
				s:str,
				properties:"title",
				fetchProperties: GridProps.gridFetchProperties
			};
			aw.ajax({
				url:"api/webnews/search",
				data:obj,
				dataType:"json",
				success:function(data){
					grid.setData(data);
				}
			});	
		},
		showPreviewDialog:function(pkWebNews){
			Dialog.confirm({
		          title : "预览",
		          content : "<iframe id='mobilePreview' src='./assets/eling/mobile/news/index.html?pkAppNews="+pkWebNews+"' scrolling=auto width=375px height=470px></iframe>",
		          setStyle:function(){
						$(".el-dialog-modal .modal").css({
							"top":"10%",
							"width":"25%",
							"left":"35%"
						});
						$(".el-dialog-modal .modal-body").css({
							"height":"500px"
						});
		          },
		          defaultButton: false,
		          buttons: [{
		            id: 'back',
		            text: '返回',
		            className: 'btn-primary',
		            handler: function () {
		            	$("#mobilePreview").attr("src",$("#mobilePreview").attr("src"));
		            }
		          }, {
		            id: 'close',
		            text: '关闭',
		            handler: function () {
		            	Dialog.close();
		            }
		          }]
		    });
		}
    };
});
