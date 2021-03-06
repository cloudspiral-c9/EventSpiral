package jp.enpit.cloud.eventspiral.view;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.hibernate.validator.constraints.NotEmpty;

/**
*
* チケット購入するときのフォームクラス
*
* @author 2014016
*
*/
public class BuyingTicketForm extends AbstractForm{

	/**
     * イベントID。チェック項目は以下の通り。
     * <ul>
	 *   <li>nullではない</li>
	 *   <li>空文字ではない</li>
	 * </ul>
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
	java.lang.String eventId;

    /**
     * 座席種別
     * <ul>
	 *   <li>nullではない</li>
	 *   <li>空文字ではない</li>
	 * </ul>
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    private String seatName;

    /**
     * 席数
     * <ul>
	 *   <li>1以上であること</li>
	 * </ul>
     */
	@Min(value=1L,message="チケット数には1以上の整数を入力してください。")
	private int count;

    /**
	 * デフォルトコンストラクタ
	 */
	public BuyingTicketForm(){
	}

	/**
     * イベントIDを取得する
     * @return イベントID
     */
	public java.lang.String getEventId() {
		return eventId;
	}

	/**
     * eventIdをDBUtilsのsanitizeを利用してサニタイズしてから、フィールドを取得する。
     * @param eventId イベントID
     */
	public void setEventId(java.lang.String eventId) {
		this.eventId = DBUtils.sanitize(eventId);
	}

	/**
     * 座席種別を取得す
     * @return 座席種別
     */
	public String getSeatName() {
		return seatName;
	}

	/**
     * seatNameをフィールドへ登録する
     * @param seatName 座席種別
     */
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	/**
     * 席数を取得する
     * @return 席数
     */
    public int getCount() {
		return count;
	}

    /**
     * countをフィールドへ登録する
     * @param count 席数
     */
	public void setCount(int count) {
		this.count = count;
	}
}
