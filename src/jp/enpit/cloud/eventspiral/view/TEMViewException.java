package jp.enpit.cloud.eventspiral.view;

import jp.enpit.cloud.eventspiral.TEMException;

public class TEMViewException extends TEMException {

	private static final long serialVersionUID = -3685978937629013084L;

	public TEMViewException (String message) {
		super(message);
	}
	public TEMViewException (String message, Throwable cause) {
		super(message, cause);
	}
}
