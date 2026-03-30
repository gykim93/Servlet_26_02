package KoreaIT;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}
	// 전체 게시글 수 구하기(컨트롤러에서 서비스로 넘어옴)
	public int getTotalCnt() {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article");

		return DBUtil.selectRowIntValue(conn, sql);
	}
	// 리스트에 보여줄 게시글 가져오기(컨트롤러에서 서비스로 넘어옴)
	// 컨트롤러가 계산해준 limitFrom, itemsInAPage를 매개변수로 받아서 쿼리에 넣어준다.
	// 이렇게 함으로써 이제 DB관련 에러가 나오면 => Service쪽으로 와서 보면된다.
	public List<Map<String, Object>> getForPrintArticles(int limitFrom, int itemsInAPage) {
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
		return DBUtil.selectRows(conn, sql);
	}
}
