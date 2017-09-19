package com.baihui.core.encrypt.util;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * ClassName: GenerateKeyUtil
 * @Description: 密钥生成工具
 * @author feifei.liu
 * @date 2016年11月29日 下午3:30:51
 */
public class GenerateKeyUtil {
	static public void main(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String keyFilename = scanner.nextLine();
		String algorithm = "DES";

		// 生成密匙
		SecureRandom sr = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(sr);
		SecretKey key = kg.generateKey();

		// 把密匙数据保存到文件
		IOUtil.writeFile(keyFilename, key.getEncoded());
	}
}
