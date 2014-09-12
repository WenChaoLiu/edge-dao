package edge.dao.sql;

import java.util.ArrayList;
import java.util.List;

public class BatchSqlCommandBuilder {
	   protected StringBuilder sql            = new StringBuilder();
	    protected List<String>  parameterNames = new ArrayList<String>();
	    
	    public BatchSqlCommandBuilder appendCommandText(String text) {
	        sql.append(text);
	        return this;
	    }
	    
	    public BatchSqlCommandBuilder addCommandParameter(String name) {
	        parameterNames.add(name);
	        return this;
	    }
	    
	    public String toSql(){
	        return sql.toString();
	    }
	    
	    public List<String> getParameterNames(){
	        return parameterNames;
	    }
}
