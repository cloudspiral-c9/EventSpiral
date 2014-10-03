package jp.enpit.cloud.eventspiral.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;
/**
 * DWRから呼び出されるログアウト処理のコントローラークラス
*/
public class LogoutController{
	/**
     * Loggerオブジェクト
	 */
	private Logger logger;
	/**
     * Loggerフィールドにオブジェクトを設定する．
	 */
	public LogoutController(){
		logger = Logger.getLogger(getClass().getName());
	}
	/**
     * DWRより呼び出されるログアウト処理のコントローラーメソッド.
	 * <ol>
     * <li>SessionクラスのdeleteSessionId()を呼び出し、現在のセッション情報を削除する。</li>
     * <li><tt>TEMFatalException</tt>が発生した場合：
     *   <ol>
     *     <li>発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージを<tt>TEMViewException</tt>にラップして投げる．</li>
     *   </ol>
     * </li>
	 * </ol>
	 * @throws TEMViewException TEMFatalExceptionが発生した場合．
	 */
	public void execute() throws TEMViewException {
		logger.info("LogoutController.execute");
		//validate();
		
		try{
			Session session = new Session();
			session.deleteSessionId();

			
			/*BooleanEntity b = new BooleanEntity(true);
			b.validate();
			return b;*/
		} catch (TEMFatalException e){
			throw new TEMViewException("システムエラーが発生しました．管理者に連絡してください", e);
		}
	}

}
