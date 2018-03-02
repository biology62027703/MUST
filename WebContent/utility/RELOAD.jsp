<%!

//***********************************************************************

	/*

		功能:能讓網頁自動更新左右兩邊的Frame頁面值!!

		參數:

			out		out

			rightframe 	右邊頁面的名稱

			leftframe	左邊頁面的名稱

		傳回值:	void 

	*/

	/*

	public void reload(javax.servlet.jsp.JspWriter out,String rightframe,String leftframe) {

		try {

			out.println("<SCRIPT LANGUAGE=\'JavaScript\'>");

      			out.println("<!--");

      			out.println("window.parent.rightFrame.location.href=\'"+rightframe+"\';");

      			out.println("window.parent.leftFrame.location.href=\'"+leftframe+"\';");

     			out.println("//-->");

      			out.println("</SCRIPT>"); 

      		} catch(Exception e) {

      			System.out.println(e);

      		}

		

	}*/

	public void reload(javax.servlet.jsp.JspWriter out,String[][] frame) {

		try {

			System.out.println("reload");

			out.println("<SCRIPT LANGUAGE=\'JavaScript\'>");

      			out.println("<!--");

      			for(int i=0;i<frame.length;i++) {

      				out.println("window.parent." + frame[i][0] + ".location.href=\'"+frame[i][1]+"\';");

      				//System.out.println("window.parent." + frame[i][0] + ".location.replace(\'"+frame[i][1]+"\');");

      				//out.println("window.parent." + frame[i][0] + ".location.replace(\'"+frame[i][1]+"\');");

      				//out.println("window.parent.location.reload();");

      			}

      			//out.println("window.parent.leftFrame.location.href=\'"+leftframe+"\';");

      			//System.out.println("window.parent.leftFrame.location.reload();");

     			out.println("//-->");

      			out.println("</SCRIPT>"); 

      			System.out.println("reload end");

      		} catch(Exception e) {

      			System.out.println(e);

      		}

		

	}

	

%>