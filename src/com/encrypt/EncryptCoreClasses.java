package com.encrypt;

import com.baihui.core.encrypt.util.IOUtil;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * ClassName: EncryptCoreClasses
 * @Description: 代码加密
 * @author feifei.liu
 * @date 2016年11月10日
 */
public class EncryptCoreClasses {
	private final static String KEY_FILENAME = "D:/key.data";
	private static Cipher cipher = generateCipher(KEY_FILENAME);
	public static void main(String[] args) {
//		encryptPackageClasses("D:/Program Files/apache-tomcat-7.0.72/webapps/baihui/WEB-INF/classes/com/baihui/core");
		encryptPackageClasses("E:/jar/com");
	}
	
	/**
	 * @Description: 生成密钥方法
	 * @param @param keyFilename
	 * @param @return   
	 * @return SecretKey  
	 * @throws
	 * @author feifei.liu
	 * @date 2016年11月10日
	 */
	private static SecretKey generateKey(String keyFilename) {
		try {
			byte[] rawKey = IOUtil.readFile(keyFilename);
			DESKeySpec dks = new DESKeySpec(rawKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm.DES.toString());
			SecretKey key = keyFactory.generateSecret(dks);
			return key;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description: 生成用于实际加密的Cipher对象
	 * @param @param keyFilename
	 * @param @return   
	 * @return Cipher  
	 * @throws
	 * @author feifei.liu
	 * @date 2016年11月10日
	 */
	private static Cipher generateCipher(String keyFilename) {
		try {
			SecretKey key = generateKey(keyFilename);
			if(key!=null) {
				SecureRandom sr = new SecureRandom();
				//创建用于实际加密操作的Cipher对象
				Cipher cipher = Cipher.getInstance(Algorithm.DES.toString());
				cipher.init(Cipher.ENCRYPT_MODE, key, sr);
				return cipher;
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description: 加密指定包下面的java类文件
	 * @param    
	 * @return void  
	 * @throws IOException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws
	 * @author feifei.liu
	 * @date 2016年11月10日
	 */
	private static void encryptPackageClasses(String packageFilename){
		try {
			File file = new File(packageFilename);
			if(file.isFile()&&packageFilename.endsWith(".class")) {
				System.out.println("packageFilename::"+packageFilename);
				//读入类文件
				byte classData[] = IOUtil.readFile(packageFilename);
				//加密
				byte encryptedClassData[] = cipher.doFinal(classData);
				//保存加密后的内容
				String rootPackagename = packageFilename.substring(packageFilename.indexOf(":")+1);
				if(packageFilename.indexOf("WEB-INF"+File.separator)!=-1){
					rootPackagename = rootPackagename.split("classes")[1].substring(1);
				}
				IOUtil.writeFile("E:\\jar\\encryptClasses\\"+rootPackagename.replace(".class", ".data"), encryptedClassData);
			}else if(file.isDirectory()) {
				File[] files = file.listFiles();
				for(File childFile:files) {
					encryptPackageClasses(childFile.getPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			System.out.println("加密失败::"+packageFilename+"-->>"+packageFilename.substring(packageFilename.indexOf(":")+1));
		}
		
	}
}
enum Algorithm {
	DES;
	
}
