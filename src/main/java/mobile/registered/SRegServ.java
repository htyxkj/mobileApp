package mobile.registered;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SRegServ{
	private static Logger log = LoggerFactory.getLogger(SRegServ.class);
	public static final String RegFileName = "regproperty";
	public static String PROPERTY_FILE="";
	
	public static final int Regdays=30;
	public static final int wardays=15;
	
	public static Properties pro=null;
	public static Map<String, RegObjectInfo> regMap=new HashMap<String, RegObjectInfo>();

	public static int Secd=60000*60*24;
	public static int[] pkid = new int[]{197107, 197506, 199912};//系统默认，暂且不在系统上做加密参数;
	
	public static String serKey="";
	public static String PERTYKEY="";

	public Object processOperator(String reg,String cropid)
			throws Exception {
		this.serKey=cropid+".SERKEYS";
		this.PERTYKEY=cropid+".prettysecret";
		if(pro==null)//初始化
			pro = loadProps();
		if(reg.equals("isReg")){//判断注册状态
			return checkReg();
		}else if(reg.equalsIgnoreCase("RegInfo")){//获取注册信息
			String serkeyString = pro.getProperty(this.serKey)+WhatEver.DIV_CELL+pro.getProperty(this.PERTYKEY);
			return serkeyString;
		}else if(reg.startsWith("RegA&")){//注册
			synchronized (reg) {
				reg = reg.replaceAll("RegA&", "");
				String skey = makeReginfo(reg);
				if(regMap.get(this.serKey)==null){
					return "No";
				}else {
					String keyString = regMap.get(this.serKey).makeMyKeys();
					String pertyK = WhatEver.string2MD5(keyString+regMap.get(this.serKey).getSecretKey());
					if(skey.equals(pertyK)){
						writeData(this.serKey, keyString);
						writeData(this.PERTYKEY, pertyK);
						return "ok";
					}
					return "no";
				}
			}
		}
		return null;
	}
	
	public Object checkReg(){
		RegObjectInfo regObject = regMap.get(this.serKey);
		if(pro.getProperty(this.serKey)==null){
			regObject=new RegObjectInfo();
			String keys = UUID.randomUUID().toString();
			regObject.setKeys(keys);
			long timestap = System.currentTimeMillis();
			regObject.setStartTime(timestap);
			String secretKey = "Bip@2017";
			regObject.setSecretKey(secretKey);
			regObject.setPkey1(pkid[0]);
			regObject.setPkey2(pkid[1]);
			regObject.setPkey3(pkid[2]);
			regObject.setRegdays(Regdays);
			regObject.setWardays(wardays);
			String keyString = regObject.makeMyKeys();
			log.info("keystring:"+keyString);
			writeData(this.serKey, keyString);
			String pertyK = WhatEver.string2MD5(keyString+regObject.getSecretKey());
			log.info("pertyK："+pertyK);
			writeData(PERTYKEY, pertyK);
			regMap.put(this.serKey, regObject);
			return new Object[]{1,"试用期为一个月时间！"};
		}else {
			if(regObject==null)
				makeReginfo( (String) pro.get(serKey));
			regObject=regMap.get(this.serKey);
			String keyString = regObject.makeMyKeys();
			if(!pro.getProperty(serKey).equals(keyString))
				writeData(serKey, keyString);
			String pertyK = WhatEver.string2MD5(keyString+regObject.getSecretKey());
			String pertyK1 = pro.getProperty(PERTYKEY);
			if(pertyK.equals(pertyK1)){
				long curr = System.currentTimeMillis();
				long regdate = regObject.getStartTime();
				long chae = curr-regdate;
				int Usedays = (int) (chae/Secd);//已使用天数；
				int unUseDays =regObject.getRegdays()-Usedays;//未使用天数；
				if(unUseDays>0){
					if(unUseDays<regObject.getWardays()){
						return new Object[]{2,"注册时间还有"+unUseDays+"天到期，请联系管理人员！"};
					}else {
						return new Object[]{0,""};
					}
				}else {
					return new Object[]{1,"注册时间已经到期，请重新注册！"};
				}
			}else {
				return new Object[]{-1,"注册信息错误！"};
			}
		}
	}

	private String makeReginfo(String keyString) {
		RegObjectInfo regObject = new RegObjectInfo();
		String[] infos = new String[9];
		int x0 = keyString.indexOf(WhatEver.DIV_CELL);
		for(int i=0;i<8;i++){
			String keys = keyString.substring(0,x0);
			keyString = keyString.substring(x0+1);
			x0 = keyString.indexOf(WhatEver.DIV_CELL);
			if(x0>0){
				infos[i] = keys;
			}else {
				infos[i] = keys;
				infos[i+1] = keyString;
				break;
			}
		}
		int k1 = Integer.parseInt(infos[7]);
		int k2 = Integer.parseInt(infos[6]);
		int k3 = Integer.parseInt(infos[5]);
		regObject.setPkey1(k1);
		regObject.setPkey2(k2);
		regObject.setPkey3(k3);
		regObject.setKeys(WhatEver.toTran(infos[0], false, k1, k2, k3));
		regObject.setSecretKey(WhatEver.toTran(infos[1], false, k1, k2, k3));
		regObject.setStartTime(Long.parseLong(WhatEver.toTran(infos[2], false, k1, k2, k3)));
		regObject.setRegdays(Integer.parseInt((WhatEver.toTran(infos[3], false, k1, k2, k3))));
		regObject.setWardays(Integer.parseInt((WhatEver.toTran(infos[4], false, k1, k2, k3))));
		regMap.put(this.serKey, regObject);
		return infos[8];
	}

	public static Properties loadProps() {
		Properties properties=null;
		InputStream in = null;
		try {
			PROPERTY_FILE = SRegServ.class.getClassLoader().getResource(RegFileName).getPath();
			File reffile = new File("prettysecret");
			if(!reffile.exists()){
				reffile.createNewFile();
			}
			properties = new Properties();
			// 　<!--第一种，通过类加载器进行获取properties文件流-->
			in = new FileInputStream(PROPERTY_FILE);
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			properties = null;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
		return properties;
	}
	
	/** 
     * 修改或添加键值对 如果key存在，修改 反之，添加。 
     *  
     * @param key 
     * @param value 
     */  
    public static void writeData(String key, String value) {  
        if(pro==null)
        	pro =  new Properties(); 
        try {  
            File file = new File(PROPERTY_FILE);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            pro.load(fis);  
            fis.close();//一定要在修改值之前关闭fis  
            OutputStream fos = new FileOutputStream(PROPERTY_FILE);  
            pro.setProperty(key, value);  
            pro.store(fos, "Update '" + key + "' value");  
            fos.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + PROPERTY_FILE + " for updating "  
                    + value + " value error");  
        }
    }
}
