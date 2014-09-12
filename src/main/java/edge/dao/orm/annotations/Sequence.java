package edge.dao.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sequence标注
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD) 
public @interface Sequence {
	public String name();
}
