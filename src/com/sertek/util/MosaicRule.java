package com.sertek.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sertek.db.DBUtility;

/*
 --------------------------------------------------------------------------------
 問題單號：Bug #4092 - 裁判書正本製作 CHD1E01
 修改摘要：增加「是否為性侵害案件」欄位
 修改人員：Eason
 修改日期：0980518
 更新版本：V9806-裁判書遮隱專版
 --------------------------------------------------------------------------------
問題單號：Bug #8205 - JUD0CY1010015-書記官裁判書遮隱
 修改摘要：書記官裁判書遮隱，增加遮隱項及調整
 更新版本：V10112
 修改人員：Carl
 修改日期：1011108
修改程式：CHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
問題單號：Bug #8575 - JUD0MC1020004_JUD0MY1020003
 修改摘要：於裁判書遮隱作業中增加 停止緊急安置、強制住院 等關鍵字詞為裁判書不公開關鍵字詞
 更新版本：V1020325-裁判書上傳
 修改人員：Carl
 修改日期：1020313
 修改程式：CHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
 */
public class MosaicRule {

	private static final String orRegex = "|"; // or

	private static final String spaceRegex = "[\\r\\n\\s　]{0,10}";

	public static List getCutWordList(DBUtility db) {
		List cutWordList = new ArrayList();
		String sql = "SELECT PARAHEAD FROM JD..S1F WHERE PRGID = 'SHD6B01'";
		cutWordList.addAll(db.doSqlSelect(sql, 1, false));

		return cutWordList;
	}
	
	/**
	 * 非結尾字詞 ，預設值「交通事件裁決所,事件紀念基金會」
	 * @param db
	 * @return
	 */
	public static List getNoEndWordList(DBUtility db) {
		List cutWordList = new ArrayList();
		cutWordList.add("交通事件裁決所");
	    cutWordList.add("事件紀念基金會");
		return cutWordList;
	}

	/**
	 * 不遮隱字詞 "Ltd.|大使|大隊|大學|小吃|小學|工務|工程|工會|工業|工廠....."<br>
	 *
	 * @return
	 */
	public static String getNoHideNameRegex() {
		List list = new ArrayList();

		list.add("Ltd.");
		list.add("大使");
		list.add("大隊");
		list.add("大學");
		list.add("小吃");
		list.add("小學");
		list.add("工務");
		list.add("工程");
		list.add("工會");
		list.add("工業");
		list.add("工廠");
		list.add("中心");
		list.add("中華民國");
		list.add("中學");
		list.add("之家");
		list.add("內政");
		list.add("公司");
		list.add("公所");
		list.add("公園");
		list.add("公會");
		list.add("公路");
		list.add("分局");
		list.add("支付");
		list.add("文獻");
		list.add("木材");
		list.add("水利");
		list.add("主計");
		list.add("代表");
		list.add("司令");
		list.add("台北");
		list.add("外交");
		list.add("民政");
		list.add("生活");
		list.add("交通");
		list.add("交換");
		list.add("休閒");
		list.add("企業");
		list.add("印刷");
		list.add("印製");
		list.add("同鄉");
		list.add("吊車");
		list.add("合作");
		list.add("地政");
		list.add("安全");
		list.add("年度");
		list.add("有限");
		list.add("考試");
		list.add("考選");
		list.add("自辦市地");
		list.add("行政");
		list.add("行銷");
		list.add("作戰");
		list.add("局長");
		list.add("役政");
		list.add("戒治");
		list.add("改良");
		list.add("材料");
		list.add("巡防");
		list.add("防治");
		list.add("事務");
		list.add("兒童");
		list.add("協會");
		list.add("委員");
		list.add("服務");
		list.add("林務");
		list.add("法人");
		list.add("法院");
		list.add("法務");
		list.add("油品");
		list.add("物園");
		list.add("股份");
		list.add("金融");
		list.add("青年");
		list.add("信用");
		list.add("信託");
		list.add("保持");
		list.add("保險");
		list.add("保護");
		list.add("前科");
		list.add("律師");
		list.add("指揮");
		list.add("政府");
		list.add("洗衣");
		list.add("看守");
		list.add("砂石");
		list.add("研究");
		list.add("研習");
		list.add("科技");
		list.add("紀念");
		list.add("美食");
		list.add("美術");
		list.add("軍備");
		list.add("軍醫");
		list.add("食品");
		list.add("旅館");
		list.add("書店");
		list.add("株式");
		list.add("氣象");
		list.add("消防");
		list.add("秘書");
		list.add("航空");
		list.add("訓練");
		list.add("財政");
		list.add("財產");
		list.add("馬戲");
		list.add("健康");
		list.add("參事");
		list.add("商行");
		list.add("商務");
		list.add("商號");
		list.add("國史");
		list.add("國防");
		list.add("國庫");
		list.add("國泰世華");
		list.add("國稅");
		list.add("基金");
		list.add("執行");
		list.add("培訓");
		list.add("專賣");
		list.add("情報");
		list.add("教育");
		list.add("教養");
		list.add("祭祀公業");
		list.add("移民");
		list.add("莊號");
		list.add("責任");
		list.add("貨運");
		list.add("造幣");
		list.add("部隊");
		list.add("鳥園");
		list.add("博物");
		list.add("報社");
		list.add("期貨");
		list.add("港務");
		list.add("渡假");
		list.add("測量");
		list.add("發展");
		list.add("裁決");
		list.add("視聽");
		list.add("貿易");
		list.add("飯店");
		list.add("搬運");
		list.add("新聞");
		list.add("會計");
		list.add("會報");
		list.add("經濟");
		list.add("補習");
		list.add("試驗");
		list.add("資料");
		list.add("資產");
		list.add("農場");
		list.add("農會");
		list.add("農糧");
		list.add("電訊");
		list.add("電臺");
		list.add("電器");
		list.add("圖書");
		list.add("實業");
		list.add("漁會");
		list.add("漁業");
		list.add("監理");
		list.add("監察");
		list.add("監獄");
		list.add("管制");
		list.add("管理");
		list.add("精品");
		list.add("製藥");
		list.add("輔育");
		list.add("輔導");
		list.add("銀行");
		list.add("銓敘");
		list.add("領事");
		list.add("餅乾");
		list.add("劇團");
		list.add("審計");
		list.add("樂園");
		list.add("樂團");
		list.add("碾米");
		list.add("稽徵");
		list.add("編譯");
		list.add("衛生");
		list.add("調查");
		list.add("賦稅");
		list.add("輪胎");
		list.add("學校");
		list.add("學院");
		list.add("機車");
		list.add("機動");
		list.add("辦公");
		list.add("辦事");
		list.add("檢查");
		list.add("檢疫");
		list.add("檢察");
		list.add("檢驗");
		list.add("營建");
		list.add("營造");
		list.add("療養");
		list.add("總局");
		list.add("總統");
		list.add("總隊");
		list.add("繁殖");
		list.add("講習");
		list.add("簡易");
		list.add("醫院");
		list.add("藥品");
		list.add("關稅");
		list.add("礦務");
		list.add("籌備");
		list.add("議會");
		list.add("警政");
		list.add("警察");
		list.add("辯護");
		list.add("體育");
		list.add("觀光");

		StringBuffer sb = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).toString());
				sb.append(orRegex);
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 性侵害或人口販運案件關鍵詞<br>
	 *
	 * @return
	 */
	public static String getSexCaseRegex() {
		List list = new ArrayList();

		// 性侵害
		list.add("妨害性自主");
		list.add("性交");
		list.add("性侵害");
		list.add("強姦");
		list.add("姦淫");
		list.add("輪姦");
		list.add("猥褻");
		list.add("性騷擾");
		list.add("性器");   //[A]Carl #8205
		list.add("性行為"); //[A]Carl #8205
		list.add("騷擾");   //[A]Carl #8205
		list.add("淫穢");   //[A]Carl #8205
		list.add("性別");   //[A]Carl #8205
		
		// 性霸凌
		list.add("性霸凌"); //[A]Carl #8205
		list.add("霸凌");   //[A]Carl #8205
		
		// 兒童
		list.add("兒童");   //[A]Carl #8205
		list.add("幼兒");   //[A]Carl #8205
		list.add("少年");   //[A]Carl #8205
		list.add("少女");   //[A]Carl #8205
		list.add("未成年子"); //[A]Carl #8205
		list.add("未成年女"); //[A]Carl #8205
		
		// 人口販運
		list.addAll(getTraffic());
		return listSpaceRegex(list);
	}

	/** [A]Carl #8205
	 * 設定遮當事人姓名之相關鍵詞
	 * @return
	 */
	public static String getSexCaseCoverNameRegex() {
		List list = new ArrayList();

		list.add("未滿1");
		list.add("未滿十");
		list.add("少年法庭");
		list.add("兒童福利法");
		list.add("未成年");
		list.add("未成年子");
		list.add("未成年女");
		list.add("兒童");
		list.add("幼兒");
		list.add("少年");
		list.add("少女");
		list.add("兒童及少年福利法");
		list.add("少上");
		list.add("少年保護");
		list.add("少年調查");
		list.add("少連偵");
		list.add("少偵");
		list.add("少年事件處理法");
		list.add("收養");
		list.add("兒童及少年福利與權益保障法");
		list.add("妨害性自主");
		list.add("刑法第二百二十一條");
		list.add("刑法第二百二十二條");
		list.add("刑法第二百二十四條");
		list.add("刑法第二百二十四條之一");
		list.add("刑法第二百二十五條");
		list.add("刑法第二百二十六條");
		list.add("刑法第二百二十六條之一");
		list.add("刑法第二百二十七條");
		list.add("刑法第二百二十七條之一");
		list.add("刑法第二百二十八條");
		list.add("刑法第二百二十九條");
		list.add("刑法第二百三十三條");
		list.add("刑法第296-1條");
		list.add("人口販運");
		list.add("勞動基準法第75條");
		list.add("停止緊急安置");
		list.add("強制住院");
		list.add("性器");
		list.add("性行為");
		list.add("騷擾");
		list.add("淫穢");
		list.add("性別");
		list.add("強姦");
		list.add("姦淫");
		list.add("輪姦");
		list.add("猥褻");
		list.add("性交");
		list.add("性侵害");
		list.add("性騷擾");
		list.add("性霸凌");
		list.add("霸凌");

		return listSpaceRegex(list);
	}
	
	/**
	 * 人口販運案件關鍵詞<br>
	 *
	 * @return
	 */
	public static String getTrafficCaseRegex() {
		/*
		 人口販運、人口販運防制法第31條至第42條、
		 兒童及少年性交易防制條例第23條至第26條、
		 刑法第296條及第296條之1、刑法第298條、第299條、第300條、
		 勞動基準法第5、6、75、76、81條
		 */
		// 人口販運
		StringBuffer result = new StringBuffer();

		return listSpaceRegex(getTraffic());
	}

	/**
	 * 少年案件關鍵詞<br>
	 *
	 * @return
	 */
	public static String getJuvenileCaseRegex() {
		List list = new ArrayList();

        //Modified by Don 101.3.29 加第1筆
		list.add("未滿1");
        list.add("未滿十");
		list.add("少年法庭");
		list.add("未成年");
		list.add("兒童及少年福利法");
		list.add("少上");
		list.add("少年保護");
		list.add("少年調查");
		list.add("少連偵");
		list.add("少偵");
		list.add("少年事件處理法");
		//add by Don 101.3.29 Bug #7434 - JUD0MY1010001
		list.add("收養");
		list.add("兒童及少年福利與權益保障法");

		return listSpaceRegex(list);
	}
	
	/** [A]Carl #8205
	 * 判斷出現過的家庭暴力關鍵詞<br>
	 * @return
	 */
	public static String getViolenceCaseRegex() {
		List list = new ArrayList();
		list.add("家暴");
		list.add("家庭暴力");
		list.add("家護");
		list.add("家庭暴力防治法");
		list.add("保護令");

		return listSpaceRegex(list);
	}

	/** [A]Carl #8575
	 * 判斷出現過的其他不公開關鍵字<br>
	 * @return
	 */
	public static String getOtherNonDisclosureWordsRegex() {
		List list = new ArrayList();
		list.add("停止緊急安置");
		list.add("強制住院");
		return listSpaceRegex(list);
	}
	
	/**
	 * 和解筆錄關鍵詞<br>
	 *
	 * @return
	 */
	public static String getIsreconciliationRegex(String[] notitle) {
		List list = new ArrayList();

		for (int i = 0; i < notitle.length; i++) {
			list.add(notitle[i]);
			System.out.println("notitle["+i+"]="+notitle[i]);
		}

		return listSpaceRegex(list);
	}
	/** 
	 * 處理折行
	 * @param str
	 * @return
	 */
	private static String processSpaceRegex(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			sb.append(str.substring(i, i + 1));
			if (i < str.length() - 1) {
				sb.append(spaceRegex);
			}
		}
		return sb.toString();
	}
	
	/** [A]Carl #8205
	 * 找出出現在過的list文字
	 * @param list
	 * @return String
	 */
	public static String listSpaceRegex(List list){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			result.append(processSpaceRegex(list.get(i).toString()));
			if (i < list.size() - 1) {
				result.append(orRegex);
			}
		}
		return result.toString();
	}
	
	// 人口販運關係詞
	public static List getTraffic(){
		List list = new ArrayList();
		list.add("人口販運");
		list.add("人口販運防制法第31條至第42條");
		list.add("人口販運防制法第三一條至第四二條");
		list.add("人口販運防制法第三十一條至第四十二條");
		list.add("兒童及少年性交易防制條例第23條至第26條");
		list.add("兒童及少年性交易防制條例第二三條至第二六條");
		list.add("兒童及少年性交易防制條例第二十三條至第二十六條");
		list.add("兒童及少年性交易防制條例第23條");
		list.add("兒童及少年性交易防制條例第二三條");
		list.add("兒童及少年性交易防制條例第二十三條");
		list.add("兒童及少年性交易防制條例第24條");
		list.add("兒童及少年性交易防制條例第二四條");
		list.add("兒童及少年性交易防制條例第二十四條");
		list.add("兒童及少年性交易防制條例第25條");
		list.add("兒童及少年性交易防制條例第二五條");
		list.add("兒童及少年性交易防制條例第二十五條");
		list.add("兒童及少年性交易防制條例第26條");
		list.add("兒童及少年性交易防制條例第二六條");
		list.add("兒童及少年性交易防制條例第二十六條");
		list.add("刑法第296條及第296條之1");
		list.add("刑法第296條及第296條-1");
		list.add("刑法第二九六條及第二九六條之一");
		list.add("刑法第二九六條及第二九六條-一");
		list.add("刑法第二九六條及第二九六條-1");
		list.add("刑法第二九六條及第二九六條之1");
		list.add("刑法第二百九十六條及第二百九十六條之一");
		list.add("刑法第二百九十六條及第二百九十六條-一");
		list.add("刑法第二百九十六條及第二百九十六條-1");
		list.add("刑法第二百九十六條及第二百九十六條之1");
		list.add("刑法第296條之1");
		list.add("刑法第296條-1");
		list.add("刑法第296-1條");
		list.add("刑法第296之1條");
		list.add("刑法第二九六條之一");
		list.add("刑法第二百九十六條之一");
		list.add("刑法第二九六條-一");
		list.add("刑法第二百九十六條-一");
		list.add("刑法第二九六之一條");
		list.add("刑法第二百九十六之一條");
		list.add("刑法第296條");
		list.add("刑法第二九六條");
		list.add("刑法第二百九十六條");
		list.add("刑法第231條及第231條之1");
		list.add("刑法第231條及第231條-1");
		list.add("刑法第二三一條及第二三一條之一");
		list.add("刑法第二百三十一條及第二百三十一條-一");
		list.add("刑法第二百三十一條及第二百三十一條之一");
		list.add("刑法第231條之1");
		list.add("刑法第231條-1");
		list.add("刑法第231-1條");
		list.add("刑法第231之1條");
		list.add("刑法第二三一條之一");
		list.add("刑法第二百三十一條之一");
		list.add("刑法第二三一條-一");
		list.add("刑法第二百三十一條-一");
		list.add("刑法第二三一之一條");
		list.add("刑法第二百三十一之一條");
		list.add("刑法第231條");
		list.add("刑法第二三一條");
		list.add("刑法第二百三十一條");
		list.add("刑法第298條");
		list.add("刑法第二九八條");
		list.add("刑法第二百九十八條");
		list.add("第299條");
		list.add("第二九九條");
		list.add("第二百九十九條");
		list.add("刑法第299條");
		list.add("刑法第二九九條");
		list.add("刑法第二百九十九條");
		list.add("刑法第300條");
		list.add("刑法第三百條");
		list.add("第300條");
		list.add("第三百條");
		list.add("勞動基準法第75條");
		list.add("勞動基準法第七五條");
		list.add("勞動基準法第七十五條");
		list.add("勞動基準法第76條");
		list.add("勞動基準法第七六條");
		list.add("勞動基準法第七十六條");
		list.add("勞動基準法第75、76條");
		list.add("勞動基準法第七五、七六條");
		list.add("勞動基準法第七十五、七十六條");
		return list;
	}
}