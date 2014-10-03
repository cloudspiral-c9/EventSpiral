package jp.enpit.cloud.eventspiral.model;

/**
 * SeatCategoryのエンティティクラス．
 *
 * @author 2014003
 *
 */
public class SeatCategory {

	/**
	 * チケット枚数
	 */
	private int count;

	/**
	 * 単価
	 */
	private int fee;

	/**
	 * 座席種別
	 */
	private java.lang.String seatName;

	/**
	 * デフォルトコンストラクタ
	 */
	public SeatCategory() {
	}

	/**
	 * <ol>
	 *   <li>チケット枚数を取得する</li>
	 * </ol>
	 * @return チケット枚数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <ol>
	 *   <li>countをフィールドへ登録する．</li>
	 * </ol>
	 * @param count チケット枚数
	 */
	public void setCount(int count) {
		this.count = count;
	}


	/**
	 * <ol>
	 *   <li>単価を取得する</li>
	 * </ol>
	 * @return 単価
	 */
	public int getFee() {
		return fee;
	}

	/**
	 * <ol>
	 *   <li>単価をフィールドへ登録する．</li>
	 * </ol>
	 * @param fee 単価
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}

	/**
	 * <ol>
	 *   <li>座席種別を取得する</li>
	 * </ol>
	 * @return 座席種別
	 */
	public java.lang.String getSeatName() {
		return seatName;
	}

	/**
	 * <ol>
	 *   <li>seatNameをフィールドへ登録する．</li>
	 * </ol>
	 * @param seatName 座席種別
	 */
	public void setSeatName(java.lang.String seatName) {
		this.seatName = seatName;
	}

}
