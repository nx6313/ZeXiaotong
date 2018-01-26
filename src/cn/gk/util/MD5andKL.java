package cn.gk.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class MD5andKL {
	private static Logger logger = Logger.getLogger(MD5andKL.class.getName());

	// MD5加码。32位
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			logger.error("[Hxw] " + e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	// 可逆的加密算法
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数
	public static void main(String args[]) {
		String s = new String("123456");
		logger.error("[Hxw] 原始：" + s);
		logger.error("[Hxw] MD5后：" + MD5(s));
		logger.error("[Hxw] " + MD5(s).length());
		logger.error("[Hxw] MD5后再加密：" + KL(MD5(s)));
		logger.error("[Hxw] 解密为MD5后的：" + JM(KL(MD5(s))));
		logger.error("[Hxw] MD5后解密：" + JM(MD5(s)));
	}
}
