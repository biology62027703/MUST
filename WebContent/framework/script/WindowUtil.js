/** 
 * @fileoverview �w�q������&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q������&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function WindowUtil()
{
}


var __href__ = location.href;
var __a  = __href__.split('/');
var webroot__ = '';
if (__a.length>3){ webroot__ = __a[3]};   
var __BasePath__ = '/' + webroot__ + '/';
if("SOL" != webroot__){
	__BasePath__ = '/';
}
//var __BasePath__ = window.location.pathname.replace(/(^\/([^\/]+\/)*).*/g,"$1");

/**
 * @private
 */
WindowUtil.basePath = __BasePath__ ;
//WindowUtil.basePath = window.location.pathname.replace(/(^\/([^\/]+\/)*).*/g,"$1");
//alert(WindowUtil.basePath);
/**
 * ���o�����򩳪����|
 * @return �����]�t"/"�������򩳸��|
 * @type String
 */
WindowUtil.getBasePath=function()
{
	try
	{
		return WindowUtil.basePath;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getBasePath",arguments,ex));
	}
}

/**
 * ���o�ثe�����}�Ҫ�����URL
 * @param {window} win ��������, �w�]�Ȭ� self ����
 * @return �Hhttp://�}�Y��URL
 * @type String
 */
WindowUtil.getBaseUrl=function(win)
{
	try
	{
		var url = ((win == null)?self:win).location.href;
		
		if(url.indexOf('?')==-1)
		{
			return url;
		}
		else
		{
			return url.substr(0,url.indexOf('?'));
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getBaseUrl",arguments,ex));
	}
}

/**
 * ���o�ثe�����}�Ҫ������ɦW
 * @param {window} win ��������, �w�]�Ȭ� self ����
 * @return �ɮצW��
 * @type String
 */
WindowUtil.getCurrentPage=function(win)
{
	try
	{
		var file = WindowUtil.getBaseUrl(win);
		
		return file.split('/')[file.split('/').length - 1];
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getCurrentPage",arguments,ex));
	}
}

/**
 * �}�ҦC�L�Ϊ�����(window.open ���ù�)
 * @param {String} url �}�Һ�����URL�r��
 * @param {String} name �}�ҵ������W�� 
 * @return �}�ҵ���������
 * @type window
 */
WindowUtil.openPrintWindow=function(url,name)
{
	try
	{
		var $2_="width="+screen.availWidth+",height="+screen.availHeight+",left=0,top=0,scrollbars=1,status=no,resizable=no,titlebar=no";
		
		win_=window.open(url,name,$2_);

		win_.resizeTo(screen.availWidth,screen.availHeight);
		win_.moveTo(0,0);
		return win_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openPrintWindow",arguments,ex));
	}
}

/**
 * �}�ҩT�w�˦�������(window.open)
 * @param {String} url �}�Һ�����URL�r��
 * @param {String} name �}�ҵ������W�� 
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @return �}�ҵ���������
 * @type window
 */
WindowUtil.openWindow=function(url,name)
{
	try
	{
		var $2_=((arguments[2]==null)?screen.availWidth:arguments[2]);
		var $3_=((arguments[3]==null)?screen.availHeight:arguments[3]);
		var $4_="width="+$2_+",height="+$3_+",left=0,top=0,scrollbars=1,status=no,resizable=no,titlebar=no";
		
		win_=window.open(url,name,$4_);

		win_.resizeTo($2_,$3_);
		win_.moveTo(0,0);
		return win_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openWindow",arguments,ex));
	}
}

/**
 * �}�ҩT�w�˦�������(showModalDialog)
 * @param {String} url �}�Һ�����URL�r��
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @param {Object} obj �n�ǻ����}�ҵ���������, �w�]�Ȭ��I�s������ self ����
 * @return showModalDialog() ���^�ǭ�
 * @type Object
 */
WindowUtil.openDialog=function(url,width,height,obj)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?self:Form.document_.parentWindow;
		return showModalDialog(url,(arguments[3] == null)?self:arguments[3],"dialogWidth:"+((arguments[1]==null)?screen.availWidth:arguments[1])+"px;dialogHeight:"+((arguments[2]==null)?screen.availHeight:arguments[2])+"px;center:1;scroll:1;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openDialog",arguments,ex));
	}
}

/**
 * �}�Ҧۭq�ǻ����󪺵���(showModalDialog)
 * @param {String} url �}�Һ�����URL�r��
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @param {Object} obj �n�ǻ����}�ҵ���������
 * @return showModalDialog() ���^�ǭ�
 * @type Object
 */
WindowUtil.openObjDialog=function(url, obj, width, height)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;
		return showModalDialog(url,obj,"dialogWidth:"+((arguments[2]==null)?screen.availWidth:arguments[2])+"px;dialogHeight:"+((arguments[3]==null)?screen.availHeight:arguments[3])+"px;center:1;scroll:1;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openObjDialog",arguments,ex));
	}
}

/**
 * �}�ҫ��O�X���~�^��������(window.open)
 * @deprecated
 * @param {String} report �^�������~�T��
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ�500px
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ�600px
 * @return �}�ҵ���������
 * @type window
 */
WindowUtil.openExceptionWindow=function(report,width,height)
{
	try
	{
		if(width==null)
			width=500;
		if(height==null)
			height=600;
		
		var $21_=(screen.width)? (screen.width - width)/ 2 :0;
		var $22_=(screen.height)? (screen.height - height)/ 2 :0;
		var $23_=window.open('','errWin','height='+height+',width='+width+',scrollbars=yes');
		$23_.document.writeln(report);
		$23_.document.close();
		$23_.focus();
		$23_.moveTo($21_,$22_);
		$23_.document.body.scrollTop='10000000';
		$23_.document.title='Script Error Report';
		return $23_;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openExceptionWindow",arguments,ex));
	}
}

/**
 * �}�ҫ��O�X���~�^��������(showModalDialog) 
 * @param {String} report �^�������~�T��
 * @type void
 */
WindowUtil.openExceptionDialog=function(report)
{
	try
	{
		var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;		
		objWindow.showModalDialog(WindowUtil.getBasePath()+"framework/utility/ErrorReport.html",report,"dialogWidth:500px;dialogHeight:400px;center:1;scroll:0;help:0;status:0;resizable:0");
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openExceptionDialog",arguments,ex));
	}
}

/**
 * �}�Ҫ���ˬd���G������(showModalDialog) 
 * @param {String} report ��檺�ˬd���G
 * @type void
 */
WindowUtil.openFormcheckDialog=function(report)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		showModalDialog(WindowUtil.getBasePath()+"framework/utility/FormCheck.html",report,"dialogWidth:500px;dialogHeight:360px;center:1;scroll:0;help:0;status:0;resizable:0");
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFormcheckDialog",arguments,ex));
	}
}

/**
 * ���J����������(showModalDialog) 
 * @param {String} url �}�Ҥ�����URL�r��, �H������|�ά۹��򩳸��|��� 
 * @param {String} title �}�ҵ��������D
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @param {String} param �ǻ����}�ҵ������Ѽ�
 * @return showModalDialog() ���^�ǭ�
 * @type void
 */
WindowUtil.openFrameDialog=function(url,title)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		if (StrUtil.isEmpty(title))
		{
			var postSrc = url.split("?")[0];
			var postData = "fetchTitle=Y"; // (url.split("?").length > 1)?url.split("?")[1]+"&fetchTitle=Y":
			title = Assist.postRemoteData(postSrc,postData).replace(/[\n\r]/g,"");
		}

		return showModalDialog(WindowUtil.getBasePath()+"framework/utility/FrameLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),(arguments[4])?new Array(self,arguments[4]):self,"dialogWidth:"+((arguments[2]==null)?screen.availWidth:arguments[2])+"px;dialogHeight:"+((arguments[3]==null)?screen.availHeight:arguments[3])+"px;center:1;scroll:0;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFrameDialog",arguments,ex));
	}
}

/**
 * ���J����������(showModalDialog) 
 * @param {String} ip �}�Ҥ�����IP�r��, �����Hhttp://�}�Y���
 * @param {String} url �}�Ҥ�����URL�r��, �H������|�ά۹��򩳸��|��� 
 * @param {String} title �}�ҵ��������D
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @param {String} param �ǻ����}�ҵ������Ѽ�
 * @return showModalDialog() ���^�ǭ�
 * @type void
 */
WindowUtil.openPageDialog=function(ip,url,title)
{
	try
	{
		if (StrUtil.isEmpty(title))
		{
			var postSrc = ip+WindowUtil.getBasePath()+url.split("?")[0];
			var postData = "fetchTitle=Y";
			title = Assist.postRemoteData(postSrc,postData).replace(/[\n\r]/g,"");
		}

		return showModalDialog(ip+WindowUtil.getBasePath()+"framework/utility/PageLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),(arguments[5])?new Array(self,arguments[5]):self,"dialogWidth:"+((arguments[3]==null)?screen.availWidth:arguments[3])+"px;dialogHeight:"+((arguments[4]==null)?screen.availHeight:arguments[4])+"px;center:1;scroll:0;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openPageDialog",arguments,ex));
	}
}

/**
 * ���J����������(window.open) 
 * @param {String} url �}�Ҥ�����URL�r��, �H������|�ά۹��򩳸��|��� 
 * @param {String} title �}�ҵ��������D
 * @param {Number} width �}�Ҫ������e��, �w�]�Ȭ��ù����e�� 
 * @param {Number} height �}�Ҫ���������, �w�]�Ȭ��ù������� 
 * @type void
 */
WindowUtil.openFrameWindow=function(url,title)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		window.open(WindowUtil.getBasePath()+"framework/utility/FrameLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),"","width="+((arguments[2]==null)?screen.availWidth:arguments[2])+",height="+((arguments[3]==null)?screen.availHeight:arguments[3])+",left=0,top=0,status=no,location=no,menubar=no,scrollbars=no,resizable=no,toolbar=no");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFrameWindow",arguments,ex));
	}
}

/**
 * ���o�ǤJ����X�b��m
 * @param {Object} srcObj �n���o��m������ 
 * @return X�b��m(pixel)
 * @type Number
 */
WindowUtil.getClientX=function(srcObj)
{
	try
	{
		var offsetX = 0;
		var obj = srcObj;
		
		while (obj != null)
		{
			offsetX += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		
		return offsetX;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getClientX",arguments,ex));
	}
}

/**
 * ���o�ǤJ����Y�b��m
 * @param {Object} srcObj �n���o��m������ 
 * @return Y�b��m(pixel)
 * @type Number
 */
WindowUtil.getClientY=function(srcObj)
{
	try
	{
		var offsetY = 0;
		var obj = srcObj;
		
		while (obj != null)
		{
			offsetY += obj.offsetTop;
			obj = obj.offsetParent;
		}
		
		return offsetY;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getClientY",arguments,ex));
	}
}