package com.blackdogz.intrgrationfastdfs.common.util.fastdfs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * FastDFSClient工具类
 * @author zqdpc
 */
public class FastDFSClient {

  private static final Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

  private static TrackerClient trackerClient;
  private static TrackerServer trackerServer;

  private static StorageClient storageClient;
  private static StorageServer storageServer;

  static {
    try {
      String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();;
      ClientGlobal.init(filePath);
      trackerClient = new TrackerClient();
      trackerServer = trackerClient.getConnection();
      storageServer = trackerClient.getStoreStorage(trackerServer);
    } catch (Exception e) {
      logger.error("FastDFS Client Init Fail!",e);
    }
  }

  /**
   * 文件上传
   * @param file
   * @param meta_list 上传信息
   * @return
   */
  public static String[] upload(FastDFSFileEntity file, NameValuePair[] meta_list) {

    logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

    long startTime = System.currentTimeMillis();
    String[] uploadResults = null;
    try {
      storageClient = new StorageClient(trackerServer, storageServer);
      uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
    } catch (IOException e) {
      logger.error("IO Exception when uploadind the file:" + file.getName(), e);
    } catch (Exception e) {
      logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
    }
    logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

    if (uploadResults == null) {
      logger.error("upload file fail, error code:" + storageClient.getErrorCode());
    }
    String groupName = uploadResults[0];
    String remoteFileName = uploadResults[1];

    logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);

    return uploadResults;
  }

  /**
   * 上传文件
   * @param file
   * @return
   */
  public static String[] upload(FastDFSFileEntity file) {
    return upload(file,null);
  }

  /**
   * 获取文件信息。
   * @param groupName
   * @param remoteFileName
   * @return
   */
  public static FileInfo getFileInfo(String groupName, String remoteFileName) {
    try {
      storageClient = new StorageClient(trackerServer, storageServer);
      return storageClient.get_file_info(groupName, remoteFileName);
    } catch (IOException e) {
      logger.error("IO Exception: Get File from Fast DFS failed", e);
    } catch (Exception e) {
      logger.error("Non IO Exception: Get File from Fast DFS failed", e);
    }
    return null;
  }

  /**
   * 获取文件上传信息。
   * @param groupName
   * @param remoteFileName
   * @return
   */
  public static NameValuePair[] getMetadata(String groupName, String remoteFileName) {
    try {
      storageClient = new StorageClient(trackerServer, storageServer);
      return storageClient.get_metadata(groupName, remoteFileName);
    } catch (IOException e) {
      logger.error("IO Exception: Get File from Fast DFS failed", e);
    } catch (Exception e) {
      logger.error("Non IO Exception: Get File from Fast DFS failed", e);
    }
    return null;
  }

  /**
   * 下载文件
   * @param groupName
   * @param remoteFileName
   * @return
   */
  public static InputStream downFile(String groupName, String remoteFileName) {
    try {
      storageClient = new StorageClient(trackerServer, storageServer);
      byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
      InputStream ins = new ByteArrayInputStream(fileByte);
      return ins;
    } catch (IOException e) {
      logger.error("IO Exception: Get File from Fast DFS failed", e);
    } catch (Exception e) {
      logger.error("Non IO Exception: Get File from Fast DFS failed", e);
    }
    return null;
  }

  /**
   * 删除文件
   * @param groupName
   * @param remoteFileName
   * @throws Exception
   */
  public static int deleteFile(String groupName, String remoteFileName)
          throws Exception {
    storageClient = new StorageClient(trackerServer, storageServer);
    int i = storageClient.delete_file(groupName, remoteFileName);
    logger.info("delete file successfully!!!" + i);
    return i;
  }

  /**
   * 关闭链接
   */
  public static void closed(){
    if(null!=storageServer)
      try {
        storageServer.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    if(null!=trackerServer)
      try {
        trackerServer.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  }
}
