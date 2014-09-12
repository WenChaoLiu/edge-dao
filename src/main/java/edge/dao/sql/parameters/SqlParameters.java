package edge.dao.sql.parameters;

import java.util.List;
import java.util.Map;

import edge.dao.DaoAccessException;

public class SqlParameters extends BaseParameters{
	private final ISqlParameters sqlParameters;
	private final List<ISqlParameters> sqlParametersList;

	public SqlParameters(Object params, List<ISqlParameters> sqlParametersList) {
		this.sqlParameters = getParameters(params);
		this.sqlParametersList = sqlParametersList;
	}

	/**
	 * 根据参数bean找到对应的处理解析器
	 */
	public static ISqlParameters getParameters(Object params) {
		if (null == params) {
			return null;
		}

		if (params instanceof Map<?, ?>) {
			return new MapParameters((Map<?, ?>) params);
		}
		
		if (params instanceof ISqlParameters) {
			return (ISqlParameters)params;
		}

		if (params instanceof String || params instanceof Number || params instanceof Boolean || params instanceof Character || params.getClass().isPrimitive()) {
			throw new DaoAccessException("Not Support Params Type:" + params.getClass().getName());
		} else {
			return new ObjectParameters(params);
		}
	}

	/* (non-Javadoc)
	 * @see bingo.dao.sql.parameters.BaseParameters#tryResolve(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean tryResolve(String name, ParamValue value) {
		boolean resolved = false;

		if (null != sqlParameters) {
			resolved = sqlParameters.tryResolve(name, value);
			value.setValue(emptyStringToNull(value.getValue()));
		} else {
			resolved = false;
		}

		if (!resolved && null != sqlParametersList) {
			for (ISqlParameters parameters: sqlParametersList) {
				if (parameters.tryResolve(name, value)) {
					value.setValue(emptyStringToNull(value.getValue()));
					return true;
				}
			}
		}
		return resolved;
	}

	private static Object emptyStringToNull(Object value) {
		if (value != null && value instanceof String && ((String) value).trim().length() == 0) {
			return null;
		}
		return value;
	}
}
