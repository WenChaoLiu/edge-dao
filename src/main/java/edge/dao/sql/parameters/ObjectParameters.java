package edge.dao.sql.parameters;

import javax.management.ReflectionException;

import edge.common.reflection.Property;
import edge.common.reflection.Type;

/**
 * 对象参数解析器
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class ObjectParameters extends BaseParameters{
	private final Object params;
	private final Type   type;

	public ObjectParameters(Object params) {
		this.params = params;
		this.type   = Type.get(params.getClass());
	}

	/* (non-Javadoc)
	 * @see bingo.dao.sql.parameters.ISqlParameters#tryResolve(java.lang.String, java.lang.Object)
	 */
	public boolean tryResolve(String name, ParamValue value) {
	    Property prop = type.findProperty(name);

	    if(null != prop){
	        try {
				value.setValue(prop.getValue(params));
			} catch (ReflectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return true;
	    }
	    
	    return false;
	}
}
