package edge.dao.sql.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Map类参数传递
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class MapParameters extends BaseParameters{
private final Map<String, Object> params = new HashMap<String, Object>();
	
	public MapParameters(Map<?, ?> params) {
		for (Object key: params.keySet()) {
			if (key instanceof String) {
				this.params.put(((String)key).toUpperCase(), params.get(key));
			}
		}
	}
	
	public boolean tryResolve(String name, ParamValue value) {
		name = name.toUpperCase();
		if (params.containsKey(name)) {
			value.setValue(params.get(name));
			return true;
		}
		return false;
	}
}
