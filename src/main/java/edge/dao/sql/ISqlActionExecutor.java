package edge.dao.sql;

import java.util.Map;

import edge.dao.sql.parameters.ISqlParameters;

/**
 * action执行者
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public interface ISqlActionExecutor {
	/**
	 * 解析action对应的sql
	 * @param action action信息
	 * @param inParams 原始的查询参数
	 * @param outParams 增加的查询参数
	 */
	String execute(SqlAction action, ISqlParameters inParams, Map<String, Object> outParams);
}
