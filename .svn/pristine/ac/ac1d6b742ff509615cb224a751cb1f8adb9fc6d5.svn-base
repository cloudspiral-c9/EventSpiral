package jp.enpit.cloud.eventspiral.view;
import javax.validation.constraints.Pattern;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <ol>
 *   <li>ログイン及びアカウント登録時に利用されるエンティティクラス</li>
 * </ol>
 */
public class IdentifyingAccountForm extends AbstractForm{
	/**
         * ユーザID．
         * チェック項目は以下の通り．
         * <ul>
         *   <li>長さが4文字以上，12文字以下であるか．</li>
         *   <li>空文字ではない．</li>
         *   <li>英数字のみが利用されているか．</li>
         * </ul>
	 */
	@Length(min=4, max=12, message="ユーザIDには4から12文字の英数字のみが利用できます")
	@Pattern(regexp="^[a-zA-Z0-9]*$", message="ユーザIDには4から12文字の英数字のみが利用できます")
	@NotEmpty(message="ユーザIDには4から12文字の英数字のみが利用できます")
	private String userId;
	
	/**
         * パスワード．
         * チェック項目は以下の通り．
         * <ul>
         *   <li>長さが4文字以上，12文字以下であるか．</li>
         *   <li>空文字ではない．</li>
         *   <li>英数字のみが利用されているか．</li>
         * </ul>
	 */
	@Length(min=4, max=12, message="パスワードには4から12文字の英数字のみが利用できます")
	@Pattern(regexp="^[a-zA-Z0-9]*$", message="パスワードには4から12文字の英数字のみが利用できます")
	@NotEmpty(message="ユーザIDには4から12文字の英数字のみが利用できます")
	private String pass;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public IdentifyingAccountForm(){
	}

	/**
	 * <ol>
         *   <li>userId、passをフィールドへ登録する．</li>
	 * </ol>
	public IdentifyingAccountForm(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
	}
	 */

	/**
	 * <ol>
         *   <li>パスワードを取得する．</li>
	 * </ol>
	 * @return パスワード
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * <ol>
         *   <li>ユーザIDを取得する．</li>
	 * </ol>
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <ol>
         *   <li>パスワードをフィールドへ登録する．</li>
	 * </ol>
	 * @param pass パスワード
	 */
	public void setPass(String pass) {
		this.pass = DBUtils.sanitize(pass);
	}

	/**
	 * <ol>
         *   <li>ユーザIDをフィールドへ登録する．</li>
	 * </ol>
	 * @param userId ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = DBUtils.sanitize(userId);
	}
}
