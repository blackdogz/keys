package com.blackdogz.intrgrationfastdfs.module.file.service.impl;

import java.io.IOException;
import java.io.InputStream;

import com.blackdogz.intrgrationfastdfs.common.util.fastdfs.FastDFSClient;
import com.blackdogz.intrgrationfastdfs.common.util.fastdfs.FastDFSFileEntity;
import com.blackdogz.intrgrationfastdfs.module.file.service.FastDFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * FastDFS 上传文件业务
 */
@Service("fastDFSService")
public class FastDFSServiceImpl implements FastDFSService {

  private static final Logger logger = LoggerFactory.getLogger(FastDFSServiceImpl.class);

  /**
   * 上传
   */
  public String saveFile(MultipartFile multipartFile) throws IOException {

    String[] fileAbsolutePath = {};

    String fileName = multipartFile.getOriginalFilename();
    String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

    byte[] file_buff = null;

    InputStream inputStream = multipartFile.getInputStream();
    if (inputStream != null) {
      int len1 = inputStream.available();
      file_buff = new byte[len1];
      inputStream.read(file_buff);
    }
    inputStream.close();

    FastDFSFileEntity file = new FastDFSFileEntity(fileName, file_buff, ext);

    fileAbsolutePath = FastDFSClient.upload(file);

    if (fileAbsolutePath == null) {
      logger.error("upload file failed,please upload again!");
    }

    String path = fileAbsolutePath[0] + "/" + fileAbsolutePath[1];

    return path;
  }

  /**
   * 删除
   */
  public int deleteFile(String groupName, String remoteFileName) throws Exception {
    return FastDFSClient.deleteFile(groupName, remoteFileName);
  }

  /**
   * 下载
   */
  public InputStream downloadFile(String groupName, String remoteFileName) {
    return FastDFSClient.downFile(groupName, remoteFileName);
  }
}
