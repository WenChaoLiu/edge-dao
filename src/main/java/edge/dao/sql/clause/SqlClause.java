package edge.dao.sql.clause;

import edge.dao.sql.BatchSqlCommandBuilder;
import edge.dao.sql.SqlCommandBuilder;
import edge.dao.sql.dialect.IDbDialect;
import edge.dao.sql.parameters.ISqlParameters;

/**
 * sql语句抽象类
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public abstract class SqlClause {
	private String rawText;

	/**
	 * @return the rawText
	 */
	public String getRawText() {
		return rawText;
	}

	/**
	 * @param rawText the rawText to set
	 */
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	
	public abstract void toCommand(IDbDialect dialect, SqlCommandBuilder builder, ISqlParameters parameters);
	
	public void toBatchCommand(IDbDialect dialect,BatchSqlCommandBuilder builder){
	    throw new UnsupportedOperationException("'" + getClass().getSimpleName() + "' not support batch command : " + getRawText());
	}
}
