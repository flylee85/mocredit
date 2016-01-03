var cms = {};

// 继承实现
cms.extend = function(subclass, superclass) {
	var F = function() {
	};
	F.prototype = superclass.prototype;
	subclass.prototype = new F();
	subclass.prototype.constructor = subclass;

	subclass.superclass = superclass.prototype;
	if (superclass.prototype.constructor = Object.prototype.constructor) {
		superclass.prototype.constructor = superclass;
	}
}

// 翻页功能集合
cms.Page = function(options) {
	var _t = this;
	var defaults = {
		url : '', // 翻页请求链接
		callBack : $.noop, // 翻页请求成功后的回调函数
		box : null, // 翻页外框
		now : 1, // 当前页
		size : 10,// 每页显示条数
		total : 1, // 总页数
		sendData : {}, // 与首次请求发送的数据
		isAjax : false, // 是否使用ajax请求页面
		numberOfPages : 5
	// 页码数
	}
	var o = $.extend(_t, defaults, options);
	_t.getDom();
}

// 翻页
cms.Page.prototype.setPage = function(page, data, cb) {
	var _t = this;
	var sendData = {
		currentpage : page,
	};
	// 自定义翻页的其他数据。
	// 主要用于搜索时的翻页
	if (data) {
		for ( var key in data) {
			sendData[key] = data[key];
		}
	}

	// 记录上次发送的数据
	// 在有关键字的情况下。关键字在第一次传入后就可以不必继续传入
	for (key in sendData) {
		_t.sendData[key] = sendData[key];
	}
	// 页面无刷新请求
	if (_t.isAjax) {
		$.post(_t.url, _t.sendData, function(d) {
			// 用户数据接收
			_t.callBack(d);
			// 对象内部数据接收
			// 用于在不点击翻页数字的时候
			// 重置翻页列表
			cb && cb(d);
		}, 'json');
	} else { // 页面直接跳转
		var link = _t.url + $.param(_t.sendData);
		// 跳转
		window.location.href = link;
	}
}

// html模板
cms.Page.prototype.getDom = function() {
	var _t = this;
	var str = '';
	str += '<div class="col-xs-12 form-group">';
	str += '<div class="input-group pull-left mr10" style="width:130px;">';
	str += '<span class="input-group-addon">分页条数:</span>';
	str += '<select style="width:70px;" class="changeSize form-control">';
	str += '<option value="10">10</option>';
	str += '<option value="20">20</option>';
	str += '<option value="30">30</option>';
	str += '<option value="40">40</option>';
	str += '<option value="50">50</option>';
	str += '</select>';
	str += '</div>';
	str += '<div class="pull-left"><ul class="pagination"></ul></div>';
	str += '<div class="input-group pagination-jump">';
	str += '<input type="text" class="form-control">';
	str += '<span class="input-group-btn">';
	str += '<button class="btn btn-default jumpPage" type="button">跳转</button>';
	str += '</span>';
	str += '</div>';
	str += '</div>';

	_t.dom = $(str);
	// 初始化每页条数显示
	_t.dom.find('.changeSize').val(_t.size);
	// 分页条数
	_t.changePageSize();
	// 跳转
	_t.jumpPage();
	// 设置页码
	_t.setPgaeList(_t.now, _t.total);

	_t.dom.appendTo(_t.box);
}

// 跳转
cms.Page.prototype.jumpPage = function() {
	var _t = this;
	var btn = _t.dom.find('.jumpPage');
	btn.on('click', function() {
		var pageNum = btn.closest('.pagination-jump').find('.form-control');
		// 对用户输入的页码作验证
		var num = parseInt(pageNum.val());
		if (isNaN(num) || num > _t.total) {
			alert('页码必须为数字,且不能超过最大页数');
			return false;
		}
		if (num <= 0) {
			alert('页码必须为正数');
			return false;
		}
		// 执行跳转
		_t.setPage(num, null, function(data) {
			_t.setPgaeList(data.page.now, data.page.total);
		});
	});
}

// 改变分页条数
cms.Page.prototype.changePageSize = function() {
	var _t = this;
	var btn = _t.dom.find('.changeSize');
	var url = _t.u
	btn.on('change', function() {
		_t.url = _t.url.split('?')[0] + "?";
		if (_t.sizeParam) {
			_t.url += _t.sizeParam + "&";
		}
		_t.setPage(1, {
			pageSize : btn.val()
		}, function(data) {
			_t.setPgaeList(data.page.now, data.page.total);
		});
	});
}

// 通过前端分页类实现分页
// 依赖于 boostrap pagination
cms.Page.prototype.setPgaeList = function(now, total) {
	var _t = this;

	var pageConfig = {
		currentPage : now,
		totalPages : total,
		numberOfPages : _t.numberOfPages,
		pageUrl : function(type, page, current) {
			return 'javascript:void(0);';
		},
		onPageClicked : function(event, originalEvent, type, page) {
			_t.setPage(page);
		}
	};
	_t.dom.find('.pagination').bootstrapPaginator(pageConfig);
}

/**
 * 帷幕控制
 */
cms.modalBackdrop = {
	// 创建帷幕
	dom : $('<div class="modal-backdrop fade"></div>'),
	// 开启帷幕
	open : function() {
		var _t = this;
		$('body').append(_t.dom);
		_t.dom.addClass('in');
	},
	// 关闭帷幕
	close : function() {
		var _t = this;
		_t.dom.removeClass('in');
		setTimeout(function() {
			_t.dom.remove();
		}, 400);
	}
}

/**
 * 弹窗母类
 * 
 * @param {String}
 *            html 弹窗的html
 */
cms.Modal = function(html) {
	var _t = this;
	if (html) {
		_t.dom = $(html);
	} else {
		_t.dom = $({});
	}
};
// 显示弹窗
cms.Modal.prototype.open = function() {
	var body = $('body');
	var _t = this;
	body.append(_t.dom);
	body.css({
		'overflow-y' : 'hidden'
	});
	_t.dom.show();
	setTimeout(function() {
		// 进入动画
		_t.dom.addClass('in');
		// 帷幕开启
		cms.modalBackdrop.open();
	}, 100);
};
// 关闭弹窗
cms.Modal.prototype.close = function() {
	var _t = this;
	var body = $('body');

	body.css({
		'overflow-y' : 'auto'
	});

	// 退出动画
	_t.dom.removeClass('in');
	// 帷幕关闭
	cms.modalBackdrop.close();
	// 删除元素
	setTimeout(function() {
		_t.dom.remove();
	}, 400);
};
// 事件绑定方法
cms.Modal.prototype.bindEvent = function() {
};
// 重设弹窗
cms.Modal.prototype.init = function(html) {
	var _t = this;
	// init方法为初始化弹窗的方法，所以必须传入html代码否则报错
	if (html) {
		_t.dom = $(html);
	} else {
		throw '请传入弹窗html代码';
	}
	_t.bindEvent();
}

/**
 * 用户编辑/添加弹窗
 * 
 * @requires cms.Modal
 * @param {String}
 *            html 弹窗的html
 */
cms.UserModal = function(html) {
	var _t = this;
	cms.UserModal.superclass.constructor.call(_t, html);
};

// 继承弹窗母类
cms.extend(cms.UserModal, cms.Modal);

// 绑定事件
cms.UserModal.prototype.bindEvent = function() {
	var _t = this;
	// 关闭/取消按钮
	_t.dom.find('.modal-close').on('click', function() {
		_t.close();
	});
};

// 选项卡
cms.tab = function(tag, content) {
	var tags = tag.children();
	var contents = content.children();
	tags.on('click', function() {
		var _t = $(this);
		var index = _t.index();
		_t.addClass('active').siblings().removeClass("active");
		contents.eq(index).show().siblings().hide();
	});
};

// 修改职业顾问弹窗
cms.ConsultantModal = function(html, data) {
	var _t = this;
	_t.data = data;
	cms.ConsultantModal.superclass.constructor.call(_t, html);
};

cms.extend(cms.ConsultantModal, cms.Modal);

// 绑定事件
cms.ConsultantModal.prototype.bindEvent = function() {
	var _t = this;
	var userId = _t.dom.find('#userId').val();

	// 翻页配置项
	var pageConfig = {
		now : _t.data.page.now,
		total : _t.data.page.total,
		box : _t.dom.find('#page'),
		url : urls.consultantlist,
		numberOfPages : 3,
		isAjax : true,
		callBack : function(data) {
			_t._resetList(data);
		}
	};

	// 翻页
	_t.page = new cms.Page(pageConfig);

	// 关闭/取消按钮
	_t.dom.find('.modal-close').on('click', function() {
		_t.close();
	});

	// 选择
	_t.dom.find('table').on('click', '.select', function() {
		var infoId = $(this).attr('info-id');
		$.post(urls.selectConsultant, {
			customerids : userId,
			userid : infoId
		}, function(data) {
			// 修改成功,则关闭弹出窗
			if (data.code === 1) {
				window.top.cms.alert.success(data.msg);
				_t.close();
				window.location.reload(true);
			} else {
				window.top.cms.alert.warning(data.msg);
			}
		}, 'json');
	});

	// 查询
	_t.dom.find('#search').on('click', function() {
		// 收集搜索的数据
		var name = _t.dom.find('#sName').val();
		var idCard = _t.dom.find("#sIdCard").val();
		// 获取数据
		// 搜索始终查询第一页
		_t.page.setPage(1, {
			name : name,
			idCard : idCard
		}, function(data) {
			_t.page.setPgaeList(data.page.now, data.page.total);
		});
	});
};

// 重设表格列表
cms.ConsultantModal.prototype._resetList = function(data) {
	var _t = this;
	var str = '';
	var start = data.startsize + 1;
	data = data.list;
	for (var i = 0, l = data.length; i < l; i++) {
		str += '<tr>';
		str += '<td>';
		str += '<div style="width:80px;" title="' + data[i].uname
				+ '" class="text-overflow">';
		str += '' + data[i].uname + '';
		str += '</div>';
		str += '</td>';
		str += '<td>' + data[i].name + '</td>';
		str += '<td>' + data[i].sex + '</td>';
		str += '<td>';
		str += '<div style="width:100px;" title="' + data[i].idCard
				+ '" class="text-overflow">';
		str += '' + data[i].idCard + '';
		str += '</div>';
		str += '</td>';
		str += '<td>' + data[i].phone + '</td>';
		str += '<td>' + data[i].status + '</td>';
		str += '<td><a href="javascript:;" class="select" info-id="'
				+ data[i].id + '">选择</a></td>';
		str += '</tr>';
	}
	_t.dom.find('table>tbody').html(str);
};

cms.ConsultantModal.prototype.init = function(html, data) {

	var _t = this;
	// init方法为初始化弹窗的方法，所以必须传入html代码否则报错
	if (html) {
		_t.dom = $(html);
	} else {
		throw '请传入弹窗html代码';
	}
	if (data)
		_t.data = data;
	_t.bindEvent();
};

cms.alert = {
	warning : function(text) {
		text = text || "数据返回错误";
		var tmp = '<div class="alert alert-danger fade">' + text + '</div>';
		var ele = $(tmp);
		ele.appendTo(window.top.document.body);
		setTimeout(function() {
			ele.addClass('in');
		}, 1);
		setTimeout(function() {
			ele.removeClass('in');
		}, 3000);
		setTimeout(function() {
			ele.remove();
		}, 4000);
	},
	success : function(text) {
		text = text || "数据返回错误";
		var tmp = '<div class="alert alert-info fade">' + text + '</div>';
		var ele = $(tmp);
		ele.appendTo(window.top.document.body);
		setTimeout(function() {
			ele.addClass('in');
		}, 1);
		setTimeout(function() {
			ele.removeClass('in');
		}, 3000);
		setTimeout(function() {
			ele.remove();
		}, 4000);
	}
};
// 验证
cms.yz = {
	// 验证是否通过
	pass : function(obj, ispass) {
		if (ispass) {
			obj.attr('yzpass', true);
			return true;
		} else {
			obj.attr('yzpass', false);
			return false;
		}
	},
	// 用于验证的方法
	method : {
		// 数字范围验证
		number : function(obj) {
			var _t = obj;
			var max = parseInt(_t.attr('max'));
			var min = parseInt(_t.attr('min'));
			var val = parseInt(_t.val());
			if (val != _t.val()) {
				return cms.yz.pass(_t, false);
			}
			return cms.yz.pass(_t, (min <= val && val <= max));
		},
		float : function(obj) {
			var _t = obj;
			var max = parseFloat(_t.attr('max'));
			var min = parseFloat(_t.attr('min'));
			var val = parseFloat(_t.val());
			if (val != _t.val()) {
				return cms.yz.pass(_t, false);
			}
			// 两位小数和整数都能通过

			if (!(/^-?\d+$/.test(_t.val()))
					&& !(/^-?\d+\.\d{2}$/.test(_t.val()))) {
				return cms.yz.pass(_t, false);
			}
			return cms.yz.pass(_t, (min <= val && val <= max));
		},
		checkboxRquire : function(obj) {
			var _t = obj;
			if (_t.find("input:checked").length === 0) {
				return cms.yz.pass(_t, false);
			} else {
				return cms.yz.pass(_t, true);
			}
		},
		// 邮箱验证
		email : function(obj) {
			var _t = obj;
			var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
			var val = $.trim(_t.val());
			var bool = true;
			if (val != '') {
				bool = reg.test(val);
			} else {
				bool = false;
			}
			// 验证是否通过
			return cms.yz.pass(_t, bool);
		},
		// 长度验证
		charlength : function(obj) {
			var _t = obj;
			var maxl = _t.attr('max') || 999;
			var minl = _t.attr('min') || 0;
			var len = $.trim(_t.val()).length;
			// 验证是否通过
			return cms.yz.pass(_t, (len <= maxl && len >= minl));
		},
		// 必填验证
		required : function(obj) {
			var _t = obj;
			var val = $.trim(_t.val());
			// 验证是否通过
			return cms.yz.pass(_t, (val !== ''));
		},
		// 电话
		phone : function(obj) {
			var _t = obj;
			// 座机验证
			var regtel = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
			// 手机验证
			var regphone = /^1\d{10}$/;
			var val = $.trim(_t.val());
			return cms.yz.pass(_t, (regtel.test(val) || regphone.test(val)));
		},
		repwd : function(obj, obj2) {
			var _t = obj;
			var tVal = _t.val();
			var pwdVal = obj2.val();
			return cms.yz.pass(_t, tVal === pwdVal);
		}
	},
	// 提交
	submit : function(obj) {
		var _t = obj;

		// 取得需要验证的class
		var needPassBoxClass = (function(obj) {
			var arr = [];
			for ( var key in obj) {
				arr.push(key);
			}
			return arr;
		})(cms.yz.method);

		// 取得className
		var needPassBox = (function(classList) {
			var arr = [];
			for (var i = 0, l = classList.length; i < l; i++) {
				arr.push('.' + classList[i]);
			}
			return arr.join(',');
		})(needPassBoxClass);

		/*
		 * var noString = /^([\u4e00-\u9fa5-_a-zA-Z0-9]*)$/; var noStringNum =
		 * 0;
		 * _t.find("input[type='text'],input[type='hidden'],textarea").each(function(){
		 * var $t = $(this); if(!($t.hasClass('email') || $t.hasClass('pwd') ||
		 * $t.hasClass('repwd'))){ var bool = ($t.val() != '' &&
		 * !noString.test($t.val())); console.log(!noString.test($t.val()));
		 * if(bool){ alert("不能输入特殊字符"); ++noStringNum; return false; } } });
		 * if(noStringNum){ return false; }
		 */
		// 根据className 进行不同的验证
		_t.find(needPassBox).each(function() {
			var $t = $(this);
			for (var i = 0, l = needPassBoxClass.length; i < l; i++) {
				var className = needPassBoxClass[i];
				if ($t.hasClass(className)) {
					if (className === 'repwd') {
						cms.yz.method[className]($t, $('.pwd'));
					} else {
						cms.yz.method[className]($t);
					}
				}
			}
		});

		// 获取验证不通过的字段
		// 弹出第一个不通过的字段的提示文字
		var notPass = _t.find('[yzpass="false"]');
		if (notPass.length != 0) {
			var prompt = notPass.eq(0).attr('prompt');
			prompt && window.top.cms.alert.warning(prompt);
			return false;
		} else {
			return true;
		}
	}
};
// 统一验证
;
(function($) {
	// 表单提交
	$('body').on(
			'submit',
			'form',
			function() {
				var _t = $(this);
				var modal = _t.closest('.modal');
				var modalcheck = _t.closest('.modalcheck');
				if ((modal.length == 0 && modalcheck.length == 0)
						|| modal.hasClass('dontAjax')) {
					return cms.yz.submit(_t);
				} else {
					if (cms.yz.submit(_t)) {
						$("#backsecondsubmit").attr("disabled","disabled");
						var data = _t.serialize();
						var url = _t.attr('action');
						var jqXHR =$.post(url, data, function(_d) {
							if (_d.code === 1) {
								if (_d.msg) {
									window.top.cms.alert.success(_d.msg);
								}
								setTimeout(function() {
									window.location.reload(true);
								}, 1100);
								modal.find('.modal-close').click();
							} else {
								window.top.cms.alert.warning(_d.msg);
							}
						});
						jqXHR.error(function(){
							if(518==jqXHR.status){//异步请求 没有权限
								window.top.cms.alert.warning('您没有对应访问权限,请联系管理员');
							}
						});
					}
					return false;
				}
			});
})(jQuery);

