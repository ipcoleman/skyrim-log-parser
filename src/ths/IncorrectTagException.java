package src.ths;

public class IncorrectTagException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7944529490572087154L; //?

	public IncorrectTagException() {
		// TODO Auto-generated constructor stub
		super("INCORRECT TAG");
	}

	public IncorrectTagException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IncorrectTagException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public IncorrectTagException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
