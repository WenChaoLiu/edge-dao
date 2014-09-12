package edge.dao.sql;

/**
 * sql中的action解析前的格式
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class SqlAction {
	private String name;
	private String text;
	
	public SqlAction(String name, String text) {
		super();
		this.name = name;
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
