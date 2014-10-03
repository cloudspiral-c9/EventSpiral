package jp.enpit.cloud.eventspiral.view;

import javax.validation.constraints.NotNull;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * イベントを削除するときのフォームクラス
 *
 * @author 2014003
 *
 */
public class IdentifyingEventForm extends AbstractForm{

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
	 * デフォルトコンストラクタ
	 */
	public IdentifyingEventForm(){
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
}
