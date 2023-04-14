package com.monster.core.oss;


import com.aliyun.oss.model.AppendObjectRequest;
import com.monster.core.oss.model.AppendFileResult;
import com.monster.core.oss.model.MonsterFile;
import com.monster.core.oss.model.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * OssTemplate抽象API
 */
public interface OssTemplate {

	/**
	 * 创建 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	void makeBucket(String bucketName);

	/**
	 * 删除 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	void removeBucket(String bucketName);

	/**
	 * 存储桶是否存在
	 *
	 * @param bucketName 存储桶名称
	 * @return boolean
	 */
	boolean bucketExists(String bucketName);

	/**
	 * 拷贝文件
	 *
	 * @param fileName       原文件名称
	 * @param destFileName   新文件名称
	 */
	void copyFile(String fileName, String destFileName);
	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 */
	void copyFile(String bucketName, String fileName, String destBucketName);

	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶文件名称
	 */
	void copyFile(String bucketName, String fileName, String destBucketName, String destFileName);

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	OssFile statFile(String fileName);

	/**
	 * 获取文件信息
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	OssFile statFile(String bucketName, String fileName);

	/**
	 * 下载文件
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	InputStream downloadFile(String fileName);

	/**
	 * 下载文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	InputStream downloadFile(String bucketName, String fileName);

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	String filePath(String fileName);

	/**
	 * 获取文件相对路径
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	String filePath(String bucketName, String fileName);

	/**
	 * 获取文件地址
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	String fileLink(String fileName);

	/**
	 * 获取文件地址
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	String fileLink(String bucketName, String fileName);

	/**
	 * 上传文件
	 *
	 * @param file 上传文件类
	 * @return KteFile
	 */
	MonsterFile putFile(MultipartFile file, String pathName);

	/**
	 * 上传文件
	 *
	 * @param file     上传文件类
	 * @param fileName 上传文件名
	 * @return KteFile
	 */
	MonsterFile putFile(String fileName, MultipartFile file, String pathName);

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   上传文件名
	 * @param file       上传文件类
	 * @return KteFile
	 */
	MonsterFile putFile(String bucketName, String fileName, MultipartFile file, String pathName);

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param stream   文件流
	 * @return KteFile
	 */
	MonsterFile putFile(String fileName, InputStream stream, String pathName);

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @param stream     文件流
	 * @return KteFile
	 */
	MonsterFile putFile(String bucketName, String fileName, InputStream stream, String pathName);

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 */
	void removeFile(String fileName);

	/**
	 * 删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 */
	void removeFile(String bucketName, String fileName);

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 */
	void removeFiles(List<String> fileNames);

	/**
	 * 批量删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileNames  存储桶对象名称集合
	 */
	void removeFiles(String bucketName, List<String> fileNames);
	/**
	 * 文件追加上传
	 * @author yangzongru
	 * @date 2022年09月28日 0028 13:26
	 * @param filePath 文件路径
	 * @param inputStream 输入流
	 * @param fileName 文件名称
	 * @param position 追加位置
	 * @return com.kte.core.oss.model.AppendFileResult
	 */
	AppendFileResult fileAppendUpload(String filePath, InputStream inputStream, String fileName, Long position, AppendObjectRequest appendRequest);
}
