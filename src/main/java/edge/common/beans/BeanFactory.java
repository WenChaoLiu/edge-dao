package edge.common.beans;

import java.util.Collection;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import edge.common.spring.SpringContext;

/**
 * 调用spring {@link ApplicationContext} 获取对象实例 {@link IBeanFactory}
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class BeanFactory implements IBeanFactory{
	private static final Logger log = LogManager.getLogger(BeanFactory.class);

	public Object getBeanForName(String name) {
		return this.getBeanForName(Object.class, name);
	}

	public <T> T getBeanForName(Class<? extends T> type, String name) {
		ApplicationContext context = SpringContext.get();
		if(context.containsBean(name)){
			return context.getBean(name, type);
		}
		return null;
	}

	public boolean containsBean(String name) {
		return SpringContext.get().containsBean(name);
	}

	public <T> Map<String, T> getBeanMapOfType(Class<T> type) {
		return SpringContext.get().getBeansOfType(type);
	}

	public <T> Collection<T> getBeansOfType(Class<T> type) {
		return getBeanMapOfType(type).values();
	}

}
