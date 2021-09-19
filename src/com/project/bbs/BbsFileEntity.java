package com.project.bbs;

public class BbsFileEntity {

	private int fileNum;			// 파일번호
	private String oName;			// 기존파일명
	private String fileName; 		// 바꿔서들어간파일명
	private int fileSize;			// 파일크기 
	private String fileType;		// 파일타입
	private int bbsNo;				// 게시물번호
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	public String getoName() {
		return oName;
	}
	public void setoName(String oName) {
		this.oName = oName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public int getBbsNo() {
		return bbsNo;
	}
	public void setBbsNo(int bbsNo) {
		this.bbsNo = bbsNo;
	}
	
}
