package com.monster.core.oss;

import com.aliyun.oss.model.AppendObjectRequest;
import com.monster.core.oss.model.AppendFileResult;
import com.monster.core.oss.model.MonsterFile;
import com.monster.core.oss.model.OssFile;
import com.monster.core.oss.props.OssProperties;
import com.monster.core.oss.rule.OssRule;
import com.monster.core.tool.utils.StringPool;
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 华为云对象存储
 */
@AllArgsConstructor
public class HuaweiObsTemplate implements OssTemplate {


	private final ObsClient obsClient;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			obsClient.createBucket(getBucketName(bucketName));
		}
	}

	@Override
	public void removeBucket(String bucketName) {
		obsClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	public boolean bucketExists(String bucketName) {
		return obsClient.headBucket(getBucketName(bucketName));
	}

	@Override
	public void copyFile(String fileName, String destFileName) {
		System.out.println("对应功能未实现");
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		obsClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		obsClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = obsClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMd5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@Override
	@SneakyThrows
	public InputStream downloadFile(String fileName) {
		return downloadFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public InputStream downloadFile(String bucketName, String fileName) {
		ObsObject object = obsClient.getObject(bucketName, fileName);
		return object.getObjectContent();
	}

	@Override
	public String filePath(String fileName) {
		return getOssHost(getBucketName()).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String filePath(String bucketName, String fileName) {
		return getOssHost(getBucketName(bucketName)).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(getBucketName(bucketName)).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public MonsterFile putFile(MultipartFile file, String pathName) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file, pathName);
	}

	@Override
	public MonsterFile putFile(String fileName, MultipartFile file, String pathName) {
		return putFile(ossProperties.getBucketName(), fileName, file, pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String bucketName, String fileName, MultipartFile file, String pathName) {
		return putFile(bucketName, fileName, file.getInputStream(), pathName);
	}

	@Override
	public MonsterFile putFile(String fileName, InputStream stream, String pathName) {
		return putFile(ossProperties.getBucketName(), fileName, stream, pathName);
	}

	@Override
	@SneakyThrows
	public MonsterFile putFile(String bucketName, String fileName, InputStream stream, String pathName) {
		return put(bucketName, stream, fileName, false, pathName);
	}

	@Override
	public void removeFile(String fileName) {
		obsClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	public void removeFile(String bucketName, String fileName) {
		obsClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@Override
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@Override
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	@Override
	public AppendFileResult fileAppendUpload(String filePath, InputStream inputStream, String fileName, Long position, AppendObjectRequest appendRequest) {
		return null;
	}

	/**
	 * 上传文件流
	 *
	 * @param bucketName
	 * @param stream
	 * @param key
	 * @param cover
	 * @return
	 */
	@SneakyThrows
	public MonsterFile put(String bucketName, InputStream stream, String key, boolean cover, String pathName) {
		makeBucket(bucketName);

		String originalName = key;

		key = getFileName(key, pathName);

		// 覆盖上传
		if (cover) {
			obsClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = obsClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getEtag()) && retry < retryCount) {
				response = obsClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}

		MonsterFile file = new MonsterFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setLink(fileLink(bucketName, key));
		return file;
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
	 * 根据规则生成存储桶名称规则  单租户
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则 多租户
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}

	/**
	 * 获取域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		String prefix = ossProperties.getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + getBucketName(bucketName) + StringPool.DOT + ossProperties.getEndpoint().replaceFirst(prefix, StringPool.EMPTY);
	}

	/**
	 * 获取域名
	 *
	 * @return String
	 */
	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}

}
