package jp.enpit.cloud.eventspiral.model;


public class TicketOccupiedException extends TEMSystemException {
	private static final long serialVersionUID = 5316999972073251313L;

	public TicketOccupiedException(String msg) {
		super(msg);
	}
}
