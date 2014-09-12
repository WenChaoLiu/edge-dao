package edge.dao.sql.parameters;

/**
 * 基础参数解析类
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public abstract class BaseParameters implements ISqlParameters{
	
	public Object resolve(String name) {
		ParamValue value = new ParamValue();
		return tryResolve(name, value) ? value.getValue() : null;
	}

	public abstract boolean tryResolve(String name, ParamValue value);
}
