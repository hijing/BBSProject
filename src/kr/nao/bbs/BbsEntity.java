package kr.nao.bbs;

import java.sql.Date;

public class BbsEntity  {

	private int bbsNo;				// PK
	private String title;			// 제목
	private Date wDate;				// 입력 및 수정일자
	private String wName;			// 작성자
	private String content;			// 내용
	private int hit; 				// 조회수
	private String delYN;			// 삭제여부 ('N'/ 'Y')
	private String searchType;		// 검색 타입
	private String searchTitle;     // 검색 내용
	private String fileName;		// 파일명
	
	public int getBbsNo() {
		return bbsNo;
	}
	public void setBbsNo(int bbsNo) {
		this.bbsNo = bbsNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getwDate() {
		return wDate;
	}
	public void setwDate(Date wDate) {
		this.wDate = wDate;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getDelYN() {
		return delYN;
	}
	public void setDelYN(String delYN) {
		this.delYN = delYN;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchTitle() {
		return searchTitle;
	}
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
