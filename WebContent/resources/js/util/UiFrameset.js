/**
 * @description �Ҧ����󭶮ت��ާ@�ҨϥΦ��@�Τ���A�]�t�F����ưe�X�P�����ô���C
 * @class ���ؤu��
 * @author chkchk
 * @constructor
 */
function UiFrameset(framesetObj) {
	this._framesetObj = framesetObj;
	this._pname = "";
	this._nname = "";
}

/**
 * @ignore �W�[����
 * */
UiFrameset.prototype.addFrame = function(options) {
	var _frameSetObj = this._framesetObj;
	try {
		var defWidth = "100%";
		var defHeight = "100%";
		$.each(options, function(index, element){
			var _newFrame = $("<iframe id='"+element.frameName+"' name='"+element.frameName+"' src='"+element.frameSrc+"'></iframe>").appendTo(_frameSetObj);
			_newFrame.attr('marginwidth', 0);
			_newFrame.attr('frameborder', 0); 
			_newFrame.attr('scrolling', 'auto'); 
			_newFrame.attr('width', defWidth);
			_newFrame.attr('height', defHeight);
			_newFrame.css('border-bottom', '1px solid');
		});
	} catch(e) {
		alert(e);
	}
}; 

/**
 * @description ��������
 * @param {string} name �����������ئW��
 * @example 
 * 	uiFrameset.removeFrame('v2');
 * */
UiFrameset.prototype.removeFrame = function(name) {
	var _frameSetObj = this._framesetObj;
	$('iframe[name=' + name + ']', _frameSetObj).remove();
};

/**
 * @description ��������
 * @param {string} name �����������ئW��
 * @example 
 * 	uiFrameset.switchFrame('v2');
 * */
UiFrameset.prototype.switchFrame = function(name) {
	var _frameSetObj = this._framesetObj;
	$('iframe[name=' + name + ']', _frameSetObj).show();
	$('iframe[name!=' + name + ']', _frameSetObj).hide();
};

/**
 * @description �������ث����Y�禡
 * @param {string} name �����������ئW��
 * @param {string} action �������ث�ҭn���檺�禡
 * @param {map} [param=null] �禡���Ѽ�
 * @example 
 *  �n������v2�����processEdit�h�bv2�����w�qprocessEdit�禡
 * 	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult); 
 * */ 
UiFrameset.prototype.switchFrameWithExecute = function(name, action, param) {
	if (action != null) {
		try {
			if (param == null) {
				eval("frames['"+name+"']." + action + "()");
			} else {
				eval("frames['"+name+"']." + action + "(param)");
			}
		} catch(ignore) {
			alert(ignore);
		}
	}
	
	this.switchFrame(name);
};

/**
 * @description ����Y���ت��Y�禡
 * @param {string} name �����������ئW��
 * @param {string} action �������ث�ҭn���檺�禡
 * @param {map} [param=null] �禡���Ѽ�
 * @example 
 *  �n������v2�����processEdit�h�bv2�����w�qprocessEdit�禡
 * 	uiFrameset.FrameWithExecute('v2', 'processEdit', jsonResult); 
 * */ 
UiFrameset.prototype.executeFrameFunction = function(name, action, param) {
	if (action != null) {
		try {
			if (param == null) {
				eval("frames['"+name+"']." + action + "()");
			} else {
				eval("frames['"+name+"']." + action + "(param)");
			}
		} catch(ignore) {
			alert(ignore);
		}
	}
};

var _uiFrameset = new UiFrameset();