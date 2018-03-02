// Class Body:
function ReportUtilClassBody() {
	var thisClass = this;
	
	/**
	 * �}�ҳ����˵��u��{��, �I�s���禡�e, �����T�w�� JSP�� INCLUDE '/utility/UtilOBJ.jsp'<br>
	 *
	 * @param reportFilePath �n�˵���������|
	 */
	thisClass.viewReport = function(reportFilePath)
	{
		var cmd = "c:\\jdc\\winstar\\winet.exe " + reportFilePath + " /~P300;";
		UtilOBJX.runCmd(cmd, '', '5');
	};
	
	thisClass.editReport = function(reportFilePath)
	{
		var cmd = "c:\\jdc\\winstar\\winstar.exe " + reportFilePath + "";
		UtilOBJX.runCmd(cmd, '', '5');
	};
	
	thisClass.downloadReportForJ = function(reportFilePath)
	{
		while( reportFilePath.indexOf("\\") > -1 )
			reportFilePath = reportFilePath.replace("\\", "/");		
		var pqFilePath = "C:/jdc/pq/" + reportFilePath.substring(reportFilePath.lastIndexOf("/")+1);
		HttpDown(reportFilePath, pqFilePath);
	}
	
	thisClass.downloadReportForJAndView = function(reportFilePath)
	{
		while( reportFilePath.indexOf("\\") > -1 )
			reportFilePath = reportFilePath.replace("\\", "/");		
		var pqFilePath = "C:/jdc/pq/" + reportFilePath.substring(reportFilePath.lastIndexOf("/")+1);
		HttpDown(reportFilePath, pqFilePath);
		
		while( pqFilePath.indexOf("/")>-1 )
			pqFilePath = pqFilePath.replace("/", "\\");
		ReportUtil.viewReport(pqFilePath);
	}
	
	return thisClass;
}

// Class Static Instance, this MUST be at the bottom line:
var ReportUtil = new ReportUtilClassBody();