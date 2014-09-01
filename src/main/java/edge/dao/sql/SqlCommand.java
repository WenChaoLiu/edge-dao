package edge.dao.sql;

/**
 * @ClassName SqlCommand
 * @Description Sql指令
 * @author WenChao_Liu@163.com
 * @date 2014年8月10日
 */
public class SqlCommand {
	
	/** 
	* @Fields CommmandText : sql语句
	*/ 
	private String CommmandText;
	
	/** 
	* @Fields params : sql参数
	*/ 
	private Object[] params;
	
	public SqlCommand(String commmandText, Object[] params) {
		super();
		CommmandText = commmandText;
		this.params = params;
	}

	public String getCommmandText() {
		return CommmandText;
	}

	public void setCommmandText(String commmandText) {
		CommmandText = commmandText;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
