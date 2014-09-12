package edge.dao;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import edge.common.ApplicationFactory;

public class ApacheDataSourceCreator implements IDataSourceCreator {

	public DataSource create(String daoName, DataSourceConfig dataSourceConfig) {
		BasicDataSource dataSourcePrototype = (BasicDataSource) ApplicationFactory
				.getBeanForName(Dao.class, daoName).getDataSourcePrototype();
		BasicDataSource targetDataSource = new BasicDataSource();
		targetDataSource.setUrl(dataSourceConfig.getUrl());
		targetDataSource.setUsername(dataSourceConfig.getUsername());
		targetDataSource.setPassword(dataSourceConfig.getPassword());
		targetDataSource.setDefaultAutoCommit(dataSourcePrototype
				.getDefaultAutoCommit());
		targetDataSource.setMaxActive(dataSourcePrototype.getMaxActive());
		targetDataSource.setMaxIdle(dataSourcePrototype.getMaxIdle());
		targetDataSource.setMinIdle(dataSourcePrototype.getMinIdle());
		targetDataSource.setDriverClassName(dataSourcePrototype
				.getDriverClassName());
		targetDataSource.setInitialSize(dataSourcePrototype.getInitialSize());
		return targetDataSource;
	}

}
