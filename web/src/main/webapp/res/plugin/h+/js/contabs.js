$(function () {
    //计算元素集合的总宽度
    function calSumWidth(elements) {
        var width = 0;
        $(elements).each(function () {
            width += $(this).outerWidth(true);
        });
        return width;
    }
    //滚动到指定选项卡
    function scrollToTab(element) {
        var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").outerWidth() < visibleWidth) {
            scrollVal = 0;
        } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
            if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                scrollVal = marginLeftVal;
                var tabElement = element;
                while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                    scrollVal -= $(tabElement).prev().outerWidth();
                    tabElement = $(tabElement).prev();
                }
            }
        } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
            scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
        }
        $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }
    
    
    /*for(var i=1;i<25;i++){
		$("#"+i).hide();
	}
	var tourist=$.queryString("tourist");
	if(tourist==1){//游客
		var flag=$.queryString("auth");
    	var num=flag.split(",");
    	for(var i=0;i<num.length;i++){
    		$("#"+num[i]).show();
    	}
	}else if(tourist==2){//平台管理员
		var flag=$.queryString("auth");
    	var num=flag.split(",");
    	for(var i=0;i<num.length;i++){
    		$("#"+num[i]).show();
    	}
	}else if(tourist==3){//超级管理员
		for(var i=1;i<25;i++){
    		$("#"+i).show();
    	}
	}else{*/
    var logintype=$.queryString('logintype');
    var orderId=$.queryString('orderId');
		var url = '/login/loginfunctions?logintype='+logintype+"&orderId="+orderId;
		$.ajax({
			url : url,
			type : "POST",
			datatype: 'JSON', 
			success : function(data) {
				var emp = null;
				if(data !=null && data != "" && data != undefined){
					emp = JSON.parse(data);
					for(var i=0;i<emp.length;i++){
			    		$("#"+emp[i].id).show();
			    	}
					var html="";
					for(var i=0;i<emp.length;i++){
			    		if(emp[i].parentId==0){
			    			var a=0;
			    			for(var m=i;m<emp.length;m++){
			    				if(emp[m].parentId==emp[i].id){
			    					a++;
			    				}
			    			}
			    			if(a>0){
			    				html+='<li><a href="javascript:;" class="J_menuItem1" id="'+emp[i].id+'"><i class="'+emp[i].portrait+'"></i><span class="nav-label" >'+emp[i].funName+'</span><span class="fa arrow"></span></a>'
			                	html+='<ul class="nav nav-second-level">';
			    	    		for(var j=i+1;j<emp.length;j++){
			    	    			if(emp[i].id==emp[j].parentId){
			    	    				html+='<li><a class="J_menuItem" href="'+emp[j].url+'" id="'+emp[j].id+'">'+emp[j].funName+'</a></li>'
			    	    			}
			    	    		}
			    	    		html+= '</ul>';
			           			html+='</li>';
			    			}else{
			    				html+='<li><a href="'+emp[i].url+'" class="J_menuItem" id="'+emp[i].id+'"><i class="'+emp[i].portrait+'"></i><span class="nav-label" >'+emp[i].funName+'</span></a>'
			           			html+='</li>';
			    			}
			    		}
					}
					$("#side-menu").append(html);
					$('.nav-second-level').hide();
					$('.J_menuItem1').on('click', function(){
						$(this).next().toggle('100');
						//$(this).parent().siblings().find('ul').hide('100');//同级其他同胞隐藏
					});
				}
				
				//点击 左菜单栏项 触发 function
			    function menuItem() {
			    		//left menu Highlight----------------------------------
			        	if(typeof(Storage) !== "undefined") {
			        		var thisId=$(this).attr('id');
			        		if(thisId!=undefined){
			        			 sessionStorage.clickcountIdValue = thisId;
			        			 $('#'+sessionStorage.clickcountIdValue).addClass("menuHighlight").parent().siblings('li').find('a').removeClass("menuHighlight");
			        			 $('#'+sessionStorage.clickcountIdValue).parent().parent().parent().siblings('li').find('.J_menuItem').removeClass("menuHighlight");
			        		}
			        		$(this).parent().siblings().find('ul').hide('100');
			        	} else {
			        	    alert("抱歉！您的浏览器不支持 Web Storage 请联系相关技术人员！");
			        	}
			        	//end left menu Highlight------------------------------
			    	

			        // 获取标识数据
			        var dataUrl = $(this).attr('href'),//左菜单栏 对应的路径
			        	dataIndex = $(this).data('index'),//左菜单栏 下标
			        	menuName = $.trim($(this).text()),//左菜单栏 标题name
			            flag = true;
			        if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
			        
			        // 选项卡菜单已存在
			        $('.J_menuTab').each(function () {
			            if ($(this).data('id') == dataUrl){
			                if (!$(this).hasClass('active')){
			                    $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
			                    scrollToTab(this);
			                    
			                    // 显示tab对应的内容区
			                    $('.J_mainContent .J_iframe').each(function(){
			                    	if ($(this).attr('src') == dataUrl){
			                            $(this).show().siblings('.J_iframe').hide();
			                            return false;
			                        }
			                    });
			                }else{
			                	//当 点击 菜单项 tab选项卡已经有对应的标题选项卡时 重写添加选项卡对应的iframe
			                	 var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
			                     $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
			                }
			                flag = false;
			                return false;
			            }
			        });

			        // 选项卡菜单不存在
			        if (flag) {
			            var str = '<a href="javascript:;" class="active J_menuTab" data-id="'+ dataUrl + '">' 
			            	+ menuName + ' <i class="fa fa-times-circle"></i></a>';
			            $('.J_menuTab').removeClass('active');

			            // 添加选项卡对应的iframe
			            var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
			            $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
			            
			            //显示loading提示
			            /*var loading = layer.load();
			            $('.J_mainContent iframe:visible').load(function () {
			                //iframe加载完成后隐藏loading提示
			                layer.close(loading);
			            });*/
			            
			            // 添加选项卡
			            $('.J_menuTabs .page-tabs-content').append(str);
			            scrollToTab($('.J_menuTab.active'));
			        }
			        return false;
			    }
			    
			    //点击 左菜单栏项 触发
			    $('.J_menuItem').on('click', menuItem);
			    //$('.J_menuItem').click(menuItem);
			    // 关闭选项卡菜单
			    function closeTab() {
			        var closeTabId = $(this).parents('.J_menuTab').data('id');
			        var currentWidth = $(this).parents('.J_menuTab').width();
			        // 当前元素处于活动状态
			        if ($(this).parents('.J_menuTab').hasClass('active')) {
			            // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
			            if ($(this).parents('.J_menuTab').next('.J_menuTab').size()) {
			                var activeId = $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').data('id');
			                $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').addClass('active');

			                $('.J_mainContent .J_iframe').each(function () {
			                    ///if ($(this).data('id') == activeId) {
			                	if ($(this).attr('src') == activeId) {
			                        $(this).show().siblings('.J_iframe').hide();
			                        return false;
			                    }
			                });

			                var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
			                if (marginLeftVal < 0) {
			                    $('.page-tabs-content').animate({
			                        marginLeft: (marginLeftVal + currentWidth) + 'px'
			                    }, "fast");
			                }

			                //  移除当前选项卡
			                $(this).parents('.J_menuTab').remove();

			                // 移除tab对应的内容区
			                $('.J_mainContent .J_iframe').each(function (){
			                    if ($(this).data('id') == closeTabId) {
			                        $(this).remove();
			                        return false;
			                    }
			                });
			            }

			            // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
			            if ($(this).parents('.J_menuTab').prev('.J_menuTab').size()) {
			                var activeId = $(this).parents('.J_menuTab').prev('.J_menuTab:last').data('id');
			                $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass('active');
			                $('.J_mainContent .J_iframe').each(function () {
			                    ///if ($(this).data('id') == activeId) {
			                	if ($(this).attr('src') == activeId) {
			                        $(this).show().siblings('.J_iframe').hide();
			                        return false;
			                    }
			                });

			                //  移除当前选项卡
			                $(this).parents('.J_menuTab').remove();

			                // 移除tab对应的内容区
			                $('.J_mainContent .J_iframe').each(function () {
			                    if ($(this).data('id') == closeTabId) {
			                        $(this).remove();
			                        return false;
			                    }
			                });
			            }
			        }
			        // 当前元素不处于活动状态
			        else {
			            //  移除当前选项卡
			            $(this).parents('.J_menuTab').remove();
			            // 移除相应tab对应的内容区
			            $('.J_mainContent .J_iframe').each(function () {
			                if ($(this).data('id') == closeTabId) {
			                    $(this).remove();
			                    return false;
			                }
			            });
			            scrollToTab($('.J_menuTab.active'));
			        }
			        return false;
			    }

			    $('.J_menuTabs').on('click', '.J_menuTab i', closeTab);

			    //关闭其他选项卡
			    function closeOtherTabs(){
			        $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function () {
			            $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
			            $(this).remove();
			        });
			        $('.page-tabs-content').css("margin-left", "0");
			    }
			    $('.J_tabCloseOther').on('click', closeOtherTabs);

			    //滚动到已激活的选项卡
			    function showActiveTab(){
			        scrollToTab($('.J_menuTab.active'));
			    }
			    $('.J_tabShowActive').on('click', showActiveTab);


			    // 点击选项卡菜单
			    function activeTab() {
			    	
			        if (!$(this).hasClass('active')) {
			        	
			            var currentId = $(this).data('id');//获取 tab的路径
			            // 显示tab对应的内容区
			            $('.J_mainContent .J_iframe').each(function () {
			            	///console.log($(this).data('id'));
			                /*if ($(this).data('id') == currentId){
			                    $(this).show().siblings('.J_iframe').hide();
			                    return false;
			                }*/
			            	if($(this).data('id')=="home.html"){
			            		if (currentId=="order/america.html"){
			                        $(this).show().siblings('.J_iframe').hide();
			                        return false;
			                    }
			            	}else{
			            		if ($(this).data('id') == currentId){
			                        $(this).show().siblings('.J_iframe').hide();
			                        return false;
			                    }
			            	}
			            });
			            $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
			            scrollToTab(this);
			            
			            //左菜单栏和Tab选项卡对应 效果————————————————————————————
			            var TabText=$(this).text().trim();
			            //console.log(TabText);
			            $('.J_menuItem').each(function(){
			            	var menuText=$(this).text();
			            	//console.log("___________"+menuText);
			            	if(TabText==menuText && !$(this).hasClass('menuHighlight')){
			            		$(this).addClass("menuHighlight");//对应的Menu添加高亮
			            		$(this).parent().siblings().find('a').removeClass('menuHighlight');//同二级的兄弟 删除高亮
			            		$(this).parents('ul.nav-second-level').show('300');//显示对应的二级展开
			            		$(this).parent().parent().parent().siblings().find('ul').hide('300');//隐藏其他一级下的二级内容项
			            		$(this).parent().parent().parent().siblings().find('.J_menuItem').removeClass('menuHighlight');
			            		$(this).parent().siblings().find('ul').hide('300');//一级menu高亮时，把所以二级隐藏
			            	}
			            });
			            //end 左菜单栏和Tab选项卡对应 效果———————————————————————————— 
			        }
			    }
			    
			    
			    
			    //页面 顶部tab 点击事件
			    $('.J_menuTabs').on('click', '.J_menuTab', activeTab);

			    //刷新iframe
			    function refreshTab() {
			        var target = $('.J_iframe[data-id="' + $(this).data('id') + '"]');
			        /*var url = target.attr('src');
			        //显示loading提示
			        var loading = layer.load();
			        target.attr('src', url).load(function () {
			            //关闭loading提示
			            layer.close(loading);
			        });*/
			    }

			    $('.J_menuTabs').on('dblclick', '.J_menuTab', refreshTab);

			    // 左移按扭
			    $('.J_tabLeft').on('click', scrollTabLeft);

			    // 右移按扭
			    $('.J_tabRight').on('click', scrollTabRight);

			    // 关闭全部
			    $('.J_tabCloseAll').on('click', function () {
			        $('.page-tabs-content').children("[data-id]").not(":first").each(function (){
			        	$('.J_iframe[src="' + $(this).data('id') + '"]').remove();
			            $(this).remove();
			        });
			        $('.page-tabs-content').children("[data-id]:first").each(function () {
			        	$('.J_iframe[src="' + $(this).data('id') + '"]').show();
			            $(this).addClass("active");
			        });
			        $('.page-tabs-content').css("margin-left", "0");
			    });
			    
			  //查看左侧隐藏的选项卡
			    function scrollTabLeft() {
			        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
			        // 可视区域非tab宽度
			        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
			        //可视区域tab宽度
			        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
			        //实际滚动宽度
			        var scrollVal = 0;
			        if ($(".page-tabs-content").width() < visibleWidth) {
			            return false;
			        } else {
			            var tabElement = $(".J_menuTab:first");
			            var offsetVal = 0;
			            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
			                offsetVal += $(tabElement).outerWidth(true);
			                tabElement = $(tabElement).next();
			            }
			            offsetVal = 0;
			            if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
			                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
			                    offsetVal += $(tabElement).outerWidth(true);
			                    tabElement = $(tabElement).prev();
			                }
			                scrollVal = calSumWidth($(tabElement).prevAll());
			            }
			        }
			        $('.page-tabs-content').animate({
			            marginLeft: 0 - scrollVal + 'px'
			        }, "fast");
			    }
			    //查看右侧隐藏的选项卡
			    function scrollTabRight() {
			        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
			        // 可视区域非tab宽度
			        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
			        //可视区域tab宽度
			        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
			        //实际滚动宽度
			        var scrollVal = 0;
			        if ($(".page-tabs-content").width() < visibleWidth) {
			            return false;
			        } else {
			            var tabElement = $(".J_menuTab:first");
			            var offsetVal = 0;
			            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
			                offsetVal += $(tabElement).outerWidth(true);
			                tabElement = $(tabElement).next();
			            }
			            offsetVal = 0;
			            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
			                offsetVal += $(tabElement).outerWidth(true);
			                tabElement = $(tabElement).next();
			            }
			            scrollVal = calSumWidth($(tabElement).prevAll());
			            if (scrollVal > 0) {
			                $('.page-tabs-content').animate({
			                    marginLeft: 0 - scrollVal + 'px'
			                }, "fast");
			            }
			        }
			    }
			    //通过遍历给菜单项加上data-index属性
			    $(".J_menuItem").each(function (index) {
			        if (!$(this).attr('data-index')) {
			            $(this).attr('data-index', index);
			        }
			    });
			},
			error : function(request) {
				layer.msg('获取失败');
			}
		});
    	//公司管理员登录
    	/*var empList=$.queryString("empList");
    	var emp=JSON.parse(JSON.parse($.base64.atob(decodeURI(empList),true)));
    	
    	for(var i=0;i<emp.length;i++){
    		$("#"+emp[i].id).show();
    	}
    	var html="";
    	for(var i=0;i<emp.length;i++){
    		if(emp[i].parentId==0){
    			var a=0;
    			for(var m=i;m<emp.length;m++){
    				if(emp[m].parentId==emp[i].id){
    					a++;
    				}
    			}
    			if(a>0){
    				html+='<li><a href="javascript:;" class="J_menuItem1" id="'+emp[i].id+'"><i class="fa fa-building"></i><span class="nav-label" >'+emp[i].funName+'</span><span class="fa arrow"></span></a>'
                	html+='<ul class="nav nav-second-level">';
    	    		for(var j=i+1;j<emp.length;j++){
    	    			if(emp[i].id==emp[j].parentId){
    	    				html+='<li><a class="J_menuItem" href="'+emp[j].url+'" id="'+emp[j].id+'">'+emp[j].funName+'</a></li>'
    	                   ///console.log("第"+j+"次:"+html);
    	    			}
    	    		}
    	    		html+= '</ul>';
           			html+='</li>';
    			}else{
    				html+='<li><a href="'+emp[i].url+'" class="J_menuItem" id="'+emp[i].id+'"><span class="nav-label" >'+emp[i].funName+'</span></a>'
           			html+='</li>';
    			}
    		}
    	}
		$("#side-menu").append(html);*/
	//}
	//}
});
