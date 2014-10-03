package jp.enpit.cloud.eventspiral.model;

/**
 * アカウント情報のエンティティクラス
 *
 * @author shinsuke-m
 */
public class Account {
	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * パスワード
	 */
	private String pass;

	/**
	 * 権限
	 */
	private String role;

	/**
	 * セッションID
	 */
	private String sessionId;

	/**
	 * デフォルトコンストラクタ
	 */
	public Account() {
	}

	/**
	 * <ol>
	 *   <li>userId、passをフィールドへ登録する．</li>
	 *   <li>権限はuserを設定する．</li>
	 *   <li>sessionIdは初期化される．</li>
	 * </ol>
	 * @param userId ユーザID
	 * @param pass パスワード
	 */
/*	public Account(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
		this.role = "user";
		this.sessionId = "";
	}
*/

	/**
	 * <ol>
	 *   <li>userId、pass、roleをフィールドへ登録する．</li>
	 *   <li>sessionIdは初期化される．</li>
	 * </ol>
	 * @param userId ユーザID
	 * @param pass パスワード
	 * @param role 権限
	 */
/*	public Account(String userId, String pass, String role) {
		this.userId = userId;
		this.pass = pass;
		this.role = role;
		this.sessionId = "";
	}
*/

	/**
	 * <ol>
	 *   <li>権限を取得する</li>
	 * </ol>
	 * @return 権限
	 */
	public String getRole() {
		return role;
	}

	/**
	 * <ol>
	 *   <li>roleをフィールドへ登録する．</li>
	 * </ol>
	 * @param role 権限
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * <ol>
	 *   <li>セッションIDを取得する</li>
	 * </ol>
	 * @return セッションID
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * <ol>
	 *   <li>sessionIdをフィールドへ登録する．</li>
	 * </ol>
	 * @param sessionId セッションID
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * <ol>
	 *   <li>ユーザIDを取得する</li>
	 * </ol>
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <ol>
	 *   <li>パスワードを取得する</li>
	 * </ol>
	 * @return パスワード
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * <ol>
	 * <li>userIdをフィールドへ登録する．</li>
	 * </ol>
	 * @param userId ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <ol>
	 * <li>passをフィールドへ登録する．</li>
	 * </ol>
	 * @param pass パスワード
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * ユーザが管理者であるかを調べる
	 * @return ユーザのroleが管理者("administrator")であったときにtrueを返す
	 */
	public boolean isAdministrator(){
		return "administrator".equals(role);
	}

	/**
	 * ユーザが興行主であるかを調べる
	 * @return ユーザのroleが興行主("promoter")であったときにtrueを返す
	 */
	public boolean isPromoter(){
		return "promoter".equals(role);
	}


	public String toString() {
		return "userId:" + userId + ", pass:" + pass + ", role:" + role + ", sessionId:" + sessionId;
	}
}
