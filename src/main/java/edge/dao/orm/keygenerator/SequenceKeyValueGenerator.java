package edge.dao.orm.keygenerator;

import java.io.Serializable;

/**
 * 基于Sequence主键生成器
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月6日
 */
public class SequenceKeyValueGenerator implements IKeyValueGenerator{
	private String sequenceName;
	
	public SequenceKeyValueGenerator(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public KeyAppendMode appendMode() {
		return KeyAppendMode.appendAsSqlFragment;
	}

	public Serializable evaluate() {
		return sequenceName + ".nextval";
	}
}
