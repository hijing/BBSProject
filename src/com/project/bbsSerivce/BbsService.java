package com.project.bbsSerivce;

import java.util.ArrayList;

import com.project.bbs.BbsEntity;

public interface BbsService {

	public ArrayList<BbsEntity> bbsList(int startCount, int endCount);
	public int getListCount();												//list 寃뚯떆臾� 媛��닔
	public int getListOptCount(String searchType , String searchTitle);		// 寃��깋議곌굔 �쟻�슜 �썑 寃뚯떆臾� 媛��닔
	public BbsEntity getBbsDetail(BbsEntity bbsEntity);
	public int bbsInsert(BbsEntity bbsEntity);	
	public int bbsUpdate(BbsEntity bbsEntity);			
	public int bbsDelete(BbsEntity bbsEntity);								// delyn ='Y'
	public int listCnt(BbsEntity bbsEntity);								// 議고쉶�닔
	public ArrayList<BbsEntity> bbsListOpt(BbsEntity bbsEntity,int startCount, int endCount);
}
