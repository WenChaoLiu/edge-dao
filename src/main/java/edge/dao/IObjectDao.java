package edge.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 对象数据访问接口
 * @author WenChao_Liu@163.com
 * @date 2014年8月9日
 */
public abstract interface IObjectDao {
	
  
    /**
     * 新增指定对象
     * @param entity 需要保存的对象
     * @return 新增记录数，成功返回1，否则返回0
     * @throws DataAccessException 可能出现的数据访问异常
     */
    public abstract int insert(Object entity) throws DataAccessException;
    

    /**
     * 新增指定对象
     * @param type 对应的java数据类型
     * @param entity
     * @return 新增记录数，成功返回1，否则返回0
     * @throws DataAccessException
     */
    public abstract int insert(Class<?> type,Object entity) throws DataAccessException;
    
    
    /**
     * 插入记录
     * @param type 映射到要插入记录的数据库表的java类型
     * @param fields 要插入的属性和值，Map中的key对应java类型中的属性或者字段名
     * @return 插入记录数，成功返回1，否则返回0
     * @throws DataAccessException 可能会发生的数据访问异常
     */
    public abstract int insertFields(Class<?> type,Map<String,Object> fields) throws DataAccessException;
    
    
    /**
     * 批量新增对象集合
     * @param type 对象类型
     * @param entities 对象集合
     * @return 
     * @throws DataAccessException 可能会发生的数据访问异常
     */
    public abstract int[] batchInsert(Class<?> type,Collection<?> entities) throws DataAccessException;
    
    
    /**
     * 更新对象
     * @param entity 待更新的对象
     * @return 更新的记录数，成功返回1，失败返回0
     * @throws DataAccessException 可能会繁盛的数据访问异常
     */
    public abstract int update(Object entity) throws DataAccessException;
    

    /**
     * 更新对象
     * @param type 数据库中记录对应java中的类型
     * @param entity 带更新的对象
     * @return 更新的记录数，成功返回1，失败返回0
     * @throws DataAccessException
     */
    public abstract int update(Class<?> type,Object entity) throws DataAccessException;
    
    /**
     * 更新一条记录
     * @param type 数据库中记录对应的java类型
     * @param fields 更新的属性和值 Map中的key对应对象的属性或者字段名
     * @return 更新的记录数。成功返回1，失败返回0
     * @throws DataAccessException
     */
    public abstract int updateFields(Class<?> type,Map<String,Object> fields) throws DataAccessException;
    

    /** 
    * @Title: updateFieldsExcluded 
    * @Description: 更行对象（除指定属性外）
    * @param @param paramObject 被更新的对象
    * @param @return
    * @return int 返回受影响的行数
    * @throws DataAccessException
    */
    public abstract int updateFieldsExcluded(Object paramObject,String... paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: updateFieldsExcluded 
    * @Description: 更新对象指定属性
    * @param @return
    * @return int 返回受影响行数
    * @throws DataAccessException
    */
    public abstract int updateFieldsExcluded(Class<?> type,Object paramObject,String... paramVarArgs) throws DataAccessException;
    
    /** 
    * @Title: delete 
    * @Description: 删除指定对象
    * @param @param paramObject 被删除的对象
    * @param @return
    * @param @throws DataAccessException
    * @return int 返回受影响行数
    * @throws DataAccessException
    */
    public abstract int delete(Object paramObject) throws DataAccessException;
    
    /** 
    * @Title: delete 
    * @Description: TODO
    * @param @param type 删除对象类型
    * @param @param paramObject 被删除对象
    * @param @return
    * @param @throws DataAccessException
    * @return int 返回受影响行数
    * @throws DataAccessException
    */
    public abstract <T> int  delete(Class<?> type,Object paramObject) throws DataAccessException;
    
    /** 
    * @Title: batchDelete 
    * @Description: 批量删除对象
    * @param @param type 被删除对象类型
    * @param @param paramCollection 被删除对象的集合
    * @param @return
    * @param @throws DataAccessException
    * @return int[] 返回受影响的行数
    * @throws DataAccessException
    */
    public abstract int[] batchDelete(Class<?> type,Collection<?> paramCollection) throws DataAccessException;
    
    /** 
    * @Title: get 
    * @Description: 获取指定对象
    * @param @param type 对象类型
    * @param @param paramObject 指定对象
    * @param @return
    * @param @throws DataAccessException
    * @return T 
    * @throws DataAccessException
    */
    public abstract <T> T get(Class<?> type,Object paramObject) throws DataAccessException;
    
    /** 
    * @Title: exists 
    * @Description: 校验指定对象是否存在
    * @param @param type
    * @param @param paramObject
    * @param @return 
    * @param @throws DataAccessException
    * @return boolean 
    * @throws DataAccessException
    */
    public abstract <T> boolean exists(Class<?> type,Object paramObject) throws DataAccessException;
}
