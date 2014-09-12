package edge.common;

import java.util.Collection;

import edge.common.beans.BeanFactory;
import edge.common.beans.IBeanFactory;

/**
 * 应用程序工厂类,获取对象实例的接口工具
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class ApplicationFactory {
	
	private static final IBeanFactory factory = new BeanFactory();
	/** 
	* 根据beanname获取bean
	* @return
	*/
	public static Object getBeanForName(String name){
		
		return factory.getBeanForName(name);
	}
	
	public static <T> T getBeanForName(Class<? extends T> type ,String name){
		
		return factory.getBeanForName(type, name);
	}
	
	/** 
	* 查找是否存在指定名称的bean 
	* @param name bean名称
	* @return true：存在指定bean；false：不存在指定bean</br>
	* @see WenChao_Liu@163.com
	*/
	public static boolean containsBean(String name){
		
		return factory.containsBean(name);
	}
	
	
	/**
	 * @see IBeanFactory#getBeansOfType(Class)
	 */
	public static <T> Collection<T> getBeansOfType(Class<T> type) {
		return factory.getBeansOfType(type);
	}
	
	/**
	 * 获取指定类型的第一个实例
	 * @param type 接口或者类
	 * @return 取不到则返回null
	 */
	public static <T> T getFirstBeanOfType(Class<T> type) {
		Collection<T> beans = getBeansOfType(type);
		for (T bean: beans) {
			return bean;
		}
		return null;
	}
	
}
