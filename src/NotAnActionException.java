/**
 * NotAnActionException - A Custom Exception
 * The designated object is not an instance of interface `Action`.
 */

public class NotAnActionException extends Exception {
	public NotAnActionException(String message) {
		super(message);
	}
}
