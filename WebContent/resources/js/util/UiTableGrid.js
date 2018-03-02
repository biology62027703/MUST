
/**
 * @description 此共用元件為動態產生表格
 * @class 動態表格產生工具
 * @author chkchk
 */
function UiTableGrid() {
}

UiTableGrid.prototype.getTableGridOption = function() {
	
	return {
		formObj : null						//查詢條件的form，此設定與分頁顯示有關
		, gridObj : null					//指定TABLE的物件
		, tableTitle : "查詢結果"				//TABLE的TITLE
		, tableTitleButtonInnerText : ""	//於TABLE最上方又編的案有(此為innerHTML)
		, columnMeta : []					//TABLE內容欄位的定義，見 getColumnMetaOptions()
		, data : {}							//資料集
		, pageInfo : null					//分頁資訊
		, autocolor : true					//滑鼠在tr上方時是否顏色會變
		, trclick: null
		, nodatamsg: "查無資料"				//無資料時的顯示訊息
		, queryFunction: null
		, isPrintMode: false				//是否為列印模式
		, jsonData: null					//後端送回來的物件
		, datamapping: null
		, trclassmap: null					//trclass的對硬表(將不會有onmoveover的event)
		, trattrpattern: ""
		, holdhead: false
	};
};

UiTableGrid.prototype.getCount = function(gridObj) {
	$tableGrid_data = $("#tableGrid_data", gridObj);
	return $("#trdata", $tableGrid_data).length / $tableGrid_data.attr("columnMetaLength");
}



UiTableGrid.prototype.render = function(options) {
	
	options = $.extend({}, uiTableGrid.getTableGridOption(), options);

	if( options.jsonData != null ) {		
		if( options.jsonData.data != null && typeof(options.jsonData.data)=="object" && typeof(options.data.length)=="undefined" )
			options.data = options.jsonData.data;		
		if( options.jsonData.pageInfo != null && typeof(options.jsonData.data)=="object" && options.pageInfo==null )
			options.pageInfo = options.jsonData.pageInfo;
	}
	
	if( !options.isPrintMode )
		options.gridObj.html(
					"<tr><td><table id='tableGrid_data' width='100%' bordercolor='#418282'></table></td></tr>" +
					"<tr><td><table id='tableGrid_pageinfo' width='100%' cellspacing='0' cellpadding='5'></table></td></tr>"
				);
	else
		options.gridObj.html(
				"<tr><td><table id='tableGrid_data' width='100%' border='1' cellspacing='0' cellpadding='5'></table></td></tr>" +
				"<tr><td><table id='tableGrid_pageinfo' width='100%' cellspacing='0' cellpadding='5'></table></td></tr>"
			);
	
	$tableGrid_data = $("#tableGrid_data", options.gridObj);
	$tableGrid_pageinfo = $("#tableGrid_pageinfo", options.gridObj);
	
	uiTableGrid.setData(
			$tableGrid_data, 
			options.tableTitle, 
			options.tableTitleButtonInnerText,
			options.columnMeta, 
			options.data, 
			options.pageInfo,
			options.autocolor,
			options.trclick,
			options.nodatamsg,
			options.isPrintMode,
			options.datamapping,
			options.trclassmap,
			options.trattrpattern,
			options.queryFunction , 
			options.formObj
		);
	
	if( options.pageInfo!=null ) {		
		uiTableGrid.setPageInfo(
				$tableGrid_pageinfo, 
				options.pageInfo, 
				options.queryFunction, 
				options.formObj
			);
		$tableGrid_pageinfo.css("display", "").attr("display", "");
	}else{
		$tableGrid_pageinfo.css("display", "none").attr("display", "none");
	}
	
	
	if( options.holdhead ) { 
		
		if( options.gridObj.attr("name")=="")
			options.gridObj.attr("name", options.gridObj.attr("id"));
		if( options.gridObj.attr("id")=="")
			options.gridObj.attr("id", options.gridObj.attr("name"));
		
		var divid = "div" + options.gridObj.attr("id");
		if( $("div[id="+divid+"]").length>0 )
			$("div[id="+divid+"]").remove();		
		trhtml = "";
		for(var i=0;i<$("tr.head", options.gridObj).length;i++) {
			trhtml += $("tr.head:eq("+i+")", options.gridObj)[0].outerHTML;
		} 

		$div = $("<div id='"+divid+"' targettableid='"+options.gridObj.attr("id")+"'></div>")
				.css("position", "absolute")
				.css("left", uiTableGrid.findPosX($("tr.head", options.gridObj).parent()[0]))
				.html("<table>"+trhtml+"</table>")
				.appendTo($("body"))
				.hide();
				
		/*
		$("div[id="+divid+"] td").each(function(i){
			$(this).attr("idx", i);
		}).click(function(){
			targettableid = $(this).parent().parent().parent().parent().attr("targettableid");
			$("table[id="+targettableid+"] tr.head td:eq("+$(this).attr("idx")+")").click();
		})
		*/
		
		$(window).scroll(function(){
			
			$("div[targettableid]").each(function(){
				
				targettableid = $(this).attr("targettableid");				
				trhead = $("table[id="+targettableid+"] tr.head"); 
				$(this).css("left", uiTableGrid.findPosX(trhead.parent()[0]))
				$("table", $(this)).attr("width", trhead[0].offsetWidth+"px");				
				
				if( document.body.scrollTop > uiTableGrid.findPosY(trhead[0]) ) {
					if( uiTableGrid.findPosY(trhead.parent()[0])+trhead.parent()[0].offsetHeight>document.body.scrollTop)
						$(this).css("top", document.body.scrollTop).show();
					else
						$(this).hide();
				}else{
					$(this).hide();
				}
			})			
			
		});	
	}	
	
};

UiTableGrid.prototype.findPosY = function(obj) {
	  var curtop = 0;  
	  if(obj.offsetParent)  
	      while(1)  
	      {  
	        curtop += obj.offsetTop;  
	        if(!obj.offsetParent)  
	          break;  
	        obj = obj.offsetParent;  
	      }  
	  else if(obj.y)  
	      curtop += obj.y;  
	  return curtop;  
}

UiTableGrid.prototype.findPosX = function(obj) {
	   var curleft = 0;  
	   if(obj.offsetParent)  
	       while(1)   
	       {  
	         curleft += obj.offsetLeft;  
	         if(!obj.offsetParent)  
	           break;  
	         obj = obj.offsetParent;  
	       }  
	   else if(obj.x)  
	       curleft += obj.x;  
	   return curleft;  
}

UiTableGrid.prototype.hindData = function(gridObj) {
	
	$tableGrid_data = $("#tableGrid_data", gridObj);
	$tableGrid_pageinfo = $("#tableGrid_pageinfo", gridObj);	
	$("tr[class='head']", $tableGrid_data).hide();	
	$("tr[class='tr-1']", $tableGrid_data).hide();	
	$("tr[class='tr-2']", $tableGrid_data).hide();
	
	if( $tableGrid_pageinfo.attr("display")=="" )
		$tableGrid_pageinfo.hide();
}

UiTableGrid.prototype.showData = function(gridObj) {
	
	$tableGrid_data = $("#tableGrid_data", gridObj);
	$tableGrid_pageinfo = $("#tableGrid_pageinfo", gridObj);
	$("tr[class='head']", $tableGrid_data).show();	
	$("tr[class='tr-1']", $tableGrid_data).show();	
	$("tr[class='tr-2']", $tableGrid_data).show();
	
	if( $tableGrid_pageinfo.attr("display")=="" )
		$tableGrid_pageinfo.show();
	
}

UiTableGrid.prototype.getColumnMetaOptions = function() {
	var options = {
			  title: ""						//欄位title名稱
			, index: null					//直接讓TD顯示該欄位的資料  (特殊設定　_SNO：總筆數的第幾筆，_RNO：頁面的第幾筆　)
			, textpattern: ""				//依據特定格式顯示TD的資料 
			, titleAttr: ""					//title行的style
			, dataAttr: ""					//data行的style
			, tag: ""						//特殊顯示，可設定 a, span, checkbox, radio 四類
			, valuepattern: ""				//td內checkbox or radio的value
			, click: null	  				//tag為 a, span 時，點及該欄位所觸發的事件(將會傳入該筆資料的json格式內容)
			, id: ""						//tag為 checkbox, radio 時物件的name與id屬性
			, tdid: null					//那個td欄位的id
			, dataFormat: null				//資料的顯示格式
			, image: null					//設定功能圖示，也會執行該功能的 doXxx method
			, colspan: null					//多行資料時使用
			, rowspan: null					//多行資料時使用
			, datamapping: null
			, order: null
		};
	
	return options;
	//註：設定權限　index > tag > textpattern
}

UiTableGrid.prototype.iniColumnMetaOptions = function(columnMeta) {
	var Column = $.extend({}, uiTableGrid.getColumnMetaOptions(), columnMeta);
	if( Column.image!=null ) {
		if( Column.titleAttr.toLowerCase().indexOf("width=")==-1 )
			Column.titleAttr += " width='50'";
		if( Column.dataAttr.toLowerCase().indexOf("align=")==-1 )
			Column.dataAttr += " align='center'";
	}
	if( Column.colspan!=null ) {
		if( Column.titleAttr.toLowerCase().indexOf("colspan=")==-1 )
			Column.titleAttr += " colspan='"+Column.colspan+"'";
		if( Column.dataAttr.toLowerCase().indexOf("colspan=")==-1 )
			Column.dataAttr += " colspan='"+Column.colspan+"'";
	}
	if( Column.rowspan!=null ) {
		if( Column.titleAttr.toLowerCase().indexOf("rowspan=")==-1 )
			Column.titleAttr += " rowspan='"+Column.rowspan+"'";
		if( Column.dataAttr.toLowerCase().indexOf("rowspan=")==-1 )
			Column.dataAttr += " rowspan='"+Column.rowspan+"'";
	}
	return Column;
}

UiTableGrid.prototype.appendHead = function($head, columnMeta, queryFunction, formObj) {
	
	var Column = uiTableGrid.iniColumnMetaOptions(columnMeta);

	$td = $("");
	if( Column.tag=="checkbox" && Column.title=="") {
		$td = $("<td "+Column.titleAttr+" nowrap='nowrap'><input type='checkbox' id='"+Column.id+"__'></td>").appendTo($head);
		
		$("input[id="+Column.id+"__]").click(function(){
			var id = $(this).attr("id").replace("__", "");
			$table = $(this).parent().parent().parent();
			if( $(this).attr("checked")=="checked" ){
				$("input[type=checkbox][id="+id+"]", $table).each(function(){
					$(this)[0].checked = true;
					$(this).click();
					$(this)[0].checked = true;
				});
			}else{
				$("input[type=checkbox][id="+id+"]", $table).each(function(){
					$(this)[0].checked = false;
					$(this).click();
					$(this)[0].checked = false;
				});
			}
		});
	}else{
		$td = $("<td "+Column.titleAttr+" nowrap='nowrap'>"+Column.title+"</td>").appendTo($head);
		if( Column.tdid!=null )
			$td.attr("id", Column.tdid);
	}
	
	
	if( typeof(Column.order)=="string" && typeof(queryFunction)=="function") {
		
		$td.attr("order", Column.order);
		
		var orderby = typeof($(":hidden[name=_ORDER_BY]", formObj).val())=="undefined" ? "" : $(":hidden[name=_ORDER_BY]", formObj).val();
		if( orderby.indexOf(",")>-1 )
			orderby = orderby.substring(0, orderby.indexOf(","));
		if( orderby == Column.order ) {
			$td.addClass("tablesorterheaderAsc");
		}else if( orderby == (Column.order + " DESC") ) {
			$td.addClass("tablesorterheaderDesc");
		}else{
			$td.addClass("tablesorterheader");
		}
		
		$td.click(function(){
			if( typeof($(":hidden[name=_ORDER_BY]", formObj).val())=="undefined" ) {
				formObj.append("<input type='hidden' name='_ORDER_BY'>");
			}
			var orderby = $(":hidden[name=_ORDER_BY]", formObj).val();
			var clickfield = $(this).attr("order");
			if( orderby.indexOf(clickfield)==-1 ) {
				orderby = clickfield + (orderby==""?"":",") +orderby;
			}else{
				orderby = ","+orderby+",";				
				asc = orderby.indexOf(","+clickfield+",");
				desc= orderby.indexOf(","+clickfield+" DESC,");				
				if( asc>-1 ) {
					orderby = orderby.replace(","+clickfield+",", ",");
					orderby = "," + clickfield + (asc==0? " DESC" : "") + orderby;
				}else{
					orderby = orderby.replace(","+clickfield+" DESC,", ",");
					orderby = "," + clickfield + orderby;
				}
				orderby = orderby.substring(1, orderby.length-1);
			}			
			$(":hidden[name=_ORDER_BY]", formObj).val(orderby);
			queryFunction();
		})
	}
}

UiTableGrid.prototype.appendTr = function($tableGrid_data, datamapping, columnMeta, data, trclick, i, s, trattrpattern) {
	
	$tr = $("<tr id='trdata' height='31' sel='"+i+"' "+strUtil.tranPattern(trattrpattern, data)+"></tr>").appendTo($tableGrid_data);
	uiTableGrid.appendData($tr, datamapping, columnMeta, data, i, s);
	
	$tr.data("json", data);	
	if( typeof(trclick)=="function" ) {
		$tr.data("clk", trclick);
		$tr.click(function(){
			$(this).data("clk")($(this).data("json"));
		});
	}
	
}

UiTableGrid.prototype.appendData = function($tr, datamapping, columnMeta, data, i, sno) {
	
	for(var j=0;j<columnMeta.length;j++) {		
		var Column = uiTableGrid.iniColumnMetaOptions(columnMeta[j]);
					
		if( Column.index != null ) {
			if( Column.index=="_SNO") {
				text = sno+"";
			}else if( Column.index=="_RNO") {
				text = (i+1)+""; 
			}else {
				text = data[Column.index];
								
				if( typeof(Column.datamapping)=="string" ) {
					if( typeof(datamapping[Column.datamapping][text])=="string" )
						text = datamapping[Column.datamapping][text];
					else if( typeof(datamapping[Column.datamapping]["_other"])=="string" )
						text = datamapping[Column.datamapping]["_other"];
				}
				
				if( typeof(text)=="string" && typeof(Column.dataFormat)=="string" ) {
					Column.dataFormat = Column.dataFormat.toLowerCase();					
					text = getFormatStr(text, Column.dataFormat);
				}
			}
		} else if( Column.image != null ){				
			if( Column.image.indexOf("[")==0 && Column.image.indexOf("]")==(Column.image.length-1) ) {
				Column.index = Column.image.substring(1, Column.image.length-1);
				if( typeof(data[Column.index])=="string" )
					Column.image = data[Column.index];
			}
			Column.image = Column.image.toLowerCase();
			if( Column.image!= "" ) {
				text = $("<img style='cursor:hand' src='"+formUtil.getWebRoot()+"image/"+Column.image+".gif' nm='"+Column.image+"'>");
				try {
					Column.click = eval("do"+Column.image.substring(0,1).toUpperCase() + Column.image.substring(1));
				} catch(e) {}
				if( typeof(Column.click)=="function" ) {
					text.data("clk", Column.click);
					text.click(function(){
						$(this).data("clk")($(this).parent().parent().data("json"));
					});
				}
			}
		} else {
			Column.textpattern = Column.textpattern.replace("@{_SNO}", sno);
			Column.textpattern = Column.textpattern.replace("@{_RNO}", i);
			text = strUtil.tranPattern(Column.textpattern, data);
			if( Column.tag.toLowerCase() == "a" || Column.tag.toLowerCase() == "span" ) {
				text = $("<"+Column.tag.toLowerCase()+">"+text+"</"+Column.tag.toLowerCase()+">");
				if( Column.click != null ) {
					if( typeof(Column.click)=="string" ) {
						try {
							Column.click = eval(Column.click);
						} catch(e) {}
					}
					if( typeof(Column.click)=="function" ) {
						text.data("clk", Column.click);
						text.click(function(){
							$(this).data("clk")($(this).parent().parent().data("json"));
						});
					}
				}
			}else if( Column.tag.toLowerCase() == "checkbox" ) {
				text = $("<input type='checkbox' name='"+Column.id+"' id='"+Column.id+"'>").attr("value", strUtil.tranPattern(Column.valuepattern, data));
			}else if( Column.tag.toLowerCase() == "radio" ) {
				text = $("<input type='radio' name='"+Column.id+"' id='"+Column.id+"'>").attr("value", strUtil.tranPattern(Column.valuepattern, data));
			}
		}
		
		if( text=="" ) text = "&nbsp;";
		if( Column.tag != "" && Column.dataAttr.toLowerCase().indexOf("align") == -1 ) {
			Column.dataAttr += " align='center'";
		}
		
		$td = $("<td "+Column.dataAttr+"></td>").appendTo($tr).append(text);
		if( Column.tdid!=null )
			$td.attr("id", Column.tdid);
	}
	
}

UiTableGrid.prototype.getTrClassMapOptions = function() {
	var options = {
			  field: ""		
			, classname: null
		};	
	return options;
}

UiTableGrid.prototype.setData = function($head, tableTitle, tableTitleButtonInnerText, columnMeta, data, pageInfo, autocolor, trclick, nodatamsg, isPrintMode, datamapping, trclassmap, trattrpattern, queryFunction, formObj) {
	
	
	var columnMetaLength = ( typeof(columnMeta[0][0])=="undefined" ? 1 : columnMeta.length) ;
	$tableGrid_data.attr("columnMetaLength", columnMetaLength);
	if( typeof(columnMeta[0][0])=="undefined" ) {
		if( !isPrintMode )
			$head = $("<tr class='head' height='31'></tr>").appendTo($tableGrid_data);
		else
			$head = $("<tr height='31'></tr>").appendTo($tableGrid_data);	
		for(var i=0;i<columnMeta.length;i++) {
			uiTableGrid.appendHead($head, columnMeta[i], queryFunction, formObj);
		}
	}else{
		for(var j=0;j<columnMeta.length;j++) {
			if( !isPrintMode )
				$head = $("<tr class='head' height='31'></tr>").appendTo($tableGrid_data);
			else
				$head = $("<tr height='31'></tr>").appendTo($tableGrid_data);	
			for(var i=0;i<columnMeta[j].length;i++) {
				uiTableGrid.appendHead($head, columnMeta[j][i], queryFunction, formObj);
			}
		}
	}
	
	var s=1;
	if( pageInfo!=null && pageInfo.pageNum!=null && pageInfo.pageNum > 0 )
		s = (pageInfo.pageNum-1)*pageInfo.pageSize+1;
	
	var text = "";
	var value = "";
	for(var i=0;i<data.length;i++) {		
		if( typeof(columnMeta[0][0])=="undefined" ) {
			uiTableGrid.appendTr($tableGrid_data, datamapping, columnMeta, data[i], trclick, i, s, trattrpattern);
		}else{
			for(var j=0;j<columnMeta.length;j++) {
				uiTableGrid.appendTr($tableGrid_data, datamapping, columnMeta[j], data[i], trclick, i, s, trattrpattern);
			}
		}
		s++;
	}	

	if( !isPrintMode ) {
		if( $("#trdata", $tableGrid_data).length>0 ) { 

			var trclassmap = $.extend({}, uiTableGrid.getTrClassMapOptions(), trclassmap);
			$("#trdata", $tableGrid_data).each(function(i){
				if( trclassmap.classname==null || trclassmap.field=="") {
					if(Math.floor(i/columnMetaLength)%2==0 )
						$(this).addClass("tr-1");
					else
						$(this).addClass("tr-2");
				}else{
					json = $(this).data("json");
					try{
						$(this).addClass(trclassmap.classname[json[trclassmap.field]]);
					}catch(e){}
				}
			})
			
			if( trclassmap.classname==null || trclassmap.field=="") {
				if( autocolor ) {
					$("#trdata", $tableGrid_data).mouseover(function(){
						$("#trdata[sel="+$(this).attr("sel")+"]", $(this).parent()).addClass("trcolor");
					});
					$("#trdata", $tableGrid_data).mouseout(function(){
						$("#trdata[sel="+$(this).attr("sel")+"]", $(this).parent()).removeClass("trcolor");
					});
				}
			}
		}else{
			if(nodatamsg!="") {
				$tableGrid_data.append("<tr class='tr-1' height='31'><td colspan='"+getTrLength($("tr[class=head]:eq(0)", $tableGrid_data))+"' align='center'>"+nodatamsg+"</td></tr>");
			}
		}
		
		$tr1 = $("<tr><td colspan='"+getTrLength($("tr[class=head]:eq(0)", $tableGrid_data))+"'><table id='grid_title_table' border='0' width='100%' cellspacing='0' cellpadding='1'><tr class='title' height='31'><td align='left'>"+tableTitle+"</td><td align='right'>"+tableTitleButtonInnerText+"</td></tr></table></td></tr>")
		$tr1.insertBefore($("tr[class=head]:eq(0)", $tableGrid_data));
		
		function getTrLength($tr) {
			var l = 0;
			$("td", $tr).each(function(){
				if( typeof($(this).attr("colspan"))=="undefined" )
					l += 1*1;
				else
					l += $(this).attr("colspan")*1;
			});
			return l;
		}
	}
}

UiTableGrid.prototype.setPageInfo = function($tableGrid_pageinfo, pageInfo, queryFunction, formObj) {
	
	var pn = pageInfo.pageNum;
	var ps = pageInfo.pageSize;
	var tot = pageInfo.totalNum;
	
	if( pn==0 || pn=="" || ps==0 || ps=="")
		return;
	
	var s = (pn-1)*ps+1;
	var e = (pn)*ps;
	var m = parseInt((tot/ps))+(tot%ps>0?1:0);

	if( e>tot )
		e = tot;	
	
	$tableGrid_pageinfo.html(
			"<tr align='center'>" +
			"	<td width='33%'> " +
			"		<input id='__pageNum' type='text' size='3' maxlength='3' style='width:30' value='"+pn+"'><input type='button' id='__query2' namr='__query2' style='font-size:16px' value='頁'>/"+m+"頁&nbsp;" + 
			"		共 "+tot+" 筆</td>" +
			"	<td width='33%' id='__pageInfo'>" +
			"		<input type='hidden' id='__nowPageNum' name='__nowPageNum' value='"+$("input[name=pageNum]", formObj).val()+"'>" +
			"		<a>第一頁</a>　<a>上一頁</a>　<a>下一頁</a>　<a>最後一頁<a>" +
			"	</td>" +
			"	<td width='33%'>" +
			"		每頁顯示筆數<input id='__pageSize' type='text' size='3' maxlength='3' style='width:30' value='"+ps+"'>" +
			"		<input type='button' id='__query' namr='__query' style='font-size:16px' value='查'>" +
			"	</td>" +
			"</tr>"
		); 
		 
	$("#__pageSize", $tableGrid_pageinfo).keypress(function(){
		if (event.keyCode==13) {
			$("#__query", $tableGrid_pageinfo).click();
		}else{
			if ((event.keyCode < 48) || (event.keyCode > 58)){
				if (event.keyCode!=45)
					event.keyCode = 0;
			}
		}
	});				
	
	$("#__pageNum", $tableGrid_pageinfo).keypress(function(){
		if (event.keyCode==13) {
			$("#__query2", $tableGrid_pageinfo).click();
		}else{
			if ((event.keyCode < 48) || (event.keyCode > 58)){
				if (event.keyCode!=45)
					event.keyCode = 0;
			}
		}
	});				
		
	$("#__query", $tableGrid_pageinfo).click(function(){				
		var newSize = $("#__pageSize", $tableGrid_pageinfo).val();	
		var newNum = $("#__pageNum", $tableGrid_pageinfo).val();
		if( !isNaN(newSize) ) {
			$("input[name=pageNum]", formObj).val(newNum);
			$("input[name=pageSize]", formObj).val(newSize);
			queryFunction();
		}
	});
		
	$("#__query2", $tableGrid_pageinfo).click(function(){				
		var newSize = $("#__pageSize", $tableGrid_pageinfo).val();	
		var newNum = $("#__pageNum", $tableGrid_pageinfo).val();
		if( !isNaN(newSize) ) {
			$("input[name=pageNum]", formObj).val(newNum);
			$("input[name=pageSize]", formObj).val(newSize);
			queryFunction();
		}
	});
	
	if( m>1 ) {		
		if( pn==1 ) {
			$("<span>上一頁</span>").insertBefore($("a:contains(上一頁)", $tableGrid_pageinfo));
			$("a:contains(上一頁)", $tableGrid_pageinfo).remove();
		}
		if( pn==m ) {
			$("<span>下一頁</span>").insertBefore($("a:contains(下一頁)", $tableGrid_pageinfo));
			$("a:contains(下一頁)", $tableGrid_pageinfo).remove();
		}
		
	}else{
		$("<span>下一頁</span>").insertBefore($("a:contains(下一頁)", $tableGrid_pageinfo));
		$("a:contains(下一頁)", $tableGrid_pageinfo).remove();
		$("<span>上一頁</span>").insertBefore($("a:contains(上一頁)", $tableGrid_pageinfo));
		$("a:contains(上一頁)", $tableGrid_pageinfo).remove();
	}
	$("a:contains(第一頁)", $tableGrid_pageinfo).click(function(){
		$("input[name=pageNum]", formObj).val("1");
		queryFunction();
	}).css("cursor", "hand");
	$("a:contains(上一頁)", $tableGrid_pageinfo).click(function(){
		$("input[name=pageNum]", formObj).val($("input[name=__nowPageNum]", $(this).parent()).val()*1-1);
		queryFunction();
	}).css("cursor", "hand");
	$("a:contains(下一頁)", $tableGrid_pageinfo).click(function(){
		$("input[name=pageNum]", formObj).val($("input[name=__nowPageNum]", $(this).parent()).val()*1+1);
		queryFunction();
	}).css("cursor", "hand");
	$("a:contains(最後一頁)", $tableGrid_pageinfo).click(function(){
		$("input[name=pageNum]", formObj).val(m);
		queryFunction();
	}).css("cursor", "hand");
}

UiTableGrid.prototype.jsonToString = function(data) {
	var json="";	
	for (var attrName in data) {
		json += (json==''?'':',') + '"'+attrName+'":"'+data[attrName]+'"'; 
	}
	json = "{"+json+"}";
	return json;
}

UiTableGrid.prototype.tranPattern = function(pattern, para) {
	
	while( getKey(pattern) != "" ){
		var Key = getKey(pattern);	
		pattern = pattern.replace("@{"+Key+"}", para[Key]);
	}
		
	function getKey(pattern){
		var k = "";
		if( pattern.indexOf("@{") > -1 ) {
			k = pattern;
			k = k.substr(k.indexOf("@{")+2);
			if( k.indexOf("}") > -1)
				k = k.substr(0, k.indexOf("}"))
			else 
				k = "";
		}
		return k;
	}
	
	return pattern;	
}

var uiTableGrid = new UiTableGrid();