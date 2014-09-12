package edge.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import edge.common.spring.SpringPropertiesPersister;
import edge.common.utils.StringUtils;

/**
 * 应用程序上下文对象，存储应用程序级别（对应于请求级别，会话级别）的上下文信息
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class ApplicationContext {
	private static final Logger log = LogManager.getLogger(ApplicationContext.class);
	private static final Object defaultApplicationContext       = new Object(); 
	private static final ThreadLocal<Object> contextThreadLocal = new ThreadLocal<Object>();
	private static final Map<Object, Application> applications  = new ConcurrentHashMap<Object, Application>();
	private static final String configFileLocation = "classpath*:/config/**/*.xml"; // xml配置文件的路径

	public static Object get(){
		Object context = contextThreadLocal.get();
		return null == context ? defaultApplicationContext : context;
	}

	public static void setThreadLocal(Object context){
		contextThreadLocal.set(context);
	}
	
	public static Object getAttribute(Object name){
		return getAttributes().get(name);
	}
	
	public static void setAttribute(Object name,Object value){
		getAttributes().put(name, value);
	}
	
	public static void removeAttribute(Object name){
		getAttributes().remove(name);
	}
	
	public static Map<Object,Object> getAttributes(){
		return getApplication().attributes;
	}
	
	/**
	 * 从当前上下文的属性文件中获取指定属性的属性值
	 * <p>属性文件通常放在config包下，可以在applicationContext.xml设置
	 * @param name 属性名称
	 * @return 返回对应属性名的属性值，如果找不到则返回NULL
	 */
	public static String getProperty(String name){
		return getProperties().getProperty(name);
	}
	
	/**
	 * 从当前上下文的属性文件中获取指定属性的属性值
	 * <p>属性文件通常放在config包下，可以在applicationContext.xml设置
	 * @param name 属性名称
	 * @param defaultValue 默认值
	 * @return 返回对应属性名的属性值，如果找不到则返回默认值
	 */
	public static String getProperty(String name,String defaultValue){
		String value = getProperties().getProperty(name);
		return StringUtils.isEmpty(value)?defaultValue:value;
	}	
	
	/**
	 * 从当前上下文的属性文件中获取属性信息
	 * <p>属性文件通常放在config包下，可以在applicationContext.xml设置
	 * @return 返回上下文中所有属性文件中的属性信息
	 */
	public static Properties getProperties(){
		return getApplication().properties;
	}
	
	private static Application getApplication(){
		Object context = get();
		Application application = applications.get(context);
		if(null == application){
			synchronized (ApplicationContext.class) {
				application = new Application();
				loadPropertyFromXml(application.properties);
				applications.put(context, application);
			}
		}
		return application;
	}
	
	/**
	 * 非web环境下，手工读取配置文件
	 */
	private static void loadPropertyFromXml(Properties properties) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = resourcePatternResolver.getResources(configFileLocation);
			InputStream inputStream = null;
			SpringPropertiesPersister propertiesPersister = new SpringPropertiesPersister();
			for (Resource resource : resources) {
				try {
					URL url = resource.getURL();

					if (log.isDebugEnabled()) {
						log.debug("found {} resource '{}'", url.getProtocol(), url.toExternalForm());
					}
					if (ResourceUtils.URL_PROTOCOL_FILE.equalsIgnoreCase(url.getProtocol())) {
						inputStream = new FileInputStream(resource.getFile());
					} else {
						inputStream = resource.getInputStream();
					}
					if (resource.getFilename().endsWith(".xml")) {
						propertiesPersister.loadFromXml(properties, inputStream);
					}
				} finally {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			log.error("读取配置文件时出现错误", e);
		}
	}
	
	private static final class Application {
		private Properties          properties = new Properties();
		private Map<Object, Object> attributes = new ConcurrentHashMap<Object, Object>();
	}
}
