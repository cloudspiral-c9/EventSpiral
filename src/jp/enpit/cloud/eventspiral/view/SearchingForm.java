package jp.enpit.cloud.eventspiral.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * <ol>
 *   <li>リスト改ページ情報のエンティティクラス</li>
 * </ol>
 * @author s-egawa
 */

public class SearchingForm extends AbstractForm{

	/**
	 * １ページあたりの表示件数 チェック項目は以下の通り．
	 * <ul>
     *   <li> 0以上である．</li>
     * </ul>
	 */

	@Min(value=0L)
	private int startIndex;

	/**
	 * 表示開始インデックス チェック項目は以下の通り．
	 * <ul>
     *   <li> 10以上である．</li>
     *   <li> 100以下である．</li>
     * </ul>
	 */

	@Min(value=10L)
	@Max(value=100L)
	private int length;

	/**
	 * デフォルトコンストラクタ
	 */
	public SearchingForm(){
	}

	/**
	 * <ol>
     *   <li>表示開始インデックスを取得する．</li>
	 * </ol>
	 * @return 表示開始インデックス
	 */

	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * <ol>
     *   <li>表示開始インデックスをフィールドへ登録する．</li>
	 * </ol>
	 * @param startIndex 表示開始インデックス
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * <ol>
     *   <li>１ページあたりの表示件数を取得する．</li>
	 * </ol>
	 * @return １ページあたりの表示件数
	 */

	public int getLength() {
		return length;
	}

	/**
	 * <ol>
     *   <li>１ページあたりの表示件数をフィールドへ登録する．</li>
	 * </ol>
	 * @param length １ページあたりの表示件数
	 */
	public void setLength(int length) {
		this.length = length;
	}
}
