package jp.enpit.cloud.eventspiral.model;

public class NotLoggedInException extends TEMSystemException {
	private static final long serialVersionUID = 4484687245534883055L;

	public NotLoggedInException(String msg) {
		super(msg);
	}
}
