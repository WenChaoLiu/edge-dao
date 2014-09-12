package edge.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edge.dao.sql.clause.SqlClause;
import edge.dao.sql.dialect.IDbDialect;
import edge.dao.sql.parameters.ISqlParameters;
import edge.dao.sql.parameters.SqlParameters;

/**
 * sql表达式
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class SqlStatement {
	private String rawText;
	private List<SqlClause> sqlClauses;
	private final List<ISqlParameters> sqlParametersList;

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

	/**
	 * @return the sqlClauses
	 */
	public List<SqlClause> getSqlClauses() {
		return sqlClauses;
	}

	/**
	 * @param sqlClauses the sqlClauses to set
	 */
	public void setSqlClauses(List<SqlClause> sqlClauses) {
		this.sqlClauses = sqlClauses;
	}
	
	public SqlStatement(String rawText, List<SqlClause> sqlClauses, List<ISqlParameters> sqlParameterslist) {
		this.setRawText(rawText);
		this.setSqlClauses(sqlClauses);
		this.sqlParametersList = sqlParameterslist;
	}
	
	public SqlCommand createCommand(IDbDialect dialect, Object params) {
		SqlParameters sqlParameters = new SqlParameters(params, this.sqlParametersList);
		SqlCommandBuilder sqlCommandBuilder = new SqlCommandBuilder();
		for (SqlClause clause: this.getSqlClauses()) {
			clause.toCommand(dialect, sqlCommandBuilder, sqlParameters);
		}
		return sqlCommandBuilder.toCommand();
	}
	
	public BatchSqlCommand createBatchCommand(IDbDialect dialect,Collection<?> params){

	    BatchSqlCommandBuilder builder = new BatchSqlCommandBuilder();
	    
        for (SqlClause clause: this.getSqlClauses()) {
            clause.toBatchCommand(dialect, builder);
        }
        
        BatchSqlCommand command = new BatchSqlCommand();

        List<Object[]> batchParams = new ArrayList<Object[]>();
        
        int paramsLength = builder.getParameterNames().size();
        
        for (Object param : params) {
            if (null == param) {
                throw new IllegalArgumentException("found null value in params collection");
            }

            ISqlParameters parameters = new SqlParameters(param,this.sqlParametersList);

            Object[] values = new Object[paramsLength];

            int index = 0;
            for (String paramName : builder.getParameterNames()) {
                values[index++] = parameters.resolve(paramName);
            }

            batchParams.add(values);
        }

        command.setCommandText(builder.toSql());
        command.setBatchParams(batchParams);

        return command;        
	}
}