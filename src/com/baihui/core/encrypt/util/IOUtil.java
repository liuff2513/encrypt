package com.baihui.core.encrypt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtil {
	// 把文件读入byte数组
	static public byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		long len = file.length();
		byte data[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(data);
		if (r != len)
			throw new IOException("Only read " + r + " of " + len + " for "
					+ file);
		fin.close();
		return data;
	}

	// 把byte数组写出到文件
	static public void writeFile(String filename, byte data[])
			throws IOException {
		File file = new File(filename.substring(0,
				filename.lastIndexOf(File.separator)));
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(data);
		fout.close();
	}
}
