package com.zgan.yckz.tools;

public class YCKZ_Static {
	/**
	 * 
	 */
	public static String ip_port;
	/**
	 * VIP�ȼ�1,2,3,4...
	 */
	public static int VIP_level;
	/**
	 * VIP����
	 */
	public static int VIP_score;
	/**
	 * �û�����
	 */
	public static String USER_password;

	/**
	 * �ֻ���/�û���
	 */
	public static String Phone_number;
	/**
	 * �豸ID
	 */
	public static String ChildDeviceID[] = new String[100];

	public static String ZHUWO_DENGMAC1;
	public static String ZHUWO_DENGJOBSTAUTES1;
	public static String ZHUWO_SHEBEINAME1;
	public static String ZHUWO_PRODUCTNO1;

	public static String ZHUWO_DENGMAC2;
	public static String ZHUWO_DENGJOBSTAUTES2;
	public static String ZHUWO_SHEBEINAME2;

	public static String ZHUWO_DENGMAC3;
	public static String ZHUWO_DENGJOBSTAUTES3;
	public static String ZHUWO_SHEBEINAME3;

	public static String ZHUWO_DENGMAC4;
	public static String ZHUWO_DENGJOBSTAUTES4;
	public static String ZHUWO_SHEBEINAME4;

	public static boolean GetStatues(String JobStatues) {
		if (JobStatues.substring(0, 1).equals("0")
				&& JobStatues.substring(1, 2).equals("0")) {

			return true;
		}
		return false;
	}

}
