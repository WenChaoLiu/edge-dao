package edge.dao.sql.parameters;

/**
 * sql解析后参数的值
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class ParamValue {
	private Object value;

	public ParamValue(){
		
	}
	
	public ParamValue(Object value){
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
