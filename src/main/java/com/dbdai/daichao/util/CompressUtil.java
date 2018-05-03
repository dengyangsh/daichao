package com.dbdai.daichao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressUtil {

	/**
	 * 
	 * @description 将byte 数组压缩
	 * @param bt
	 * @return
	 */
	public static byte[] compressGz(byte[] bt) {
		// 将byte数据读入文件流
		ByteArrayOutputStream bos = null;
		GZIPOutputStream gzipos = null;
		try {
			bos = new ByteArrayOutputStream();
			gzipos = new GZIPOutputStream(bos);
			gzipos.write(bt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(gzipos);
			closeStream(bos);
		}
		return bos.toByteArray();
	}

	/**
	 * 
	 * @description 关闭数据流
	 * @param oStream
	 * 
	 */
	public static void closeStream(Closeable oStream) {
		if (null != oStream) {
			try {
				oStream.close();
			} catch (IOException e) {
				oStream = null;// 赋值为null,等待垃圾回收
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @description 解压缩byte数组
	 * @param bt
	 * @return
	 */
	public static byte[] unCompressGz(byte[] bt) {
		// byte[] unCompress=null;
		ByteArrayOutputStream byteAos = null;
		ByteArrayInputStream byteArrayIn = null;
		GZIPInputStream gzipIn = null;
		try {
			byteArrayIn = new ByteArrayInputStream(bt);
			gzipIn = new GZIPInputStream(byteArrayIn);
			byteAos = new ByteArrayOutputStream();
			byte[] b = new byte[4096];
			int temp = -1;
			while ((temp = gzipIn.read(b)) > 0) {
				byteAos.write(b, 0, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeStream(byteAos);
			closeStream(gzipIn);
			closeStream(byteArrayIn);
		}
		return byteAos.toByteArray();
	}

	/**
	 * 只针对 字节进行压缩 zip
	 * 
	 * @param bt
	 * @return
	 */
	// public static byte[] compressZip(byte[] bt) {
	// ByteArrayOutputStream bos = null;
	// ZipOutputStream zipos = null;
	// try {
	// bos = new ByteArrayOutputStream();
	// zipos = new ZipOutputStream(bos);
	// ZipEntry entry = new ZipEntry("zip");
	// entry.setSize(bt.length);
	// zipos.putNextEntry(entry);
	// zipos.write(bt);
	// zipos.closeEntry();;
	// bt = bos.toByteArray();
	// } catch (Exception e) {
	// logger.error("zip压缩异常");
	// e.printStackTrace();
	// } finally {
	// closeStream(zipos);
	// closeStream(bos);
	// }
	// return bt;
	// }
	//
	// /**
	// * 只针对 字节进行解压缩 zip
	// *
	// * @param bt
	// * @return
	// */
	// public static byte[] unCompressZip(byte[] bt) {
	// ByteArrayInputStream byteArrayIn = null;
	// ByteArrayOutputStream byteAos = null;
	// ZipInputStream zins = null;
	// try {
	// byteArrayIn = new ByteArrayInputStream(bt);
	// zins = new ZipInputStream(byteArrayIn);
	// while(zins.getNextEntry()!=null) {
	// byteAos = new ByteArrayOutputStream();
	// byte[] b = new byte[4096];
	// int temp = -1;
	// while ((temp = zins.read(b)) > 0) {
	// byteAos.write(b, 0, temp);
	// }
	// }
	// bt=byteAos.toByteArray();
	// byteAos.flush();
	// } catch (IOException e) {
	// logger.error("zip解压缩异常", e);
	// e.printStackTrace();
	// return null;
	// } finally {
	// closeStream(byteAos);
	// closeStream(zins);
	// closeStream(byteArrayIn);
	// }
	// return bt;
	// }

}
