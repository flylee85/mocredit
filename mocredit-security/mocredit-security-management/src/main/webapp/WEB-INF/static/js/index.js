// 主页面
(function($) {
	// 左导航选中状态
	$('.list-group a').on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
	});
	// iframe高度自适应
	var rigthConter = $('#right-content');
	// 获取iframe最小高度函数
	var getMinHeight = function() {
		var h = $(window).height() - $('header').outerHeight() - 20;
		rigthConter.height(h);
	}
	// 初始化iframe最小高度值
	var iframeMinHeight = getMinHeight();

	window.setIframeHeight = function() {
		// 获取iframe内html标签的高度作为iframe高度
//		var h = rigthConter.contents().find('body').height() + 20;
//		h = (h > iframeMinHeight) ? h : iframeMinHeight;
//		rigthConter.height(h);
		getMinHeight()
	}

	// 窗口大小改变时重设iframe高度
	$(window).resize(function(){
		// 重置iframe最小高度值
//		iframeMinHeight = getMinHeight();
//		// 重置iframe最小高度
//		setIframeHeight();
		getMinHeight();
	});
	// iframe加载完成时重新计算高度
//	rigthConter.on('load', setIframeHeight);

//	setTimeout(setIframeHeight);

	// 修改密码弹窗
	// 
	// 获取模板html
	var reset_password = $('#reset_password').html();
	$('.reset_password').on('click',function(){
		// 编译模板html
		var html = Handlebars.compile(reset_password)({});
		// 弹出弹窗
		var modal = new cms.UserModal();
		modal.init(html);
		modal.open();
	});

})(jQuery);