package exception;

public class ObjectDontExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectDontExistException(String massage) {
		super(massage);
	}
}
