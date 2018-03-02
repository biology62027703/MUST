<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">

<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:新細明體;
	panose-1:2 2 5 0 0 0 0 0 0 0;
	mso-font-alt:PMingLiU;
	mso-font-charset:136;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-1610611969 684719354 22 0 1048577 0;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:1;
	mso-generic-font-family:roman;
	mso-font-format:other;
	mso-font-pitch:variable;
	mso-font-signature:0 0 0 0 0 0;}
@font-face
	{font-family:Tahoma;
	panose-1:2 11 6 4 3 5 4 4 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-pitch:variable;
	mso-font-signature:-520081665 -1073717157 41 0 66047 0;}
@font-face
	{font-family:"\@新細明體";
	panose-1:2 2 5 0 0 0 0 0 0 0;
	mso-font-charset:136;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-1610611969 684719354 22 0 1048577 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:"新細明體","serif";
	mso-bidi-font-family:新細明體;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-priority:99;
	mso-style-link:"頁首 字元";
	margin:0cm;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	tab-stops:center 207.65pt right 415.3pt;
	layout-grid-mode:char;
	font-size:10.0pt;
	font-family:"新細明體","serif";
	mso-bidi-font-family:新細明體;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-priority:99;
	mso-style-link:"頁尾 字元";
	margin:0cm;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	tab-stops:center 207.65pt right 415.3pt;
	layout-grid-mode:char;
	font-size:10.0pt;
	font-family:"新細明體","serif";
	mso-bidi-font-family:新細明體;}
a:link, span.MsoHyperlink
	{mso-style-noshow:yes;
	mso-style-priority:99;
	color:blue;
	text-decoration:underline;
	text-underline:single;}
a:visited, span.MsoHyperlinkFollowed
	{mso-style-noshow:yes;
	mso-style-priority:99;
	color:purple;
	mso-themecolor:followedhyperlink;
	text-decoration:underline;
	text-underline:single;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
	{mso-style-priority:34;
	mso-style-unhide:no;
	mso-style-qformat:yes;
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:24.0pt;
	margin-bottom:.0001pt;
	mso-para-margin-top:0cm;
	mso-para-margin-right:0cm;
	mso-para-margin-bottom:0cm;
	mso-para-margin-left:2.0gd;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:"新細明體","serif";
	mso-bidi-font-family:新細明體;}
span.a
	{mso-style-name:"頁首 字元";
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:頁首;
	mso-ansi-font-size:10.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:"新細明體","serif";
	mso-ascii-font-family:新細明體;
	mso-fareast-font-family:新細明體;
	mso-hansi-font-family:新細明體;
	mso-bidi-font-family:新細明體;
	mso-font-kerning:0pt;}
span.a0
	{mso-style-name:"頁尾 字元";
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:頁尾;
	mso-ansi-font-size:10.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:"新細明體","serif";
	mso-ascii-font-family:新細明體;
	mso-fareast-font-family:新細明體;
	mso-hansi-font-family:新細明體;
	mso-bidi-font-family:新細明體;
	mso-font-kerning:0pt;}
.MsoChpDefault
	{mso-style-type:export-only;
	mso-default-props:yes;
	font-family:"Calibri","sans-serif";
	mso-bidi-font-family:"Times New Roman";
	mso-bidi-theme-font:minor-bidi;}
 /* Page Definitions */
 @page
	{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;
	mso-footnote-separator:url("NOTICE_MAIL_SAMPLE.files/header.htm") fs;
	mso-footnote-continuation-separator:url("NOTICE_MAIL_SAMPLE.files/header.htm") fcs;
	mso-endnote-separator:url("NOTICE_MAIL_SAMPLE.files/header.htm") es;
	mso-endnote-continuation-separator:url("NOTICE_MAIL_SAMPLE.files/header.htm") ecs;}
@page WordSection1
	{size:841.9pt 595.3pt;
	mso-page-orientation:landscape;
	margin:1.0cm 1.0cm 1.0cm 1.0cm;
	mso-header-margin:14.2pt;
	mso-footer-margin:19.85pt;
	mso-paper-source:0;
	layout-grid:18.0pt;}
div.WordSection1
	{page:WordSection1;}
 /* List Definitions */
 @list l0
	{mso-list-id:362755534;
	mso-list-type:hybrid;
	mso-list-template-ids:400349420 -1326958242 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}
@list l0:level1
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:18.0pt;
	text-indent:-18.0pt;}
@list l0:level2
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%2、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:48.0pt;
	text-indent:-24.0pt;}
@list l0:level3
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:72.0pt;
	text-indent:-24.0pt;}
@list l0:level4
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:96.0pt;
	text-indent:-24.0pt;}
@list l0:level5
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%5、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:120.0pt;
	text-indent:-24.0pt;}
@list l0:level6
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:144.0pt;
	text-indent:-24.0pt;}
@list l0:level7
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:168.0pt;
	text-indent:-24.0pt;}
@list l0:level8
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%8、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:192.0pt;
	text-indent:-24.0pt;}
@list l0:level9
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:216.0pt;
	text-indent:-24.0pt;}
@list l1
	{mso-list-id:982543029;
	mso-list-type:hybrid;
	mso-list-template-ids:-672786804 -356883930 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}
@list l1:level1
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:18.0pt;
	text-indent:-18.0pt;}
@list l1:level2
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%2、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:48.0pt;
	text-indent:-24.0pt;}
@list l1:level3
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:72.0pt;
	text-indent:-24.0pt;}
@list l1:level4
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:96.0pt;
	text-indent:-24.0pt;}
@list l1:level5
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%5、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:120.0pt;
	text-indent:-24.0pt;}
@list l1:level6
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:144.0pt;
	text-indent:-24.0pt;}
@list l1:level7
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:168.0pt;
	text-indent:-24.0pt;}
@list l1:level8
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%8、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:192.0pt;
	text-indent:-24.0pt;}
@list l1:level9
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:216.0pt;
	text-indent:-24.0pt;}
@list l2
	{mso-list-id:1417748504;
	mso-list-type:hybrid;
	mso-list-template-ids:1261493336 -1615330174 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}
@list l2:level1
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:18.0pt;
	text-indent:-18.0pt;}
@list l2:level2
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%2、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:48.0pt;
	text-indent:-24.0pt;}
@list l2:level3
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:72.0pt;
	text-indent:-24.0pt;}
@list l2:level4
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:96.0pt;
	text-indent:-24.0pt;}
@list l2:level5
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%5、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:120.0pt;
	text-indent:-24.0pt;}
@list l2:level6
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:144.0pt;
	text-indent:-24.0pt;}
@list l2:level7
	{mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:168.0pt;
	text-indent:-24.0pt;}
@list l2:level8
	{mso-level-number-format:ideograph-traditional;
	mso-level-text:%8、;
	mso-level-tab-stop:none;
	mso-level-number-position:left;
	margin-left:192.0pt;
	text-indent:-24.0pt;}
@list l2:level9
	{mso-level-number-format:roman-lower;
	mso-level-tab-stop:none;
	mso-level-number-position:right;
	margin-left:216.0pt;
	text-indent:-24.0pt;}
ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>

</head>

<body lang=ZH-TW link=blue vlink=purple style='tab-interval:24.0pt;text-justify-trim:
punctuation'>

<div class=WordSection1 style='layout-grid:18.0pt'>

<p class=MsoNormal style='mso-outline-level:1'><b><span style='font-size:20.0pt;
mso-ascii-font-family:Tahoma;mso-hansi-font-family:Tahoma;mso-bidi-font-family:
Tahoma;background:yellow;mso-highlight:yellow'>請款通知信</span></b><span
lang=EN-US style='font-size:20.0pt;font-family:"Tahoma","sans-serif"'><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
 mso-yfti-tbllook:1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width=525 valign=top style='width:394.0pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='mso-outline-level:1'><b><span lang=EN-US
  style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'>Subject:</span></b><span
  lang=EN-US style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'> </span><span
  lang=EN-US style='font-size:10.0pt;font-family:"Times New Roman","serif"'>MUST
  <b style='mso-bidi-font-weight:normal'><span style='color:red'>I141</span></b>
  </span><span style='font-size:10.0pt;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>權利金通知信：請款</span><span
  lang=EN-US style='font-size:10.0pt;font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span lang=EN-US
  style='font-size:10.0pt;font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span style='mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
  "Times New Roman"'>親愛的會員</span><span style='font-family:"Times New Roman","serif"'>
  </span><b style='mso-bidi-font-weight:normal'><span style='mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
  "Times New Roman";color:red'>金牌大風音樂文化股份有限公司</span></b><span style='font-family:
  "Times New Roman","serif"'> </span><span style='mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>您好：</span><span
  lang=EN-US style='font-family:"Times New Roman","serif"'><br>
  <br>
  </span><span style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman";mso-bidi-font-family:"Times New Roman"'>協會先行寄發「<b
  style='mso-bidi-font-weight:normal'><span style='color:blue'>年度使用報酬分配</span></b></span><b
  style='mso-bidi-font-weight:normal'><span style='font-family:"Times New Roman","serif";
  color:blue'> </span></b><b style='mso-bidi-font-weight:normal'><span
  style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
  mso-bidi-font-family:"Times New Roman";color:blue'>請款單</span></b><span
  style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
  mso-bidi-font-family:"Times New Roman"'>」給團體會員，請各會員依請款單開立權利金<b
  style='mso-bidi-font-weight:normal'><span style='color:blue'>總計</span></b>之發票。</span><span
  lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span lang=EN-US
  style='font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span style='mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
  "Times New Roman"'>協會收到發票後，<b style='mso-bidi-font-weight:normal'><span
  style='color:blue'>依請款作業統一</span></b>寄出權利金支票予團體會員。</span><span lang=EN-US
  style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span lang=EN-US
  style='font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span style='mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
  "Times New Roman"'>感謝配合營業稅作業。</span><span lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span lang=EN-US
  style='font-family:"Times New Roman","serif"'><br>
  </span><span style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
  "Times New Roman";mso-bidi-font-family:"Times New Roman"'>如有任何疑問，請聯絡：管理部財務</span><span
  style='font-family:"Times New Roman","serif"'> </span><span style='mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
  "Times New Roman"'>陳薐絪小姐</span><span lang=EN-US style='font-family:"Times New Roman","serif"'>
  (02)2511-0869</span><span style='mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>分機</span><span
  lang=EN-US style='font-family:"Times New Roman","serif"'>370</span><b><span
  lang=EN-US style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'><o:p></o:p></span></b></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal>&#10045;給各團體會員的請款單會作為附件隨著此<span lang=EN-US>email</span>寄發</p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><b><span style='font-size:20.0pt;mso-ascii-font-family:Tahoma;
mso-hansi-font-family:Tahoma;mso-bidi-font-family:Tahoma;background:yellow;
mso-highlight:yellow'>(解款)分發通知信</span></b></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
 mso-yfti-tbllook:1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width=716 valign=top style='width:537.0pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='mso-outline-level:1'><b><span lang=EN-US
  style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'>Subject:</span></b><span
  lang=EN-US style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'> </span><span
  lang=EN-US style='font-size:10.0pt;font-family:"Times New Roman","serif"'>MUST
  <b style='mso-bidi-font-weight:normal'><span style='color:red'>P142</span></b>
  </span><span style='font-size:10.0pt;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>權利金通知信：分發</span><span
  lang=EN-US style='font-size:10.0pt'><o:p></o:p></span></p>
  <p class=MsoNormal style='mso-outline-level:1'><span lang=EN-US
  style='font-size:10.0pt'><o:p>&nbsp;</o:p></span></p>
  <table class=MsoNormalTable border=0 cellpadding=0 style='mso-cellspacing:
   1.5pt;mso-yfti-tbllook:1184;mso-padding-alt:0cm 0cm 0cm 0cm'>
   <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
    <td style='padding:.75pt .75pt .75pt .75pt'>
    <p class=MsoNormal><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>親愛的會員</span><span
    style='font-family:"Times New Roman","serif";color:red'> </span><b
    style='mso-bidi-font-weight:normal'><span style='mso-ascii-font-family:
    "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
    "Times New Roman";color:red'>亞薩藝能事業有限公司</span></b><span style='font-family:
    "Times New Roman","serif";color:red'> </span><span style='mso-ascii-font-family:
    "Times New Roman";mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:
    "Times New Roman"'>您好：</span><span lang=EN-US style='font-family:"Times New Roman","serif"'><br>
    <br>
    <b style='mso-bidi-font-weight:normal'><span style='color:red'>P142</span></b></span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>的使用報酬</span><span style='font-family:
    "Times New Roman","serif"'> <b style='mso-bidi-font-weight:normal'><span
    lang=EN-US style='color:red'>NT$11,397</span></b><span lang=EN-US> </span></span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>已於</span><span style='font-family:
    "Times New Roman","serif"'> <b style='mso-bidi-font-weight:normal'><span
    lang=EN-US style='color:red'>2015.11.09</span></b><span lang=EN-US
    style='color:red'> </span></span><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>分發，明細表等相關檔案亦已上傳於</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'>MUST</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>會員專區。</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=EN-US style='font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
    <p class=MsoNormal><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>下載方式：</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd;text-indent:-18.0pt;mso-list:l0 level1 lfo2'><![if !supportLists]><span
    lang=EN-US style='font-family:"Times New Roman","serif";mso-fareast-font-family:
    "Times New Roman"'><span style='mso-list:Ignore'>1.<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span></span></span><![endif]><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>點選下方「附件」連結後下載</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd;text-indent:-18.0pt;mso-list:l0 level1 lfo2'><![if !supportLists]><span
    lang=EN-US style='font-family:"Times New Roman","serif";mso-fareast-font-family:
    "Times New Roman"'><span style='mso-list:Ignore'>2.<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span></span></span><![endif]><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>登入</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'>MUST</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>網站</span><span lang=EN-US><a
    href="http://www.must.org.tw/1login/1-1.asp" target="_blank"><span
    lang=EN-US style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
    "Times New Roman";mso-bidi-font-family:"Times New Roman"'><span lang=EN-US>「會員專區」</span></span></a></span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>進行下載</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=EN-US style='font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
    <p class=MsoNormal><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>提醒：</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd;text-indent:-18.0pt;mso-list:l1 level1 lfo3'><![if !supportLists]><span
    lang=EN-US style='font-family:"Times New Roman","serif";mso-fareast-font-family:
    "Times New Roman"'><span style='mso-list:Ignore'>1.<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span></span></span><![endif]><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>權利金明細表解壓縮密碼：</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd'><span style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
    "Times New Roman";mso-bidi-font-family:"Times New Roman"'>個人會員為身份證字號</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'> / </span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>護照號碼</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'> / </span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>居留證號</span><span style='mso-bidi-font-family:
    "Times New Roman"'>。</span><span lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd'><span style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
    "Times New Roman";mso-bidi-font-family:"Times New Roman"'>團體會員為公司統一編號。</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoListParagraph style='margin-left:18.0pt;mso-para-margin-left:
    0gd;text-indent:-18.0pt;mso-list:l1 level1 lfo3'><![if !supportLists]><span
    lang=EN-US style='font-family:"Times New Roman","serif";mso-fareast-font-family:
    "Times New Roman"'><span style='mso-list:Ignore'>2.<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span></span></span><![endif]><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>任何單次分配給付<b
    style='mso-bidi-font-weight:normal'><span style='color:blue'>個人會員</span></b>金額少於新台幣</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'>500</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>元者，將累積達新台幣</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'>500</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>元後予以合併付款；<b style='mso-bidi-font-weight:
    normal'><span style='color:blue'>團體會員</span></b>不受此限；<b style='mso-bidi-font-weight:
    normal'><span style='color:blue'>海外匯款</span></b>累積達新台幣</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'>2000</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>元以美金匯出</span><span
    style='mso-bidi-font-family:"Times New Roman"'>。</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=EN-US style='font-family:"Times New Roman","serif"'><o:p>&nbsp;</o:p></span></p>
    <p class=MsoNormal><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>如有任何疑問，歡迎來電</span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'>/</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'>信洽詢。</span><span lang=EN-US
    style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'>TEL</span><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman";
    mso-ansi-language:DE'>：</span><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'><span style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp; </span>(02)2511-0869
    #360 Ingrid </span><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>吳小姐</span><span
    lang=DE style='font-family:"Times New Roman","serif";mso-ansi-language:
    DE'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'>FAX</span><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman";
    mso-ansi-language:DE'>：</span><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'><span style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp; </span>(02)2511-0759<o:p></o:p></span></p>
    <p class=MsoNormal><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'>Email</span><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman";
    mso-ansi-language:DE'>：</span><span lang=DE style='font-family:"Times New Roman","serif";
    mso-ansi-language:DE'><span style='mso-tab-count:1'>&nbsp;&nbsp; </span></span><span
    lang=EN-US><a href="mailto:member@must.org.tw"><span lang=DE
    style='font-family:"Times New Roman","serif";mso-ansi-language:DE'>member@must.org.tw</span></a></span><span
    lang=DE style='font-family:"Times New Roman","serif";mso-ansi-language:
    DE'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
    <td style='padding:.75pt .75pt .75pt .75pt'>
    <p class=MsoNormal><span style='mso-ascii-font-family:"Times New Roman";
    mso-hansi-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman"'>附件</span><span
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman";mso-ansi-language:DE'>：</span><span
    lang=DE style='font-family:"Times New Roman","serif";mso-ansi-language:
    DE'><o:p></o:p></span></p>
    <p class=MsoNormal><span lang=EN-US><a
    href="http://www.must.org.tw/data_download.asp?dkey=Ly_kljp909IYXfkndH0RxbeDagxK@o_UpRPm1k5@E@mfBy0pTJETcuUFRibM&amp;dtype=em&amp;send_type=fml&amp;drpt=st&amp;dist_id=144"
    target="_blank"><span style='font-family:"Times New Roman","serif"'>2014</span><span
    lang=EN-US style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
    "Times New Roman";mso-bidi-font-family:"Times New Roman"'><span lang=EN-US>年第</span></span><span
    style='font-family:"Times New Roman","serif"'>2</span><span lang=EN-US
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'><span lang=EN-US>次公開播送權利金明細表</span></span></a></span><span
    lang=EN-US style='font-family:"Times New Roman","serif"'><o:p></o:p></span></p>
    <p class=MsoNormal style='margin-bottom:12.0pt'><span lang=EN-US><a
    href="http://www.must.org.tw/data_download.asp?dkey=Ly_kljp909IYXfkndH0RxbeDagxK@o_UpRPm1k5@E@mfBy0pTJETcuUFRibM&amp;dtype=em&amp;send_type=fml&amp;drpt=ds&amp;dist_id=144"
    target="_blank"><span style='font-family:"Times New Roman","serif"'>2014</span><span
    lang=EN-US style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:
    "Times New Roman";mso-bidi-font-family:"Times New Roman"'><span lang=EN-US>年第</span></span><span
    style='font-family:"Times New Roman","serif"'>2</span><span lang=EN-US
    style='mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman";
    mso-bidi-font-family:"Times New Roman"'><span lang=EN-US>次公開播送分配說明</span></span></a></span><u><span
    lang=EN-US style='font-family:"Times New Roman","serif";color:blue'><o:p></o:p></span></u></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal style='mso-outline-level:1'><b><span lang=EN-US
  style='font-size:10.0pt;font-family:"Tahoma","sans-serif"'><o:p></o:p></span></b></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='mso-outline-level:1'>&#10045;附件為各會員的明細表與分配說明的檔案連結</p>

</div>

</body>

</html>
