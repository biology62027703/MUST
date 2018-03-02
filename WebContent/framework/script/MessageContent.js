/** 
 * @fileoverview 定義訊息內容的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */
var	MessageHashContent_	=	new Hashtable();

//------0: 前端一般性錯誤------------------
MessageHashContent_.put('001', '帳號密碼錯誤或無使用權限,請重新登入');
MessageHashContent_.put('002', '您尚未登入本系統,請重新登入');
MessageHashContent_.put('003', '您無權限使用本系統之功能, 請通知管理人員');
MessageHashContent_.put('004', '系統發生錯誤, 是否檢視詳細錯誤資訊??');
MessageHashContent_.put('005', '是否檢視詳細錯誤資訊??');
MessageHashContent_.put('006', '查無符合資料');
//-----------------------------------------

//------1: Javascript 檢查錯誤或提示警告---
MessageHashContent_.put('101', '該欄位不可為空白');
MessageHashContent_.put('102', '該欄位必須為英文字');
MessageHashContent_.put('103', '該欄位必須為數字型態');
MessageHashContent_.put('104', '該欄位必須為正數字型態');
MessageHashContent_.put('105', '該欄位必須為整數型態');
MessageHashContent_.put('106', '該欄位必須為正整數型態');
MessageHashContent_.put('107', '日期格式輸入錯誤');
MessageHashContent_.put('108', '時間格式輸入錯誤');
MessageHashContent_.put('109', '身份証字號輸入錯誤');
MessageHashContent_.put('110', '統一編號輸入錯誤');
MessageHashContent_.put('111', '該欄位長度不可超過 @ 字元');
//2007.08.31 modified by lewiswang [#148]
MessageHashContent_.put('112', '該欄位長度超出 @ 中文字部分已擷取');
MessageHashContent_.put('113', '請使用輔助視窗輸入');
MessageHashContent_.put('114', '輸入長度必須等於');
MessageHashContent_.put('115', '日期起迄區間輸入錯誤');
MessageHashContent_.put('116', '該欄位必須為英數字');
MessageHashContent_.put('117', '起迄區間資料輸入錯誤');
MessageHashContent_.put('118', '所輸入的內容過長');
MessageHashContent_.put('119', '該欄位必須為民國年月');
MessageHashContent_.put('120', '該欄位必須為民國年');
MessageHashContent_.put('121', '是否確定刪除資料?');
MessageHashContent_.put('122', '請先查詢資料出來再進行刪除動作');
MessageHashContent_.put('123', '該欄位必須落於區間內');
MessageHashContent_.put('124', '請先選取要處理的資料再作處理');
MessageHashContent_.put('125', '請先查詢資料出來再進行列印動作');
//-----------------------------------------

//------2: 保留----------------------------
//'201'
//-----------------------------------------

//------3: 保留----------------------------
//'301'
//-----------------------------------------

//------4: jsp 資料庫處理錯誤或提示警告----
MessageHashContent_.put('401', '無法連接資料庫');
MessageHashContent_.put('402', '新增資料重複');
MessageHashContent_.put('499', '資料庫發生未知錯誤');
//-----------------------------------------


//------5: jsp IO 處理錯誤或提示警告-------
MessageHashContent_.put('501', '檔案讀取失敗');
MessageHashContent_.put('502', '檔案輸出失敗');
MessageHashContent_.put('503', '檔案未知編碼錯誤');
MessageHashContent_.put('504', '請選擇欲上傳之檔案');
MessageHashContent_.put('505', '檔案讀取有誤,請確認該檔案是否存在');
MessageHashContent_.put('506', '檔案上傳失敗');
MessageHashContent_.put('507', '檔案已存在');
MessageHashContent_.put('599', '檔案存取發生未知錯誤');
//----------------------------------------

//------6: 保留----------------------------
//'601'
//-----------------------------------------

//------7: 保留----------------------------
//'701'
//-----------------------------------------

//------8: 保留----------------------------
//'801'
//-----------------------------------------

//------9: 後端一般性錯誤------------------
MessageHashContent_.put('901', '上傳作業發生錯誤');
MessageHashContent_.put('902', '信件傳送失敗');
MessageHashContent_.put('903', '無法設定成Binary的上傳格式');
MessageHashContent_.put('904', '無法設定成Ascii的上傳格式');
MessageHashContent_.put('905', '無法連結遠端FTP Server,或不正確的ID/PASSWORD');
MessageHashContent_.put('906', '找不到欲上傳的檔案');
MessageHashContent_.put('907', '上傳過程中發生IO讀寫錯誤');
MessageHashContent_.put('908', '無法結束FTP連結');
MessageHashContent_.put('909', '無法設定成Binary的下載格式');
MessageHashContent_.put('910', '無法設定成Ascii的下載格式');
MessageHashContent_.put('911', '找不到欲下載的檔案');
MessageHashContent_.put('912', '下載過程中發生IO讀寫錯誤');
MessageHashContent_.put('913', '無此功能之權限');
MessageHashContent_.put('914', '此資料已被其他人修正過，是否強制修正為目前資料??');
MessageHashContent_.put('999', '系統發生未知錯誤');
//-----------------------------------------

//------A: 一般性之正常提示訊息------------
MessageHashContent_.put('A01', '資料新增成功');
MessageHashContent_.put('A02', '資料修改成功');
MessageHashContent_.put('A03', '資料刪除成功');
MessageHashContent_.put('A04', '確定刪除此筆資料?');
MessageHashContent_.put('A05', '請選擇一筆資料');
MessageHashContent_.put('A06', '無符合條件資料');
MessageHashContent_.put('A07', '資料處理完畢');
MessageHashContent_.put('A08', '確定執行此作業?');
MessageHashContent_.put('A09', '已執行過此作業,是否重新執行?');
//-----------------------------------------

//===========================================================//
/** 定義 MYSQL 的錯誤訊息 @private */
MessageHashContent_.put('DB_1062', '新增資料重複, ');
MessageHashContent_.put('DB_1048', '欄位資料不可為空, ');

/** 定義 ORACLE 的錯誤訊息 @private */
MessageHashContent_.put('DB_1', '新增資料重複, ');

/** 定義 MSSQL 的錯誤訊息 @private */
MessageHashContent_.put('DB_2627', '新增資料重複, ');