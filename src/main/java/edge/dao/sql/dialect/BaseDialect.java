package edge.dao.sql.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * IDaoProvider基础实现
 * @author: WenChao_Liu@163.com
 * @date: 2014年8月31日
 */
public abstract class BaseDialect implements IDbDialect{
	
	private final String orderByClausePatterString = "order\\s*by[\\w|\\s]*";
	private String name;
	
	/**
	 * 获取内建的dbDialect
	 */
	public static List<IDbDialect> getBuildinProvider() {
		List<IDbDialect> buildinProviders = new ArrayList<IDbDialect>();
		buildinProviders.add(new MySqlDialect());
		return buildinProviders;
	}

	/**
	 * 判断是否有与别名配套的dbDialect
	 */
	public static boolean findDbDialect(String alias) {
		for (IDbDialect dbDialect: getBuildinProvider()) {
			if (dbDialect.getAlias().equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}

	public String getAlias() {
		return name;
	}

	public boolean supportsDbDialect(String dbDialectName) {
		return false;
	}

	public String escapeParamValue(String value) {
		return null == value ? value : value.replaceAll("'", "''");
	}

	protected String removeOrderByClause(String sql) {
		sql = Pattern.compile(orderByClausePatterString, Pattern.CASE_INSENSITIVE).matcher(sql).replaceAll("");
		return sql;
	}
	
	public String escapeLikeParamValue(String content) {
		return content;
	}

	public String escapeIdentifier(String name) {
		return name;
	}

	public abstract String wrapPageSql(String sql, String orderClause);

	public String wrapCountSql(String sql) {
		sql = removeOrderByClause(sql);
		
		if (sql.toLowerCase().contains("count(")) {
			return sql;
		}
		
		return " select count(1) from (\n" + sql + "\n) tt";
	}

	public String getSchema(String userName) {
		return userName.toLowerCase();
	}

	public String getCatalog(String userName) {
		return userName.toLowerCase();
	}

	public int getFirstPageParam(int startRowIndex, int endRowIndex) {
		return endRowIndex;
	}

	public int getSecondPageParam(int startRowIndex, int endRowIndex) {
		return startRowIndex;
	}
	
}
