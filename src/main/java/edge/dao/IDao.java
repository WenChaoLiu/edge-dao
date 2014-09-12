package edge.dao;

import javax.sql.DataSource;

import edge.dao.sql.SqlParser;
import edge.dao.sql.dialect.IDbDialect;

/**
 * 数据持久化接口
 * @author WenChao_Liu@163.com
 * @date 2014年8月10日
 */
public abstract interface IDao extends IObjectDao,ISqlDao{
	
	/**
	 * 获取传统Jdbc访问数据的对象
	 */
	IJdbcDao getJdbcDao();
	
	/**
	 * 获取sql解析器
	 */
	SqlParser getSqlParser();
	
	/**
	 * 获取指定sqlId且与当前数据库类型配套的sql
	 */
	String getSql(String sqlId);
	
	/**
	 * 获取Dao对应的数据源
	 */
	DataSource getDataSource();
	
	/**
	 * 获取Dao对应数据库的方言
	 */
	IDbDialect getDbDialect();
	
}
