package kr.nao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBconnection {

	public static Connection dbConn;

	public static Connection getConnection() { // DB oracle 연결

		dbConn = null;
		
		try {
			String id = "jy01";
			String pw = "jy01";
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			dbConn = DriverManager.getConnection(url, id, pw);
			
//			System.out.println("Database에 연결되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DB 드라이버 로딩실패" + e.toString());
		} catch (SQLException e) {
			System.out.println("DB접속실패" + e.toString());
		} catch (Exception e) {
			System.out.println("Unknown error");
			e.printStackTrace();
		}
		return dbConn;
	}

	public static void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement psmt, Connection conn) {
		try {
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
