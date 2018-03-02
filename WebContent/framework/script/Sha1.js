/**
 * @fileoverview 定義處理 SHA1 編碼的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理 SHA1 編碼的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function SHA1()
{
}

/**
 * @final
 * @private
 */
SHA1.chrsz = 8;

/**
 * 將字串編碼成 SHA1 格式
 * @param str 要編碼的字串 
 * @return 編碼後的字串
 * @type String
 */
SHA1.encode=function(str)
{
	return SHA1.binb2hex(SHA1.core_sha1(SHA1.str2binb(str),str.length * SHA1.chrsz));
}

/**
 * 將字串編碼成 HMAC-SHA1 格式
 * @param str 要編碼的字串 
 * @param key 編碼所用的金鑰
 * @type String
 */
SHA1.encodeHMAC=function(str,key)
{
	return SHA1.binb2hex(SHA1.core_hmac_sha1(str,key));
}

/**
 * @private
 */
SHA1.core_sha1=function($4_, $5_)
{
  $4_[$5_ >> 5] |= 0x80 << (24 - $5_ % 32);
  $4_[(($5_ + 64 >> 9) << 4) + 15] = $5_;

  var $6_ = new Array(80);
  var $7_ =  1732584193;
  var $8_ = -271733879;
  var $9_ = -1732584194;
  var $10_ =  271733878;
  var $11_ = -1009589776;

  for(var i = 0; i < $4_.length; i += 16)
  {
    var $12_ = $7_;
    var $13_ = $8_;
    var $14_ = $9_;
    var $15_ = $10_;
    var $16_ = $11_;

    for(var j = 0; j < 80; j++)
    {
      if(j < 16) $6_[j] = $4_[i + j];
      else $6_[j] = SHA1.rol($6_[j-3] ^ $6_[j-8] ^ $6_[j-14] ^ $6_[j-16], 1);
      var $17_ = SHA1.safe_add(SHA1.safe_add(SHA1.rol($7_, 5), SHA1.sha1_ft(j, $8_, $9_, $10_)), SHA1.safe_add(SHA1.safe_add($11_, $6_[j]), SHA1.sha1_kt(j)));
      $11_ = $10_;
      $10_ = $9_;
      $9_ = SHA1.rol($8_, 30);
      $8_ = $7_;
      $7_ = $17_;
    }

    $7_ = SHA1.safe_add($7_, $12_);
    $8_ = SHA1.safe_add($8_, $13_);
    $9_ = SHA1.safe_add($9_, $14_);
    $10_ = SHA1.safe_add($10_, $15_);
    $11_ = SHA1.safe_add($11_, $16_);
  }
  return new Array($7_, $8_, $9_, $10_, $11_);
}

/**
 * @private
 */
SHA1.sha1_ft=function($18_, $19_, $20_, $21_)
{
  if($18_ < 20) return ($19_ & $20_) | ((~$19_) & $21_);
  if($18_ < 40) return $19_ ^ $20_ ^ $21_;
  if($18_ < 60) return ($19_ & $20_) | ($19_ & $21_) | ($20_ & $21_);
  return $19_ ^ $20_ ^ $21_;
}

/**
 * @private
 */
SHA1.sha1_kt=function($22_)
{
  return ($22_ < 20) ?  1518500249 : ($22_ < 40) ?  1859775393 : ($22_ < 60) ? -1894007588 : -899497514;
}

/**
 * @private
 */
SHA1.core_hmac_sha1=function($23_, $24_)
{
  var $25_ = SHA1.str2binb($23_);
  if($25_.length > 16) $25_ = SHA1.core_sha1($25_, $23_.length * SHA1.chrsz);

  var $26_ = new Array(16);
  var $27_ = new Array(16);
  
  for(var i = 0; i < 16; i++)
  {
    $26_[i] = $25_[i] ^ 0x36363636;
    $27_[i] = $25_[i] ^ 0x5C5C5C5C;
  }

  return SHA1.core_sha1($27_.concat(SHA1.core_sha1($26_.concat(SHA1.str2binb($24_)), 512 + $24_.length * SHA1.chrsz)), 512 + 160);
}

/**
 * @private
 */
SHA1.safe_add=function($28_, $29_)
{
  var $30_ = ($28_ & 0xFFFF) + ($29_ & 0xFFFF);
  var $31_ = ($28_ >> 16) + ($29_ >> 16) + ($30_ >> 16);
  return ($31_ << 16) | ($30_ & 0xFFFF);
}

/**
 * @private
 */
SHA1.rol=function($32_, $33_)
{
  return ($32_ << $33_) | ($32_ >>> (32 - $33_));
}

/**
 * @private
 */
SHA1.str2binb=function($34_)
{
  var $35_ = new Array();
  var $36_ = (1 << SHA1.chrsz) - 1;
  for(var i = 0; i < $34_.length * SHA1.chrsz; i += SHA1.chrsz)
    $35_[i>>5] |= ($34_.charCodeAt(i / SHA1.chrsz) & $36_) << (32 - SHA1.chrsz - i%32);
  return $35_;
}

/**
 * @private
 */
SHA1.binb2hex=function($37_)
{
  var $38_ = "0123456789abcdef";
  var $39_ = "";
  for(var i = 0; i < $37_.length * 4; i++)
  {
    $39_ += $38_.charAt(($37_[i>>2] >> ((3 - i%4)*8+4)) & 0xF)+$38_.charAt(($37_[i>>2] >> ((3 - i%4)*8  )) & 0xF);
  }
  return $39_;
}