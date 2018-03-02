<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sertek.util.*"%>
<%
String doc_no = "";

boolean isMultipart = ServletFileUpload.isMultipartContent(request);

if (isMultipart) {
	FileItemFactory factory = new DiskFileItemFactory();

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	// Parse the request
	List /* FileItem */items = upload.parseRequest(request);

	Iterator iter = items.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();

		
		if (item.isFormField()) {
		// processFormField(item);
		} else {
			String fileName = item.getName();
			System.out.println("rcv_onlinecasedata.jsp fileName = " + fileName);
			
			int pos = fileName.indexOf(".");
			if(pos > 0){
				doc_no = fileName.substring(0, pos);
			}
		}
	}
} 

util_date ud = new util_date();

StringBuffer xml = new StringBuffer();

xml.append("<?xml version=\"1.0\" encoding=\"BIG5\"?>" + "\r\n");
xml.append("	<Profile>" + "\r\n");
xml.append("		<crtid>TPA</crtid>" + "\r\n");
xml.append("		<sys>A</sys>" + "\r\n");
xml.append("		<doc_no>" + doc_no + "</doc_no>" + "\r\n");
xml.append("		<c_status>Y</c_status>" + "\r\n");
xml.append("		<comdt>" + ud.nowCDate() + "</comdt>" + "\r\n");
xml.append("		<comtm>" + ud.nowTime() + "</comtm>" + "\r\n");
xml.append("	</Profile>");

/* Output the response. */
response.setContentType("text/plain;charset=MS950");
PrintWriter writer = response.getWriter();
writer.write(xml.toString());
writer.flush();
%>