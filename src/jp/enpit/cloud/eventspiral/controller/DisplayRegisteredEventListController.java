package jp.enpit.cloud.eventspiral.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.EventDetailSearchResultEntity;
import jp.enpit.cloud.eventspiral.view.SearchingForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * DWRから呼び出される登録済イベント一覧表示処理のコントローラークラス
 *
 * @author 2014016
 *
 */
public class DisplayRegisteredEventListController {
    /**
     * Loggerオブジェクト
     */
    private Logger logger;


    /**
     * Loggerフィールドにオブジェクトを設定する．
     */
    public DisplayRegisteredEventListController() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * DWRより呼び出されるイベント一覧表示のコントローラーメソッド.
     *
     * @param form リスト改ページ情報のためのフォームクラス．
     * @return イベント情報の一覧
     * @throws TEMViewException
     */
    public EventDetailSearchResultEntity execute(SearchingForm form) throws TEMViewException {

        logger.info("DisplayRegisteredEventListController.execute");
        form.validate();

        try{
            Session session = new Session();
            Account account = session.getCurrentAccount();

            if ( ! account.isPromoter() ) {
                throw new TEMViewException("興行主アカウントでログインしてください");
            }

            EventModel eventModel = new EventModel();
            int totalCount = eventModel.getTotalCount(account.getUserId());

            List<Event> eventLists = eventModel.getEvents(
                    form.getStartIndex(), form.getLength(), account.getUserId());

            List<EventDetailEntity> eventDetailEntities = new ArrayList<EventDetailEntity>(totalCount);

            Iterator<Event> iEvent = eventLists.iterator();

            while (iEvent.hasNext()) {
                Event eve = iEvent.next();
                EventDetailEntity ede = new EventDetailEntity();

                ede.setDescription(eve.getDescription());
                ede.setEventDate(eve.getEventDate());
                ede.setEventId(eve.getEventId());
                ede.setEventName(eve.getEventName());
                ede.setTicketStartDate(eve.getTicketStartDate());

                eventDetailEntities.add(ede);
            }

            EventDetailSearchResultEntity searchResult = new EventDetailSearchResultEntity();
            searchResult.setTotalCount(totalCount);
            searchResult.setList(eventDetailEntities);
            searchResult.validate();

            return searchResult;

        } catch (TEMFatalException e){
            String message = "システムエラーが発生しました。管理者に連絡してください。";
            logger.severe(message);
            throw new TEMViewException(message, e);
        } catch (NotLoggedInException e) {
            String message = "興行主アカウントでログインしてください";
            logger.severe(message);
            throw new TEMViewException(message, e);
        }

    }

}
