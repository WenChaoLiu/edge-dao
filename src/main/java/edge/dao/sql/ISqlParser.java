package edge.dao.sql;

import java.util.Collection;

import edge.dao.sql.dialect.IDbDialect;

/**
 * sql解析器
 * @author WenChao_Liu@163.com
 * @date 2014年8月10日
 */
public interface ISqlParser {
	
	/**
	 * 解析制定sql或sqlId的sql语句为sql语句
	 * @param sqlId sqlId或sql
	 * @param params 传递给sql中参数的匹配载体，Map或者实体对象
	 * @param dialect 数据库方言
	 * @return sql语句
	 */
	public SqlCommand getSqlCommand(String sqlId, Object params, IDbDialect dialect);
	
    /**
     * 解析制定sql或sqlId的sql语句为sql命名
     * @param sqlId sqlId或sql
     * @param params 传递给sql中参数集合，Map或者实体对象
     * @param dialect 数据库方言
     * @return sql语句
     */
	public BatchSqlCommand getBatchSqlCommand(String sqlId,Collection<?> params,IDbDialect dialect);
	
	/**
	 * 从Sql源中获取sql语句
	 * @param sqlId sqlId
	 * @param dbDialect 数据库方言
	 */
	public String getSql(String sqlId, IDbDialect dbDialect);
	
	/**
	 * 把sql语句解析为sqlStatement
	 * @param 带命名参数或动态条件的裸Sql
	 */
	public SqlStatement parseSqlStatement(String rawSql);
}
