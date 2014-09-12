package edge.dao;

import java.lang.annotation.Annotation;
import java.util.UUID;

import javax.sql.DataSource;

import edge.common.ApplicationFactory;
import edge.common.utils.StringUtils;
import edge.dao.orm.annotations.Sequence;
import edge.dao.orm.keygenerator.IKeyValueGenerator;
import edge.dao.orm.keygenerator.SequenceKeyValueGenerator;
import edge.dao.orm.keygenerator.UUIDKeyValueGenerator;
import edge.dao.sql.ISqlActionExecutor;

/**
 * Dao工厂类
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class DaoFactory {
public static final String DEFAULT_DAO_NAME = "dao";
	
	/**
	 * 获取缺省的Dao对象(Spring配置文件中名字为dao的Bean)
	 * @return 如果在spring bean容器中找不到名字为baseDao的bean则返回null
	 */
	public static IDao getDao() {
		return ApplicationFactory.getBeanForName(IDao.class, DEFAULT_DAO_NAME);
	}
	
	/**
	 * 获取指定名称的Dao对象
	 * @param daoName 在spring中声明的dao bean名称 
	 * @return 如果daoName为null或者空字符串，则返回daoName为dao的Dao；如果在spring bean容器中找不到则返回null
	 */
	public static IDao getDao(String daoName) {
		if (StringUtils.isEmpty(daoName)) {
			return getDao();
		}
		return ApplicationFactory.getBeanForName(IDao.class, daoName);
	}
	
	/**
	 * 获取sql中@action表达式的解析器
	 * @param action 表达式关键字
	 */
	public static ISqlActionExecutor getActionExecutor(String action) {
		ISqlActionExecutor executor = ApplicationFactory.getBeanForName(ISqlActionExecutor.class, action);
		if (null == executor) {
			//throw new dataacc(StringUtils.format("action's exector not set,action name:%s", action));
		}
		return executor;
	}
	
	/**
	 * 根据主键字段上的标注寻找对应的组件生成器
	 * @param keyAnnotation 主键字段上的标注
	 * @return 如果是非支持的标注，则返回null
	 */
	public static IKeyValueGenerator getKeyValueGenerator(Annotation keyAnnotation) {
		if (null == keyAnnotation) {
			return null;
		}
		
		if (UUID.class.isAssignableFrom(keyAnnotation.getClass())) {
			return new UUIDKeyValueGenerator();
		}
		if (Sequence.class.isAssignableFrom(keyAnnotation.getClass())) {
			return new SequenceKeyValueGenerator(((Sequence)keyAnnotation).name());
		}
		return null;
	}
	
	/**
	 * 根据特定Dao对应的spring原始配置，以及数据源的基础配置，生成出对应的数据源给Dao
	 * @param daoName dao名字
	 * @param dataSourceConfig 数据源配置
	 */
	public static DataSource getDataSourceByPrototype(String daoName, DataSourceConfig dataSourceConfig) {
		IDataSourceCreator dataSourceCreator = ApplicationFactory.getFirstBeanOfType(IDataSourceCreator.class);
		if (null == dataSourceCreator) {
			dataSourceCreator = new ApacheDataSourceCreator();
		}
		return dataSourceCreator.create(daoName, dataSourceConfig);
	}
}
