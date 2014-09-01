package edge.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 采用JdbcTemplate实现IJdbcDao
 * @author: WenChao_Liu@163.com
 * @date: 2014年8月23日
 */
public class JdbcDao implements IJdbcDao{
	
	private static final Logger logger = LogManager.getLogger(JdbcDao.class);
	
	@SuppressWarnings("rawtypes")
	private static Set<Class> FOR_SINGLE_FILED_TYPE = new HashSet<Class>();
	{
		FOR_SINGLE_FILED_TYPE.add(String.class);
		FOR_SINGLE_FILED_TYPE.add(boolean.class);
		FOR_SINGLE_FILED_TYPE.add(Boolean.class);
		FOR_SINGLE_FILED_TYPE.add(byte.class);
		FOR_SINGLE_FILED_TYPE.add(Byte.class);
		FOR_SINGLE_FILED_TYPE.add(short.class);
		FOR_SINGLE_FILED_TYPE.add(Short.class);
		FOR_SINGLE_FILED_TYPE.add(int.class);
		FOR_SINGLE_FILED_TYPE.add(Integer.class);
		FOR_SINGLE_FILED_TYPE.add(long.class);
		FOR_SINGLE_FILED_TYPE.add(Long.class);
		FOR_SINGLE_FILED_TYPE.add(float.class);
		FOR_SINGLE_FILED_TYPE.add(Float.class);
		FOR_SINGLE_FILED_TYPE.add(double.class);
		FOR_SINGLE_FILED_TYPE.add(Double.class);
		FOR_SINGLE_FILED_TYPE.add(byte[].class);
		FOR_SINGLE_FILED_TYPE.add(java.sql.Date.class);
		FOR_SINGLE_FILED_TYPE.add(java.sql.Time.class);
		FOR_SINGLE_FILED_TYPE.add(java.sql.Timestamp.class);
		FOR_SINGLE_FILED_TYPE.add(BigDecimal.class);
		FOR_SINGLE_FILED_TYPE.add(Blob.class);
		FOR_SINGLE_FILED_TYPE.add(Clob.class);
	}

	private IDao dao = null;
	private JdbcTemplate jdbcTemplate = null;
	
	public IDao getDao() {
		return dao;
	}

	public void setDao(IDao dao) {
		this.dao = dao;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public JdbcDao(IDao dao, JdbcTemplate jdbcTemplate) {
		this.setJdbcTemplate(jdbcTemplate);
		this.setDao(dao);
	}

	public int insert(String sql, Object... params) throws DataAccessException {
		// TODO Auto-generated method stub
		return update(sql,params);
	}

	public int delete(String sql, Object... params) throws DataAccessException {
		// TODO Auto-generated method stub
		return update(sql,params);
	}

	public int update(String sql, Object... params) throws DataAccessException {
		// TODO Auto-generated method stub
		String executeSql = this.dao.getSql(sql).trim();

		if(logger.isDebugEnabled()){
			logger.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",executeSql,params);
		}
		
		long start = System.currentTimeMillis();
		
		int result = jdbcTemplate.update(executeSql, params);
		
		if(logger.isDebugEnabled()){
			logger.debug("execute using {}ms, update result = {}",System.currentTimeMillis() - start,result);
		}
		
		return result;
	}

	public boolean exists(String sql, Object... params)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	public void execute(String sql) throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();

		long start = System.currentTimeMillis();

		jdbcTemplate.execute(executeSql);

		if (logger.isDebugEnabled()) {
			logger.debug("execute {}ms of sql : \n\n{}\n",
					System.currentTimeMillis() - start, executeSql);
		}

	}

	public Map<String, Object> queryForMap(String sql, Object... params)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> queryForListMap(String sql,
			Object... params) throws DataAccessException {
		//获取执行sql
		String executeSql = this.dao.getSql(sql).trim();
		if(logger.isDebugEnabled()){
			logger.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",executeSql,params);
		}
		long start = System.currentTimeMillis();

		List<Map<String, Object>> result = jdbcTemplate.queryForList(
				executeSql, params);

		if (logger.isDebugEnabled()) {
			logger.debug("execute using {}ms, query rows = {}",
					System.currentTimeMillis() - start, null == result ? "0"
							: result.size());
		}
		return result;
	}

	public <T> T queryForObject(Class<T> requiredType, String sql, Object... params)
			throws DataAccessException {
		String targetSql = this.dao.getSql(sql).trim();

		if (Map.class.isAssignableFrom(requiredType)) {
			return (T) queryForMap(targetSql, params);
		} else if (FOR_SINGLE_FILED_TYPE.contains(requiredType)) {
			
			if(logger.isDebugEnabled()){
				logger.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",targetSql,params);
			}			
			
			long start = System.currentTimeMillis();
			
			T result =  (T) jdbcTemplate.queryForObject(targetSql, params, requiredType);
			
			if(logger.isDebugEnabled()){
				logger.debug("execute using {}ms, query result = {}",System.currentTimeMillis() - start,null == result ? "null" : "{object...}");
			}
			
			return result;
		} else {
			T targetBean;
			try {
				targetBean =  requiredType.newInstance();
			} catch (Exception e) {
				throw new DaoException("query for bean and instantice [" + requiredType.getSimpleName() + "] throw error", e);
			}
			Map<String, Object> tempObject = queryForMap(targetSql, params);
			if (null == tempObject) {
				return null;
			}
			try {
			    TypeMapper.fillEntity(this.getDao(), targetBean, tempObject);
			} catch (Exception e) {
				throw new DaoException("query for bean and convert it to [" + requiredType.getSimpleName() + "] throw error", e);
			}
			return targetBean;
		}
	}

	public String queryForString(String paramString, Object... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public int queryForInt(String paramString, Object... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Long queryForLong(String paramString, Object... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public double queryForDouble(String paramString, Object... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float queryForFloat(String paramString, Object... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> T queryForList(Class<?> type, String paramString,
			Object... paramVarArgs) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
