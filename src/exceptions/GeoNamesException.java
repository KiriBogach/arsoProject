package exceptions;

@SuppressWarnings("serial")
public class GeoNamesException extends RuntimeException {

	public GeoNamesException(String msg) {
		super(msg);
	}
	
	public GeoNamesException(String msg, Throwable e) {
		super(msg, e);
	}
}
