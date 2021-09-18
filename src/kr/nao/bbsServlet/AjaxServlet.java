package kr.nao.bbsServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import kr.nao.bbs.BbsEntity;
import kr.nao.bbsdao.BbsDao;

/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
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
		
		request.setCharacterEncoding("UTF-8");
		
		BbsDao dao = new BbsDao();
		List<BbsEntity> list = new ArrayList<BbsEntity>();
		list = dao.bbsList();
		
		Gson gson = new Gson();
		
		response.setContentType("application/json charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter pw = response.getWriter();
		
		Paging paging = new Paging();
		paging.setTotalCount(dao.getListCount());
		
		String json = gson.toJson(list);
		String pagingJson = gson.toJson(paging);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", json);
		jsonObject.put("paging",pagingJson);
		
//		JSONObject jsonObject = new JSONObject();
//		JSONArray array = new JSONArray();
//		for (int i = 0; i < list.size(); i++) {
//			
//			JSONObject dtoObject = new JSONObject();
//			dtoObject.put("bbsNo", list.get(i).getBbsNo());
//			dtoObject.put("wName",list.get(i).getwName());
//			dtoObject.put("title",list.get(i).getTitle());
//			array.add(dtoObject);
//		}
//
//		jsonObject.put("list", array);
		
		pw.print(jsonObject);
		
		pw.flush();
		pw.close();
		
		System.out.println(jsonObject.toString());
	}

}
