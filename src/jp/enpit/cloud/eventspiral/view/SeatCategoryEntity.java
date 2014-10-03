package jp.enpit.cloud.eventspiral.view;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * 座席情報のエンティティクラス
 *
 * @author 2014003
 *
 */
public class SeatCategoryEntity extends AbstractEntity {

	/**
     * 席数
     * <ul>
	 *   <li>0以上である</li>
	 * </ul>
     */
	@Min(value=0L)
	private int count;

    /**
     * <ul>
	 *   <li>0以上である</li>
	 * </ul>
     * 単価
     */
    @Min(value=0L)
    private int fee;

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
	 * デフォルトコンストラクタ
	 */
	public SeatCategoryEntity() {
	}

	/**
     * <ol>
     *  <li>席数を取得する</li>
     * </ol>
     * @return 席数
     */
    public int getCount() {
		return count;
	}

    /**
     * <ol>
     * 		<li>countをフィールドへ登録する</li>
     * </ol>
     * @param count 席数
     */
	public void setCount(int count) {
		this.count = count;
	}

	/**
     * <ol>
     *  <li>単価を取得する</li>
     * </ol>
     * @return 単価
     */
	public int getFee() {
		return fee;
	}

	/**
     * <ol>
     * 		<li>feeをフィールドへ登録する</li>
     * </ol>
     * @param fee 単価
     */
	public void setFee(int fee) {
		this.fee = fee;
	}

	/**
     * <ol>
     *  <li>座席種別を取得する</li>
     * </ol>
     * @return 座席種別
     */
	public String getSeatName() {
		return seatName;
	}

	/**
     * <ol>
     * 		<li>seatNameをフィールドへ登録する</li>
     * </ol>
     * @param seatName 座席種別
     */
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

}
