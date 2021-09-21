package com.project.bbsServlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.bbs.BbsEntity;
import com.project.bbsdao.BbsDao;

/**
 * Servlet implementation class FileDownload
 */
@WebServlet("/fileDown")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
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
		
		int seq = Integer.parseInt(request.getParameter("bbsNo"));
		
		BbsEntity bbsEntity = new BbsEntity();
		bbsEntity.setBbsNo(seq);
		
		BbsDao dao = new BbsDao();
		
		BbsEntity dto = dao.getBbsDetail(bbsEntity);
		
		String fileName = dao.fileName(seq);
		String uploadFileurl = getServletContext().getRealPath("\\upload") + "/" + fileName;
		
//		System.out.println("BBS Fileupload Servlet: �뙆�씪�떎�슫濡쒕뱶寃쎈줈 " + uploadFileurl);
		
		File file = new File(uploadFileurl);
			
		if (file.exists()&& file.isFile()) { // �뙆�씪�씠 議댁옱�븷�떆 �떎�슫濡쒕뱶
				
			try {
				long fileSize = file.length();
				
				response.setContentType("application/x-msdownload");
				response.setContentLength((int)fileSize);
				
				String strClient = request.getHeader("user-agent");
				
				if(strClient.indexOf("MSIE 5.5")!=-1){ //釉뚮씪�슦���씪�븣		
					response.setHeader("Content-Disposition", "fileName="+fileName+";") ;
				}else{
					fileName= URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20");
					response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";" );
				}
					
				response.setHeader("Content-Length", String.valueOf(fileSize));
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "private");
				
				byte b[] = new byte[1024];
				
				BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
				
				int read = 0;
				
				while ((read=fin.read(b)) != -1 ) {
					outs.write(b,0,read);
				}
				outs.flush();
				outs.close();
				fin.close();
				
			} catch (Exception e) {
				System.out.println("Download Exception : " + e.getMessage());
			}
		}
	}

}
