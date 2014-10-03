package jp.enpit.cloud.eventspiral.view;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import jp.enpit.cloud.eventspiral.TEMFatalException;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * イベントを登録するときのフォームクラス．
 * @author s-egawa
 */
public class RegisteringEventForm extends AbstractForm{

	/**
	 * 説明．
	 * チェック項目は以下の通り．
	 * <ul>
	 *   <li>空文字ではない．</li>
	 * </ul>
	 */
	@NotEmpty(message="必須項目が未入力です")
	private String description;

	/**
	 * イベント開催日時．
	 * チェック項目は以下の通り．
	 * <ul>
	 *   <li>nullではない．</li>
	 *   <li>未来の日付である．</li>
	 * </ul>
	 */
	@NotNull(message="必須項目が未入力です")
	@Future(message="イベント開催日時は未来の日時を入力してください")
	private Date eventDate;

	/**
	 * イベント名．
	 * チェック項目は以下の通り．
	 * <ul>
	 *   <li>空文字ではない．</li>
	 * </ul>
	 */
	@NotEmpty(message="必須項目が未入力です")
	private String eventName;

	/**
	 * チケット販売開始日時．
	 * チェック項目は以下の通り．
	 * <ul>
	 *   <li>nullではない．</li>
	 *   <li>未来の日付である．</li>
	 * </ul>
	 */
	@NotNull(message="必須項目が未入力です")
	@Future(message="チケット販売開始日時は未来の日時を入力してください")
	private Date ticketStartDate;

	/**
	 * デフォルトコンストラクタ
	 */
	public RegisteringEventForm(){
	}

	/**
	 * <ol>
	 *   <li>validateのオーバーライド チケット販売日がイベントの開始日の前であることをチェックする． そうでなかった場合は，TEMValidationExceptionにエラーメッセージをセットし，throwする</li>
	 * </ol>
	 * @return 検証に成功したか否か．基本的に true を返す．falseとなる場合は，返り値が帰る前に TEMValidationExceptionが投げられる．
	 * @throws TEMFatalException HibernateValiadtorの各制約違反が発生した場合．
	 */
	public boolean validate() throws TEMValidationException{
		super.validate();
		Date eventDate = this.getEventDate();
		Date ticketStartDate = this.getTicketStartDate();

		if (eventDate.compareTo(ticketStartDate) == 1){
			return true;
		}else{
			throw new TEMValidationException("チケット販売日時がイベント開催日時より後です");
		}


	}

	/**
	 * <ol>
         *   <li>説明を取得する．</li>
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
	 * @param description 説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <ol>
         *   <li>イベント開始日時を取得する．</li>
	 * </ol>
	 * @return イベント開始日時
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * <ol>
         *   <li>eventDateをフィールドへ登録する．</li>
	 * </ol>
	 * @param eventDate イベント開始日時
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * <ol>
         *   <li>イベント名を取得する．</li>
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
         *   <li>チケット販売開始日時を取得する．</li>
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



}
