package com.blackdogz.intrgrationfastdfs.module.file.web;

import com.blackdogz.intrgrationfastdfs.common.util.R;
import com.blackdogz.intrgrationfastdfs.common.util.exception.BIZException;
import com.blackdogz.intrgrationfastdfs.module.file.service.FastDFSService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author zqdpc
 */
@RestController
@RequestMapping("fdfs")
public class FastDFSController {
  private static final Logger logger = LoggerFactory.getLogger(FastDFSController.class);

  //open file server  true:open   false:close
  @Value("${fastdfs.open}")
  private boolean open;
  @Value("${fastdfs.upload.addr}")
  private String addr;
  @Value("${fastdfs.file_path}")
  private String filePath;
  @Value("${fastdfs.server_url}")
  private String serverUrl;

  @Autowired
  private FastDFSService fastDFSService;

  /**
   * 上传文件
   * @param file
   * @return
   */
  @PostMapping("/upload")
  public R singleFileUpload(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      throw new BIZException("file is null");
    }

    StringBuilder path = new StringBuilder();

    try {
      //open file server
      if (open) {
        String fastPath = fastDFSService.saveFile(file);
        logger.info("upload success: '" + file.getOriginalFilename() + "'");
        logger.info("fastPath : '" + fastPath + "'");
        path.append(addr).append(fastPath);
      } else {
        // 往指定地方传图
        String filename = file.getOriginalFilename();
        StringBuilder localFilePath = new StringBuilder();
        Long timpstamp = System.currentTimeMillis();
        localFilePath.append(filePath).append(timpstamp).append('-').append(filename);
        logger.info("ready upload: '" + timpstamp + "'");
        file.transferTo(new File(localFilePath.toString()));
        path.append(serverUrl).append(timpstamp).append('-').append(filename);
      }
    } catch (Exception e) {
      logger.error("upload fail", e);
    }

    return R.ok().put("data", path.toString());
  }

  /**
   * 删除文件
   * @param groupName	group名称
   * @param remoteFileName	路径
   * @return
   */
  @PostMapping("/delete")
  public R singleFileDelete(String groupName, String remoteFileName) {
    if(StringUtils.isEmpty(groupName) || StringUtils.isEmpty(remoteFileName)){
      throw new BIZException("file is not exist");
    }

    int count = -1;
    try {
      count = fastDFSService.deleteFile(groupName, remoteFileName);
    } catch (Exception e) {
      logger.error("delete error", e);
    }
    return count == 0 ? R.ok() : R.error("delete fail");
  }

  /**
   * 下载文件
   * @param groupName
   * @param remoteFileName
   * @return
   */
  @PostMapping("/download")
  public R singleFileDownload(String groupName, String remoteFileName) {
    if(StringUtils.isEmpty(groupName) || StringUtils.isEmpty(remoteFileName)){
      throw new BIZException("file is not exist");
    }
    try {
      fastDFSService.downloadFile(groupName, remoteFileName);
    } catch (Exception e) {
      logger.error("download error", e);
    }
    return R.ok();
  }
}
