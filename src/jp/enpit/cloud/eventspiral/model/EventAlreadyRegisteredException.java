package jp.enpit.cloud.eventspiral.model;


/**
 * Event登録時に既存のものと重複していた場合に発生する例外
 */
public class EventAlreadyRegisteredException extends TEMSystemException {
	private static final long serialVersionUID = -6409384266967711383L;

	public EventAlreadyRegisteredException(String msg) {
		super(msg);
	}
}
