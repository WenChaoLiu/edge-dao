package edge.dao;

import javax.sql.DataSource;

/**
 * 特定数据源创建接口
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public interface IDataSourceCreator {
	/**
	 * 根据数据源配置信息创建特定Dao所需的数据源
	 * @param daoName dao名称
	 * @param dataSourceConfig 数据源配置信息
	 */
	DataSource create(String daoName, DataSourceConfig dataSourceConfig);
}
