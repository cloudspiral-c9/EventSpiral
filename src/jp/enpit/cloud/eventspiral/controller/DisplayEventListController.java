package jp.enpit.cloud.eventspiral.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.EventDetailSearchResultEntity;
import jp.enpit.cloud.eventspiral.view.SearchingForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * DWRから呼び出されるイベント一覧表示処理のコントローラークラス
 *
 * @author 2014003
 */
public class DisplayEventListController {

	/**
	 * Loggerオブジェクト
	 */
	private Logger logger;

	/*
	 * Loggerフィールドにオブジェクトを設定する．
	 */
	public DisplayEventListController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/*
	 * DWRより呼び出されるイベント一覧表示のコントローラーメソッド.
	 * <ol>
	 * 	<li>formデータをバリデートする．（formオブジェクトのvalidate()を呼び出す）
	 * 	<li>EventModelクラスのgetFutureEventTotalCount()を呼び出し、未来のイベント情報の総数を取得し，int型変数に代入する．
	 * 	<li>EventModelクラスのgetFutureEvents()を呼び出し（引数はformオブジェクトのstartIndexとlength）、未来のイベント情報の一覧を取得する(List型の変数にgetFutureEventsの返り値を代入する)．
	 * 	<li>Listを初期化し，Listの各要素を代入するEventDetailSearchResultEntityクラスを生成し、イベント情報の一覧(List)と総数を登録する．
	 * 	<li>EventDetailSearchResultEntityオブジェクトをバリデートする．
	 * 	<li>EventDetailSearchResultEntityオブジェクト(イベント情報の一覧)を返す．
	 * 	<li>TEMFatalExceptionが発生した場合：
	 * 		<ol>
	 * 			<li>発生した例外と「システムエラーが発生しました．管理者に連絡してください」というメッセージをTEMViewExceptionにラップして投げる．
	 *
	 * @param form SearchingForm
	 *
	 * @return イベント情報の一覧
	 *
	 * @throws TEMViewException TEMFatalExceptionが発生した場合．
	 */
	public EventDetailSearchResultEntity execute(SearchingForm form)
			throws TEMViewException {

		logger.info("DisplayEventListController.execute");

		try {

			form.validate();

			EventModel eventModel = new EventModel();
			int futureEventTotalCount = eventModel.getFutureEventTotalCount();
			List<Event> futureEventsList = eventModel.getFutureEvents(
					form.getStartIndex(), form.getLength());

			List<EventDetailEntity> eventDetailEntities = new ArrayList<EventDetailEntity>(
					futureEventTotalCount);

			Iterator<Event> iEvent = futureEventsList.iterator();

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

			EventDetailSearchResultEntity eventDetailSearchResultEntity = new EventDetailSearchResultEntity();
			eventDetailSearchResultEntity.setList(eventDetailEntities);
			eventDetailSearchResultEntity.setTotalCount(futureEventTotalCount);
			eventDetailSearchResultEntity.validate();

			return eventDetailSearchResultEntity;

		} catch (TEMFatalException e) {
			String message = "システムエラーが発生しました。管理者に連絡してください。";
			logger.severe(message);
			throw new TEMViewException(message);
		}
	}
}
