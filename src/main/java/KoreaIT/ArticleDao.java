package KoreaIT;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}
	// 전체 게시글 수 구하기(컨트롤러에서 서비스로 넘어옴)
	
	public List<Article> getForPrintArticles(int limitFrom, int itemsInAPage) {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article");

		int totalCnt = DBUtil.selectRowIntValue(conn, sql);
		int totalPage = (int) Math.ceil(totalCnt / (double) itemsInAPage);

		sql = SecSql.from("SELECT A.*, M.name");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("ORDER BY A.id DESC");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);
		
		List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
		
		List<Article> articles = new ArrayList<>();
		
		for (Map<String, Object> articleMap : articleRows) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}
	public int getTotalCnt() {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article;");
		
		return DBUtil.selectRowIntValue(conn, sql);
	}
}
