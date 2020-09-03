/**
 * 
 */
package mobile.registered;

import java.security.MessageDigest;

/**
 * @author www.bip-soft.com
 * 
 */
public class WhatEver {
	private static byte __0$[];
	private static byte __1$[];
	private static byte __0$b[];
	private static byte __1$b[];
	public static final boolean HDDOG = true;
	public static final boolean HDINFO = true;
	public static final boolean OSCHK = true;
	public static final boolean NETREGCHK = true;
	public final static char DIV_CELL = (char) 0x1F;//单元分隔

	public WhatEver() {
	}
	
	public static String toTran(Object ov, boolean tojm, int key, int ch1, int ch2) {
		 return s07((String) ov, tojm, key, ch1, ch2);
		}

	private static void initialize() {
		String sin = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789+-*$#";
		initializea(sin, false);
		String sbz = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		initializea(sbz, true);
	}

	private static void initializea(String sv, boolean bas) {
		byte _0b$[] = new byte[128];
		byte _1b$[] = new byte[64];
		for (int i = 0; i < 64; i++) {
			char c0 = sv.charAt(i);
			_0b$[c0] = (byte) i;
			_1b$[i] = (byte) c0;
		}
		if (bas) {
			__0$b = _0b$;
			__1$b = _1b$;
		} else {
			__0$ = _0b$;
			__1$ = _1b$;
		}
	}

	public static byte[] s00(byte _$0[]) {
		return s00(__0$, _$0);
	}

	private static byte[] s00(byte _r$[], byte _$0[]) {
		int _$3;
		int _$4;
		int _$5;
		byte _$1[];
		int _$2 = _$0.length;
		_$3 = _$2 % 4;
		_$4 = _$2 - _$3;
		_$5 = 0;
		_$1 = new byte[(_$2 * 3) / 4];
		byte _$6 = 0;
		byte _$7 = 0;
		byte _$8 = 0;
		byte _$9 = 0;
		if (_$2 <= 0) {

		}
		for (int i = 0; i < _$4;) {
			_$6 = _r$[_$0[i++]];
			_$7 = _r$[_$0[i++]];
			_$8 = _r$[_$0[i++]];
			_$9 = _r$[_$0[i++]];
			_$1[_$5++] = (byte) ((_$7 & 3) << 6 | _$6);
			_$1[_$5++] = (byte) ((_$8 & 0xf) << 4 | _$7 >> 2);
			_$1[_$5++] = (byte) (_$8 >> 4 | _$9 << 2);
		}

		if (_$3 == 2) {
			_$6 = _r$[_$0[_$4]];
			_$7 = _r$[_$0[_$4 + 1]];
			_$1[_$5++] = (byte) ((_$7 & 3) << 6 | _$6);
		} else if (_$3 == 3) {
			_$6 = _r$[_$0[_$4]];
			_$7 = _r$[_$0[_$4 + 1]];
			_$8 = _r$[_$0[_$4 + 2]];
			_$1[_$5++] = (byte) ((_$7 & 3) << 6 | _$6);
			_$1[_$5++] = (byte) ((_$8 & 0xf) << 4 | _$7 >> 2);
		}
		return _$1;
		// return null;
	}

	public static byte[] s01(int _$0, int _$1, int _$2, byte _$3[]) {
		int _$4;
		_$4 = _$3.length;
		if (_$4 <= 0) {
			return null;
		}
		byte _$5[];
		_$5 = new byte[_$4];
		for (int i = 0; i < _$4; i++) {
			_$5[i] = (byte) (_$3[i] ^ _$0 >> 8);
			_$0 = (_$0 ^ _$3[i]) + _$1 * _$3[i] + _$2;
		}

		return _$5;
	}

	public static byte[] s02(byte _$0[]) {
		return s02(__1$, _$0);
	}

	private static byte[] s02(byte _r$[], byte _$0[]) {
		int _$2 = _$0.length;
		int _$3 = _$2 % 3;
		int _$4 = _$2 - _$3;
		int _$5 = (_$2 * 4) / 3;
		int _$6 = 0;
		if (_$2 % 3 > 0)
			_$5++;
		byte _$1[] = new byte[_$5];
		if (_$2 > 0) {
			try {
				for (int i = 0; i < _$4;) {
					byte _$7 = _$0[i++];
					byte _$8 = _$0[i++];
					byte _$9 = _$0[i++];
					_$1[_$6++] = _r$[_$7 & 0x3f];
					_$1[_$6++] = _r$[(_$8 & 0xf) << 2 | _$7 >> 6 & 3];
					_$1[_$6++] = _r$[(_$9 & 3) << 4 | _$8 >> 4 & 0xf];
					_$1[_$6++] = _r$[_$9 >> 2 & 0x3f];
				}

				if (_$3 > 0) {
					byte _$7 = _$0[_$4];
					_$1[_$6++] = _r$[_$7 & 0x3f];
					if (_$3 > 1) {
						byte _$8 = _$0[_$4 + 1];
						_$1[_$6++] = _r$[(_$8 & 0xf) << 2 | _$7 >> 6 & 3];
						_$1[_$6] = _r$[_$8 >> 4 & 0xf];
					} else {
						_$1[_$6] = _r$[_$7 >> 6 & 3];
					}
				}
			} catch (Exception e0) {
			}
			return _$1;
		} else {
			return null;
		}
	}

	public static byte[] s03(int _$0, int _$1, int _$2, Object _$3) {
		byte _$4[] = (_$3 instanceof String) ? ((String) _$3).getBytes()
				: (byte[]) (byte[]) _$3;
		int _$5 = _$4.length;
		if (_$5 > 0) {
			try {
				for (int i = 0; i < _$5; i++) {
					_$4[i] = (byte) (_$4[i] ^ _$0 >> 8);
					_$0 = (_$0 ^ _$4[i]) + _$1 * _$4[i] + _$2;
				}

			} catch (Exception e0) {
			}
			return _$4;
		} else {
			return null;
		}
	}

	public static byte[] s04(byte bs[]) {
		return s00(__0$b, bs);
	}

	public static byte[] s05(byte bs[]) {
		return s02(__1$b, bs);
	}

	public static String s06(Object ov, int key) {
		byte bs0[];
		if (ov instanceof String)
			bs0 = ((String) ov).getBytes();
		else
			bs0 = (byte[]) (byte[]) ov;
		int x1 = bs0.length;
		int cc = x1 / 2;
		for (int i = 0; i < cc; i++) {
			byte b0 = bs0[i];
			x1--;
			bs0[i] = bs0[x1];
			bs0[x1] = b0;
		}

		return new String(s02(s03(key, 13579, 24680, bs0)));
	}

	public static String s07(Object ov, boolean tojm, int key, int ch1, int ch2) {
		byte bs[] = null;
		if (ov instanceof String) {
			String sv = (String) ov;
			if (sv.length() < 1)
				return null;
			bs = sv.getBytes();
		} else {
			if (ov == null)
				return null;
			bs = (byte[]) (byte[]) ov;
		}
		return new String(tojm ? s02(s03(key, ch1, ch2, bs)) : s01(key, ch1,
				ch2, s00(bs)));
	}

	static {
		initialize();
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 'f');  
        }  
        String s = new String(a);  
        return s;  
  
    } 
    public static String convertMD5Two(String inStr){  
        return convertMD5(convertMD5(inStr));  
  
    } 
}
