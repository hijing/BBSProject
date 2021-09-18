package kr.nao.bbsServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.nao.bbs.BbsEntity;
import kr.nao.bbs.BbsFileEntity;
import kr.nao.bbsdao.BbsDao;

/**
 * Servlet implementation class BbsDetail
 */
@WebServlet("/bbsDetail")
public class BbsDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsDetailServlet() {
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
		System.out.println("실행된 Url: "+getUrl);
		
		BbsEntity bbsEntity = new BbsEntity();
		BbsDao dao = new BbsDao();
		List<BbsFileEntity> list = new ArrayList<BbsFileEntity>();
		
		// page 이동처리
		ServletContext context = getServletContext();
		String url = request.getParameter("urlName"); // 넘어오는 url
		System.out.println(url);
		
		// 게시판번호 및 dao 처리
		int bbsNo =  Integer.parseInt(request.getParameter("bbsNo"));
//		System.out.println("detail bbsNo 값: "+ bbsNo);
		
		bbsEntity.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
		bbsEntity = dao.getBbsDetail(bbsEntity);
		list = dao.fileList(bbsNo);

		request.setAttribute("dto", bbsEntity);
		request.setAttribute("fileList", list);
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null; //쿠키를 비교하기위한 값 세팅
		
		if(cookies != null && cookies.length>0) { 	// 쿠키가 존재할시
			for(int i =0; i< cookies.length; i++) { // 쿠키가 등록된 배열 만큼 반복
				if(cookies[i].getName().equals("cookie"+bbsNo)) {
					System.out.println("쿠키 여부 확인후  동일한 쿠키 있으면 조회수 처리않고 페이지이동");
					cookie = cookies[i];
					
					RequestDispatcher dispatcher = context.getRequestDispatcher(url);//프로젝트명이이미붙음  /web/web/exercise/resisterList.jsp
					dispatcher.forward(request, response);
				}
			}
		}
		
		if(cookie == null) {// 위에 배열에서 쿠키가 존재하지 않을 시 쿠키를 생성 및 추가 한다.
			System.out.println("쿠키없음");
			
			Cookie newCookie = new Cookie("cookie"+bbsNo, "|"+bbsNo+"|" ); //쿠키생성 ( 이름, 값)
			response.addCookie(newCookie);
			dao.listCnt(bbsEntity);// list 조회수 증가 
			
			RequestDispatcher dispatcher = context.getRequestDispatcher(url);//프로젝트명이이미붙음  /web/web/exercise/resisterList.jsp
			dispatcher.forward(request, response);
		}
		
	}

}
