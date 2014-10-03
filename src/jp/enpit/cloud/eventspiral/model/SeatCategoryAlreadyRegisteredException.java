package jp.enpit.cloud.eventspiral.model;


public class SeatCategoryAlreadyRegisteredException extends TEMSystemException {
	private static final long serialVersionUID = 7718452949157952324L;
	
	public SeatCategoryAlreadyRegisteredException(String msg) {
		super(msg);
	}
}
