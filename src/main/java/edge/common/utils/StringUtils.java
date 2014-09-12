package edge.common.utils;

import java.text.MessageFormat;
import java.util.StringTokenizer;

/**
 * String帮助类
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class StringUtils {
	/**
     * <p/>
     * <code>format("Hello ''{0}'', you are welcome!","xiaoming")</code><br/>
     * 
     * <p/>
     * 注意如果要使用单引号的话，必须使用两个连续的单引号表示
     */
    public static String format(String format,Object... args){
        return MessageFormat.format(format, args);
    }
    
    /**
     * @return 指定的字符串是否为<code>null</code>或者空字符串（包含空白字符也被认为是空字符串）
     */
    public static boolean isEmpty(String string){
        return null == string || "".equals(string.trim());
    }
    
    public static boolean isNotEmpty(String string){
        return null != string && !"".equals(string.trim());
    }
    
    /**
     * 替换一个字符中指定的内容为另外一部分内容（非正则表达式）
     */
    public static String replace(String text, String replace, String replaceTo)
    {
        if (text == null || replace == null || replaceTo == null || replace.length() == 0)
        {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int searchFrom = 0;
        while (true)
        {
            int foundAt = text.indexOf(replace, searchFrom);
            if (foundAt == -1)
            {
                break;
            }

            buf.append(text.substring(searchFrom, foundAt)).append(replaceTo);
            searchFrom = foundAt + replace.length();
        }
        buf.append(text.substring(searchFrom));

        return buf.toString();
    }
    
    public static String join(Object[] objects){
        return join(objects,",");
    }
    
    public static String join(Object[] objects,char seperator){
        return join(objects,String.valueOf(seperator));
    }
    
    public static String join(Object[] objects,String seperator){
        if(null == objects || objects.length == 0){
            return "";
        }
        
        if(objects.length == 1){
            return null == objects[0]?"":objects[0].toString();
        }
        
        StringBuilder string = new StringBuilder();
        
        for(Object object : objects){
            string.append(seperator).append(object);
        }
        
        return string.substring(seperator.length()).toString();
    }
    
    public static String[] split(String string){
        return split(string,",");
    }
    
    /**
     * 和{@link String#split(String)}相同的功能，只是分隔字符串不需要使用正则表达式的写法
     */
    public static String[] split(String string,String seperator){
        if(null == string){
            return new String[]{};
        }
        
        StringTokenizer tokens = new StringTokenizer(string.trim(), seperator, false);
        String[] result = new String[ tokens.countTokens() ];
        int i=0;
        while ( tokens.hasMoreTokens() ) {
            result[i++] = tokens.nextToken().trim();
        }
        return result;        
    }
    
    /**
     * 把字符串转换为大写，如果传入的字符串是<code>null</code>则直接返回<code>null</code>
     */
    public static String upperCase(String string){
        if(null == string){
            return null;
        }
        return string.toUpperCase();
    }
}
