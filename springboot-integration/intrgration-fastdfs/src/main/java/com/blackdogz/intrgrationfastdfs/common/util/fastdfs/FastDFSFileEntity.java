package com.blackdogz.intrgrationfastdfs.common.util.fastdfs;
/**
 * FastDFS 上传文件 信息实体
 * @author zqdpc
 */
public class FastDFSFileEntity {

  private String name;
  private byte[] content;		//上传文件大小
  private String ext;			//上传文件后缀名称
  private String md5;
  private String author;

  public FastDFSFileEntity(){};

  /**
   *
   * @param name	文件名称
   * @param content	文件大小
   * @param ext	后缀名
   */
  public FastDFSFileEntity(String name, byte[] content, String ext) {
    // TODO Auto-generated constructor stub
    this.name = name;
    this.content = content;
    this.ext = ext;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getExt() {
    return ext;
  }

  public void setExt(String ext) {
    this.ext = ext;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

}
