package jp.enpit.cloud.eventspiral.model;


public class TicketSoldOutException extends TEMSystemException {

	private static final long serialVersionUID = 8306284584380726136L;

	public TicketSoldOutException(String msg) {
		super(msg);
	}

}
