package jp.enpit.cloud.eventspiral.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.model.EventNotFoundException;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.model.OutOfDateException;
import jp.enpit.cloud.eventspiral.model.SeatCategoryNotFoundException;
import jp.enpit.cloud.eventspiral.model.TicketModel;
import jp.enpit.cloud.eventspiral.model.TicketNotFoundException;
import jp.enpit.cloud.eventspiral.model.TicketSoldOutException;
import jp.enpit.cloud.eventspiral.util.ConversionUtils;
import jp.enpit.cloud.eventspiral.view.BuyingTicketForm;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 *
 * DWRから呼び出されるチケット購入処理のコントローラークラス
 *
 * @author 2014003
 *
 */
public class BuyTicketController {

	/**
     * Loggerオブジェクト
     */
    private Logger logger;


    /**
     * Loggerフィールドにオブジェクトを設定する．
     */
    public BuyTicketController() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * DWRより呼び出されるチケット購入処理のコントローラーメソッド.
     * <ol>
     *  <li>formデータをバリデートする．</li>
     *  <li>EventModelクラスのgetEvent()を呼び出し，Eventが存在するか確認する</li>
     *  <li>SessionクラスのgetCurrentAccount()を呼び出し、ユーザがログインしているか確認すると同時にユーザ情報（Accountオブジェクト）を取得する．</li>
     *  <li>TicketModelクラスのbuyTickets()を呼び出し、チケット購入処理を行う．</li>
     *  <li>TEMFatalExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *              発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>NotLoggedInExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *              発生した例外と「ログインしてください」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>TicketSoldOutException TicketNotFoundExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「残席数が足りません」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>OutOfDateExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「まだ販売が開始されていません」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>SeatCategoryNotFoundExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「座席種別名が正しくありません」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>EventNotFoundExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「指定されたイベントが存在しません」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  </ol>
     * @param form BuyingTicketFormオブジェクト
     * @throws TEMViewException - EventNotFoundException,SeatCategoryNotFoundException,OutOfDateException,TicketSoldOutException,TicketNotFoundException,NotLoggedInException,TEMFatalExceptionが発生した場合．
     */
    public void execute(BuyingTicketForm form) throws TEMViewException {

        logger.info("BuyTicketController.execute");

        //formデータをバリデートする．
        form.validate();

        try{
        	//EventModelクラスのgetEvent()を呼び出し、イベント情報を取得し，EventDetailEntityオブジェクトに設定する
            EventModel eventModel = new EventModel();
            EventDetailEntity entity = new EventDetailEntity();
            Event event = eventModel.getEvent(form.getEventId());
       	 	ConversionUtils.convert(entity, event);

       	 	//SessionクラスのgetCurrentAccount()を呼び出し、ユーザがログインしているか確認すると同時にユーザ情報（Accountオブジェクト）を取得する．
       	 	Session session = new Session();
       	 	Account account = session.getCurrentAccount();

            //TicketModelクラスのbuyTickets()を呼び出し、チケット購入処理を行う．
            TicketModel ticketModel = new TicketModel();
            ticketModel.buyTickets(form.getEventId(), form.getSeatName(), account.getUserId(), form.getCount());

        } catch (TEMFatalException e){
            String message = "システムエラーが発生しました。管理者に連絡してください。";
            logger.severe(message);
            throw new TEMViewException(message, e);
        } catch (NotLoggedInException e) {
        	String message = "ログインしてください";
            logger.severe(message);
            throw new TEMViewException(message, e);
		} catch (TicketSoldOutException e) {
			String message = "残席数が足りません";
            logger.severe(message);
            throw new TEMViewException(message, e);
		} catch (TicketNotFoundException e) {
			String message = "残席数が足りません";
            logger.severe(message);
            throw new TEMViewException(message, e);
		} catch (OutOfDateException e) {
			String message = "まだ販売が開始されていません";
            logger.severe(message);
            throw new TEMViewException(message, e);
		} catch (SeatCategoryNotFoundException e) {
			String message = "座席種別名が正しくありません";
            logger.severe(message);
            throw new TEMViewException(message, e);
		} catch (EventNotFoundException e) {
            String message = "指定されたイベントが存在しません";
            logger.severe(message);
            throw new TEMViewException(message, e);
        }
    }

}
