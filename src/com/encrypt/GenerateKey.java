package com.encrypt;

import java.security.*;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.*;

import com.baihui.core.encrypt.util.IOUtil;

public class GenerateKey {
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
