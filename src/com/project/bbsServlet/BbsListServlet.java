package com.project.bbsServlet;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class BbsList
 */
@WebServlet("/bbsList")
public class BbsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsListServlet() {
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
		System.out.println("�떎�뻾�맂 Url: " + getUrl);
		
		ArrayList<BbsEntity> list = new ArrayList<BbsEntity>();
		BbsDao dao = new BbsDao();
		
		// paging 湲곕낯媛믪꽭�똿
		int page = 1;			
		int limit = 5;			
   		int listCount = dao.getListCount();
   		
   		Paging paging = new Paging();	// �럹�씠吏� �겢�옒�뒪
   		
   		if(request.getParameter("page") != null){
   			 paging.setPage(Integer.parseInt(request.getParameter("page")));
   			 page = paging.getPage();
   		}
   		
   		System.out.println("�쁽�옱 page踰덊샇: " + page);
   		
   		String type = request.getParameter("searchType");
   		String title = request.getParameter("searchTitle");
   		
   		paging.setPage(page);
   		paging.setLimit(limit);
   		paging.setTotalCount(listCount);
   		paging.setCountPage(3);
   		
   		if(type != null && !type.equals("0") && title != null){
   			BbsEntity dto = new BbsEntity();
   			
   			dto.setSearchTitle(title);
   			dto.setSearchType(type);
   			listCount = dao.getListOptCount(type, title);
   			paging.setTotalCount(listCount);
   			list = dao.bbsListOpt(dto, paging.getStartCount(), paging.getEndCount());
   		}else{
   			type = null;
   			title = null;
   			list = dao.bbsList(paging.getStartCount(), paging.getEndCount());
   		}
   		
   		request.setAttribute("Type", type );
   		request.setAttribute("TT", title );							
   		request.setAttribute("list", list);
   		request.setAttribute("page", page);		 				   // �쁽�옱 �럹�씠吏�
   		request.setAttribute("maxpage", paging.getMaxPage()); 	   // �럹�씠吏� 以� �젣�씪 �겙 �닔 
   		request.setAttribute("startpage", paging.getStartPage());  // 泥� �럹�씠吏� 
   		request.setAttribute("endpage", paging.getEndPage());      // 留덉�留� �럹�씠吏�
		request.setAttribute("listcount", listCount); 			   // �쟾泥� 寃뚯떆臾� �닔
		request.setAttribute("countPage", paging.getCountPage());  // �븳 �솕硫댁뿉 蹂댁뿬吏� �럹�씠吏� �닔 
		request.setAttribute("limit", limit);					   // �븳 �럹�씠吏� 蹂댁뿬以� 寃뚯떆臾� �닔 
		
//		System.out.println("max" + paging.getMaxPage() + " start" + paging.getStartPage() + 
//							" endpage" + paging.getEndPage() + " listcount" + listCount);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/bbs/bbsList.jsp"); //root紐낆씠誘몃텤�쓬  /web/web/exercise/resisterList.jsp
		dispatcher.forward(request, response);
	}

}
