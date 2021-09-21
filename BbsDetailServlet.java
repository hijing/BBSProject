package com.project.bbsServlet;

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

import com.project.bbs.BbsEntity;
import com.project.bbs.BbsFileEntity;
import com.project.bbsdao.BbsDao;

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
		System.out.println("�떎�뻾�맂 Url: "+getUrl);
		
		BbsEntity bbsEntity = new BbsEntity();
		BbsDao dao = new BbsDao();
		List<BbsFileEntity> list = new ArrayList<BbsFileEntity>();
		
		// page �씠�룞泥섎━
		ServletContext context = getServletContext();
		String url = request.getParameter("urlName"); // �꽆�뼱�삤�뒗 url
		System.out.println(url);
		
		// 寃뚯떆�뙋踰덊샇 諛� dao 泥섎━
		int bbsNo =  Integer.parseInt(request.getParameter("bbsNo"));
//		System.out.println("detail bbsNo 媛�: "+ bbsNo);
		
		bbsEntity.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
		bbsEntity = dao.getBbsDetail(bbsEntity);
		list = dao.fileList(bbsNo);

		request.setAttribute("dto", bbsEntity);
		request.setAttribute("fileList", list);
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null; //荑좏궎瑜� 鍮꾧탳�븯湲곗쐞�븳 媛� �꽭�똿
		
		if(cookies != null && cookies.length>0) { 	// 荑좏궎媛� 議댁옱�븷�떆
			for(int i =0; i< cookies.length; i++) { // 荑좏궎媛� �벑濡앸맂 諛곗뿴 留뚰겮 諛섎났
				if(cookies[i].getName().equals("cookie"+bbsNo)) {
					System.out.println("荑좏궎 �뿬遺� �솗�씤�썑  �룞�씪�븳 荑좏궎 �엳�쑝硫� 議고쉶�닔 泥섎━�븡怨� �럹�씠吏��씠�룞");
					cookie = cookies[i];
					
					RequestDispatcher dispatcher = context.getRequestDispatcher(url);//�봽濡쒖젥�듃紐낆씠�씠誘몃텤�쓬  /web/web/exercise/resisterList.jsp
					dispatcher.forward(request, response);
				}
			}
		}
		
		if(cookie == null) {// �쐞�뿉 諛곗뿴�뿉�꽌 荑좏궎媛� 議댁옱�븯吏� �븡�쓣 �떆 荑좏궎瑜� �깮�꽦 諛� 異붽� �븳�떎.
			System.out.println("荑좏궎�뾾�쓬");
			
			Cookie newCookie = new Cookie("cookie"+bbsNo, "|"+bbsNo+"|" ); //荑좏궎�깮�꽦 ( �씠由�, 媛�)
			response.addCookie(newCookie);
			dao.listCnt(bbsEntity);// list 議고쉶�닔 利앷� 
			
			RequestDispatcher dispatcher = context.getRequestDispatcher(url);//�봽濡쒖젥�듃紐낆씠�씠誘몃텤�쓬  /web/web/exercise/resisterList.jsp
			dispatcher.forward(request, response);
		}
		
	}

}
