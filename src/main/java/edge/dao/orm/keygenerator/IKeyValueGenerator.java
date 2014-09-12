package edge.dao.orm.keygenerator;

import java.io.Serializable;

/**
 * 主键字段value生成器接口
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public interface IKeyValueGenerator {
	/**
	 * 是否是append主键值到与sql中与?一一对应的参数列表里
	 * @return true 以append到参数列表  false append拼接到sql
	 */
	KeyAppendMode appendMode();
	
	/**
	 * 返回主键生成器下准备append as param或者append as sql的值
	 * @return 主键的值或者生成主键的sql片段
	 */
	Serializable evaluate();
}
