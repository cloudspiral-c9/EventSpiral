package jp.enpit.cloud.eventspiral.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.AccountModel;
import jp.enpit.cloud.eventspiral.model.AuthenticationFailureException;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.RoleEntity;
import jp.enpit.cloud.eventspiral.view.TEMViewException;
/**
 * DWRから呼び出されるログイン処理のコントローラークラス
*/
public class LoginController{
	/**
     * Loggerオブジェクト
	 */
	private Logger logger;
	/**
     * Loggerフィールドにオブジェクトを設定する．
	 */
	public LoginController(){
		this.logger = Logger.getLogger(getClass().getName());
	}
	/**
     * DWRより呼び出されるログアウト処理のコントローラーメソッド.
	 * <ol>
     * <li>formデータをバリデートする．</li>
     * <li>AccountModelクラスのauthenticate()を呼び出し、認証チェックを行う．</li>
     * <li>SessionクラスのregisterSessionId()を呼び出し、セッションIDを登録する．</li>
     * <li>AccountModelクラスのgetRole()を呼び出し、ユーザ権限を取得する．</li>
     * <li>RoleEntityクラスを生成し、setRole()を呼び出して、ユーザ権限を登録する．</li>
     * <li>RoleEntityオブジェクトをバリデートする．</li>
     * <li>RoleEntityオブジェクト(ユーザ権限情報)を返す．</li>

     * <li><tt>AuthenticationFailureException</tt>が発生した場合：
     *   <ol>
     *     <li>発生した例外と「ユーザIDもしくはパスワードが正しくありません」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
     *   </ol>
     * </li>
     * <li><tt>TEMFatalException</tt>が発生した場合：
     *   <ol>
     *     <li>発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
     *   </ol>
     * </li>

	 * </ol>
	 * @param form ログイン情報（ユーザID、パスワード）
	 * @return ユーザIDの権限情報
	 * @throws TEMViewException TEMFatalException、AuthenticationFailureExceptionが発生した場合．
	 */
	public RoleEntity execute(IdentifyingAccountForm form) throws TEMViewException {
		logger.info("LoginController.execute");
		form.validate();

		try{
			AccountModel adao = new AccountModel();
			Session session = new Session();

			// 認証チェック
			Account account = adao.authenticate(form.getUserId(), form.getPass());
			session.registerSessionId(form.getUserId());
			
			RoleEntity re = new RoleEntity();
			re.setRole(account.getRole());
			re.validate();
			return re;
		} catch (AuthenticationFailureException e) {
			throw new TEMViewException("ユーザIDもしくはパスワードが正しくありません");
		} catch (TEMFatalException e) {
			throw new TEMViewException("システムエラーが発生しました．管理者に連絡して下さい", e);
		}

	}

}