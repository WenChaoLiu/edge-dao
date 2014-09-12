package edge.common.beans;

import java.util.Collection;
import java.util.Map;

/**
 * bean工厂类
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public interface IBeanFactory {
	/**
	 * 根据对象名称获取对象实例，对象是否单例（缓存对象）由具体的实现类决定
	 * 
	 * @param name 对象的名称
	 * @return 返回名称对应的对象实例，如果不存在返回null
	 */
	Object getBeanForName(String name);
	
	/**
	 * 根据对象名称获取指定类型的对象实例，对象是否单例（缓存对象）由具体的实现类决定
	 * 
	 * @param type 指定类型的Class，获取到的对象必须是指定类型或者其子类的实例
	 * @param name 对象的名称
	 * @return 返回名称对应的对象实例，如果不存在返回null,如果类型不匹配抛出异常
	 */
	<T> T getBeanForName(Class<? extends T> type,String name);
	
	/**
	 * 检测容器中时候包含指定名称的bean
	 */
	boolean containsBean(String name);
	
	/**
	 * 获取与指定类型相匹配的Bean实例，包括接口实现类以及类的之类
	 * @param type 接口或者类
	 * @return 返回包含bean名称和对应实例的Map
	 */
	<T> Map<String, T> getBeanMapOfType(Class<T> type);
	
	/**
	 * 获取与指定类型相匹配的Bean实例，包括接口实现类以及类的之类
	 * @param type 接口或者类
	 * @return 返回制定类型所有实例
	 */
	<T> Collection<T> getBeansOfType(Class<T> type);
}
