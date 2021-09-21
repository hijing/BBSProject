package com.project.bbsServlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import com.project.bbs.BbsEntity;
import com.project.bbs.BbsFileEntity;
import com.project.bbsdao.BbsDao;

/**
 * Servlet implementation class BbsInsert
 */
@WebServlet("/bbsInsert")
public class BbsInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsInsertServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String getUrl = request.getRequestURI();
		String getCotentType = request.getContentType();
		
		System.out.println("�떎�뻾�맂 Url:" + getUrl +", getcontentType: " + getCotentType);
		System.out.println("contextPath():" + getServletContext().getContextPath());
		
		BbsDao dao = new BbsDao();
		int result = 0;

		int maxPostSize = 10 * 1024 * 1024; // 10MB �븵�뿉 100 �씪�떆 100MB 
		String saveDirectory = getServletContext().getRealPath("\\upload");
		
//		System.out.println("�뙆�씪���옣寃쎈줈: " + saveDirectory);
		
		List<BbsFileEntity> fileRList = new ArrayList<BbsFileEntity>();
		
		MultipartRequest multi = new MultipartRequest(request,saveDirectory,maxPostSize,"utf-8",new DefaultFileRenamePolicy()); // 誘몃━ multipartRequest 媛앹껜瑜� �깮�꽦�빐�빞�븿
		
		Enumeration eNum = multi.getFileNames();
		String formName= "";
		File fileVal = null;
		
//		if(	(String)eNum.nextElement()!=null){
			
			while(eNum.hasMoreElements()){
				BbsFileEntity filedto = new BbsFileEntity();
				formName = (String)eNum.nextElement();
				filedto.setoName(multi.getOriginalFileName(formName));  // file oName
				filedto.setFileName(multi.getFilesystemName(formName)); // file Name
				filedto.setFileType(multi.getContentType(formName));    // file contentType
				fileVal	= multi.getFile(formName);					    // �뙆�씪�궗�씠利덈�� 援ы븯湲곗쐞�빐�꽑 file ���엯�뿉 �뙆�씪誘명꽣�뿉  form tag Name 紐낆쓣 �꽔�뼱�빞�븿.
				filedto.setFileSize((int)fileVal.length());			    // file Size 罹먯뒪�똿�븞�븯硫� long���엯
				fileRList.add(filedto);
			}
			
			for(int i = 0; i < fileRList.size(); i++){
				System.out.println("�뙆�씪 �씠由�("+i+"): " +fileRList.get(i).getoName());
				System.out.println("�뙆�씪 �궗�씠利�("+i+"): " +fileRList.get(i).getFileSize());
			}
//		}
		
//		Enumeration eNum =multi.getFileNames();		// file upload �떆  form �깭洹몄뿉 name �쓣 媛��졇�삤�뒗 寃�
//		while(eNum.hasMoreElements()){
//			formName = (String)eNum.nextElement();
//			fileList.add(formName);
//		}
//		
//		if(fileList.size()>0){
//			for(int i = 0;i < fileList.size();i++){
//				fileName.add(multi.getFilesystemName( (String)fileList.get(i) )) ;
//				System.out.println("file �씠由�: "+fileName.get(i));
//			}
//		}
		
		//RealPath C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/BBSProject/fileUpload ==>�쎒�봽濡쒖젥�듃紐낃퉴吏� 遺덈윭�샂
		
		try{
			BbsEntity bbsEntity = new BbsEntity(); 
		
			if(fileRList.size() == 0){
				bbsEntity.setwName(multi.getParameter("wName"));
				bbsEntity.setTitle(multi.getParameter("title"));
				bbsEntity.setContent(multi.getParameter("content"));
				bbsEntity.setFileName(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
				result = dao.bbsInsert(bbsEntity);
			}else{
				bbsEntity.setwName(multi.getParameter("wName"));
				bbsEntity.setTitle(multi.getParameter("title"));
				bbsEntity.setContent(multi.getParameter("content"));
				bbsEntity.setFileName(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
				result = dao.bbsInsertFile(bbsEntity, fileRList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(result > 0){ // �셿猷뚯떆 �럹�씠吏��씠�룞 泥섎━
			response.sendRedirect("/bbs/bbsList");
		}else{
			response.sendRedirect("/bbs/bbs/insertFail.jsp");
		}
	}

}
