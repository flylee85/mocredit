// 选择上级弹窗
// 依赖Page,Modal,Handlebars
cms.SelectSuperModal = function(html) {
	var _t = this;
	cms.SelectSuperModal.superclass.constructor.call(_t, html);
}

cms.extend(cms.SelectSuperModal, cms.Modal);
// 绑定事件
cms.SelectSuperModal.prototype.bindEvent = function() {
	var _t = this;
	// 关闭/取消按钮
	_t.dom.find('.modal-close').on('click', function() {
		_t.close();
	});

	// 选择
	_t.dom.find('.table').on('click', '.select', function() {
		var $t = $(this);
		var tr = $t.closest('tr');
		var name = tr.children().eq(0).text();
		var id = tr.attr('info-id');
		_t.nameBox.val(name);
		_t.idBox.val(id);
		_t.close();
	});

	// 查询
	_t.dom.find('#search').on('click',function(){
		var key = _t.dom.find('#sName').val();
		_t.page.setPage(1, {key:key}, function(data) {
			console.log(data.page);
			_t.page.setPgaeList(data.page.now, data.page.total);
		});
	});

}
// 显示弹窗
// 因为需要在显示弹窗的时候初始化分页插件，所以需要重写
cms.SelectSuperModal.prototype.open = function(page,userId){
	var _t = this;
	cms.SelectSuperModal.superclass.open.call(_t);
	// 初始化翻页
	_t.page = new cms.Page({
		url: urls.getsuper+'?userId='+userId,
		box: _t.dom.find('#page'),
		now:page.now,
		total:page.total,
		numberOfPages:3,
		sizeParam:'userId='+userId,
		isAjax:true,
		callBack:function(data){
			_t.setList(data);
		}
	});
}

// 设置信息列表
cms.SelectSuperModal.prototype.setList = function(data){
	var _t = this;
	if(!_t.listTemlate){
		_t.listTemlate = $('#select_super_list').html();
	}
	var html = Handlebars.compile(_t.listTemlate)(data);
	_t.dom.find('.listBox').html(html);
}

// 设置 选择成功后显示 上级名字和ID的input
cms.SelectSuperModal.prototype.setSuperBox = function(o) {
	var _t = this;
	_t.nameBox = o.name;
	_t.idBox = o.id;
}

// 关闭选择上级弹窗时，不关闭帷幕所以需要重写close函数
cms.SelectSuperModal.prototype.close = function() {
	var _t = this;
	_t.dom.removeClass('in');
	setTimeout(function() {
		_t.dom.remove();
	}, 400);
}

// 新增/编辑用户弹窗
cms.UserControlModal = function(html) {
	var _t = this;
	cms.UserControlModal.superclass.constructor.call(_t, html);
}

cms.extend(cms.UserControlModal, cms.Modal);

cms.UserControlModal.prototype.bindEvent = function() {
	var _t = this;
	// 关闭/取消按钮
	_t.dom.find('.modal-close').on('click', function() {
		_t.close();
	});

	var selectsuper = $('#select_super').html();
	var modal = new cms.SelectSuperModal;
	// 选择上级
	_t.dom.find('.selectsuper').on('click', function() {
		var userId = _t.dom.find('#userId').val();
		// 获取上线列表信息
		$.post(urls.getsuper,{userId:userId}, function(data) {
			var html = Handlebars.compile(selectsuper)(data);
			modal.init(html,data.page);
			// 设置显示上级名字以及记录上级的ID的input
			modal.setSuperBox({
				name: _t.dom.find('#superName'),
				id: _t.dom.find('#superId')
			});
			modal.open(data.page,userId);
		}, 'json');
	});
}



$(document).ready(function(){
	var update_edit = $("#update_edit").html();
	$('.add').on('click', function() {
			var modal = new cms.UserControlModal();
			var html = Handlebars.compile(update_edit)({
					title: '新增版本',type:'add'
				});
			// 将数据传入弹窗
				modal.init(html);
			// 显示弹窗
				modal.open();
	});
	
	// 删除楼盘
	$('table').on('click', '.del', function() {
		if(confirm("确认删除吗?")){
			var _t = $(this);
			var id = _t.closest('tr').attr('info-id');
			$.post(urls.del,{id:id},function(data){
				if (data.code === 1) {
					window.top.cms.alert.success(data.msg);
					window.location.reload(true);
				}else{
					window.top.cms.alert.warning(data.msg);
				}
			});
		}
	});
	
});

