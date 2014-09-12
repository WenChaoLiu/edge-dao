package edge.dao.orm.metadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 表元数据
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class Table implements Cloneable{
	private final String catalog;
	private final String schema;
	private final String name;
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	private Map<String, Column> keys = new LinkedHashMap<String, Column>();

	public Table(String tableName, String catalog, String schema) {
		this.name = tableName;
		this.catalog = catalog;
		this.schema = schema;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the columns
	 */
	public Map<String, Column> getColumns() {
		return columns;
	}

	/**
	 * @return the keys
	 */
	public Map<String, Column> getKeys() {
		return keys;
	}
	
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

	/**
	 * @param keys the keys to set
	 */
	public void setKeys(Map<String, Column> keys) {
		this.keys = keys;
	}

	public void addColumn(String columnName, Column column) {
		this.getColumns().put(columnName, column);
	}
	
	public void addKey(String keyColumnName, Column column) {
		this.getKeys().put(keyColumnName, column);
	}
	
	public Column getColumn(String columnName) {
		return this.getColumns().get(columnName);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Table table = (Table)super.clone();
		Map<String, Column> columns = new LinkedHashMap<String, Column>();
		Map<String, Column> keys = new LinkedHashMap<String, Column>();
		for (String key: table.getColumns().keySet()) {
			columns.put(key, (Column)table.getColumn(key).clone());
		}
		for (String key: table.getKeys().keySet()) {
			keys.put(key, columns.get(key));
		}
		table.columns = columns;
		table.keys = keys;
		
		return table;
	}
}
