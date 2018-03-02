/** 
 * @fileoverview �w�q�B�zUNICODE���A��URL�s�X&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �N�r��s�X��URL�榡
 * @param {String} url �n�s�X���r��
 * @return �s�X�᪺�r��
 * @type String
 */
function encodeURL(url)
{
	return encodeURIComponent(url);
}

/**
 * �N�s�X��URL�r���٭�
 * @param {String} url �n�ѽX���r��
 * @return �ѽX�᪺�r��
 * @type String
 */
function decodeURL(url)
{
	return decodeURIComponent(url);
}
