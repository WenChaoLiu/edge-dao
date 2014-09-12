package edge.common.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.management.ReflectionException;

import edge.common.utils.StringUtils;

public class Property {
private static Object[] EMPTY_ARGS = new Object[]{};
    
    private final Field    field;
    private final String   name;
    private final Class<?> type;
    private final Method   getter;
    private final Method   setter;
    
    Property(java.lang.reflect.Field field){
        this.field  = field;
        this.name   = field.getName();
        this.type   = field.getType();
        this.setter = findSetter(field);
        this.getter = findGetter(field);
    }
    
    public Field field(){
        return field;
    }
    
    public Class<?> type(){
        return type;
    }
    
    public String name(){
        return name;
    }
    
    public void setValue(Object instance, Object value) throws ReflectionException{
        try {
            if(null != setter){
                setter.invoke(instance, value);
            }else{
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                field.set(instance, value);
            }
        } catch (Exception e) {
            throw new ReflectionException(
                        e);
        }
    }
    
    public Object getValue(Object instance) throws ReflectionException {
        try {
            if(null != getter){
                return getter.invoke(instance, EMPTY_ARGS);
            }else {
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                return field.get(instance);
            }
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }
    
    private static Method findGetter(Field field){
        Class<?> clazz  = field.getDeclaringClass();
        String   name   = field.getName();
        
        Method   method = Type.findMethod(clazz, 
                                     "get" + Character.toUpperCase(name.charAt(0))  + (name.length() > 1 ? name.substring(1) : ""), 
                                     new Class[] { field.getType() }); 
        
        if(null == method && (field.getType() == Boolean.TYPE || field.getType() == Boolean.class)){
            method = Type.findMethod(clazz, 
                                "is" + Character.toUpperCase(name.charAt(0))  + (name.length() > 1 ? name.substring(1) : ""), 
                                new Class[] { field.getType() });                 
        }
        
        return method;
    }
    
    private static Method findSetter(Field field){
        Class<?> clazz = field.getDeclaringClass();
        String   name  = field.getName();
        
        return Type.findMethod(clazz, 
                           "set" + Character.toUpperCase(name.charAt(0))  + (name.length() > 1 ? name.substring(1) : ""), 
                           new Class[] { field.getType() });            
    }
}
