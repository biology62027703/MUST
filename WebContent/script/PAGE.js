
/*
 * 使用此分頁Page.js的方法
 * 
 * queryForm 變數為接收查詢條件的 Form
 * resultForm 變數為接收查詢條件的 Form
 * 
 * 分頁的動作是讓 queryForm 變更顯示頁面與顯示筆數後在重新查詢一次
 * 因此 queryForm 必須要有 pageSize 與 pageNum 欄位
 *   而   resultForm 必須要有 totalNum 欄位
 * 才有辦法產生分頁顯示的HTML字串
 * 
 * 先使用 PageUtil.setForm 設定兩個Form
 * 再使用 PageUtil.getPageInfo 就能取得分頁畫面的HTML
 * 最後讓畫面的某各ID的innerHTML=此回傳值時，畫面就會有分頁的功能了
 */

PageUtil.resultForm;
var formTarget = "";
	
function PageUtil()
{

}

PageUtil.setForm=function(result)
{
	resultForm = result;
}

PageUtil.setTarget=function(target)
{
	formTarget = target;
}

PageUtil.chgPage=function(num)
{
	top.showProcessBar();
	resultForm.pageNum.value = num;
	if("" != formTarget){
		resultForm.target = formTarget;
	}
	resultForm.submit();
}

PageUtil.chgSize=function()
{
	top.showProcessBar();
	resultForm.pageNum.value = 1;
	resultForm.pageSize.value = resultForm.size.value;
	if("" != formTarget){
		resultForm.target = formTarget;
	}
	resultForm.submit();
}

PageUtil.getPageInfo=function()
{
	if(resultForm == null)
		return "";

	var size = resultForm.pageSize.value*1;
	var num = resultForm.pageNum.value*1;
	var total = resultForm.totalNum.value*1;
	var maxnum = parseInt((total/size))+(total%size>0?1:0);
	
	var numinfo, chginfo, sizinfo;
	if( total>0 )
	{
		numinfo = "第&nbsp;"+((num-1)*size+1)+"&nbsp;~&nbsp;"+((num*size)>total ? total : (num*size) )+"&nbsp;筆　共&nbsp;"+total+"&nbsp;筆";
		chginfo = "";
		if(num==1)
		{
			chginfo += "第一頁　上一頁";
		}
		else
		{
			chginfo += "<a href='#' onclick=\"PageUtil.chgPage(1)\">第一頁</a>";
			chginfo += "　";
			chginfo += "<a href='#' onclick=\"PageUtil.chgPage("+(num*1-1)+")\">上一頁</a>";
		}
		chginfo += "　";
		if(num==maxnum)
		{
			chginfo += "下一頁　最後一頁";
		}
		else
		{
			chginfo += "<a href='#' onclick='PageUtil.chgPage("+(num*1+1)+")'>下一頁</a>";
			chginfo += "　";
			chginfo += "<a href='#' onclick='PageUtil.chgPage("+maxnum+")'>最後一頁</a>";
		}	
		var sizinfo = "每頁顯示筆數<input type='text' style='font-size:16px' name='size' size='2' maxlength='2' onKeyPress='lockNum()' value='"+size+"'><input type='button' style='font-size:16px' value='查' onclick='PageUtil.chgSize()'>";
	}
	else
	{
		numinfo = "共&nbsp;"+total+"&nbsp;筆";
		chginfo = "第一頁　上一頁　下一頁　最後一頁";
		sizinfo = "";
	}
	
	var html = "";
	html += "<table width='80%' border='0'><tr align='center'>\r\n";
	html += "<td width='25%' style='font-size:16px'>"+numinfo+"</td>\r\n";
	html += "<td width='50%' style='font-size:16px'>"+chginfo+"</td>\r\n";
	html += "<td width='25%' style='font-size:16px'>"+sizinfo+"</td>\r\n";
	html += "</tr></table>";
	
	return html;
}