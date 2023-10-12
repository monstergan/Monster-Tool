package com.monster.core.oss;

import com.aliyun.oss.model.AppendObjectRequest;
import com.monster.core.oss.model.AppendFileResult;
import com.monster.core.oss.model.MonsterFile;
import com.monster.core.oss.model.OssFile;
import com.monster.core.oss.props.OssProperties;
import com.monster.core.oss.rule.OssRule;
import com.monster.core.tool.utils.StringPool;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 腾讯云 COS 操作
 */
@AllArgsConstructor
public class TencentCosTemplate implements OssTemplate {
	private final COSClient cosClient;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			cosClient.createBucket(getBucketName(bucketName));
			// TODO: 权限是否需要修改为私有，当前为 公有读、私有写
			cosClient.setBucketAcl(getBucketName(bucketName), CannedAccessControlList.PublicRead);
		}
	}

	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		cosClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return cosClient.doesBucketExist(getBucketName(bucketName));
	}

	@Override
	public void copyFile(String fileName, String destFileName) {
		System.out.println("对应功能未实现");
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = cosClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
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
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 文件对象
	 *
	 * @param file 上传文件类
	 * @return
	 */
	@Override
	@SneakyThrows
	public MonsterFile putFile(MultipartFile file, String pathName) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file, pathName);
	}

	/**
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 * @return
	 */
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
			cosClient.putObject(getBucketName(bucketName), key, stream, null);
		} else {
			PutObjectResult response = cosClient.putObject(getBucketName(bucketName), key, stream, null);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getETag()) && retry < retryCount) {
				response = cosClient.putObject(getBucketName(bucketName), key, stream, null);
				retry++;
			}
		}
		MonsterFile file = new MonsterFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost(bucketName));
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		cosClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		cosClient.deleteObject(getBucketName(bucketName), fileName);
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
		return ossRule.bucketName(bucketName).concat(StringPool.DASH).concat(ossProperties.getAppId());
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
	 * 获取域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		return "http://" + cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(getBucketName(bucketName));
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
