package edge.dao;

/**
 * DaoAccessException异常处理
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
@SuppressWarnings("serial")
public class DaoAccessException extends RuntimeException{
	public DaoAccessException(String message) {
		super(message);
	}

	public DaoAccessException(String message, Throwable e) {
		super(message, e);
	}
}
