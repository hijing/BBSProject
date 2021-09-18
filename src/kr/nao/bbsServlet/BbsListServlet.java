package kr.nao.bbsServlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.nao.bbs.BbsEntity;
import kr.nao.bbsdao.BbsDao;

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
		System.out.println("실행된 Url: " + getUrl);
		
		ArrayList<BbsEntity> list = new ArrayList<BbsEntity>();
		BbsDao dao = new BbsDao();
		
		// paging 기본값세팅
		int page = 1;			
		int limit = 5;			
   		int listCount = dao.getListCount();
   		
   		Paging paging = new Paging();	// 페이징 클래스
   		
   		if(request.getParameter("page") != null){
   			 paging.setPage(Integer.parseInt(request.getParameter("page")));
   			 page = paging.getPage();
   		}
   		
   		System.out.println("현재 page번호: " + page);
   		
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
   		request.setAttribute("page", page);		 				   // 현재 페이지
   		request.setAttribute("maxpage", paging.getMaxPage()); 	   // 페이지 중 제일 큰 수 
   		request.setAttribute("startpage", paging.getStartPage());  // 첫 페이지 
   		request.setAttribute("endpage", paging.getEndPage());      // 마지막 페이지
		request.setAttribute("listcount", listCount); 			   // 전체 게시물 수
		request.setAttribute("countPage", paging.getCountPage());  // 한 화면에 보여질 페이지 수 
		request.setAttribute("limit", limit);					   // 한 페이지 보여줄 게시물 수 
		
//		System.out.println("max" + paging.getMaxPage() + " start" + paging.getStartPage() + 
//							" endpage" + paging.getEndPage() + " listcount" + listCount);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/bbs/bbsList.jsp"); //root명이미붙음  /web/web/exercise/resisterList.jsp
		dispatcher.forward(request, response);
	}

}
