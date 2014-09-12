package edge.dao.orm.metadata;

import edge.dao.orm.keygenerator.IKeyValueGenerator;

/**
 * 表列元数据
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class Column implements Cloneable{
private String name;
	
	private int dataType;
	
	private boolean isKey;
	
	private boolean isMapped;
	
	private boolean isAutoIncrement;

	private String propertyName;
	
	private String typeName;
	
	private IKeyValueGenerator generator;
	
	public Column() {
		
	}
	
	public Column(String name, String typeName, boolean isAutoIncrement) {
		this.name = name;
		this.typeName = typeName;
		this.isAutoIncrement = isAutoIncrement;
	}
	
	public Column(String name,int dataType, String typeName, boolean isAutoIncrement) {
		this.name = name;
		this.dataType = dataType;
		this.typeName = typeName;
		this.isAutoIncrement = isAutoIncrement;
	}	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the generator
	 */
	public IKeyValueGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator the generator to set
	 */
	public void setGenerator(IKeyValueGenerator generator) {
		this.generator = generator;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return {@link Types}中定义的数据类型
	 */
	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the isKey
	 */
	public boolean isKey() {
		return isKey;
	}

	/**
	 * @param isKey the isKey to set
	 */
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	/**
	 * @return the isMapped
	 */
	public boolean isMapped() {
		return isMapped;
	}

	/**
	 * @param isMapped the isMapped to set
	 */
	public void setMapped(boolean isMapped) {
		this.isMapped = isMapped;
	}

	/**
	 * @return the isAutoIncrement
	 */
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	/**
	 * @param isAutoIncrement the isAutoIncrement to set
	 */
	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Column column = (Column)super.clone();
		column.setPropertyName(null);
		column.setMapped(false);
		return column;
	}
}
