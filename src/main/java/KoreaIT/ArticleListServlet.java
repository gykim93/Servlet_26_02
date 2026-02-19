package KoreaIT;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		
		String driverName = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스를 찾을 수 없습니다: " + e.getMessage());
			return; // 드라이버가 없으면 더 이상 진행할 수 없으므로 종료
		}

		String url = "jdbc:mysql://127.0.0.1:3306/Spring_26_01?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			// 2. DB 연결 시도
			conn = DriverManager.getConnection(url, user, password);
			
			// 테스트를 위해 콘솔에도 출력
			System.out.println("데이터베이스 연결 성공!");

			// DBUtil 활용 
			DBUtil dbUtil = new DBUtil(request, response);
			String sql = "SELECT * FROM article;";
			List<Map<String, Object>> articleRows = dbUtil.selectRows(conn, sql);

			// 브라우저 화면에 결과 출력
			response.getWriter().append("<h1>게시글 목록</h1>");
			response.getWriter().append(articleRows.toString());

		} catch (SQLException e) {
			// 3. 연결 실패 시 상세 에러 메시지 출력
			System.out.println("데이터베이스 연결 실패!");
			System.out.println("에러 내용 : " + e.getMessage());
			response.getWriter().append("연결 실패: " + e.getMessage());
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}