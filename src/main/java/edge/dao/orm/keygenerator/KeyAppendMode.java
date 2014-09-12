package edge.dao.orm.keygenerator;

/**
 * 主键生成拼接到SQL的策略模式 *
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public enum KeyAppendMode {
	appendAsParam, appendAsSqlFragment
}
