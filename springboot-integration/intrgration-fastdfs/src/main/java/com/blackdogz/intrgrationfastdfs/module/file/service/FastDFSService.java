package com.blackdogz.intrgrationfastdfs.module.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * FastDFS 上传文件业务
 * @author zqdpc
 */
public interface FastDFSService {

  /**
   * 上传文件
   * @param file
   * @return
   * @throws IOException
   */
  String saveFile(MultipartFile file) throws IOException;

  /**
   * 删除
   * @param groupName
   * @param remoteFileName
   * @throws Exception
   */
  int deleteFile(String groupName, String remoteFileName) throws Exception;

  /**
   * 下载
   * @param groupName
   * @param remoteFileName
   * @return
   */
  InputStream downloadFile(String groupName, String remoteFileName);

}
