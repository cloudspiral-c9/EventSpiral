package jp.enpit.cloud.eventspiral.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventAlreadyRegisteredException;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.model.OutOfDateException;
import jp.enpit.cloud.eventspiral.view.EventInfoEntity;
import jp.enpit.cloud.eventspiral.view.RegisteringEventForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * DWRから呼び出されるイベント登録処理のコントローラークラス
 *
 * @author 2014016
 *
 */
public class RegisterEventController {
    /**
     * Loggerオブジェクト
     */
    private Logger logger;

    /**
     * Loggerフィールドにオブジェクトを設定する．
     */
    public RegisterEventController() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * DWRより呼び出される新規イベント登録処理のコントローラーメソッド.
     * <ol>
     *  <li>eFormデータをバリデートする．</li>
     *  <li>SessionクラスのgetCurrentAccount()を呼び出し、ユーザがログインしているか確認すると同時にユーザ情報（Accountオブジェクト）を取得する．</li>
     *  <li>AccountクラスのisPromoter()を呼び出し、ユーザIDが興行主であるかチェックする．</li>
     *  <li>ユーザIDが興行主でない場合、「興行主アカウントでログインしてください」というメッセージをTEMViewExceptionにラップして投げる．</li>
     *  <li>EventModelクラスのregisterEvent()を呼び出し、データベースにイベント情報を登録する．</li>
     *  <li>EventInfoEntityクラスを生成し、イベント情報を登録する．</li>
     *  <li>EventInfoEntityオブジェクトをバリデートする．</li>
     *  <li>EventInfoEntityオブジェクト（登録したイベント情報）を返す．</li>
     *  <li>TEMFatalExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *              発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>NotLoggedInExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *              発生した例外と「興行主アカウントでログインしてください」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>EventAlreadyRegisteredExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「指定されたイベントは既に存在します」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     *  <li>OutOfDateExceptionが発生した場合：</li>
     *      <ol>
     *          <li>
     *          発生した例外と「イベント開催日時は未来の日時を入力してください」というメッセージをTEMViewExceptionにラップして投げる．
     *          </li>
     *      </ol>
     * @param eForm 登録するイベント情報
     * @return
     * @throws TEMViewException TEMFatalException
     */
    public EventInfoEntity execute(RegisteringEventForm eForm) throws TEMViewException{
        logger.info("RegisterEventController.execute");
        eForm.validate();

        try{
            Session session = new Session();
            Account account = session.getCurrentAccount();

            if(!account.isPromoter()) {
                throw new TEMViewException("興行主アカウントでログインしてください");
            }


            EventModel eventModel = new EventModel();
            Event event = eventModel.registerEvent(eForm.getEventName(),
                                                      eForm.getEventDate(),
                                                      eForm.getTicketStartDate(),
                                                      eForm.getDescription(),
                                                      account.getUserId());

            EventInfoEntity eventInfoEntity = new EventInfoEntity();
            eventInfoEntity.setEventId(event.getEventId());
            eventInfoEntity.setEventName(event.getEventName());
            eventInfoEntity.setEventDate(event.getEventDate());
            eventInfoEntity.setTicketStartDate(event.getTicketStartDate());
            eventInfoEntity.setDescription(event.getDescription());
            eventInfoEntity.setPromoterId(account.getUserId());

            eventInfoEntity.validate();

            return eventInfoEntity;

        } catch (TEMFatalException e){
            throw new TEMViewException("システムエラーが発生しました．管理者に連絡して下さい", e);
        } catch (NotLoggedInException e) {
            throw new TEMViewException("興行主アカウントでログインしてください", e);
        } catch (EventAlreadyRegisteredException e){
            throw new TEMViewException("指定されたイベントは既に存在します", e);
        } catch (OutOfDateException e) {
            throw new TEMViewException("イベント開催日時は未来の日時を入力してください", e);
        }
    }


}
