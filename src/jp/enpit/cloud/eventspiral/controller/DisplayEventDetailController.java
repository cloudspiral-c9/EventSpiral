package jp.enpit.cloud.eventspiral.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.model.EventNotFoundException;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.model.SeatCategory;
import jp.enpit.cloud.eventspiral.model.TicketModel;
import jp.enpit.cloud.eventspiral.util.ConversionUtils;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.IdentifyingEventForm;
import jp.enpit.cloud.eventspiral.view.SeatCategoryEntity;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * DWRから呼び出されるイベント詳細情報表示処理のコントローラークラス
 *
 * @author s-egawa(2014001)
 */
public class DisplayEventDetailController {
	/**
     * Loggerオブジェクト
     */
    private Logger logger;


    /**
     * Loggerフィールドにオブジェクトを設定する．
     */
    public DisplayEventDetailController() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * DWRより呼び出されるイベント詳細表示のコントローラーメソッド.
     *
     * @param form IdentifyingEventFormオブジェクト
     * @return イベント詳細情報
     * @throws TEMViewException - TEMFatalException,EventNotFoundExceptionが発生した場合．
     * @throws NotLoggedInException
     */
    public EventDetailEntity execute(IdentifyingEventForm form) throws TEMViewException, NotLoggedInException {

        logger.info("DisplayEventDetailController.execute");

        //formデータをバリデートする．
        form.validate();

        try{
        	//EventModelクラスのgetEvent()を呼び出し、イベント情報を取得し，EventDetailEntityオブジェクトに設定する
            EventModel eventModel = new EventModel();
            EventDetailEntity entity = new EventDetailEntity();
            Event event = eventModel.getEvent(form.getEventId());
       	 	ConversionUtils.convert(entity, event);

            //TicketModelクラスのgetRemainSeatCategories()を呼び出し、座席種別ごとの残席数を取得する．
            TicketModel ticketModel = new TicketModel();
            java.util.List<SeatCategory> remainSeatList = ticketModel.getRemainSeatCategories(event);

            //残席数をSeatCategoryEntityオブジェクトに設定し，EventDetailEntityオブジェクトに登録する．
            java.util.List<SeatCategoryEntity> remainSeatEntityList = new ArrayList<SeatCategoryEntity>();
            for (SeatCategory remainSeat: remainSeatList){
            	SeatCategoryEntity remainSeatEntity = new SeatCategoryEntity();
            	ConversionUtils.convert(remainSeatEntity, remainSeat);
            	remainSeatEntityList.add(remainSeatEntity);
            }
            entity.setRemainSeats(remainSeatEntityList);

            //SessionクラスのgetCurrentAccount()を呼び出し、ユーザがログインしているか確認すると同時にユーザ情報（Accountオブジェクト）を取得する．
            Session session = new Session();
            Account account = new Account();
            try{
            	account = session.getCurrentAccount();
            }catch(NotLoggedInException e){// ここでNotLoggedInExceptionが発生した場合、EventDetailEntityオブジェクトをvalidate後にreturnして終了．
            	entity.validate();
            	return entity;
            }

            //TicketModelクラスのgetBoughtSeatCategories()を呼び出し、座席種別ごとのチケット購入数を取得する．
            java.util.List<SeatCategory> boughtSeatList = ticketModel.getBoughtSeatCategories(event, account.getUserId());

            //チケット購入数をEventDetailEntityオブジェクトに設定する．
            java.util.List<SeatCategoryEntity> boughtSeatEntityList = new ArrayList<SeatCategoryEntity>();
            for (SeatCategory boughtSeat: boughtSeatList){
            	SeatCategoryEntity boughtSeatEntity = new SeatCategoryEntity();
            	ConversionUtils.convert(boughtSeatEntity, boughtSeat);
            	boughtSeatEntityList.add(boughtSeatEntity);
            }
            entity.setBoughtSeats(boughtSeatEntityList);

            //EventDetailEntityオブジェクトをバリデートする．
            entity.validate();

            //EventDetailEntityオブジェクト(イベント詳細情報)を返す．
            return entity;

        } catch (TEMFatalException e){
            String message = "システムエラーが発生しました。管理者に連絡してください。";
            logger.severe(message);
            throw new TEMViewException(message, e);
        } catch (EventNotFoundException e) {
            String message = "指定されたイベントが存在しません";
            logger.severe(message);
            throw new TEMViewException(message, e);
        }

    }

}
