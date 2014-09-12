package edge.common.spring;

import javax.swing.Spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring上下文
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public final class SpringContext {
private static final Logger log = LogManager.getLogger(SpringContext.class);	
	private static final String SPRING_CONFIG_LOCATION = "classpath*:/spring/**/*.xml"; 
	private static ApplicationContext defaultContext   = null;

	/**
	 * @return 当前应用中的Spring {@link ApplicationContext}
	 */
	public static final ApplicationContext get(){
		ApplicationContext context = 
			(ApplicationContext)edge.common.ApplicationContext.getAttribute(org.springframework.context.ApplicationContext.class);
		
		if(null == context){
			init();
			
			if(log.isTraceEnabled()){
				log.trace("get default spring's context");
			}
			
			return defaultContext;
		}
		
		if(log.isTraceEnabled()){
			log.trace("get spring'context from application's context");
		}
		
		return context;
	}
	
	/**
	* 初始化sporing  ApplicationContext 
	* 2014年9月6日
	*/
	private static void init(){
		if(null == defaultContext){
			synchronized (SpringContext.class) {
				log.info("create spring context from location : {}",SPRING_CONFIG_LOCATION);
				defaultContext = new ClassPathXmlApplicationContext(SPRING_CONFIG_LOCATION);
			}
		}
	}
}
