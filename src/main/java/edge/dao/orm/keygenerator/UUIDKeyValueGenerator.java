package edge.dao.orm.keygenerator;

import java.io.Serializable;

import edge.dao.Dao;

public class UUIDKeyValueGenerator implements IKeyValueGenerator{
	public KeyAppendMode appendMode() {
		return KeyAppendMode.appendAsParam;
	}

	public Serializable evaluate() {
		return Dao.getUUID();
	}
}
