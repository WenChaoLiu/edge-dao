package edge.common.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Type {
	 private static ConcurrentHashMap<Class<?>, Type> cache = new ConcurrentHashMap<Class<?>, Type>();
	    
	    public static Type get(Class<?> clazz){
	        Type type = cache.get(clazz);
	        if(null == type){
	            synchronized (cache) {
	                type = new Type(clazz);
	                cache.put(clazz, type);
	            }
	        }
	        return type;
	    }

	    private Class<?>              type;
	    private Map<String, Property> properties = new LinkedHashMap<String, Property>();
	    
	    protected Type(Class<?> type){
	        this.type = type;
	        this.initialize();
	    }
	    
	    public Map<String, Property> getProperties(){
	        return properties;
	    }
	    
	    public Property getProperty(String name){
	        return properties.get(name);
	    }
	    
	    public Property findProperty(String name){
	        for(String key : properties.keySet()){
	            if(key.equalsIgnoreCase(name)){
	                return properties.get(key);
	            }
	        }
	        return null;
	    }
	    
	    private void initialize(){
	        List<Field> fields = getDeclaredFields(type, Object.class);
	        
	        for(Field field : fields){
	            if(!field.isSynthetic()){
	                properties.put(field.getName(), new Property(field));
	            }
	        }
	    }
	    
	    public static List<Field> getDeclaredFields(Class<?> clazz,Class<?> stopClass){
	        List<Field> fields = new ArrayList<Field>();
	        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
	            if (stopClass != null && c == stopClass) {
	                break;
	            }else{
	                for(Field field : c.getDeclaredFields()){
	                    //exclude final and static field
	                    if(!( Modifier.isFinal(field.getModifiers())
	                            ||Modifier.isStatic(field.getModifiers()))){
	                        fields.add(field) ;
	                    }
	                }
	            }
	        }
	        return fields;
	    } 
	    
	    public static Method findMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
	        try {
	            return clazz.getMethod(methodName, paramTypes);
	        } catch (NoSuchMethodException ex) {
	            return findDeclaredMethod(clazz, methodName, paramTypes);
	        }
	    }    
	    
	    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
	        try {
	            return clazz.getDeclaredMethod(methodName, paramTypes);
	        } catch (NoSuchMethodException ex) {
	            if (clazz.getSuperclass() != null) {
	                return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
	            }
	            return null;
	        }
	    }
}
