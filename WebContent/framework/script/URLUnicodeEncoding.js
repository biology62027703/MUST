/** 
 * @fileoverview 定義處理UNICODE型態的URL編碼&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 將字串編碼成URL格式
 * @param {String} url 要編碼的字串
 * @return 編碼後的字串
 * @type String
 */
function encodeURL(url)
{
	return encodeURIComponent(url);
}

/**
 * 將編碼的URL字串還原
 * @param {String} url 要解碼的字串
 * @return 解碼後的字串
 * @type String
 */
function decodeURL(url)
{
	return decodeURIComponent(url);
}
