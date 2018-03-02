
/*
 * �ϥΦ�����Page.js����k
 * 
 * queryForm �ܼƬ������d�߱��� Form
 * resultForm �ܼƬ������d�߱��� Form
 * 
 * �������ʧ@�O�� queryForm �ܧ���ܭ����P��ܵ��ƫ�b���s�d�ߤ@��
 * �]�� queryForm �����n�� pageSize �P pageNum ���
 *   ��   resultForm �����n�� totalNum ���
 * �~����k���ͤ�����ܪ�HTML�r��
 * 
 * ���ϥ� PageUtil.setForm �]�w���Form
 * �A�ϥ� PageUtil.getPageInfo �N����o�����e����HTML
 * �̫����e�����Y�UID��innerHTML=���^�ǭȮɡA�e���N�|���������\��F
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
		numinfo = "��&nbsp;"+((num-1)*size+1)+"&nbsp;~&nbsp;"+((num*size)>total ? total : (num*size) )+"&nbsp;���@�@&nbsp;"+total+"&nbsp;��";
		chginfo = "";
		if(num==1)
		{
			chginfo += "�Ĥ@���@�W�@��";
		}
		else
		{
			chginfo += "<a href='#' onclick=\"PageUtil.chgPage(1)\">�Ĥ@��</a>";
			chginfo += "�@";
			chginfo += "<a href='#' onclick=\"PageUtil.chgPage("+(num*1-1)+")\">�W�@��</a>";
		}
		chginfo += "�@";
		if(num==maxnum)
		{
			chginfo += "�U�@���@�̫�@��";
		}
		else
		{
			chginfo += "<a href='#' onclick='PageUtil.chgPage("+(num*1+1)+")'>�U�@��</a>";
			chginfo += "�@";
			chginfo += "<a href='#' onclick='PageUtil.chgPage("+maxnum+")'>�̫�@��</a>";
		}	
		var sizinfo = "�C����ܵ���<input type='text' style='font-size:16px' name='size' size='2' maxlength='2' onKeyPress='lockNum()' value='"+size+"'><input type='button' style='font-size:16px' value='�d' onclick='PageUtil.chgSize()'>";
	}
	else
	{
		numinfo = "�@&nbsp;"+total+"&nbsp;��";
		chginfo = "�Ĥ@���@�W�@���@�U�@���@�̫�@��";
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