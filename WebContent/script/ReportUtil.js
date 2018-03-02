// Class Body:
function ReportUtilClassBody() {
	var thisClass = this;
	
	/**
	 * 開啟報表檢視工具程式, 呼叫此函式前, 必須確定於 JSP中 INCLUDE '/utility/UtilOBJ.jsp'<br>
	 *
	 * @param reportFilePath 要檢視的報表路徑
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