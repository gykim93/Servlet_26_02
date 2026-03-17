package KoreaIT;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/s/*") // 모든 요청을 여기서 가로챈다.
public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 공통설정
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 없음");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://127.0.0.1:3306/AM_jsp_2026_02?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			HttpSession session = request.getSession();

			boolean isLogined = false;
			int loginedMemberId = -1;
			Map<String, Object> loginedMember = null;
			if (session.getAttribute("loginedMemberId") != null) {
				isLogined = true;
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				loginedMember = (Map<String, Object>) session.getAttribute("loginedMember");
			}
			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("loginedMember", loginedMember);

			String requestUri = request.getRequestURI();

			String[] reqUriBits = requestUri.split("/");

			if (reqUriBits.length < 5) { // 주소가 너무 짧다면? ex) /s/article << 쳐낸다.
				response.getWriter().append(
						String.format("<script>alert('올바른 요청이 아님'); location.replace('../home/main');</script>"));
				return;
			}
			String controllerName = reqUriBits[3]; // article, member
			String actionMethodName = reqUriBits[4]; // list, write, login
			if (controllerName.equals("home")) {
				HomeController homeController = new HomeController(request, response, conn);

				homeController.showMain();

			} else if (controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(request, response, conn);
				
				switch (actionMethodName) {
				case "list":
					articleController.showList();
					break;
				case "detail":
					articleController.showDetail();
					break;
				case "doDelete":
					articleController.deDelete();
					break;		
				case "modify":
					articleController.showModify();
					break;	
				case "doModify":
					articleController.doModify();
					break;		
				case "write":
					articleController.showWrite();
					break;	
				case "doWrite":
					articleController.doWrite();
					break;					
				}
				
			}
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
