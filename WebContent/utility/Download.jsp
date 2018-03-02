<%@page	language="java"	contentType="text/html;charset=MS950" buffer="100kb" import="java.io.*,com.sertek.sys.*,com.sertek.util.*"%>
<%@page import="org.apache.log4j.Logger"%>
<%Logger logger = Logger.getLogger(this.getClass()); %>
<%
try{
String filenm_1 = "1-「司法院線上起訴及書狀傳送作業平台」使用說明.pdf";
String filenm_21 = "2-1-智財行政訴訟線上起訴之測試帳號表.pdf";
String filenm_22 = "2-2-稅務行政訴訟線上起訴之測試帳號表.pdf";
String filenm_3 = "3-線上起訴教育訓練new.pdf";
String filenm_4 = "行政訴訟委任書.doc";
String filenm_51 = "FILECHECK.zip";
String filenm_52 = "檔案檢測工具(請解壓縮執行).zip";
String filenm_7 = "證據清單範例檔.xlsx";
String filenm_8 = "司法院公告.pdf";
String filenm_9 = "民事訴訟委任書.doc";

// 下載檔案
CheckObject check = new CheckObject();
String guide = check.checkNull(request.getParameter("guide"), "").toString();
String filenm = check.checkNull(request.getParameter("filenm"), "").toString();
String saveFilenm = check.checkNull(request.getParameter("filenm"), "").toString();

if("1".equals(guide)){
	filenm = filenm_1;
	saveFilenm = filenm_1;
} else if("21".equals(guide)){
	filenm = filenm_21;
	saveFilenm = filenm_21;	
} else if("22".equals(guide)){
	filenm = filenm_22;
	saveFilenm = filenm_22;
} else if("3".equals(guide)){
	filenm = filenm_3;
	saveFilenm = filenm_3;
} else if("4".equals(guide)){
	filenm = filenm_4;
	saveFilenm = filenm_4;
} else if("6".equals(guide)){
	filenm = filenm_51;
	saveFilenm = filenm_52;
} else if("7".equals(guide)){ 
	filenm = filenm_7;
	saveFilenm = filenm_7;
} else if("8".equals(guide)){ 
	filenm = filenm_8;
	saveFilenm = filenm_8;
} else if("9".equals(guide)){ 
	filenm = filenm_9;
	saveFilenm = filenm_9;
}


filenm = Project.getWebRoot() + "guide/" + filenm;
logger.info("filenm = " + filenm);

File file = new File(filenm);
if (file.exists()) {
	if("".equals(saveFilenm)){
		saveFilenm = file.getName();
	}
	response.setContentType("Content-Type: application/octet-stream");
	response.setHeader("Content-Length", Long.toString(file.length()));
	if (request.getHeader("User-Agent").indexOf("MSIE 5.5") != -1) {
		response.setHeader("Content-Disposition","filename=" + new String(saveFilenm.getBytes("MS950"), "ISO-8859-1" ));
	}else {
		response.addHeader( "Content-Disposition", "attachment;filename=" + new String(saveFilenm.getBytes("MS950"), "ISO-8859-1" ));
	}
	try {
		out.clear();
		out = pageContext.pushBody();
		byte[] buffer = new byte[8192];
		OutputStream output = response.getOutputStream();   
		InputStream in = new FileInputStream(file);   
		int c = in.read(buffer); 
		while (c != -1) {
			output.write(buffer,0,c);
			c = in.read(buffer);
		}
		in.close();
	} catch (IllegalStateException e) {
		e.printStackTrace();
	}
} else {
	logger.warn("檔案" + filenm + "不存在");
}
response.setStatus( response.SC_OK );
response.flushBuffer();
}catch(Exception e){
	logger.error(e, e);
}
%>