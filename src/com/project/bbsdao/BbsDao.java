package com.project.bbsdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;

import com.oreilly.servlet.MultipartRequest;

import com.project.bbs.BbsEntity;
import com.project.bbs.BbsFileEntity;
import com.project.bbsSerivce.BbsService;
import com.project.db.DBconnection;

public class BbsDao implements BbsService {

	private static BbsDao instance;
	
	// �떛湲��넠 �깮�꽦
	public static BbsDao getInstance(){
		if (instance == null)
			instance = new BbsDao();
		return instance;
	}
	
	// list 異쒕젰
	@Override
	public ArrayList<BbsEntity> bbsList(int startCount, int endCount) {
		
		ArrayList<BbsEntity> list = new ArrayList<BbsEntity>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append(" select B.RNUM,B.BBSNO,B.TITLE,B.WDATE,B.WNAME,B.CONTENT,B.HIT,B.DELYN FROM  ");				
			sql.append("(select ROWNUM AS RNUM , A.BBSNO,A.TITLE,A.WDATE,A.WNAME,A.CONTENT,A.HIT,A.DELYN FROM ");
			sql.append("(select BBSNO,(CASE WHEN LENGTH(TITLE)>10 then substr(TITLE,1,10)||"+"'...'"+" else title end) AS TITLE ,");
			sql.append("wdate,wname,content,hit,delyn  from bbs where delyn ='N' order by wdate DESC)A  ");
			sql.append(" where ROWNUM <= ? )B"); 	//寃뚯떆臾� �빐�떦 �럹�씠吏� 留덉�留됯볼源뚯�
			sql.append(" where B.RNUM >= ? ");		//寃뚯떆臾� 泥ル쾲吏�
			
//			System.out.println("List荑쇰━:"+sql);
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, endCount );
			psmt.setInt(2, startCount);
			rs = psmt.executeQuery();
		
			sql.delete(0, sql.toString().length()); // stringBuffer �쓽寃쎌슦 �옱�궗�슜 �븷 寃껋씠誘�濡� �븯�굹�쓽 �옉�뾽�씠 �걹�굹硫� 踰꾪띁瑜� 鍮꾩썙以��떎.
			
			while (rs.next()) {
				BbsEntity bbsEntity = new BbsEntity();
				
				bbsEntity.setBbsNo(Integer.parseInt(rs.getString(2)));
				bbsEntity.setTitle(rs.getString(3));
				bbsEntity.setwDate(rs.getDate(4));
				bbsEntity.setwName(rs.getString(5));
				bbsEntity.setContent(rs.getString(6));
				bbsEntity.setHit(rs.getInt(7));
				bbsEntity.setDelYN(rs.getString(8));
				list.add(bbsEntity);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		
		return list;
	}
	
	// 寃��깋寃곌낵 list
	@Override
	public ArrayList<BbsEntity> bbsListOpt(BbsEntity bbsEntity, int startCount, int endCount) {

		ArrayList<BbsEntity> list = new ArrayList<BbsEntity>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null; 
		
		StringBuffer sql = new StringBuffer();
		String searchType = bbsEntity.getSearchType();
		
		if(searchType.equals("1")){
			 searchType = "TITLE";
		}else if(searchType.equals("2")){
			searchType="WNAME";
		}
		
		System.out.println("寃��깋議곌굔: " + searchType + ", 寃��깋�뼱: " + bbsEntity.getSearchTitle());
		
		try {
			if(searchType.equals("TITLE")){
				sql.append(" select B.RNUM,B.BBSNO,B.TITLE,B.WDATE,B.WNAME,B.CONTENT,B.HIT,B.DELYN FROM  ");
				sql.append("(select ROWNUM AS RNUM , A.BBSNO,A.TITLE,A.WDATE,A.WNAME,A.CONTENT,A.HIT,A.DELYN FROM  ");
				sql.append("(select BBSNO,(CASE WHEN LENGTH(TITLE)>10 then substr(TITLE,1,10)||"+"'...'"+" else title end) AS TITLE ,");
				sql.append("wdate,wname,content,hit,delyn  from bbs where delyn ='N' and TITLE like ? order by wdate DESC)A  ");
				sql.append(" where ROWNUM <= ? )B"); 	//寃뚯떆臾� �빐�떦 �럹�씠吏� 留덉�留됯볼源뚯�
				sql.append(" where B.RNUM >= ? ");		//寃뚯떆臾� 泥ル쾲吏�
			}else if(searchType.equals("WNAME")){
				sql.append(" select B.RNUM,B.BBSNO,B.TITLE,B.WDATE,B.WNAME,B.CONTENT,B.HIT,B.DELYN FROM  ");
				sql.append("(select ROWNUM AS RNUM , A.BBSNO,A.TITLE,A.WDATE,A.WNAME,A.CONTENT,A.HIT,A.DELYN FROM  ");
				sql.append("(select BBSNO,(CASE WHEN LENGTH(TITLE)>10 then substr(TITLE,1,10)||"+"'...'"+" else title end) AS TITLE ,");
				sql.append("wdate,wname,content,hit,delyn  from bbs where delyn ='N' and WNAME like ? order by wdate DESC)A  ");
				sql.append(" where ROWNUM <= ? )B"); 	//寃뚯떆臾� �빐�떦 �럹�씠吏� 留덉�留됯볼源뚯�
				sql.append(" where B.RNUM >= ? ");		//寃뚯떆臾� 泥ル쾲吏�
			}
			
//			System.out.println("listOpt 荑쇰━:"+sql.toString());
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, "%"+bbsEntity.getSearchTitle()+"%");
			psmt.setInt(2, endCount );
			psmt.setInt(3, startCount);
			rs = psmt.executeQuery();
			
			sql.delete(0, sql.toString().length());
			
			while (rs.next()) {
				BbsEntity dto = new BbsEntity();
				
				dto.setBbsNo(Integer.parseInt(rs.getString(2)));
				dto.setTitle(rs.getString(3));
				dto.setwDate(rs.getDate(4));
				dto.setwName(rs.getString(5));
				dto.setContent(rs.getString(6));
				dto.setHit(rs.getInt(7));
				dto.setDelYN(rs.getString(8));
				list.add(dto);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		return list;
	}
	
	// �븳紐� �쉶�썝�젙蹂� 議고쉶
	@Override
	public BbsEntity getBbsDetail(BbsEntity bbsEntity) {
		
		BbsEntity dto = new BbsEntity();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = "";
		int bbsno= 0;
		
		try {
			sql = "select * from bbs where bbsno = ?";
			
//			System.out.println(sql);
			
			bbsno = bbsEntity.getBbsNo();
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, bbsno);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				dto.setBbsNo(rs.getInt(1));
				dto.setTitle(rs.getString(2));
				dto.setwDate(rs.getDate(3));
				dto.setwName(rs.getString(4));
				dto.setContent(rs.getString(5));
				dto.setHit(rs.getInt(6));
				dto.setDelYN(rs.getString(7));
				dto.setFileName(rs.getString(8));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		
		return dto;
	}

	public String fileName(int bbsNo){
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String fileName = "";
		
		StringBuffer sql = new StringBuffer();
		
		try{
			conn = DBconnection.getConnection();
			sql.append("SELECT FILENAME	FROM BBSFILE WHERE BBSNO = ?");
			
//			System.out.println("fileName 議고쉶 �떆 寃뚯떆臾� 踰덊샇 媛� 荑쇰━: \n"+sql.toString());

			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, bbsNo);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				fileName = rs.getString(1);
			}
			sql.delete(0, sql.length());
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		
		return fileName;
	}
	
	public List<BbsFileEntity> fileList(int bbsNo){
		
		List<BbsFileEntity> list = new ArrayList<BbsFileEntity>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		try{
			sql.append("SELECT FILENUM,	ONAME, FILENAME, FILESIZE, BBSNO FROM BBSFILE");
			sql.append(" WHERE BBSNO = ?");
			
//			System.out.println("fileList 荑쇰━: " + sql.toString());
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, bbsNo);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				BbsFileEntity dto = new BbsFileEntity();
				
				dto.setFileNum(rs.getInt(1));
				dto.setoName(rs.getString(2));
				dto.setFileName(rs.getString(3));
				dto.setFileSize(rs.getInt(4));
				dto.setBbsNo(rs.getInt(5));
				list.add(dto);
			}
			sql.delete(0, sql.length());
			
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBconnection.close(rs, psmt, conn);
			}
		
		return list;
	}
	
	@Override
	public int bbsInsert(BbsEntity bbsEntity) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = "";
		
		try{
			sql = "insert into bbs (BBSNO, wName, TITLE, WDATE, CONTENT, HIT, DELYN, FILENAME)";
			sql += " VALUES (BBSNO.nextval,?,?,SYSDATE,?,0,'N',?)";
			
//			System.out.println("insert 荑쇰━:"+sql);

			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, bbsEntity.getwName());
			psmt.setString(2, bbsEntity.getTitle());
			psmt.setString(3, bbsEntity.getContent());
			psmt.setString(4, bbsEntity.getFileName());
			
			result = psmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(psmt, conn);
		}
		return result;
	}
	
	public int bbsInsertFile(BbsEntity bbsEntity, List<BbsFileEntity> list){
		
		int result = 0;
		int bbsNo = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		try{
			sql.append("insert into bbs (BBSNO,wName,TITLE,WDATE,CONTENT,HIT,DELYN,FILENAME)") ;
			sql.append(" VALUES (BBSNO.nextval,?,?,SYSDATE,?,0,'N',?) "   );
			
//			System.out.println("bbsInsertFile 寃뚯떆臾쇱옘由щЦ: \n"+sql.toString() );
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, bbsEntity.getwName());
			psmt.setString(2, bbsEntity.getTitle());
			psmt.setString(3, bbsEntity.getContent());
			psmt.setString(4, bbsEntity.getFileName());
			psmt.executeUpdate();
			
			sql.delete(0, sql.length());
			
			sql.append("SELECT 											 		 \n");
			sql.append("						MAX(BBSNO)				 		 \n");
			sql.append("FROM 											 		 \n");
			sql.append("						BBS				 				 \n");
			sql.append("ORDER BY 										 		 \n");
			sql.append("						BBSNO				 			 \n");
			sql.append("						DESC			 				 \n");
			
//			System.out.println("bbsInsertFile insert �떆 寃뚯떆臾� 踰덊샇 媛� 荑쇰━: \n"+sql.toString());
			
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();
			
			while(rs.next()){
				bbsNo = rs.getInt(1);
			}
			
			sql.delete(0, sql.length());
			
			sql.append("INSERT INTO BBSFILE	(FILENUM, ONAME, FILENAME, FILESIZE, BBSNO) ");	
			sql.append("VALUES(FILENUM.NEXTVAL,	?, ?, ?, ?)								");
			
//			System.out.println("bbsInsertFile �뙆�씪荑쇰━臾�\n"+sql.toString());
			
			psmt = conn.prepareStatement(sql.toString());
			
			for(int i = 0; i < list.size(); i++){
				psmt.setString(1, list.get(i).getoName() );
				psmt.setString(2, list.get(i).getFileName());
				psmt.setInt(3, list.get(i).getFileSize());
				psmt.setInt(4, bbsNo);
				psmt.executeUpdate();
				result++;
			}
			
			sql.delete(0, sql.length());
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		
		return result;
	}
	
	@Override
	public int bbsUpdate(BbsEntity bbsEntity) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = "";
		
		try{
			if(bbsEntity.getFileName() != null){
				sql = "UPDATE BBS SET TITLE = ?, WDATE = SYSDATE , CONTENT = ? , FileName = ? ";
			}else{
				sql = "UPDATE BBS SET TITLE = ?, WDATE = SYSDATE , CONTENT = ? ";	
			}
			
			sql += " WHERE BBSNO = ? ";
			
//			System.out.println("update荑쇰━:"+sql);
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, bbsEntity.getTitle());
			psmt.setString(2, bbsEntity.getContent());
			
			if(bbsEntity.getFileName() != null){
				psmt.setString(3, bbsEntity.getFileName());
				psmt.setInt(4,bbsEntity.getBbsNo());
			}else{
				psmt.setInt(3,bbsEntity.getBbsNo());	
			}
			result = psmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(psmt, conn);
		}
		
		return result;
	}

	@Override
	public int bbsDelete(BbsEntity bbsEntity) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = "";
		
		try{
			sql = "UPDATE BBS SET  DELYN = 'Y' WHERE BBSNO = ? ";
			
//			System.out.println(sql);
			
			conn = DBconnection.getConnection();
			psmt= conn.prepareStatement(sql);
			psmt.setInt(1, bbsEntity.getBbsNo());
			psmt.executeUpdate();
			result = psmt.executeUpdate();
			
			System.out.println("�궘�젣�셿猷�");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(psmt, conn);
		}
		
		return result;
	}
	
	// 議고쉶�닔
	@Override
	public int listCnt(BbsEntity bbsEntity) {
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int result = 0;
		
		String sql = "update bbs set hit = ?+1 where bbsno = ?";
		
		try{
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, bbsEntity.getHit());
			psmt.setInt(2, bbsEntity.getBbsNo());
			psmt.executeUpdate();
			result = psmt.executeUpdate();
			
//			System.out.println("議고쉶�닔利앷�:" + result);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBconnection.close(psmt, conn);
		}
		return result;
	}
	
	// 寃뚯떆�뙋 湲� 媛��닔 議고쉶
	@Override
	public int getListCount() {
		
		int count = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		try{
			sql.append("select count(*) from bbs where delYn='N'");
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBconnection.close(rs, psmt, conn);
		}
		
		return count;
	}
	
	// 寃��깋 議곌굔�썑 寃뚯떆臾� �닔
	@Override
	public int getListOptCount(String searchType, String searchTitle) {
		
		int count = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		if(searchType.equals("1")){
			 searchType = "TITLE";
		}else if(searchType.equals("2")){
			searchType="WNAME";
		}
		
		try{
			if(searchType.equals("TITLE")){
				sql.append("select count(*)from bbs where delYn='N' AND TITLE like ? ");
			}else if(searchType.equals("WNAME")){
				sql.append("select count(*)from bbs where delYn='N' AND WNAME like ? ");
			}
			
//			System.out.println("getListOptCount(寃��깋 �썑 珥앷쾶�떆臾� 媛��닔):"+sql.toString());
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, "%"+searchTitle+"%");
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBconnection.close(rs, psmt, conn);
		}
		
		return count;
	}
	
	// list 異쒕젰
	public ArrayList<BbsEntity> bbsList() {
		
		ArrayList<BbsEntity> list = new ArrayList<BbsEntity>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append("select BBSNO,(CASE WHEN LENGTH(TITLE)>10 then substr(TITLE,1,10)||"+"'...'"+" else title end) AS TITLE ,");
			sql.append("wdate,wname,content,hit,delyn  from bbs where delyn ='N' order by wdate DESC ");
			
//			System.out.println("List荑쇰━:"+sql);
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();
			
			sql.delete(0, sql.toString().length()); // stringBuffer �쓽寃쎌슦 �옱�궗�슜 �븷 寃껋씠誘�濡� �븯�굹�쓽 �옉�뾽�씠 �걹�굹硫� 踰꾪띁瑜� 鍮꾩썙以��떎.
			
			while (rs.next()) {
				BbsEntity bbsEntity = new BbsEntity();
				
				bbsEntity.setBbsNo(Integer.parseInt(rs.getString(1)));
				bbsEntity.setTitle(rs.getString(2));
				bbsEntity.setwDate(rs.getDate(3));
				bbsEntity.setwName(rs.getString(4));
				bbsEntity.setContent(rs.getString(5));
				bbsEntity.setHit(rs.getInt(6));
				bbsEntity.setDelYN(rs.getString(7));
				list.add(bbsEntity);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}finally{
			DBconnection.close(rs, psmt, conn);
		}
		return list;
	}
	
}
