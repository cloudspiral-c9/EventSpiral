package jp.enpit.cloud.eventspiral.view;

/**
 * イベント詳細を表示するときのエンティティクラス．
 *
 * @author 2014016 (takatori)
 * @author 2014040 (miura)
 */

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class EventDetailEntity extends AbstractEntity {

    /**
     * イベントID
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    private String eventId;

    /**
     * イベント名
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    private String eventName;

    /**
     * イベント開催日時
     */
    @NotNull(message="必須項目が未入力です")
    private Date eventDate;

    /**
     * チケット販売開始日時
     */
    @NotNull(message="必須項目が未入力です")
    private Date ticketStartDate;

    /**
     * 説明
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    private String description;

    /**
     * 興行主ID
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    private String promoterId;

    /**
     * 座席種別ごとの残席数
     */
    @NotNull(message="必須項目が未入力です")
    private java.util.List<SeatCategoryEntity> remainSeats;

    /**
     * 座席種別ごとの購入数
     */
    private java.util.List<SeatCategoryEntity> boughtSeats;

    /**
     * 座席種別ごとの席数
     */
    @NotNull(message="必須項目が未入力です")
    private java.util.List<SeatCategoryEntity> totalSeats;

    /**
     * デフォルトコンストラクタ
     */
    public EventDetailEntity() {

    }

    /**
     * <ol>
     *  <li><興行主IDを取得する</li>
     * </ol>
     * @return 興行主ID
     */
    public String getPromoterId() {
		return promoterId;
	}

    /**
     * promoterIdをフィールドへ登録する
     * @param promoterId 興行主ID
     */
	public void setPromoterId(String promoterId) {
		this.promoterId = promoterId;
	}

	/**
	 * <ol>
	 *     <li>残席数を含んだ座席情報を取得する</li>
	 * </ol>
	 * @return 残席数を含んだ座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public java.util.List<SeatCategoryEntity> getRemainSeats() {
		return remainSeats;
	}

	/**
	 * <ol>
	 *     <li>残席数を含んだ座席種別ごとの座席情報をフィールドへ登録する</li>
	 * </ol>
	 * @param remainSeats 残席数を含んだ座席種別ごとの座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public void setRemainSeats(java.util.List<SeatCategoryEntity> remainSeats) {
		this.remainSeats = remainSeats;
	}

	/**
	 * <ol>
	 *     <li>座席種別ごとの購入数を含んだ座席情報を取得する</li>
	 * </ol>
	 * @return 座席種別ごとの購入数を含んだ座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public java.util.List<SeatCategoryEntity> getBoughtSeats() {
		return boughtSeats;
	}

	/**
	 * <ol>
	 *     <li>座席種別ごとの購入数を含んだ座席情報をフィールドへ登録する</li>
	 * </ol>
	 * @param boughtSeats 座席種別ごとの購入数を含んだ座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public void setBoughtSeats(java.util.List<SeatCategoryEntity> boughtSeats) {
		this.boughtSeats = boughtSeats;
	}

	/**
	 * <ol>
	 *     <li>座席情報の総数を取得する</li>
	 * </ol>
	 * @return 座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public java.util.List<SeatCategoryEntity> getTotalSeats() {
		return totalSeats;
	}

	/**
	 * <ol>
	 *     <li>座席情報の総数を登録する</li>
	 * </ol>
	 * @param totalSeats 座席種別ごとの座席情報（SeatCategoryEntityオブジェクト）のリスト
	 */
	public void setTotalSeats(java.util.List<SeatCategoryEntity> totalSeats) {
		this.totalSeats = totalSeats;
	}

	/**
     * <ol>
     *  <li>イベントIDを取得する</li>
     *</ol>
     * @return イベントID
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * <ol>
     *  <li>eventIdをフィールドへ登録する</li>
     * </ol>
     * @param eventId イベントId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * <ol>
     *  <li>イベント名を取得する</li>
     *</ol>
     * @return イベント名
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * <ol>
     *  <li>eventNameをフィールドへ登録する</li>
     * </ol>
     * @param eventName イベント名
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * <ol>
     *  <li>イベント開催日時を取得する</li>
     *</ol>
     * @return イベント開催日時
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * <ol>
     *  <li>eventDateをフィールドへ登録する</li>
     * </ol>
     * @param eventDate イベント開催日時
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * <ol>
     *  <li>チケット販売開始日時を取得する</li>
     *</ol>
     * @return チケット販売開始日時
     */
    public Date getTicketStartDate() {
        return ticketStartDate;
    }

    /**
     * <ol>
     *  <li>ticketStartDateをフィールドへ登録する</li>
     * </ol>
     * @param ticketStasrtDate チケット販売開始日時
     */
    public void setTicketStartDate(Date ticketStartDate) {
        this.ticketStartDate = ticketStartDate;
    }

    /**
     * <ol>
     *  <li>説明を取得する</li>
     *</ol>
     * @return 説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * <ol>
     *  <li>descriptionをフィールドへ登録する</li>
     * </ol>
     * @param description 説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
