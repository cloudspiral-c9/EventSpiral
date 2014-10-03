package jp.enpit.cloud.eventspiral.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * イベント情報のエンティティクラス
 *
 * @author 2014024
 */

public class Event {
	/**
	 * イベント名
	 */
	private String eventName;
	/**
	 * イベント開催日時
	 */
	private Date eventDate;
	/**
	 * イベントID
	 */
	private String eventId;
	/**
	 * チケット販売開始日時
	 */
	private Date ticketStartDate;
	/**
	 * 説明
	 */
	private String description;
	/**
	 * 興行主ID
	 */
	private String promoterId;

	private List<SeatCategory> totalSeats;
	/**
	 * デフォルトコンストラクタ
	 */
	public Event() {
		totalSeats = new ArrayList<SeatCategory>();
	}

	/**
	 * <ol>
	 *   <li>イベント名を取得する</li>
	 * </ol>
	 * @return イベント名
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * <ol>
	 *   <li>eventNameをフィールドへ登録する．</li>
	 * </ol>
	 * @param eventName イベント名
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * <ol>
	 *   <li>イベント開催日時をを取得する</li>
	 * </ol>
	 * @return イベント開催日時
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * <ol>
	 *   <li>eventDateをフィールドへ登録する．</li>
	 * </ol>
	 * @param eventDate イベント開催日時
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * <ol>
	 *   <li>イベントIDを取得する</li>
	 * </ol>
	 * @return イベントID
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * <ol>
	 *   <li>eventIdをフィールドへ登録する．</li>
	 * </ol>
	 * @param eventId イベントID
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * <ol>
	 *   <li>チケット販売開始日時を取得する</li>
	 * </ol>
	 * @return チケット販売開始日時
	 */
	public Date getTicketStartDate() {
		return ticketStartDate;
	}

	/**
	 * <ol>
	 *   <li>ticketStartDateをフィールドへ登録する．</li>
	 * </ol>
	 * @param ticketStartDate チケット販売開始日時
	 */
	public void setTicketStartDate(Date ticketStartDate) {
		this.ticketStartDate = ticketStartDate;
	}

	/**
	 * <ol>
	 *   <li>説明を取得する</li>
	 * </ol>
	 * @return 説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <ol>
	 *   <li>descriptionをフィールドへ登録する．</li>
	 * </ol>
	 * @param role description 説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <ol>
	 *   <li>興行主IDを取得する</li>
	 * </ol>
	 * @return 興行主ID
	 */
	public String getPromoterId() {
		return promoterId;
	}
	/**
	 * <ol>
	 *   <li>promoterIdをフィールドへ登録する．</li>
	 * </ol>
	 * @param promoterId 興行主ID
	 */
	public void setPromoterId(String promoterId) {
		this.promoterId = promoterId;
	}


	/**
	 * <ol>
	 *   <li>座席情報を取得する</li>
	 * </ol>
	 * @return 座席情報（SeatCategoryオブジェクト）のリスト
	 */
	public List<SeatCategory> getTotalSeats(){
		return totalSeats;
	}

	public void setTotalSeats(List<SeatCategory> totalSeats){
		this.totalSeats = totalSeats;
	}
}