package com.project.bbsServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.bbs.BbsEntity;
import com.project.bbsdao.BbsDao;

/**
 * Servlet implementation class BbsDeleteServlet
 */
@WebServlet("/bbsDelete")
public class BbsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsDeleteServlet() {
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
		System.out.println("�떎�뻾�맂 Url: "+getUrl);
			
		int result = 0;
			
		BbsDao dao = new BbsDao();
		BbsEntity bbsEntity = new BbsEntity();
			
		bbsEntity.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
			
		dao.bbsDelete(bbsEntity);
		result= dao.bbsDelete(bbsEntity);
			
		if(result > 0){
			response.sendRedirect("/bbs/bbsList");
		}else{
			request.setAttribute("dto", bbsEntity);
			ServletContext context = getServletContext();
			
			RequestDispatcher dispatcher = context.getRequestDispatcher("/bbs/bbsDetail");//�봽濡쒖젥�듃紐낆씠�씠誘몃텤�쓬  /web/web/exercise/resisterList.jsp
			dispatcher.forward(request, response);		
		}
	}

}