package edge.dao.sql.dialect;

/**
 * 数据库方言接口
 * @author WenChao_Liu@163.com
 * @date 2014年8月23日
 */
 public interface IDbDialect {
	
	/**
	 * Provider的名称，与数据库元数据中数据库的名称一致，用于自动识别数据库
	*/
	 String getName();
	
	/**
     * 数据库别名，用于sql文件名字中识别sql文件所属的数据库类型，一般与name相同
     */
     String getAlias();
    
    /**
     * 返回是否支持某个
     * dbDialectName 数据库方言名称
     */
     boolean supportsDbDialect(String dbDialectName);
    
    /**
	 * 对参数的值进行转义处理
	 */
	 String escapeParamValue(String value);
	
	/**
	 * 转义Like子句参数里面的特殊字符
	 */
	 String escapeLikeParamValue(String content);
	
	/**
	 * 转义字段名或者表名
	 */
	 String escapeIdentifier(String name);
	
	/**
	 * 把指定的SQL语句进行自动包装为分页查询的语句
	 * oraderClause排序规则
	 */
	 String wrapPageSql(String sql, String orderClause);
	
	
	/**
	 * 把指定的SQL语句进行自动包装为查询总数的语句
	 */
	 String wrapCountSql(String sql);
	
	/**
	 * 获取特定用户名对应的schema
	 */
	 String getSchema(String userName);
	
	/**
	 * 获取特定用户名对应的catalog
	 */
	 String getCatalog(String userName);
	
	/**
	 * 设置第一个分页参数的值（通常是从多少条）
	 * @param startRowIndex 从多少条
	 * @param endRowIndex 到多少条
	 */
	 int getFirstPageParam(int startRowIndex, int endRowIndex);
	
	/**
	 * 设置第二个分页参数的值（通常是到多少条或者分页条数大小）
	 * @param startRowIndex 从多少条
	 * @param endRowIndex 到多少条
	 */
	 int getSecondPageParam(int startRowIndex, int endRowIndex);
	
	
	
}
