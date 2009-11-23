package dk.bot.adrianmassey;

/**
 * 
 * @author daniel
 *
 */
public class AdrianMasseyException extends RuntimeException{

	public AdrianMasseyException(String message) {
		super(message);
	}
	
	public AdrianMasseyException(String message,Throwable t) {
		super(message,t);
	}
	
	public AdrianMasseyException(Throwable t) {
		super(t);
	}
}
