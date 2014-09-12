package edge.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 基于jdbc方式的数据持久接口
 * @author WenChao_Liu@163.com
 * @date 2014年8月10日
 */
public interface IJdbcDao {
	
	/**
	 * 基于jdbc方式插入单条数据
	 * @param sql 带问号占位符的SQL语句，或者代表SQL的语句的SQL ID
	 * @param param 单条数据参数 ，注意与SQL语句中问号占位符一一对应
	 * @return 插入记录数，成功返回1，否则返回0
	 * @throws DataAccessException 
	 */
	public abstract int insert(String sql,Object...params) throws DataAccessException;
	
	/**
	 * 基于jdbc方式删除数据
	 * @param sql 带问号占位符的SQL语句，活着代表配置文件中的SQL_ID
	 * @param params 单条数据参数 ，注意与SQL语句中问号占位符一一对应
	 * @return 删除记录条数
	 * @throws DataAccessException 
	 */
	public abstract int delete(String sql,Object...params) throws DataAccessException;
	
	/**
	 * 基于jdbc方式更新数据
	 * @param sql 带问号占位符的SQL语句，活着代表配置文件中的SQL_ID
	 * @param params 单条数据参数 ，注意与SQL语句中问号占位符一一对应
	 * @return 返回更新的记录条数
	 * @throws DataAccessException 
	 */
	public abstract int update(String sql,Object...params) throws DataAccessException;
	
	/**
	 * 检查特定数据是否存在（传入的SQL语句必须是select count(1) ...）
	 * @param sql 带问号占位符的SQL语句，或者代表SQL的语句的SQL ID
	 * @param params 查询参数，注意与SQL语句中问号占位符一一对应
	 * @throws DataAccessException 有可能发生数据访问异常
	 * @return true 存在 false 不存在
	 */
	public abstract boolean exists(String sql,Object...params) throws DataAccessException;
	
	/**
	 * 执行DDL或者DML语句
	 * @param sql DDL或者DML
	 * @throws DataAccessException 有可能发生数据访问异常
	 */
	public abstract void execute(String sql) throws DataAccessException;
	
	/**
	 * 查询单条数据，以Map的格式返回（传统问号占位符模式）
	 * @param sql 带问号占位符的传统SQL语句或者SQL ID
	 * @param params 查询参数，注意需要与占位符一一对应
	 * @throws DataAccessException 有可能发生数据访问异常
	 * @return 以Map的格式返回单条数据，查询不到数据返回NULL
	 */
	public abstract Map<String,Object> queryForMap(String sql,Object...params) throws DataAccessException;
	
	/**
	 * 查询多条数据的数据结果集，以Map的格式返回（传统问号占位符模式）
	 * @param sql 带问号占位符的传统SQL语句或者SQL ID
	 * @param params 查询参数，注意需要与占位符一一对应
	 * @throws DataAccessException 有可能发生数据访问异常
	 * @return 以Map的格式返回结果集，查询不到数据返回空List
	 */
	public abstract List<Map<String,Object>> queryForListMap(String sql,Object...params) throws DataAccessException;
	
    /**
     * 基于jdbc查询  返回指定类型对象
     * @param type 返回字段的对象类型，支持实体对象、Map、String等类型
     * @param sql 带问号占位符的传统SQL语句或者SQL ID
     * @param params 查询参数，注意需要与占位符一一对应
     * @return 以指定的类型返回单个字段或单条数据的值。
     * @throws DataAccessException 可能发生的数据库异常
     */
    public abstract <T> T queryForObject(Class<T> type,String sql,Object...params) throws DataAccessException;
    
    /** 
    * @Title: queryForString 
    * @Description: 基于jdbc查询  返回字符串
    * @param @param paramString sqlId
    * @param @param paramVarArgs 所需参数
    * @param @return
    * @param @throws DataAccessException
    * @return String
    * @throws DataAccessException
    */
    public abstract String queryForString(String paramString,Object...paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: queryForInt 
    * @Description: 基于jdbc查询  返回整型值
    * @param @param paramString sqlId
    * @param @param paramVarArgs 所需参数
    * @param @return
    * @param @throws DataAccessException
    * @return int
    * @throws DataAccessException
    */
    public abstract int queryForInt(String paramString,Object...paramVarArgs) throws DataAccessException;
    
    /** 
     * @Title: queryForLong 
     * @Description: 基于jdbc查询  返回Long
     * @param @param paramString sqlId
     * @param @param paramVarArgs 所需参数
     * @param @return
     * @param @throws DataAccessException
     * @return Long
     * @throws DataAccessException
     */
    public abstract Long queryForLong(String paramString,Object...paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: queryForDouble 
    * @Description: 基于jdbc查询  返回double型值
    * @param @param paramString sqlId
    * @param @param paramVarArgs 所需参数
    * @param @return
    * @param @throws DataAccessException
    * @return double
    * @throws DataAccessException
    */
    public abstract double queryForDouble(String paramString,Object...paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: queryForFloat 
    * @Description: 基于jdbc查询  返回fload型值
    * @param @param paramString sqlId
    * @param @param paramVarArgs 所需参数
    * @param @return
    * @param @throws DataAccessException
    * @return float
    * @throws DataAccessException
    */
    public abstract float queryForFloat(String paramString,Object...paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: queryForList 
    * @Description: 基于jdbc查询  返回List
    * @param @param type 返回List泛型类型
    * @param @param paramString sqlId
    * @param @param paramVarArgs 所需参数
    * @param @return
    * @param @throws DataAccessException
    * @return T
    * @throws DataAccessException
    */
    public abstract <T> T queryForList(Class<?> type,String paramString,Object...paramVarArgs) throws DataAccessException;
    
    
    
    
}
