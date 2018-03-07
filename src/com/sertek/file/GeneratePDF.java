/*package com.sertek.file;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import sun.font.FontManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.sertek.form.SOLHelper;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.table.C60;
import com.sertek.table.C60_ATT;
import com.sertek.table.C60_WEB;
import com.sertek.table.C61;
import com.sertek.table.C6A;

import com.sertek.table.COURT;
import com.sertek.util.CheckObject;
import com.sertek.util.util_date;
import com.sertek.util.utility;

public class GeneratePDF {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SqlDBUtility sqlDBUtility = null;
	
	private HashMap form = new HashMap();
	
	private CheckObject check = new CheckObject();
	
	private utility ut = new utility();
	
	private util_date ud = new util_date();
	
	private Font font = new Font();
	
	private Font redFont = new Font();
	
	private Font blueFont = new Font();
	
	private Font headerFont = new Font();
	
	private Font signFont = new Font();
	
	Document document = null;
	
	public GeneratePDF() throws Exception {
		initFont();
	}
	
	public class Watermark extends PdfPageEventHelper {
 
        protected String text = "";
        
        public Watermark(String text) {
        	this.text = text;
        }
        
        @Override 
        public void onEndPage(PdfWriter writer, Document document) {    		
            PdfContentByte canvas = writer.getDirectContentUnder();
            String watertxt = String.valueOf(document.getPageNumber()) + "  " + text;
            Phrase watermark = new Phrase( watertxt, font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, watermark, 298, 20, 0);
        }
    }
	
	*//**
	 * 產生「網頁繕打」的PDF
	 * @param sqlDBUtility
	 * @param form
	 * @param filenm
	 * @throws Exception
	 *//*
	public void createPDF(SqlDBUtility sqlDBUtility, HashMap form, String filenm) throws Exception {
		this.sqlDBUtility = sqlDBUtility;
		this.form = form;
		
		try {
			int marginLeft = -20;
			int marginRight = -20;
			int marginTop = 40;
			int marginBottom = 40;
			
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filenm));
			writer.setPageEvent(new Watermark(getDocumentFooterText()));
			document.open();
			
			C60 c60 = new C60();
			HashMap c60Key = new HashMap();
			c60Key.put("crtid", check.checkNull(form.get("crtid"), ""));
			c60Key.put("sys", check.checkNull(form.get("sys"), ""));
			c60Key.put("c_no", check.checkNull(form.get("c_no"), ""));
			List c60List = sqlDBUtility.queryForList("C60.queryByKey", c60Key);
			if (c60List.size() > 0) {
				c60 = (C60) c60List.get(0);
				
				HashMap courtKey = new HashMap();
				courtKey.put("crtid", c60.getCrtid());
				List courtList = sqlDBUtility.queryForList("COURT.queryByKey", courtKey);
				if (courtList.size() > 0) {
					COURT court = (COURT) courtList.get(0);
					form.put("crtnm", court.getCrtnm());
				}
			}
			
			// 聲請人
			C61 c61_1 = new C61();
			C6A c6a = new C6A();
			HashMap c61Key = new HashMap();
			c61Key.put("crtid", check.checkNull(form.get("crtid"), ""));
			c61Key.put("sys", check.checkNull(form.get("sys"), ""));
			c61Key.put("c_no", check.checkNull(form.get("c_no"), ""));
			List c61List1 = sqlDBUtility.queryForList("FGSO1A04.C61_Query1", c61Key);
			if (c61List1.size() > 0) {
				c61_1 = (C61) c61List1.get(0);

				HashMap c6aKey = new HashMap();
				c6aKey.put("crtid", c61_1.getCrtid());
				c6aKey.put("sys", c61_1.getSys());
				c6aKey.put("c_no", c61_1.getC_no());
				c6aKey.put("sysid", c61_1.getSysid());
				List c6aList = sqlDBUtility.queryForList("FGSO1A04.C6A_Query", c6aKey);
				if (c6aList.size() > 0) {
					c6a = (C6A) c6aList.get(0);
				}
			}
			
			// (機關首長)
			form.put("leader", getGSOS08Argvl("FGSO1A04", "機關首長"));

			// 受收容人
			C61 c61_2 = new C61();
			List c61List2 = sqlDBUtility.queryForList("FGSO1A04.C61_Query2", c61Key);
			if(c61List2.size() > 0){
				c61_2 = (C61) c61List2.get(0);
				
				HashMap c91Key = new HashMap();
				c91Key.put("st_code", c61_2.getNat());
				List c91List = sqlDBUtility.queryForList("C91.queryByKey", c91Key);
				if (c91List.size() > 0) {
					C91 c91 = (C91) c91List.get(0);
					c61_2.setNat(c91.getCdds());
				}
			}

			C60_WEB web = new C60_WEB();
			HashMap webKey = new HashMap();
			webKey.put("crtid", check.checkNull(form.get("crtid"), ""));
			webKey.put("sys", check.checkNull(form.get("sys"), ""));
			webKey.put("c_no", check.checkNull(form.get("c_no"), ""));
			webKey.put("s_seq", check.checkNull(form.get("s_seq"), ""));
			List webList = sqlDBUtility.queryForList("C60_WEB.queryByKey", webKey);
			if (webList.size() > 0) {
				web = (C60_WEB) webList.get(0);
			}

			createHeader(c60);
			
			createBody(c60, c61_1, c6a, c61_2, web);
			
			createBody2(web);
			
			createBody3(web);
			
			createFooter(web);
			
			createDocumentFooter();
			
			//CryptoFile.encryptFile(item.getInputStream(), s_filenm);

		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	*//**
	 * 產生「查看及列印」的PDF
	 * @param sqlDBUtility
	 * @param form
	 * @param filenm
	 * @throws Exception
	 *//*
	public void createPDF2(SqlDBUtility sqlDBUtility, HashMap form, String filenm) throws Exception {
		this.sqlDBUtility = sqlDBUtility;
		this.form = form;
		
		try {
			int marginLeft = -20;
			int marginRight = -20;
			int marginTop = 40;
			int marginBottom = 40;
			
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filenm));
			writer.setPageEvent(new Watermark(getDocumentFooterText()));
			document.open();
			
			C60 c60 = new C60();
			HashMap c60Key = new HashMap();
			c60Key.put("crtid", check.checkNull(form.get("crtid"), ""));
			c60Key.put("sys", check.checkNull(form.get("sys"), ""));
			c60Key.put("c_no", check.checkNull(form.get("c_no"), ""));
			List c60List = sqlDBUtility.queryForList("C60.queryByKey", c60Key);
			if (c60List.size() > 0) {
				c60 = (C60) c60List.get(0);
				
				HashMap courtKey = new HashMap();
				courtKey.put("crtid", c60.getCrtid());
				List courtList = sqlDBUtility.queryForList("COURT.queryByKey", courtKey);
				if (courtList.size() > 0) {
					COURT court = (COURT) courtList.get(0);
					form.put("crtnm", court.getCrtnm());
				}
			}
			
			// 聲請人
			C61 c61_1 = new C61();
			C6A c6a = new C6A();
			HashMap c61Key = new HashMap();
			c61Key.put("crtid", check.checkNull(form.get("crtid"), ""));
			c61Key.put("sys", check.checkNull(form.get("sys"), ""));
			c61Key.put("c_no", check.checkNull(form.get("c_no"), ""));
			List c61List1 = sqlDBUtility.queryForList("FGSO1A04.C61_Query1", c61Key);
			if (c61List1.size() > 0) {
				c61_1 = (C61) c61List1.get(0);

				HashMap c6aKey = new HashMap();
				c6aKey.put("crtid", c61_1.getCrtid());
				c6aKey.put("sys", c61_1.getSys());
				c6aKey.put("c_no", c61_1.getC_no());
				c6aKey.put("sysid", c61_1.getSysid());
				List c6aList = sqlDBUtility.queryForList("FGSO1A04.C6A_Query", c6aKey);
				if (c6aList.size() > 0) {
					c6a = (C6A) c6aList.get(0);
				}
			}
			
			// (機關首長)
			form.put("leader", getGSOS08Argvl("FGSO1A04", "機關首長"));

			// 受收容人
			C61 c61_2 = new C61();
			List c61List2 = sqlDBUtility.queryForList("FGSO1A04.C61_Query2", c61Key);
			if(c61List2.size() > 0){
				c61_2 = (C61) c61List2.get(0);
				
				HashMap c91Key = new HashMap();
				c91Key.put("st_code", c61_2.getNat());
				List c91List = sqlDBUtility.queryForList("C91.queryByKey", c91Key);
				if (c91List.size() > 0) {
					C91 c91 = (C91) c91List.get(0);
					c61_2.setNat(c91.getCdds());
				}
			}

			C60_WEB web = new C60_WEB();
			HashMap webKey = new HashMap();
			webKey.put("crtid", check.checkNull(form.get("crtid"), ""));
			webKey.put("sys", check.checkNull(form.get("sys"), ""));
			webKey.put("c_no", check.checkNull(form.get("c_no"), ""));
			webKey.put("s_seq", check.checkNull(form.get("s_seq"), ""));
			List webList = sqlDBUtility.queryForList("C60_WEB.queryByKey", webKey);
			if (webList.size() > 0) {
				web = (C60_WEB) webList.get(0);
			}
			
			createPreHeader();
			
			createHeader(c60);
			
			createBody(c60, c61_1, c6a, c61_2, web);
			
			createBody2(web);
			
			createBody3(web);
			
			createFooter(web);
			
			createAttList();
			
			createDocumentFooter();
			
			//CryptoFile.encryptFile(item.getInputStream(), s_filenm);

		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	private void createPreHeader() throws Exception {
		PdfPTable table = new PdfPTable(2);

		float[] columnWidths = new float[] { 4f, 16f };
		table.setWidths(columnWidths);

		table.addCell(createCell("注意事項"));

		Phrase phrase = new Phrase();
		phrase.add(new Chunk("您的案件已完成遞狀，\n", font));
		
		phrase.add(new Chunk("收狀日期為", font));
		phrase.add(new Chunk("" + ud.formatCDate(check.checkNull(form.get("rcvdt"), "").toString(), "/") + "　" + ud.formatTime(check.checkNull(form.get("rcvtm"), "").toString(), ":"), redFont));
		phrase.add(new Chunk("，\n", font));
		
		phrase.add(new Chunk("遞狀流水號為", font));
		phrase.add(new Chunk("" + check.checkNull(form.get("doc_no"), ""), redFont));
		phrase.add(new Chunk("，\n", font));
		
		System.out.println("************************************");
		System.out.println(form.toString());
		
		phrase.add(new Chunk("遞狀種類為", font));
		//phrase.add(new Chunk("" + SOLHelper.getSuit_nm(check.checkNull(form.get("s_kd"), "").toString()), redFont));
		phrase.add(new Chunk("，\n", font));
		
		phrase.add(new Chunk("本案當事人共", font));
		phrase.add(new Chunk("" + check.checkNull(form.get("c61Cnt"), ""), redFont));
		phrase.add(new Chunk("名、上傳共", font));
		phrase.add(new Chunk("" + check.checkNull(form.get("docCnt"), ""), redFont));
		phrase.add(new Chunk("個附件。\n", font));
		
		phrase.add(new Chunk("本案後續書狀遞送請由「聲請查詢」功能查詢出本案時，點「遞補充書狀」圖示，接著點選「新增」進行。", blueFont));
		
		table.addCell(createCell(phrase));

		document.add(table);
		
		document.newPage();
		//document.add(new Phrase("\n"));
	}
	
	private void createHeader(C60 c60) throws Exception {
		PdfPTable table = new PdfPTable(1);

		float[] columnWidths = new float[] { 1f };
		table.setWidths(columnWidths);

		table.addCell(createHeader(SOLHelper.getSuit_nm(c60.getS_kd()), 1));
		
		document.add(table);
	}
	
	private void createBody(C60 c60, C61 c61_1, C6A c6a, C61 c61_2, C60_WEB web) throws Exception {
		PdfPTable table = new PdfPTable(5);

		float[] columnWidths = new float[] { 2f, 2f, 3f, 2f, 1f };
		table.setWidths(columnWidths);

		table.addCell(createCell("案號"));
		table.addCell(createColspanCell("　" + c60.getCrmyy() + "　年度　" + c60.getCrmid() + "　字第　" + c60.getCrmno() + "　號", 2));
		table.addCell(createCell("承辦股別"));
		table.addCell(createCell(c60.getDpt()));
			
		table.addCell(createCell("訴訟標的金額或價額"));
		table.addCell(createColspanCell("新臺幣　" + ut.Fillspace(web.getS_price(), 16, 0) + "　元", 4));
		
		// 稱謂
		table.addCell(createCell("稱謂"));
		table.addCell(createCell("姓名或名稱"));
		
		StringBuffer str1 = new StringBuffer();
		str1.append("依序填寫：國民身分證統一編號或營利事業統一編號、性別、出生年月日、職業、住居所、就業處所、公務所、事務所或營業所、郵遞區號、電話、傳真、電子郵件位址、指定送達代收人及其送達處所。");
		
		table.addCell(createColspanCell(str1.toString(), 3));
		
		// 聲請人
		table.addCell(createCell("聲請人"));
		table.addCell(createCell(getSyskdGNm()));
		
		StringBuffer str2 = new StringBuffer();
		str2.append("設：" + c61_1.getCity() + c61_1.getArea() + c61_1.getAddr() + "\n");
		str2.append("送達代收人：" + c6a.getClnm() + "\n");
		str2.append("送達處所：" + c6a.getCity() + c6a.getArea() + c6a.getAddr() + "\n");
		
		table.addCell(createColspanCell(str2.toString(), 3));
		
		// 代表人
		table.addCell(createCell("代表人"));
		table.addCell(createCell(check.checkNull(form.get("leader"), "") + "\n" + "(機關首長)"));
		
		StringBuffer str3 = new StringBuffer();
		str3.append("住同上" + "\n");
		
		table.addCell(createColspanCell(str3.toString(), 3));
		
		// 受收容人
		table.addCell(createCell("受收容人"));
		table.addCell(createCell((c61_2.getClnm().equals("") ? c61_2.getClnm_e() : c61_2.getClnm()) + "\n"));
		
		StringBuffer str4 = new StringBuffer();
		str4.append("國民身分證統一編號（或" + c61_2.getNat() + "國籍，護照號碼）：" + c61_2.getIdno() + "\n");
		str4.append("性別：" + formatSex(c61_2.getSex()) + "\n");
		str4.append("生日：" + formatBirdt(c61_2.getBirdt()) + "\n");
		str4.append("職業：" + c61_2.getPfs() + "\n");
		str4.append("住：" + c61_2.getCity() + c61_2.getArea() + c61_2.getAddr() + "\n");
		str4.append("郵遞區號：" + c61_2.getZip() + "\n");
		str4.append("電話：" + c61_2.getTel() + "\n");
		str4.append("傳真：" + c61_2.getFax() + "\n");
		str4.append("電子郵件位址：" + c61_2.getEmail() + "\n");
		str4.append("＊有通譯需求之語言別：" + SOLHelper.getInterp(c61_2.getInterp(), c61_2.getInt_rmk()) + "\n");
		
		table.addCell(createColspanCell(str4.toString(), 3));
		
		document.add(table);
	}
	
	private void createBody2(C60_WEB web) throws Exception {
		PdfPTable table = new PdfPTable(1);

		float[] columnWidths = new float[] { 1f };
		table.setWidths(columnWidths);
		
		table.addCell(this.getBody2Cell(web));
		
		document.add(table);
	}
	
	private PdfPTable getBody2Cell(C60_WEB web) throws Exception {
		PdfPTable table = new PdfPTable(1);

		float[] columnWidths = new float[] { 1f };
		table.setWidths(columnWidths);

		StringBuffer stmt = new StringBuffer();
		stmt.append(web.getS_stmt() + "\n");
		stmt.append("\n");
		stmt.append("\n");
		stmt.append("　　此　致\n");
		stmt.append("\n");
		stmt.append(check.checkNull(form.get("crtnm"), "") + "行政訴訟庭　　　　公鑒\n");

		table.addCell(createColspanCell(stmt.toString(), 0, 1));

		return table;
	}
	
	private void createBody3(C60_WEB web) throws Exception {
		PdfPTable table = new PdfPTable(5);

		float[] columnWidths = new float[] { 2f, 2f, 3f, 2f, 1f };
		table.setWidths(columnWidths);
		
		table.addCell(createCell("證物名稱及件數"));
		table.addCell(createColspanCell(web.getS_evd(), 4));
		
		document.add(table);
	}
	
	private void createFooter(C60_WEB web) throws Exception {
		PdfPTable table = new PdfPTable(1);

		float[] columnWidths = new float[] { 1f };
		table.setWidths(columnWidths);
		
		table.addCell(this.getFootCell());
		
		document.add(table);
	}
	
	private PdfPTable getFootCell() throws Exception {
		String usrnm = check.checkNull(form.get("usrnm"), "").toString();
		String s_opsn = check.checkNull(form.get("s_opsn"), "").toString();
		
		PdfPTable table = new PdfPTable(4);

		float[] columnWidths = new float[] { 14f, 3f, 2f, 1f };
		table.setWidths(columnWidths);

		StringBuffer str = new StringBuffer();
		
		String sysdt = ud.nowCDate();
		
		str.setLength(0);
		str.append("中華民國　" + sysdt.substring(0, 3) + "　年　" + sysdt.substring(3, 5) + "　月　" + sysdt.substring(5, 7) + "　日\n");
		table.addCell(createColspanCell(str.toString(), 0, 4));
		
		str.setLength(0);
		str.append("　　　　　　　　具狀人　內政部移民署　代表人　　");
		table.addCell(createColspanCell(str.toString(), 0, 1));
		table.addCell(createCell("簽名蓋章　", 0));
		
		table.addCell(createCell("", 0));
		
		str.setLength(0);
		str.append("　　　　　　　　撰狀人　" + usrnm + "　");
		table.addCell(createColspanCell(str.toString(), 0, 1));
		table.addCell(createCell("簽名蓋章　", 0));
		table.addCell(createSignCell(usrnm));
		table.addCell(createCell("", 0));
		
		return table;
	}
	
	private void createAttList() throws Exception {
		List attList = new ArrayList();
		if (form.get("attList") != null) {
			attList = (List) form.get("attList");
		}
		
		PdfPTable table = new PdfPTable(5);

		float[] columnWidths = new float[] { 2f, 6f, 5f, 4f, 3f };
		table.setWidths(columnWidths);
		
		table.addCell(createCell("序號"));
		table.addCell(createCell("檔名"));
		table.addCell(createCell("說明"));
		table.addCell(createCell("上傳日期"));
		table.addCell(createCell("檔案大小"));
		
		for (int i = 0; i < attList.size(); i++) {
			C60_ATT att = (C60_ATT) attList.get(i);
			table.addCell(createCell("" + (i + 1)));
			table.addCell(createCell(att.getS_file()));
			table.addCell(createCell(att.getS_fileds()));
			table.addCell(createCell(ud.formatCDate(att.getS_updt(), "/") + " " + ud.formatTime(att.getS_uptm(), ":")));
			table.addCell(createCell(formatFileSize(att.getS_filesz())));
		}
		
		document.add(new Phrase("\n"));
		document.add(table);
	}
	
	private void createDocumentFooter() throws Exception {
		String usrnm = check.checkNull(form.get("usrnm"), "").toString();
		
		PdfPTable table = new PdfPTable(1);

		float[] columnWidths = new float[] { 1f };
		table.setWidths(columnWidths);

		table.addCell(createLeftFooter("＊依實際狀況填載，若無此狀況者免填。", 1));
		
		document.add(table);
	}
	
	private String getDocumentFooterText() throws Exception {
		String usrnm = check.checkNull(form.get("usrnm"), "").toString();
		
		StringBuffer str = new StringBuffer();		
		String sysdt = ud.nowCDate();
		String systm = ud.nowTime();
			
		str.setLength(0);
		str.append(sysdt.substring(0, 3) + "年" + sysdt.substring(3, 5) + "月" + sysdt.substring(5, 7) + "日");
		str.append(systm.substring(0, 2) + "時" + systm.substring(2, 4) + "分" + systm.substring(4, 6) + "秒");
		str.append("　" + usrnm + "　" + "印");
		
		return str.toString();
	}
	
	private String formatSex(String sex) {
		if ("M".equals(sex)) {
			return "男";
		} else if ("F".equals(sex)) {
			return "女";
		} else if ("C".equals(sex)) {
			return "法人";
		} else if ("G".equals(sex)) {
			return "中央及地方機關";
		} else if ("O".equals(sex)) {
			return "其他";
		} else {
			return sex;
		}
	}
	
	private String formatBirdt(String birdt) {
		if (birdt.length() >= 8) {
			return birdt.substring(0, 4) + "/" + birdt.substring(4, 6) + "/" + birdt.substring(6, 8);
		} else {
			return birdt;
		}
	}
	
	private PdfPCell createCell(String str) {
		return createCell(str, 1, 1);
	}
	
	private PdfPCell createColspanCell(String str, int colspan) {
		return createCell(str, colspan, 1);
	}
	
	private PdfPCell createColspanCell(String str, int border, int colspan) {
		return createCell(str, border, colspan, 1);
	}
	
//	private PdfPCell createRowspanCell(String str, int rowspan) {
//		return createCell(str, 1, rowspan);
//	}
	
	private PdfPCell createHeader(String str, int colspan) {
		PdfPCell cell = new PdfPCell(new Phrase(str, headerFont));
		//cell.setBorder(0);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(8);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		//cell.setLeading(0f, 1.2f);
		return cell;
	}
	
//	private PdfPCell createHeader2(String str, int colspan) {
//		PdfPCell cell = new PdfPCell(new Phrase(str,font));
//		cell.setBorder(0);
//		cell.setColspan(colspan);
//		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		cell.setVerticalAlignment(Element.ALIGN_TOP);
//		cell.setPaddingTop(0);
//		cell.setPaddingBottom(8);
//		cell.setPaddingLeft(4);
//		cell.setPaddingRight(4);
//		//cell.setLeading(0f, 1.2f);
//		return cell;
//	}
	
	private PdfPCell createLeftFooter(String str, int colspan) {
		PdfPCell cell = new PdfPCell(new Phrase(str,font));
		cell.setBorder(0);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(8);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		//cell.setLeading(0f, 1.2f);
		return cell;
	}
	
//	private PdfPCell createRightFooter(String str, int colspan) {
//		PdfPCell cell = new PdfPCell(new Phrase(str,font));
//		cell.setBorder(0);
//		cell.setColspan(colspan);
//		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		cell.setVerticalAlignment(Element.ALIGN_TOP);
//		cell.setPaddingTop(0);
//		cell.setPaddingBottom(8);
//		cell.setPaddingLeft(4);
//		cell.setPaddingRight(4);
//		//cell.setLeading(0f, 1.2f);
//		return cell;
//	}
	
	private PdfPCell createCell(String str, int border) {
		return createCell(str, border, 1, 1);
	}
	
	private PdfPCell createCell(String str, int colspan, int rowspan) {
		PdfPCell cell = new PdfPCell(new Phrase(str, font));
		//cell.setBorder(border);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setLeading(0f, 1.2f);
		return cell;
	}
	
	private PdfPCell createCell(String str, int border, int colspan, int rowspan) {
		PdfPCell cell = new PdfPCell(new Phrase(str, font));
		cell.setBorder(border);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setLeading(0f, 1.2f);
		return cell;
	}
	
	private PdfPCell createCell(Phrase phrase) {
		PdfPCell cell = new PdfPCell(phrase);
		//cell.setBorder(border);
		//cell.setColspan(colspan);
		//cell.setRowspan(rowspan);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setLeading(0f, 1.2f);
		return cell;
	}
	
	private PdfPCell createDocumentFootCell(String str) {
		PdfPCell cell = new PdfPCell(new Phrase(str, font));
		cell.setBorder(0);
		//cell.setColspan(colspan);
		//cell.setRowspan(rowspan);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setLeading(0f, 1.2f);
		return cell;
	}
	
	private PdfPCell createSignCell(String str) throws Exception {
		PdfPCell cell = new PdfPCell(new Phrase(str, signFont));
		//cell.setBorder(1);
		cell.setBorderWidth(1.2f);
		cell.setBorderColor(BaseColor.RED);
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(0);
		cell.setPaddingBottom(8);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setLeading(0f, 1.2f);
		return cell;
	}
	
	private String formatFileSize(String bytes) {
		BigDecimal KB = new BigDecimal(1024);
		BigDecimal MB = new BigDecimal(1024 * 1024);
		int SCALE = new Integer(2).intValue();
		
		try {
			BigDecimal result = new BigDecimal(bytes).divide(KB, SCALE, BigDecimal.ROUND_HALF_UP);
			if (result.intValue() < 1000) {
				return result.toString() + "KB";
			} else {
				result = new BigDecimal(bytes).divide(MB, SCALE, BigDecimal.ROUND_HALF_UP);
				return result.toString() + "MB";
			}
		} catch (Exception e) {
			return bytes;
		}
	}
	
//	private String formatCrmyyidno(String crmyy, String crmid, String crmno) {
//		if("".equals(crmyy) && "".equals(crmid) && "".equals(crmno)){
//			return "";
//		} else {
//			return crmyy + "." + crmid + "." + crmno;
//		}
//	}
	
	private String getGSOS08Argvl(String prgid, String argnm) {
		StringBuffer sql = new StringBuffer();
		sql.append("select argvl from gso..s08 where prgid = '" + prgid + "' and argnm = '" + argnm + "'");

		String argvl = "";
		List s08MapList = sqlDBUtility.queryForList("UTIL.executeSelectSql", sql.toString());
		if (s08MapList.size() > 0) {
			HashMap map = (HashMap) s08MapList.get(0);
			argvl = check.checkNull(map.get("ARGVL"), "").toString();
		}
		return argvl;
	}
	
	private void initFont() throws Exception {
		logger.info("font path = [" + FontManager.getFontPath(true) + "]");
		// 指定要使用的字型(KAIU.TTF為Windows內建的標楷體)
		// C:\WINDOWS\Fonts\KAIU.TTF
		String winFont = "KAIU.TTF";
		BaseFont bf = BaseFont.createFont(FontManager.getFontPath(true) + File.separator + winFont, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 設定中文字型(BaseFont、字型大小、字型型態)
		font = new Font(bf, 12, Font.NORMAL);
		
		redFont = new Font(bf, 12, Font.NORMAL);
		redFont.setColor(BaseColor.RED);
		
		blueFont = new Font(bf, 12, Font.NORMAL);
		blueFont.setColor(BaseColor.BLUE);
		
		headerFont = new Font(bf, 20, Font.NORMAL);
		
		signFont = new Font(bf, 10, Font.NORMAL);
		signFont.setColor(BaseColor.RED);
	}
	
	private void testPDF() throws Exception {		
		try {
			int marginLeft = -20;
			int marginRight = -20;
			int marginTop = 40;
			int marginBottom = 40;
			
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter.getInstance(document, new FileOutputStream("C:\\test.pdf"));
			document.open();
			
			
			PdfPTable table = new PdfPTable(4);
			//table.setTotalWidth(2f);

			float[] columnWidths = new float[] { 14f, 3f, 2f, 1f };
			table.setWidths(columnWidths);

			table.addCell("");
			table.addCell("");
			//table.addCell(getSign("張律師"));
			table.addCell(createSignCell("張律師"));
			table.addCell("");
			
			document.add(table);

		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	private String getSyskdGNm() {
		List list = this.sqlDBUtility.queryForList("SYS.C79_syskdG_select");
		return list.size()==0 ? "內政部移民署" : ((HashMap)list.get(0)).get("DS").toString();
	}
	
	public static void main(String[] args){
		try{
			GeneratePDF pdf = new GeneratePDF();
			pdf.testPDF();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}*/