package framework.exception;

public class FrameworkException extends Exception{
	public static final String baseMessage = "An error appeared on the framework : ";
	public FrameworkException(){
		super( baseMessage );
	}

	public FrameworkException( String message ){
		super( FrameworkException.baseMessage + message );
	}
}