/**
 * 
 */
package mobile.registered;

/**
 * @author www.bip-soft.com
 * 
 */
public class RegObjectInfo {

	private String keys;
	private String secretKey;
	private long startTime;
	private int regdays;
	private int wardays;
	private int pkey1;
	private int pkey2;
	private int pkey3;

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getRegdays() {
		return regdays;
	}

	public void setRegdays(int regdays) {
		this.regdays = regdays;
	}

	public int getWardays() {
		return wardays;
	}

	public void setWardays(int wardays) {
		this.wardays = wardays;
	}

	public int getPkey1() {
		return pkey1;
	}

	public void setPkey1(int pkey1) {
		this.pkey1 = pkey1;
	}

	public int getPkey2() {
		return pkey2;
	}

	public void setPkey2(int pkey2) {
		this.pkey2 = pkey2;
	}

	public int getPkey3() {
		return pkey3;
	}

	public void setPkey3(int pkey3) {
		this.pkey3 = pkey3;
	}

	public String makeMyKeys() {
		if (keys == null || secretKey == null)
			return "";
		String jmkeys = WhatEver.toTran(keys, true, pkey1, pkey2, pkey3);
		String sercStr = WhatEver.toTran(secretKey, true, pkey1, pkey2, pkey3);
		String bgtstr = WhatEver.toTran(startTime+"", true, pkey1, pkey2, pkey3);
		String regdaystr = WhatEver.toTran(regdays + "", true, pkey1, pkey2,
				pkey3);
		String wardaystr = WhatEver.toTran(wardays + "", true, pkey1, pkey2,
				pkey3);
		return jmkeys + WhatEver.DIV_CELL + sercStr + WhatEver.DIV_CELL
				+ bgtstr + WhatEver.DIV_CELL 
				+ regdaystr + WhatEver.DIV_CELL + wardaystr + WhatEver.DIV_CELL
				+ pkey3 + WhatEver.DIV_CELL + pkey2 + WhatEver.DIV_CELL + pkey1;
	}

}
