package KoreaIT;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;

	private ArticleService articleService;
	
	private Connection conn;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
		
		this.articleService = new ArticleService(conn);
	}

	private boolean isLogined() {
		return request.getSession().getAttribute("loginedMemberId") != null;
	}

	private int getLoginedMemberId() {
		return (int) request.getSession().getAttribute("loginedMemberId");
	}

	public void showList() throws ServletException, IOException {
		int page = 1;
		String pageParam = request.getParameter("page");
		if (pageParam != null && pageParam.length() != 0) {
			page = Integer.parseInt(pageParam);
		}

		int itemsInAPage = 10;
		int limitFrom = (page - 1) * itemsInAPage;


		int totalCnt = articleService.getTotalCnt();
		int totalPage = (int) Math.ceil(totalCnt / (double) itemsInAPage);

		List<Map<String, Object>> articleRows = articleService.getForPrintArticles(limitFrom, itemsInAPage);

		request.setAttribute("page", page);
		request.setAttribute("articleRows", articleRows);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("totalPage", totalPage);

		// list.jsp 화면으로 제어권을 넘기기. (포워딩)
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void showDetail() throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT A.*, M.name");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

		request.setAttribute("articleRow", articleRow);

		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}

	public void doDelete() throws IOException {
		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인을 하고오세요'); location.replace('../member/login');</script>");
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

		int loginedMemberId = getLoginedMemberId();

		if (loginedMemberId != (int) articleRow.get("memberId")) {
			response.getWriter()
					.append(String.format("<script>alert('%d번 글에 대한 권한 없음'); location.replace('list');</script>", id));

			return;
		}

		sql = SecSql.from("DELETE");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 삭제 되었습니다'); location.replace('list');</script>", id));
	}

	public void doModify() throws IOException {
		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인을 하고오세요'); location.replace('../member/login');</script>");
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));

		String title = request.getParameter("title");
		String body = request.getParameter("body");

		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);

		response.getWriter().append(
				String.format("<script>alert('%d번 글이 수정 되었습니다'); location.replace('detail?id=%d');</script>", id, id));

	}

	public void doWrite() throws IOException {
		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인을 하고오세요'); location.replace('../member/login');</script>");
			return;
		}
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int loginedMemberId = getLoginedMemberId();

		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("memberId = ?,", loginedMemberId);
		sql.append("title = ?,", title);
		sql.append("`body` = ?", body);

		int id = DBUtil.insert(conn, sql);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 작성 되었습니다'); location.replace('list');</script>", id));
	}

	public void showModify() throws IOException, ServletException {
		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인을 하고오세요'); location.replace('../member/login');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

		int loginedMemberId = getLoginedMemberId();
		if (loginedMemberId != (int) articleRow.get("memberId")) {
			response.getWriter()
					.append(String.format("<script>alert('%d번 글에 대한 권한 x'); location.replace('list');</script>", id));
			return;
		}

		request.setAttribute("articleRow", articleRow);

		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void showWrite() throws IOException, ServletException {
		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인을 하고오세요'); location.replace('../member/login');</script>");
			return;
		}
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}
}
