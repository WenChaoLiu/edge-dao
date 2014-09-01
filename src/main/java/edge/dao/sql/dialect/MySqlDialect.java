package edge.dao.sql.dialect;

import org.springframework.util.StringUtils;

/**
 * MySql适配器
 * @author: WenChao_Liu@163.com
 * @date: 2014年8月31日
 * @version 1.0
 */
public class MySqlDialect extends BaseDialect{

	@Override
	public String wrapPageSql(String sql, String orderClause) {
		sql = removeOrderByClause(sql);
		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);

		pagingSelect.append("select * from (");
		pagingSelect.append(sql);
		if (!StringUtils.isEmpty(orderClause)) {
			pagingSelect.append(" Order By ").append(orderClause);
		}
		pagingSelect.append(") t limit ?, ?");

		return pagingSelect.toString();
	}

	
	
}
