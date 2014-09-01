package edge.dao.ext;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类基类
 * @author: WenChao_Liu@163.com
 * @date: 2014年8月23日
 */
public class BaseObject implements IBaseObject{
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
				
	private static final long serialVersionUID = 1L;
	
	/** 
	* @Fields createBy : 创建人
	*/ 
	private Serializable createBy;
	
	/** 
	* @Fields createDate : 创建时间
	*/ 
	private Date createDate;
	
	/** 
	* @Fields lastUpdatedBy : 最后修改人
	*/ 
	private Serializable lastUpdatedBy;
	
	/** 
	* @Fields lastUpdateddDate : 最后修改时间
	*/ 
	private Date lastUpdateddDate;

	public Serializable getCreateBy() {
		return createBy;
	}

	public Serializable getCreateDate() {
		return createDate;
	}

	public Serializable getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Serializable getLastUpdatedDate() {
		return lastUpdateddDate;
	}

	public void setCreateBy(Serializable createBy) {
		this.createBy=createBy;
		
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		
	}

	public void setLastUpdatedBy(Serializable lastUpdatedBy) {
		this.lastUpdatedBy=lastUpdatedBy;
		
	}

	public void setLastUpdatedDate(Date lastUpdateddDate) {
		this.lastUpdateddDate=lastUpdateddDate;
	}

}
