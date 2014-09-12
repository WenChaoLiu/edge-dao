package edge.dao.sql.parameters;

/**
 * sql动态参数接口
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public interface ISqlParameters {
	/**
	 * 返回指定参数名字对应的值
	 * @param name 参数名称
	 */
	Object resolve(String name);

	/**
	 * 试图返回指定参数对应的值
	 * @param name 参数名称
	 * @param value 传出参数
	 * @return true解析成功 false解析不成功
	 */
	boolean tryResolve(String name, ParamValue value);
}
