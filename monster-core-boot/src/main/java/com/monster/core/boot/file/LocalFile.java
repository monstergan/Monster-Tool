package com.monster.core.boot.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kte.core.boot.props.KteFileProperties;
import com.kte.core.tool.utils.DateUtil;
import com.kte.core.tool.utils.SpringUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件封装
 */
@Data
public class LocalFile {
	/**
	 * 文件配置
	 */
	private static KteFileProperties fileProperties;
	/**
	 * 上传文件在附件表中的id
	 */
	private Object fileId;
	/**
	 * 上传文件
	 */
	@JsonIgnore
	private MultipartFile file;
	/**
	 * 文件外网地址
	 */
	private String domain;
	/**
	 * 上传分类文件夹
	 */
	private String dir;
	/**
	 * 上传物理路径
	 */
	private String uploadPath;
	/**
	 * 上传虚拟路径
	 */
	private String uploadVirtualPath;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 真实文件名
	 */
	private String originalFileName;

	public LocalFile(MultipartFile file, String dir) {
		this.dir = dir;
		this.file = file;
		this.fileName = file.getName();
		this.originalFileName = file.getOriginalFilename();
		this.domain = getKteFileProperties().getUploadDomain();
		this.uploadPath = KteFileUtil.formatUrl(File.separator + getKteFileProperties().getUploadRealPath() + File.separator + dir + File.separator + DateUtil.format(DateUtil.now(), "yyyyMMdd") + File.separator + this.originalFileName);
		this.uploadVirtualPath = KteFileUtil.formatUrl(getKteFileProperties().getUploadCtxPath().replace(getKteFileProperties().getContextPath(), "") + File.separator + dir + File.separator + DateUtil.format(DateUtil.now(), "yyyyMMdd") + File.separator + this.originalFileName);
	}

	public LocalFile(MultipartFile file, String dir, String uploadPath, String uploadVirtualPath) {
		this(file, dir);
		if (null != uploadPath) {
			this.uploadPath = KteFileUtil.formatUrl(uploadPath);
			this.uploadVirtualPath = KteFileUtil.formatUrl(uploadVirtualPath);
		}
	}

	private static KteFileProperties getKteFileProperties() {
		if (fileProperties == null) {
			fileProperties = SpringUtil.getBean(KteFileProperties.class);
		}
		return fileProperties;
	}

	/**
	 * 图片上传
	 */
	public void transfer() {
		transfer(getKteFileProperties().getCompress());
	}

	/**
	 * 图片上传
	 *
	 * @param compress 是否压缩
	 */
	public void transfer(boolean compress) {
		IFileProxy fileFactory = FileProxyManager.me().getDefaultFileProxyFactory();
		this.transfer(fileFactory, compress);
	}

	/**
	 * 图片上传
	 *
	 * @param fileFactory 文件上传工厂类
	 * @param compress    是否压缩
	 */
	public void transfer(IFileProxy fileFactory, boolean compress) {
		try {
			File file = new File(uploadPath);

			if (null != fileFactory) {
				String[] path = fileFactory.path(file, dir);
				this.uploadPath = path[0];
				this.uploadVirtualPath = path[1];
				file = fileFactory.rename(file, path[0]);
			}

			File pfile = file.getParentFile();
			if (!pfile.exists()) {
				pfile.mkdirs();
			}

			this.file.transferTo(file);

			if (compress) {
				fileFactory.compress(this.uploadPath);
			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

}
