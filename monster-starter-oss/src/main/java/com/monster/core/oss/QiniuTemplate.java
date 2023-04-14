package com.monster.core.oss;

import com.aliyun.oss.model.AppendObjectRequest;
import com.monster.core.oss.model.AppendFileResult;
import com.monster.core.oss.model.MonsterFile;
import com.monster.core.oss.model.OssFile;
import com.monster.core.oss.props.OssProperties;
import com.monster.core.oss.rule.OssRule;
import com.monster.core.tool.utils.CollectionUtil;
import com.monster.core.tool.utils.Func;
import com.monster.core.tool.utils.StringPool;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 七牛云存储
 */
@AllArgsConstructor
public class QiniuTemplate implements OssTemplate {
	private final Auth auth;
	private final UploadManager uploadManager;
	private final BucketManager bucketManager;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!CollectionUtil.contains(bucketManager.buckets(), getBucketName(bucketName))) {
			bucketManager.createBucket(getBucketName(bucketName), Zone.autoZone().getRegion());
		}
	}

	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		// ig
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return CollectionUtil.contains(bucketManager.buckets(), getBucketName(bucketName));
	}

	@Override
	public void copyFile(String fileName, String destFileName) {
		System.out.println("对应功能未实现");
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		FileInfo stat = bucketManager.stat(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(Func.isEmpty(stat.key) ? fileName : stat.key);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.hash);
		ossFile.setLength(stat.fsize);
		ossFile.setPutTime(new Date(stat.putTime / 10000));
		ossFile.setContentType(stat.mimeType);
		return ossFile;
	}

	@Override
	public InputStream downloadFile(String fileName) {
		return null;
	}

	@Override
	public InputStream downloadFile(String bucketName, String fileName) {
		return null;
	}

	@Override
	@SneakyThrows
	public String filePath(String fileName) {
		return getBucketName().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getBucketName(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return ossProperties.getEndpoint().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return ossProperties.getEndpoint().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(MultipartFile file, String pathName) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file, pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String fileName, MultipartFile file, String pathName) {
		return putFile(ossProperties.getBucketName(), fileName, file, pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String bucketName, String fileName, MultipartFile file, String pathName) {
		return putFile(bucketName, fileName, file.getInputStream(), pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String fileName, InputStream stream, String pathName) {
		return putFile(ossProperties.getBucketName(), fileName, stream, pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String bucketName, String fileName, InputStream stream, String pathName) {
		return put(bucketName, stream, fileName, false, pathName);
	}

	@SneakyThrows
	public MonsterFile put(String bucketName, InputStream stream, String key, boolean cover, String pathName) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key, pathName);
		// 覆盖上传
		if (cover) {
			uploadManager.put(stream, key, getUploadToken(bucketName, key), null, null);
		} else {
			Response response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
			int retry = 0;
			int retryCount = 5;
			while (response.needRetry() && retry < retryCount) {
				response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
				retry++;
			}
		}
		MonsterFile file = new MonsterFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost());
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		bucketManager.delete(getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		bucketManager.delete(getBucketName(bucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@Override
	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	@Override
	public AppendFileResult fileAppendUpload(String filePath, InputStream inputStream, String fileName, Long position, AppendObjectRequest appendRequest) {
		return null;
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}

	/**
	 * 根据规则生成文件名称规则
	 *
	 * @param originalFilename 原始文件名
	 * @return string
	 */
	private String getFileName(String originalFilename, String pathName) {
		return ossRule.fileName(originalFilename, pathName);
	}

	/**
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName) {
		return auth.uploadToken(getBucketName(bucketName));
	}

	/**
	 * 获取上传凭证，覆盖上传
	 */
	private String getUploadToken(String bucketName, String key) {
		return auth.uploadToken(getBucketName(bucketName), key);
	}

	/**
	 * 获取域名
	 *
	 * @return String
	 */
	public String getOssHost() {
		return ossProperties.getEndpoint();
	}

}
