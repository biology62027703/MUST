package com.sertek.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sertek.db.DBUtility;

/*
 --------------------------------------------------------------------------------
 ���D�渹�GBug #4092 - ���P�ѥ����s�@ CHD1E01
 �ק�K�n�G�W�[�u�O�_���ʫI�`�ץ�v���
 �ק�H���GEason
 �ק����G0980518
 ��s�����GV9806-���P�ѾB���M��
 --------------------------------------------------------------------------------
���D�渹�GBug #8205 - JUD0CY1010015-�ѰO�x���P�ѾB��
 �ק�K�n�G�ѰO�x���P�ѾB���A�W�[�B�����νվ�
 ��s�����GV10112
 �ק�H���GCarl
 �ק����G1011108
�ק�{���GCHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
���D�渹�GBug #8575 - JUD0MC1020004_JUD0MY1020003
 �ק�K�n�G����P�ѾB���@�~���W�[ ������w�m�B�j���| ������r�������P�Ѥ����}����r��
 ��s�����GV1020325-���P�ѤW��
 �ק�H���GCarl
 �ק����G1020313
 �ק�{���GCHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
 */
public class MosaicRule {

	private static final String orRegex = "|"; // or

	private static final String spaceRegex = "[\\r\\n\\s�@]{0,10}";

	public static List getCutWordList(DBUtility db) {
		List cutWordList = new ArrayList();
		String sql = "SELECT PARAHEAD FROM JD..S1F WHERE PRGID = 'SHD6B01'";
		cutWordList.addAll(db.doSqlSelect(sql, 1, false));

		return cutWordList;
	}
	
	/**
	 * �D�����r�� �A�w�]�ȡu��q�ƥ���M��,�ƥ��������|�v
	 * @param db
	 * @return
	 */
	public static List getNoEndWordList(DBUtility db) {
		List cutWordList = new ArrayList();
		cutWordList.add("��q�ƥ���M��");
	    cutWordList.add("�ƥ��������|");
		return cutWordList;
	}

	/**
	 * ���B���r�� "Ltd.|�j��|�j��|�j��|�p�Y|�p��|�u��|�u�{|�u�||�u�~|�u�t....."<br>
	 *
	 * @return
	 */
	public static String getNoHideNameRegex() {
		List list = new ArrayList();

		list.add("Ltd.");
		list.add("�j��");
		list.add("�j��");
		list.add("�j��");
		list.add("�p�Y");
		list.add("�p��");
		list.add("�u��");
		list.add("�u�{");
		list.add("�u�|");
		list.add("�u�~");
		list.add("�u�t");
		list.add("����");
		list.add("���إ���");
		list.add("����");
		list.add("���a");
		list.add("���F");
		list.add("���q");
		list.add("����");
		list.add("����");
		list.add("���|");
		list.add("����");
		list.add("����");
		list.add("��I");
		list.add("���m");
		list.add("���");
		list.add("���Q");
		list.add("�D�p");
		list.add("�N��");
		list.add("�q�O");
		list.add("�x�_");
		list.add("�~��");
		list.add("���F");
		list.add("�ͬ�");
		list.add("��q");
		list.add("�洫");
		list.add("��");
		list.add("���~");
		list.add("�L��");
		list.add("�L�s");
		list.add("�P�m");
		list.add("�Q��");
		list.add("�X�@");
		list.add("�a�F");
		list.add("�w��");
		list.add("�~��");
		list.add("����");
		list.add("�Ҹ�");
		list.add("�ҿ�");
		list.add("�ۿ쥫�a");
		list.add("��F");
		list.add("��P");
		list.add("�@��");
		list.add("����");
		list.add("�ЬF");
		list.add("�٪v");
		list.add("��}");
		list.add("����");
		list.add("����");
		list.add("���v");
		list.add("�ư�");
		list.add("�ൣ");
		list.add("��|");
		list.add("�e��");
		list.add("�A��");
		list.add("�L��");
		list.add("�k�H");
		list.add("�k�|");
		list.add("�k��");
		list.add("�o�~");
		list.add("����");
		list.add("�ѥ�");
		list.add("����");
		list.add("�C�~");
		list.add("�H��");
		list.add("�H�U");
		list.add("�O��");
		list.add("�O�I");
		list.add("�O�@");
		list.add("�e��");
		list.add("�߮v");
		list.add("����");
		list.add("�F��");
		list.add("�~��");
		list.add("�ݦu");
		list.add("���");
		list.add("��s");
		list.add("���");
		list.add("���");
		list.add("����");
		list.add("����");
		list.add("���N");
		list.add("�x��");
		list.add("�x��");
		list.add("���~");
		list.add("���]");
		list.add("�ѩ�");
		list.add("�覡");
		list.add("��H");
		list.add("����");
		list.add("����");
		list.add("���");
		list.add("�V�m");
		list.add("�]�F");
		list.add("�]��");
		list.add("����");
		list.add("���d");
		list.add("�Ѩ�");
		list.add("�Ӧ�");
		list.add("�Ӱ�");
		list.add("�Ӹ�");
		list.add("��v");
		list.add("�꨾");
		list.add("��w");
		list.add("����@��");
		list.add("��|");
		list.add("���");
		list.add("����");
		list.add("���V");
		list.add("�M��");
		list.add("����");
		list.add("�Ш|");
		list.add("�оi");
		list.add("�������~");
		list.add("����");
		list.add("����");
		list.add("�d��");
		list.add("�f�B");
		list.add("�y��");
		list.add("����");
		list.add("����");
		list.add("�ժ�");
		list.add("����");
		list.add("���f");
		list.add("���");
		list.add("�簲");
		list.add("���q");
		list.add("�o�i");
		list.add("���M");
		list.add("��ť");
		list.add("�T��");
		list.add("����");
		list.add("�h�B");
		list.add("�s�D");
		list.add("�|�p");
		list.add("�|��");
		list.add("�g��");
		list.add("�ɲ�");
		list.add("����");
		list.add("���");
		list.add("�겣");
		list.add("�A��");
		list.add("�A�|");
		list.add("�A³");
		list.add("�q�T");
		list.add("�q�O");
		list.add("�q��");
		list.add("�Ϯ�");
		list.add("��~");
		list.add("���|");
		list.add("���~");
		list.add("�ʲz");
		list.add("�ʹ�");
		list.add("�ʺ�");
		list.add("�ި�");
		list.add("�޲z");
		list.add("��~");
		list.add("�s��");
		list.add("���|");
		list.add("����");
		list.add("�Ȧ�");
		list.add("�ͱ�");
		list.add("���");
		list.add("�氮");
		list.add("�@��");
		list.add("�f�p");
		list.add("�ֶ�");
		list.add("�ֹ�");
		list.add("�V��");
		list.add("�]�x");
		list.add("�sĶ");
		list.add("�å�");
		list.add("�լd");
		list.add("��|");
		list.add("���L");
		list.add("�Ǯ�");
		list.add("�ǰ|");
		list.add("����");
		list.add("����");
		list.add("�줽");
		list.add("���");
		list.add("�ˬd");
		list.add("�ˬ�");
		list.add("�˹�");
		list.add("����");
		list.add("���");
		list.add("��y");
		list.add("���i");
		list.add("�`��");
		list.add("�`��");
		list.add("�`��");
		list.add("�c��");
		list.add("����");
		list.add("²��");
		list.add("��|");
		list.add("�ī~");
		list.add("���|");
		list.add("�q��");
		list.add("�w��");
		list.add("ĳ�|");
		list.add("ĵ�F");
		list.add("ĵ��");
		list.add("�G�@");
		list.add("��|");
		list.add("�[��");

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
	 * �ʫI�`�ΤH�f�c�B�ץ������<br>
	 *
	 * @return
	 */
	public static String getSexCaseRegex() {
		List list = new ArrayList();

		// �ʫI�`
		list.add("���`�ʦۥD");
		list.add("�ʥ�");
		list.add("�ʫI�`");
		list.add("�j��");
		list.add("���]");
		list.add("����");
		list.add("�T��");
		list.add("�����Z");
		list.add("�ʾ�");   //[A]Carl #8205
		list.add("�ʦ欰"); //[A]Carl #8205
		list.add("���Z");   //[A]Carl #8205
		list.add("�]©");   //[A]Carl #8205
		list.add("�ʧO");   //[A]Carl #8205
		
		// ���Q��
		list.add("���Q��"); //[A]Carl #8205
		list.add("�Q��");   //[A]Carl #8205
		
		// �ൣ
		list.add("�ൣ");   //[A]Carl #8205
		list.add("����");   //[A]Carl #8205
		list.add("�֦~");   //[A]Carl #8205
		list.add("�֤k");   //[A]Carl #8205
		list.add("�����~�l"); //[A]Carl #8205
		list.add("�����~�k"); //[A]Carl #8205
		
		// �H�f�c�B
		list.addAll(getTraffic());
		return listSpaceRegex(list);
	}

	/** [A]Carl #8205
	 * �]�w�B��ƤH�m�W���������
	 * @return
	 */
	public static String getSexCaseCoverNameRegex() {
		List list = new ArrayList();

		list.add("����1");
		list.add("�����Q");
		list.add("�֦~�k�x");
		list.add("�ൣ�֧Q�k");
		list.add("�����~");
		list.add("�����~�l");
		list.add("�����~�k");
		list.add("�ൣ");
		list.add("����");
		list.add("�֦~");
		list.add("�֤k");
		list.add("�ൣ�Τ֦~�֧Q�k");
		list.add("�֤W");
		list.add("�֦~�O�@");
		list.add("�֦~�լd");
		list.add("�ֳs��");
		list.add("�ְ�");
		list.add("�֦~�ƥ�B�z�k");
		list.add("���i");
		list.add("�ൣ�Τ֦~�֧Q�P�v�q�O�٪k");
		list.add("���`�ʦۥD");
		list.add("�D�k�ĤG�ʤG�Q�@��");
		list.add("�D�k�ĤG�ʤG�Q�G��");
		list.add("�D�k�ĤG�ʤG�Q�|��");
		list.add("�D�k�ĤG�ʤG�Q�|�����@");
		list.add("�D�k�ĤG�ʤG�Q����");
		list.add("�D�k�ĤG�ʤG�Q����");
		list.add("�D�k�ĤG�ʤG�Q�������@");
		list.add("�D�k�ĤG�ʤG�Q�C��");
		list.add("�D�k�ĤG�ʤG�Q�C�����@");
		list.add("�D�k�ĤG�ʤG�Q�K��");
		list.add("�D�k�ĤG�ʤG�Q�E��");
		list.add("�D�k�ĤG�ʤT�Q�T��");
		list.add("�D�k��296-1��");
		list.add("�H�f�c�B");
		list.add("�Ұʰ�Ǫk��75��");
		list.add("������w�m");
		list.add("�j���|");
		list.add("�ʾ�");
		list.add("�ʦ欰");
		list.add("���Z");
		list.add("�]©");
		list.add("�ʧO");
		list.add("�j��");
		list.add("���]");
		list.add("����");
		list.add("�T��");
		list.add("�ʥ�");
		list.add("�ʫI�`");
		list.add("�����Z");
		list.add("���Q��");
		list.add("�Q��");

		return listSpaceRegex(list);
	}
	
	/**
	 * �H�f�c�B�ץ������<br>
	 *
	 * @return
	 */
	public static String getTrafficCaseRegex() {
		/*
		 �H�f�c�B�B�H�f�c�B����k��31���ܲ�42���B
		 �ൣ�Τ֦~�ʥ��������Ҳ�23���ܲ�26���B
		 �D�k��296���β�296����1�B�D�k��298���B��299���B��300���B
		 �Ұʰ�Ǫk��5�B6�B75�B76�B81��
		 */
		// �H�f�c�B
		StringBuffer result = new StringBuffer();

		return listSpaceRegex(getTraffic());
	}

	/**
	 * �֦~�ץ������<br>
	 *
	 * @return
	 */
	public static String getJuvenileCaseRegex() {
		List list = new ArrayList();

        //Modified by Don 101.3.29 �[��1��
		list.add("����1");
        list.add("�����Q");
		list.add("�֦~�k�x");
		list.add("�����~");
		list.add("�ൣ�Τ֦~�֧Q�k");
		list.add("�֤W");
		list.add("�֦~�O�@");
		list.add("�֦~�լd");
		list.add("�ֳs��");
		list.add("�ְ�");
		list.add("�֦~�ƥ�B�z�k");
		//add by Don 101.3.29 Bug #7434 - JUD0MY1010001
		list.add("���i");
		list.add("�ൣ�Τ֦~�֧Q�P�v�q�O�٪k");

		return listSpaceRegex(list);
	}
	
	/** [A]Carl #8205
	 * �P�_�X�{�L���a�x�ɤO�����<br>
	 * @return
	 */
	public static String getViolenceCaseRegex() {
		List list = new ArrayList();
		list.add("�a��");
		list.add("�a�x�ɤO");
		list.add("�a�@");
		list.add("�a�x�ɤO���v�k");
		list.add("�O�@�O");

		return listSpaceRegex(list);
	}

	/** [A]Carl #8575
	 * �P�_�X�{�L����L�����}����r<br>
	 * @return
	 */
	public static String getOtherNonDisclosureWordsRegex() {
		List list = new ArrayList();
		list.add("������w�m");
		list.add("�j���|");
		return listSpaceRegex(list);
	}
	
	/**
	 * �M�ѵ��������<br>
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
	 * �B�z���
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
	 * ��X�X�{�b�L��list��r
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
	
	// �H�f�c�B���Y��
	public static List getTraffic(){
		List list = new ArrayList();
		list.add("�H�f�c�B");
		list.add("�H�f�c�B����k��31���ܲ�42��");
		list.add("�H�f�c�B����k�ĤT�@���ܲĥ|�G��");
		list.add("�H�f�c�B����k�ĤT�Q�@���ܲĥ|�Q�G��");
		list.add("�ൣ�Τ֦~�ʥ��������Ҳ�23���ܲ�26��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�T���ܲĤG����");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�Q�T���ܲĤG�Q����");
		list.add("�ൣ�Τ֦~�ʥ��������Ҳ�23��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�T��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�Q�T��");
		list.add("�ൣ�Τ֦~�ʥ��������Ҳ�24��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�|��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�Q�|��");
		list.add("�ൣ�Τ֦~�ʥ��������Ҳ�25��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG����");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�Q����");
		list.add("�ൣ�Τ֦~�ʥ��������Ҳ�26��");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG����");
		list.add("�ൣ�Τ֦~�ʥ��������ҲĤG�Q����");
		list.add("�D�k��296���β�296����1");
		list.add("�D�k��296���β�296��-1");
		list.add("�D�k�ĤG�E�����βĤG�E�������@");
		list.add("�D�k�ĤG�E�����βĤG�E����-�@");
		list.add("�D�k�ĤG�E�����βĤG�E����-1");
		list.add("�D�k�ĤG�E�����βĤG�E������1");
		list.add("�D�k�ĤG�ʤE�Q�����βĤG�ʤE�Q�������@");
		list.add("�D�k�ĤG�ʤE�Q�����βĤG�ʤE�Q����-�@");
		list.add("�D�k�ĤG�ʤE�Q�����βĤG�ʤE�Q����-1");
		list.add("�D�k�ĤG�ʤE�Q�����βĤG�ʤE�Q������1");
		list.add("�D�k��296����1");
		list.add("�D�k��296��-1");
		list.add("�D�k��296-1��");
		list.add("�D�k��296��1��");
		list.add("�D�k�ĤG�E�������@");
		list.add("�D�k�ĤG�ʤE�Q�������@");
		list.add("�D�k�ĤG�E����-�@");
		list.add("�D�k�ĤG�ʤE�Q����-�@");
		list.add("�D�k�ĤG�E�����@��");
		list.add("�D�k�ĤG�ʤE�Q�����@��");
		list.add("�D�k��296��");
		list.add("�D�k�ĤG�E����");
		list.add("�D�k�ĤG�ʤE�Q����");
		list.add("�D�k��231���β�231����1");
		list.add("�D�k��231���β�231��-1");
		list.add("�D�k�ĤG�T�@���βĤG�T�@�����@");
		list.add("�D�k�ĤG�ʤT�Q�@���βĤG�ʤT�Q�@��-�@");
		list.add("�D�k�ĤG�ʤT�Q�@���βĤG�ʤT�Q�@�����@");
		list.add("�D�k��231����1");
		list.add("�D�k��231��-1");
		list.add("�D�k��231-1��");
		list.add("�D�k��231��1��");
		list.add("�D�k�ĤG�T�@�����@");
		list.add("�D�k�ĤG�ʤT�Q�@�����@");
		list.add("�D�k�ĤG�T�@��-�@");
		list.add("�D�k�ĤG�ʤT�Q�@��-�@");
		list.add("�D�k�ĤG�T�@���@��");
		list.add("�D�k�ĤG�ʤT�Q�@���@��");
		list.add("�D�k��231��");
		list.add("�D�k�ĤG�T�@��");
		list.add("�D�k�ĤG�ʤT�Q�@��");
		list.add("�D�k��298��");
		list.add("�D�k�ĤG�E�K��");
		list.add("�D�k�ĤG�ʤE�Q�K��");
		list.add("��299��");
		list.add("�ĤG�E�E��");
		list.add("�ĤG�ʤE�Q�E��");
		list.add("�D�k��299��");
		list.add("�D�k�ĤG�E�E��");
		list.add("�D�k�ĤG�ʤE�Q�E��");
		list.add("�D�k��300��");
		list.add("�D�k�ĤT�ʱ�");
		list.add("��300��");
		list.add("�ĤT�ʱ�");
		list.add("�Ұʰ�Ǫk��75��");
		list.add("�Ұʰ�Ǫk�ĤC����");
		list.add("�Ұʰ�Ǫk�ĤC�Q����");
		list.add("�Ұʰ�Ǫk��76��");
		list.add("�Ұʰ�Ǫk�ĤC����");
		list.add("�Ұʰ�Ǫk�ĤC�Q����");
		list.add("�Ұʰ�Ǫk��75�B76��");
		list.add("�Ұʰ�Ǫk�ĤC���B�C����");
		list.add("�Ұʰ�Ǫk�ĤC�Q���B�C�Q����");
		return list;
	}
}