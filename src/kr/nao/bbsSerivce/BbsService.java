package kr.nao.bbsSerivce;

import java.util.ArrayList;

import kr.nao.bbs.BbsEntity;

public interface BbsService {

	public ArrayList<BbsEntity> bbsList(int startCount, int endCount);
	public int getListCount();												//list 게시물 갯수
	public int getListOptCount(String searchType , String searchTitle);		// 검색조건 적용 후 게시물 갯수
	public BbsEntity getBbsDetail(BbsEntity bbsEntity);
	public int bbsInsert(BbsEntity bbsEntity);	
	public int bbsUpdate(BbsEntity bbsEntity);			
	public int bbsDelete(BbsEntity bbsEntity);								// delyn ='Y'
	public int listCnt(BbsEntity bbsEntity);								// 조회수
	public ArrayList<BbsEntity> bbsListOpt(BbsEntity bbsEntity,int startCount, int endCount);
}
