package jp.enpit.cloud.eventspiral.controller;

/**
 * DWRから呼び出される興行主アカウント登録処理のコントローラークラス
 *
 * @author 2014040 (a-miura)
 */

import java.util.logging.Logger;
import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.AccountAlreadyRegisteredException;
import jp.enpit.cloud.eventspiral.model.AccountModel;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

public class SignUpPromoterController{
	/**
	 * Loggerオブジェクト
	 */
	private Logger logger;
	/**
	 * Loggerフィールドにオブジェクトを設定する．
	 */
	public SignUpPromoterController(){
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * DWRより呼び出される興行主登録処理のコントローラーメソッド.
	 * <ol>
	 *   <li>formデータをバリデートする．</li>
	 *   <li>SessionクラスのgetCurrentAccount()を呼び出し、ユーザがログインしているか確認すると同時にユーザ情報（Accountオブジェクト）を取得する．</li>
	 *   <li>AccountクラスのisAdministrator()を呼び出し、ユーザIDが管理者であるかチェックする．</li>
	 *   <li>ユーザIDが管理者でない場合、「管理者アカウントでログインしてください」 というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
	 *   <li>AccountModelクラスのregisterPromoter()を呼び出し、アカウント情報をデータベースに登録する．</li>
	 *   <li><tt>TEMFatalException</tt>が発生した場合：
	 *     <ol>
	 *       <li>発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
	 *     </ol>
	 *   </li>
	 *   <li><tt>AccountAlreadyRegisteredException</tt>が発生した場合：
	 *     <ol>
	 *       <li>発生した例外と「指定されたユーザIDは既に存在します」 というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
	 *     </ol>
	 *   </li>
	 *   <li><tt>NotLoggedInException</tt>が発生した場合：
	 *     <ol>
	 *       <li>発生した例外と「管理者アカウントでログインしてください」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
	 *     </ol>
	 *   </li>
	 * </ol>
	 *
	 * @param form 登録するアカウント情報
	 * @throws TEMViewException TEMFatalException, AccountAlreadyRegisteredException, NotLoggedInExceptionが発生した場合．
	 */
	public void execute (IdentifyingAccountForm form) throws TEMViewException {
		logger.info("SignUpPromoterController.execute");
		form.validate();

		try{
			Session session = new Session();
			Account account = session.getCurrentAccount();

			if ( ! account.isAdministrator() ) {
				throw new TEMViewException("管理者アカウントでログインしてください");
			}

			AccountModel accountModel = new AccountModel();
			accountModel.registerPromoter(form.getUserId(),  form.getPass());
		} catch (TEMFatalException e){
			throw new TEMViewException("システムエラーが発生しました．管理者に連絡して下さい", e);
		} catch (AccountAlreadyRegisteredException e){
			throw new TEMViewException("指定されたユーザIDは既に存在します", e);
		} catch (NotLoggedInException e) {
			throw new TEMViewException("管理者アカウントでログインしてください", e);
		}
	}
}
