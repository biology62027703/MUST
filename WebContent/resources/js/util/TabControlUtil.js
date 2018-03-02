function TabControlUtil() {
}

TabControlUtil.prototype.pageIndex = {};

TabControlUtil.prototype.setAllPage = function (options) {
	
	$divObj = options.targetObj;
	$divObj.addClass("abgne_tab");
	$divObj.html(
			"<ul class='tabs'></ul>" +
			"<div class='tab_container'></div>"
		);
	
	$pages = options.pages;
	for(var i=0;i<$pages.length;i++) {
		tabControlUtil.addPage($divObj, $pages[i].title, $pages[i].content, $pages[i].click);
	}
	
	var $defaultLi = $('ul.tabs li', $divObj).eq(0).addClass('active');
	$($defaultLi.find('a').attr('tar'), $divObj).show();	
	tabControlUtil.setEvent($divObj);
	
	tabControlUtil.pageIndex[$divObj.attr("id")] = 0;
}

TabControlUtil.prototype.addPages = function (options) {
	
	$divObj = options.targetObj;
	$pages = options.pages;
	
	var now = $("a:last", $divObj).attr("tar").replace("#tab", "");
	for(var i=0;i<$pages.length;i++) {
		tabControlUtil.addPage($divObj, $pages[i].title, $pages[i].content, $pages[i].click);
	} 
	tabControlUtil.setEvent($divObj);
}

TabControlUtil.prototype.addPage = function ($divObj, title, content, click) {
	var now = $("a:last", $divObj).attr("tar");
	if( (typeof now)=="undefined" )
		now = 1;
	else
		now = now.replace("#tab", "")*1+1;	

	$(".tabs", $divObj).append("<li idx="+$('ul.tabs li', $divObj).length+"><a tar='#tab"+now+"'>"+title+"</a></li>");
	$tab_content = $("<div id='tab"+now+"' class='tab_content'></div>").appendTo($(".tab_container", $divObj));
		
	if( typeof(content) != "undefined" ) {
		var type = (typeof content);
		if( type=="string" ) {
			$tab_content.html(content);
		}else{
			if( content.length==1 ) {
				content.appendTo($tab_content);
			}else{
				for(var j=0;j<content.length;j++) {
					content[j].appendTo($tab_content);
				}
			}
		}
		$("a[tar=#tab"+now+"]", $divObj).parent().attr("content", "Y");
	}else{
		$("a[tar=#tab"+now+"]", $divObj).parent().attr("content", "N");
	}
	
	if( typeof(click) == "function" ) {
		$("a[tar=#tab"+now+"]", $divObj).click(function(){
			click();
		});
	}
	
	$tab_content.hide();
}

TabControlUtil.prototype.removePage = function ($divObj, title) {
	
	$("ul.tabs li:contains("+title+")", $divObj).remove();
}

TabControlUtil.prototype.showPage = function ($divObj, title) {
	
	if( typeof(title) == "string")
		$("ul.tabs li:contains("+title+")", $divObj).click();
	else{
		$("ul.tabs li[idx="+title+"]", $divObj).click();
	}
}


TabControlUtil.prototype.getPageIndex = function ($divObj) {	
	return tabControlUtil.pageIndex[$divObj.attr("id")];
}

TabControlUtil.prototype.setEvent = function ($divObj) {
	$('ul.tabs li', $divObj).unbind("click");	
	$('ul.tabs li[content=Y]', $divObj).click(function() {
		var $this = $(this);
		tabControlUtil.pageIndex[$divObj.attr("id")] = $this.attr("idx");
		var _clickTab = $this.find('a').attr('tar');
		$this.addClass('active').siblings('.active').removeClass('active');
		$(_clickTab, $divObj).stop(false, true).fadeIn().siblings().hide();
		return false;
	}).find('a').focus(function(){
		this.blur();
	});
}

var tabControlUtil = new TabControlUtil();