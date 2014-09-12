package edge.dao;

/**
 * 数据源配置信息
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class DataSourceConfig {

	private String	url;
	private String	username;
	private String	password;

	public DataSourceConfig() {

	}

	public DataSourceConfig(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DataSourceConfig)) {
			return false;
		}
		DataSourceConfig other = (DataSourceConfig) object;
		return this.getUrl().equals(other.getUrl()) && this.getUsername().equals(other.getUsername()) && this.getPassword().equals(other.getPassword());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getUrl().hashCode() + this.getUsername().hashCode() + this.getPassword().hashCode();
	}
}
