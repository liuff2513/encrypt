package com.baihui.core.encrypt.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.catalina.loader.WebappClassLoader;

import com.baihui.core.encrypt.util.IOUtil;

public class EncryptClassLoaderBeta extends WebappClassLoader {
	private final static String ALGORITHM = "DES";

	public EncryptClassLoaderBeta() {
	}
	
	public EncryptClassLoaderBeta(ClassLoader parent) {
		super(parent);
	}
	public Class<?> findClass(String name) throws ClassNotFoundException {
		if(name!=null&&name.startsWith("com.baihui.core")) {//要解密的类
			System.out.println("name:::"+name);
		}
		if(name!=null&&name.startsWith("com.baihui.core")) {//要解密的类
			// 我们要创建的Class对象
			Class<?> clazz = null;
			
			// 如果类已经在系统缓冲之中,我们不必再次装入它
			clazz = findLoadedClass(name);
			if (clazz != null)
				return clazz;
			try {
				//读取密钥
				byte keyData[] = IOUtil.readFile("D:/key.data");
				DESKeySpec keySpec = new DESKeySpec(keyData);
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
				SecretKey key = keyFactory.generateSecret(keySpec);
				//声明实例Cipher对象
				SecureRandom sr = new SecureRandom();
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, key, sr);
				// 读取经过加密的类文件
				System.out.println(("D:/Program Files/apache-tomcat-8.0.20/webapps/scrm/WEB-INF/classes/")+name.replace(".", File.separator) + ".data");
				byte classData[] = IOUtil.readFile(("D:/Program Files/apache-tomcat-8.0.20/webapps/scrm/WEB-INF/classes/")+name.replace(".", File.separator) + ".data");
				if (classData != null) {
					//解密
					byte decryptedClassData[] = cipher.doFinal(classData);
					clazz = defineClass(name, decryptedClassData, 0, decryptedClassData.length);
					if(clazz==null) {
						System.out.println("name-error:::"+name);
						throw new ClassNotFoundException(name);
					}else{
						// 必需的步骤3：如有必要，则装入相关的类
//						if (resolve && clazz != null)
//							resolveClass(clazz);
						System.out.println("name-success:::"+name);
						return clazz;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.findClass(name);
	}

	@Override
	public synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		if(name!=null&&name.startsWith("com.baihui.core")) {//要解密的类
			return findClass(name);
		}
		return super.loadClass(name, resolve);
	}
	
}