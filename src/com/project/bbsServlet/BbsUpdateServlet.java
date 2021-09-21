package com.project.bbsServlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import com.project.bbs.BbsEntity;
import com.project.bbsdao.BbsDao;

/**
 * Servlet implementation class BbsUpdateServlet
 */
@WebServlet("/bbsUpdate")
public class BbsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsUpdateServlet() {
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
		response.setContentType("text/html;charset=utf-8");
			
		String getUrl = request.getRequestURI();
		System.out.println("�떎�뻾�맂 Url: " + getUrl); 
			
		BbsEntity bbsEntity = new BbsEntity();
		BbsDao dao = new BbsDao();
			
		int maxPostSize = 10 * 1024 * 1024; // 10MB
		String saveDirectory = getServletContext().getRealPath("\\upload");
			
//			BbsEntity dto = new BbsEntity();   //�궘�젣 �떎�뙣
//			MultipartRequest mul = new MultipartRequest(request, saveDirectory);
//			dto.setBbsNo(Integer.parseInt(mul.getParameter("bbsNo") ) );
//			dto = dao.getBbsDetail(bbsEntity);
//			if(dto.getFileName()!=null){// �닔�젙 �솕硫댁뿉�꽌 泥⑤��뙆�씪 諛붽퓭以� �븣 湲곗〈 �뙆�씪�뿬遺� �솗�씤 �썑 �엳�쑝硫� 吏��썙以��떎
//				
//				String uploadFileName = getServletContext().getRealPath("\\upload")+"/"+ dto.getFileName();
//				
//				File uploadFile = new File(uploadFileName);
//				uploadFile.delete(); //�뙆�씪 �엳�쓣 �떆 �궘�젣
//			}
			
		try{
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, "utf-8", new DefaultFileRenamePolicy());	
			
			int result = 0;
			bbsEntity.setBbsNo(Integer.parseInt(multi.getParameter("bbsNo")));
			
			bbsEntity.setContent(multi.getParameter("content"));
			bbsEntity.setTitle(multi.getParameter("title"));
			bbsEntity.setFileName(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
			
			result = dao.bbsUpdate(bbsEntity);
			
			if(result > 0){
				response.sendRedirect("/bbs/bbsList");
			}else{
				response.sendRedirect("/bbs/bbs/updateFail.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
