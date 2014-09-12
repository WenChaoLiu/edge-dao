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

import edge.common.utils.StringUtils;



/**
 * 用JdbcTemplate实现IJdbcDao
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class jdbcDao implements IJdbcDao{
	
	private static final Logger log = LogManager.getLogger(jdbcDao.class.getName());

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
	
	
	public int insert(String sql, Object... params) throws DataAccessException {
		return update(sql,params);
	}
	
	public int delete(String sql, Object... params) throws DataAccessException {
		return update(sql,params);
	}
	
	public int update(String sql, Object... params) throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();
		
		if(log.isDebugEnabled()){
			log.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",executeSql,StringUtils.join(params,","));
		}
		
		long start = System.currentTimeMillis();
		
		int result = jdbcTemplate.update(executeSql, params);
		
		if(log.isDebugEnabled()){
			log.debug("execute using {}ms, update result = {}",System.currentTimeMillis() - start,result);
		}
		
		return result;
	}
	
	public boolean exists(String sql, Object... params)
			throws DataAccessException {
		Number number = jdbcTemplate.queryForObject(sql, Integer.class, params);
		
		Integer result = null != number ? number.intValue() : 0;
		
		return result > 0 ? true : false;
	}
	
	public void execute(String sql) throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();
		
		long start = System.currentTimeMillis();
		
		jdbcTemplate.execute(executeSql);
		
		if(log.isDebugEnabled()){
			log.debug("execute {}ms of sql : \n\n{}\n",System.currentTimeMillis() - start,executeSql);
		}
		
		
	}
	
	public Map<String, Object> queryForMap(String sql, Object... params)
			throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();

		if (log.isDebugEnabled()) {
			log.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",
					executeSql, StringUtils.join(params, ","));
		}

		long start = System.currentTimeMillis();

		Map<String, Object> result = null;
		try {
			result = jdbcTemplate.queryForMap(executeSql, params);
		} catch (Exception e) {
			result = null;
		}
		if (log.isDebugEnabled()) {
			log.debug("execute using {}ms, query result = {}",
					System.currentTimeMillis() - start, null == result ? "null"
							: "{map...}");
		}

		return result;
	}
	
	public List<Map<String, Object>> queryForListMap(String sql,
			Object... params) throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();

		if (log.isDebugEnabled()) {
			log.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",
					executeSql, StringUtils.join(params, ","));
		}

		long start = System.currentTimeMillis();

		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList(executeSql, params);
		} catch (Exception e) {
			result = null;
		}

		if (log.isDebugEnabled()) {
			log.debug("execute using {}ms, query result = {}",
					System.currentTimeMillis() - start, null == result ? "null"
							: "{map...}");
		}
		return result;
	}
	public <T> T queryForObject(Class<T> type, String sql, Object... params)
			throws DataAccessException {
		String executeSql = this.dao.getSql(sql).trim();
		if (log.isDebugEnabled()) {
			log.debug("executing sql : \n\n Sql    -> {}\n Params -> [{}]\n",
					executeSql, StringUtils.join(params, ","));
		}
		
		if(Map.class.isAssignableFrom(type)){
			return (T)queryForMap(executeSql, params);
		}else if(FOR_SINGLE_FILED_TYPE.contains(type)){
			Long start = System.currentTimeMillis();
			
			T result = jdbcTemplate.queryForObject(executeSql, params, type);
			
			if(log.isDebugEnabled()){
				log.debug("execute using {}ms, query result = {}",System.currentTimeMillis() - start,null == result ? "null" : "{object...}");
			}
			
			return result;
		}else{
			T targetBean;
			try {
				targetBean = type.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, Object> tempObject = queryForMap(sql, params);
			if (null == tempObject) {
				return null;
			}
			try {
			   // TypeMapper.fillEntity(this.getDao(), targetBean, tempObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return targetBean;
			
		}
		
		return null;
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
