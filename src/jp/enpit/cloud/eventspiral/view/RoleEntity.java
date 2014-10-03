package jp.enpit.cloud.eventspiral.view;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <ol>
 * <li>アカウント権限のエンティティクラス</li>
 * </ol>
 */
public class RoleEntity extends AbstractEntity {
	/**
	 * アカウント権限
	 * チェック項目は以下の通り．
	 * <ul>
	 *   <li>空文字ではない．</li>
	 * </ul>
	 */
	@NotEmpty
	private String role;
	
	/**
	 * デフォルトコンストラクタ．roleを""で初期化する
	 */
	public RoleEntity(){
	}
	/**
	 * <ol>
         *   <li>アカウント権限を取得する．</li>
	 * </ol>
	 * @return アカウント権限
	 */
	public String getRole() {
		return role;
	}

	/**
	 * <ol>
         *   <li>アカウント権限をフィールドへ登録する．</li>
	 * </ol>
	 * @param role アカウント権限
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}
