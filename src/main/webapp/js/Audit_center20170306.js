$(document).ready(function(){
	//	列表隐藏
	// $('.audit_list>.audit_list_container').hide();
	//
	// //	列表首个显示
	// $('.audit_list>.audit_list_container').first().show();
	
//	点击选项卡切换
	$('.audit_tab_control>li').click(function(){
		$(this).children('a').addClass('audit_tabcon_click');
		$(this).siblings().children('a').removeClass('audit_tabcon_click');
		// $('.audit_list_container').eq($(this).index('.audit_tab_control>li')).show().siblings().hide();
	})
})
