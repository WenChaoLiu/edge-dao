package edge.dao.sql;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edge.dao.sql.dialect.IDbDialect;
import edge.dao.sql.parameters.ISqlParameters;

public class SqlParser implements ISqlParser{
	private ISqlSource sqlSource;
	private List<ISqlParameters> sqlParameters = new ArrayList<ISqlParameters>();
	private final static Map<String, SqlStatement> sqlStatementCache = new ConcurrentHashMap<String, SqlStatement>();
	private ITestExpressionResolver testExpressionResolver = null;

	/**
	 * @return the testExpressionResolver
	 */
	public ITestExpressionResolver getTestExpressionResolver() {
		return testExpressionResolver;
	}

	/**
	 * @param testExpressionResolver the testExpressionResolver to set
	 */
	public void setTestExpressionResolver(ITestExpressionResolver testExpressionResolver) {
		this.testExpressionResolver = testExpressionResolver;
	}

	/**
	 * @return the sqlParameters
	 */
	public List<ISqlParameters> getSqlParameters() {
		return sqlParameters;
	}

	/**
	 * @param sqlParameters the sqlParameters to set
	 */
	public void setSqlParameters(List<ISqlParameters> sqlParameters) {
		this.sqlParameters = sqlParameters;
	}

	/**
	 * @return the sqlSource
	 */
	public ISqlSource getSqlSource() {
		return sqlSource;
	}

	/**
	 * @param sqlSource the sqlSource to set
	 */
	public void setSqlSource(ISqlSource sqlSource) {
		this.sqlSource = sqlSource;
	}

	/* (non-Javadoc)
	 * @see bingo.dao.sql.ISqlParser#getSqlCommand(java.lang.String)
	 */
	public SqlCommand getSqlCommand(String sqlId, Object params, IDbDialect dialect) {
		SqlStatement sqlStatement = sqlStatementCache.get(sqlId);
		
		if(null == sqlStatement) {
			sqlStatement = parseSqlStatement(getSql(sqlId, dialect));
			sqlStatementCache.put(sqlId, sqlStatement);
		}

		SqlCommand sqlCommand = sqlStatement.createCommand(dialect, params);
		
		if (logger.isDebugEnabled()) {
			logger.debug("found sql command '{}' :\n\n{}\n",sqlId,sqlStatement.getRawText().trim());
		}
		
		return sqlCommand;
	}
	
	public BatchSqlCommand getBatchSqlCommand(String sqlId, Collection<?> params, IDbDialect dialect) {
        SqlStatement sqlStatement = sqlStatementCache.get(sqlId);
        
        if(null == sqlStatement) {
            sqlStatement = parseSqlStatement(getSql(sqlId, dialect));
            sqlStatementCache.put(sqlId, sqlStatement);
        }

        BatchSqlCommand sqlCommand = sqlStatement.createBatchCommand(dialect, params);
        
        if (logger.isDebugEnabled()) {
            logger.debug("found sql command '{}' :\n\n{}\n",sqlId,sqlStatement.getRawText().trim());
        }
        
        return sqlCommand;
    }

    /**
	 * 把原始sql语句分解成sqlStatement对象
	 */
	public SqlStatement parseSqlStatement(String rawSql) {
		List<SqlClause> sqlClauses = new LinkedList<SqlClause>();
		StringBuilder textSql = new StringBuilder();
		for (int i = 0; i < rawSql.length(); i++) {
			char c = rawSql.charAt(i);

			//处理客制化指令@action{...}
			if (c == '@') {
				int ds = rawSql.indexOf("{", i + 1);
				// @action{}  action不能为空 
				String action = rawSql.substring(i + 1, ds).trim();
				int de = rawSql.indexOf("}", ds + 1);
				if (action.length() > 0 || de > 0) {
					sqlClauses.add(new TextClause(textSql.toString()));
					textSql = new StringBuilder();
					sqlClauses.add(new ActionClause(this, rawSql.substring(i, de + 1), rawSql.substring(i + 1, ds), rawSql.substring(ds + 1, de)));
					i = de;
				} else {
					textSql.append(c);
					continue;
				}
			}
			//解析动态sql  {? ...}
			else if (c == '{') {
				int ds = rawSql.indexOf("?", i + 1);
				// 兼容{  ? ...}
				if (rawSql.substring(i + 1, ds).trim().length() == 0) {
					//解析测试条件
					ConditionClause conditionClause = null;
					int ce = ds;
					if (rawSql.charAt(ds + 1) == '(') {
						ce = rawSql.indexOf(")", ds + 2);
						String condition = rawSql.substring(ds + 2, ce);
						conditionClause = new ConditionClause(condition, parseSqlStatement(condition).getSqlClauses());
					}
					int de = rawSql.indexOf("}", i + 1);
					sqlClauses.add(new TextClause(textSql.toString()));
					textSql = new StringBuilder();
					// 考虑{??...的情况，子参数区域就需要去掉??，而content则保留?以便DynamicClause解析时区分
					int pp = "?".equals(rawSql.substring(ds + 1, ds + 2)) ? ce + 2 : ce + 1;
					sqlClauses.add(new DynamicClause(rawSql.substring(i, de + 1), conditionClause, rawSql.substring(ce + 1, de), parseSqlStatement(rawSql.substring(pp, de))
							.getSqlClauses(), this.getTestExpressionResolver()));
					i = de;
				} else { // 如果"{"没有"?"则忽略继续
					textSql.append(c);
					continue;
				}
			}
			//解析#param#
			else if (c == '#') {
				int de = rawSql.indexOf("#", i + 1);
				if (de >= 0) {
					sqlClauses.add(new TextClause(textSql.toString()));
					textSql = new StringBuilder();
					sqlClauses.add(new NamedParameterClause(rawSql.substring(i, de + 1), rawSql.substring(i + 1, de)));
					i = de;
				} else {
					textSql.append(c);
					continue;
				}
			}
			//解析$param$
			else if (c == '$') {
				int de = rawSql.indexOf("$", i + 1);
				if (de >= 0) {
					ParameterClauseUsage parameterClauseUsage = ParameterClauseUsage.Normal;
					if (textSql.toString().toUpperCase().indexOf(" LIKE ") >= 0) {
						parameterClauseUsage = ParameterClauseUsage.Like;
					} else if (textSql.toString().toUpperCase().indexOf(" IN ") >= 0) {
						parameterClauseUsage = ParameterClauseUsage.In;
					}
					sqlClauses.add(new TextClause(textSql.toString()));
					textSql = new StringBuilder();
					sqlClauses.add(new ValueParameterClause(rawSql.substring(i, de + 1), rawSql.substring(i + 1, de), parameterClauseUsage));
					i = de;
				} else {
					textSql.append(c);
					continue;
				}
			} else {
				textSql.append(c);
			}
		}
		if (textSql.length() > 0) {
			sqlClauses.add(new TextClause(textSql.toString()));
		}
		return new SqlStatement(rawSql, sqlClauses, sqlParameters);
	}

	/* (non-Javadoc)
	 * @see bingo.dao.sql.ISqlParser#getSql(java.lang.String)
	 */
	public String getSql(String sqlId, IDbDialect dialect) {
		// 如果sqlId包含空格，则认为sqlId本身就是SQL
		if (sqlId.contains(" ")) {
			return sqlId;
		}
		// 先严格按dbDialect匹配
		String sql = this.sqlSource.getSql(sqlId + "$" + dialect.getAlias().toLowerCase());
		// 再找公用的
		if (StringUtils.isEmpty(sql)) {
			sql = this.sqlSource.getSql(sqlId);
		}
		if (StringUtils.isEmpty(sql)) {
			throw new DaoException("sql id[" + sqlId + "] is not found in sql file.");
		}
		return sql;
	}

}	
