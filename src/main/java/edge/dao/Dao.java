package edge.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import edge.dao.ext.IBaseObject;
import edge.dao.sql.SqlParser;
import edge.dao.sql.dialect.IDbDialect;
import edge.dao.sql.parameters.ISqlParameters;
import edge.dao.sql.parameters.ParamValue;

public class Dao implements IDao, InitializingBean{
	
	private String name = DaoFactory.DEFAULT_DAO_NAME;
	
	private String defualtSequenceName = "SEQ_DEFAULT";//用来生成序列的序列名称
	private String currentUserIdEnvName = "Env:User.Id";//获取当前登录用户主键的环境变量名称
	private SqlParser sqlParser;
	private DataSource dataSource;
	private IJdbcDao jdbcDao;
	private IDbDialect dbDialect;
	private List<IDbDialect> otherProviders = new LinkedList<IDbDialect>();
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefualtSequenceName() {
		return defualtSequenceName;
	}

	public void setDefualtSequenceName(String defualtSequenceName) {
		this.defualtSequenceName = defualtSequenceName;
	}

	public String getCurrentUserIdEnvName() {
		return currentUserIdEnvName;
	}

	public void setCurrentUserIdEnvName(String currentUserIdEnvName) {
		this.currentUserIdEnvName = currentUserIdEnvName;
	}

	public SqlParser getSqlParser() {
		return sqlParser;
	}

	public void setSqlParser(SqlParser sqlParser) {
		this.sqlParser = sqlParser;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IJdbcDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(IJdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public IDbDialect getDbDialect() {
		return dbDialect;
	}

	public void setDbDialect(IDbDialect dbDialect) {
		this.dbDialect = dbDialect;
	}

	public List<IDbDialect> getOtherProviders() {
		return otherProviders;
	}

	public void setOtherProviders(List<IDbDialect> otherProviders) {
		this.otherProviders = otherProviders;
	}

	/**
	 * 获取UUID的值，并去掉里面的减号，js中对带减号的UUID偶尔有问题
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public DataSource getDataSourcePrototype() {
		return this.dataSource;
	}
	
	
	/** 
	* 如果是是想了IEntityObject，则自动设置四个默认字段的值
	* @param entity 原始实体对象
	* @return 返回包装后的实体对象
	*/
	@SuppressWarnings("unused")
	private Object wrapEntity(Object entity) {
		if (entity instanceof IBaseObject) {
			IBaseObject wrapEntity = (IBaseObject) entity;

			Serializable userId = getLoginUserId();
			if (null == wrapEntity.getCreateBy()
					|| null == wrapEntity.getCreateDate()) {
				if (null != userId) {
					wrapEntity.setCreateBy(userId);
				}
				wrapEntity.setCreateDate(new Date());
			}

			if (null != userId) {
				wrapEntity.setLastUpdatedBy(userId);
			}
			wrapEntity.setLastUpdatedDate(new Date());

			return wrapEntity;
		}
		return null;
	}
	
	/** 
	* 获取当前登陆用户Id
	*/
	private Serializable getLoginUserId(){
		ParamValue paramValue = new ParamValue();
		for (ISqlParameters sqlParameters : this.sqlParser) {
			
		}
		return null;
	}

	public int insert(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Class<?> type, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insertFields(Class<?> type, Map<String, Object> fields)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int[] batchInsert(Class<?> type, Collection<?> entities)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public int update(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update(Class<?> type, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateFields(Class<?> type, Map<String, Object> fields)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateFieldsExcluded(Object paramObject, String... paramVarArgs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateFieldsExcluded(Class<?> type, Object paramObject,
			String... paramVarArgs) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Object paramObject) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> int delete(Class<?> type, Object paramObject)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int[] batchDelete(Class<?> type, Collection<?> paramCollection)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T get(Class<?> type, Object paramObject)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> boolean exists(Class<?> type, Object paramObject)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getSql(String sqlId) {
		// TODO Auto-generated method stub
		return null;
	}
}
