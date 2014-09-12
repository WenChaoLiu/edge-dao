package edge.dao.sql;

import java.util.List;

/**
 * 批量sql语句执行
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class BatchSqlCommand {
	protected String commandText;
	protected List<Object[]> batchParams;

	public String getCommandText() {
		return commandText;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}

	public List<Object[]> getBatchParams() {
		return batchParams;
	}

	public void setBatchParams(List<Object[]> batchParams) {
		this.batchParams = batchParams;
	}
}
