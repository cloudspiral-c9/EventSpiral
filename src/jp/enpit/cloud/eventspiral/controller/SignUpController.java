package jp.enpit.cloud.eventspiral.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.AccountAlreadyRegisteredException;
import jp.enpit.cloud.eventspiral.model.AccountModel;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;
/**
 * DWRから呼び出されるユーザアカウント登録処理のコントローラークラス
*/
public class SignUpController{
	/**
     * Loggerオブジェクト
	 */
	private Logger logger;
	/**
     * Loggerフィールドにオブジェクトを設定する．
	 */
	public SignUpController(){
		logger = Logger.getLogger(getClass().getName());
	}

	/**
     * DWRより呼び出される一般ユーザ登録処理のコントローラーメソッド.
	 * <ol>
     * <li>formデータをバリデートする．</li>
     * <li>AccountModelクラスのregisterUser()を呼び出し、アカウント情報をデータベースに登録する。</li>

     * <li><tt>TEMFatalException</tt>が発生した場合：
     *   <ol>
     *     <li>発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
     *   </ol>
     * </li>
     * <li><tt>AccountAlreadyRegisteredException</tt>が発生した場合：
     *   <ol>
     *     <li>発生した例外と「指定されたユーザIDは既に存在します」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
     *   </ol>
     * </li>

	 * </ol>
	 * @param form 登録するアカウント情報
	 * @throws TEMViewException TEMFatalException,AccountAlreadyRegisteredExceptionが発生した場合．
	 */
	public void execute (IdentifyingAccountForm form) throws TEMViewException {
		logger.info("SignUpController.execute");
		form.validate();

		try{
			AccountModel dao = new AccountModel();

			dao.registerUser(form.getUserId(), form.getPass());
			
			/*BooleanEntity b = new BooleanEntity(true);;
			b.validate();
			return b;*/
		} catch (TEMFatalException e){
			throw new TEMViewException("システムエラーが発生しました．管理者に連絡して下さい", e);
		} catch (AccountAlreadyRegisteredException e){
			throw new TEMViewException("指定されたユーザIDは既に存在します", e);
		}
	}
}
