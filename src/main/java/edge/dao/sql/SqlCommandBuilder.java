package edge.dao.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建sql语句
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class SqlCommandBuilder {
	private final StringBuilder sql = new StringBuilder();
	private final List<Object> params = new ArrayList<Object>();
	
	public SqlCommandBuilder appendCommandText(String text) {
		sql.append(text);
		return this;
	}
	
	public SqlCommandBuilder addCommandParameter(Object value) {
		params.add(value);
		return this;
	}
	
	public SqlCommand toCommand() {
		return new SqlCommand(sql.toString(), params.toArray());
	}
}
