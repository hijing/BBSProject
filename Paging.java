package com.project.bbsServlet;

public class Paging {

	
	private int page; //현재페이지 
	private int limit; //한페이지당 나오는 게시물 수
	
	private int countPage;  // 한 화면에 보여주고싶은 페이지 수  [1] [2] [3] ...
	
	private int totalCount; // 전체 게시물 수 쿼리통해서얻어옴

	private int maxPage; // 페이지의 마지막 쪽 수 및 페이지 총 수 (totalCount/limit);
	private int startPage;  // 해당페이징의 게시물의 시작페이지 ( (현재페이지-1)/한화면에 보여주고싶은 페이지수 )*한화면에 보여주고싶은 페이지수  +1  
	private int endPage;	// 해당페이징의  마지막페이지 및 마지막페이지값 ( startPage+countpage-1)  시작페이지+보여주고싶은 페이지수-1;
	private int startCount;  //rownum 에 시작할 게시물 번호  (page-1)*limit +1; (페이지 카운트식)
	private int endCount;     //rownum 에 마지막 게시물번호  (page*limit);
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getMaxPage() {		//페이지수 및 마지막페이지   (전체게시물수 / 한페이지 보여줄 게시물수)
		maxPage = (getTotalCount()/getLimit());
		
		if(getTotalCount() % getLimit()>0){
			maxPage++;
		}
		if(maxPage<getPage()){		//maxPage는 현 존재하는 게시물의 마지막페이지 
				maxPage=getPage();
		}
		
		return maxPage;
	}
	
	public int getStartPage() {
		startPage =  ( (getPage()-1)/getCountPage() )*getCountPage()+1; // 
		return startPage;
	}
	public int getEndPage() {
		endPage = getStartPage()+getCountPage()-1;
		
		if(endPage > getMaxPage()){ //maxPage는 현 존재하는 게시물의 마지막페이지  즉 endPage는  page당 보고싶은 페이지 수의 마지막 수
			endPage = getMaxPage();
		}
		return endPage;
	}
	
	public int getStartCount() {
		startCount= (getPage()-1)*getLimit()+1;
		return startCount;
	}
	public int getEndCount() {
		endCount = getPage()*getLimit();
		return endCount;
	}
}
