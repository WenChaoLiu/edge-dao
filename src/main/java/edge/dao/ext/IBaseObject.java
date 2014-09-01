package edge.dao.ext;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类都应该实现的接口
 * @author: WenChao_Liu@163.com
 * @date: 2014年8月23日
 */
public interface IBaseObject extends Serializable {
	
	public Serializable getCreateBy();

	public Serializable getCreateDate();

	public Serializable getLastUpdatedBy();

	public Serializable getLastUpdatedDate();

	public void setCreateBy(Serializable createBy);

	public void setCreateDate(Date createDate);

	public void setLastUpdatedBy(Serializable lastUpdatedBy);

	public void setLastUpdatedDate(Date lastUpdatedDate);
}
