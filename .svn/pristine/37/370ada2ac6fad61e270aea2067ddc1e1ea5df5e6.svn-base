package jp.enpit.cloud.eventspiral.model;

/**
 * UC:イベント一覧の表示 <br />
 * testGetFutureEvents01(): 正常系. <br />
 * testGetFutureEvents02(): 正常系. 登録イベントが0件の場合<br />
 * testGetFutureEvents03(): 正常系．startIndexが境界値<br />
 * testGetFutureEvents04(): 例外:startIndexが不正<br />
 * testGetFutureEvents05(): startIndexがイベント数より多い<br />
 * testGetFutureEvents06(): lengthが不正<br />
 * testGetFutureEvents07(): lengthがイベント数より多い<br />
 *
 * testGetFutureEventTotalCount01():正常系<br />
 * testGetFutureEventTotalCount02():正常系.登録イベント数が0件の場合<br />
 *
 * testRegisterEvents01(): 正常系. 未来のイベントを1つ登録 <br />
 * testRegisterEvents02(): 正常系. 未来のイベントが6つあるところに新たに未来のイベントを1つ登録 <br />
 *
 * testRegisterSeatCategory01(): 正常系: 存在するイベントに座席種別を1つ登録する
 * testRegisterSeatCategory02(): 異常系: 同じ座席種別の二重登録
 * testRegisterSeatCategory03(): 異常系: 存在しないイベントに座席種別を登録しようとする
 *
 * testGetEvents01(): 正常系. <br />
 * testGetEvents02(): 正常系. 登録イベントが0件の場合<br />
 * testGetEvents03(): 正常系．  startIndexが境界値<br />
 * testGetEvents04(): 例外: startIndexが不正<br />
 * testGetEvents05(): 例外: startIndexがイベント数より多い<br />
 * testGetEvents06(): 例外: lengthがイベント数より多い<br />
 * testGetEvents07(): 例外: 不正なlength
 * testGetEvents08(): 例外: 存在しないユーザーID
 *
 * testGetTotalCount01(): 正常系. イベント数取得成功 <br />
 * testGetTotalCount02(): 正常系. イベント一覧に登録されていないpromoterId (イベントが0件)<br />
 *
 * @author 2014016
 * @author 2014040 (a-miura)
 * @author 2014043
 *
 */

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.List;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;

import org.junit.Test;

public class EventModelTest {

	/**
	 * イベント取得成功 <br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *          startIndexが0, lengthが6
	 * 期待する結果: イベント取得件数が6件
	 */
	@Test
	public void testGetFutureEvents01() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件の内未来のイベントは6件

		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getFutureEvents(0, 6);

		// 件数の検査
		assertEquals(6, eventList.size());
	}

	/**
	 * 正常系:登録イベントが0件の場合 <br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDBN0()関数で初期化.登録イベント0件
	 *          startIndexが0, lengthが0
	 * 期待する結果: イベント取得件数が0件
	 */
	@Test
	public void testGetFutureEvents02() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDBN0(); //eventコレクションを初期化. 登録件数0件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getFutureEvents(0, 0);
		// 件数の検査
		assertEquals(0, eventList.size());
	}

	/**
	 * 正常系:startIndexがイベント数と同じ(境界値)<br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *          startIndexが5, lengthが1
	 * 期待する結果: イベント取得件数が1件
	 */
	@Test
	public void testGetFutureEvents03() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getFutureEvents(5, 1);
		// 件数の検査
		assertEquals(1, eventList.size());
	}

	/**
	 * 例外: 不正なstartIndex<br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *         startIndexが-1, lengthが2
	 * 期待する結果: {@link TEMFatalException}が発生
	 */
	@Test(expected = TEMFatalException.class)
	public void testGetFutureEvents04() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		eventModel.getFutureEvents(-1, 2);
		// TEMFatalExceptionが発生しなければ失敗
	}

	/**
	 * startIndexがイベント数より大きい<br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *          startIndexが6, lengthが3
	 * 期待する結果: イベント取得件数が0件
	 */
	@Test
	public void testGetFutureEvents05() throws  UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getFutureEvents(6, 3);
		assertEquals(0, eventList.size());
	}

	//Mongoの仕様が想定と違ったためコメントアウト(20140806大槻20144003)
	/**
	 * 例外: 不正なlength<br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *          startIndexが1, lengthが-2
	 * 期待する結果: {@link TEMFatalException}が発生
	 */

	//
	//@Test
	//public void testGetFutureEvents06()  throws  UnknownHostException, TEMFatalException{
	//    EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
	//    EventModel eventModel = new EventModel();
	//    List<Event> eventList = eventModel.getFutureEvents(1,-2);
	//    assertEquals(0, eventList.size());
	//    // TEMFatalExceptionが発生しなければ失敗
	//}

	/**
	 * lengthがイベント数より大きい<br />
	 * 対象: {@link EventModel#getFutureEvents(int, int)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 *          startIndexが0, lengthが7
	 * 期待する結果: イベント取得件数が6件
	 */
	@Test
	public void testGetFutureEvents07() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getFutureEvents(0, 7);
		assertEquals(6, eventList.size());
	}




	/**
	 * 正常系: イベント数取得成功 <br />
	 * 対象: {@link EventModel#getFutureEventTotalCount()} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, 現時点(2014/08/05)で未来のイベントは6件
	 * 期待する結果:eventが6件取得できる
	 */
	@Test
	public void testGetFutureEventTotalCount01() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		int event_num = eventModel.getFutureEventTotalCount();

		// 件数の検査
		assertEquals(6, event_num);
	}

	/**
	 * 正常系: イベント数0 <br />
	 * 対象: {@link EventModel#getFutureEventTotalCount()} <br />
	 * 条件: EventInitalizerのinitDBN0()関数で初期化.
	 * 期待する結果:eventが0件取得できる
	 * @throws Exception
	 */
	@Test
	public void testGetFutureEventTotalCount02() throws UnknownHostException, TEMFatalException{
		EventInitializer.initDBN0(); //eventコレクションを初期化．登録件数0件
		EventModel eventModel = new EventModel();
		int event_num = eventModel.getFutureEventTotalCount();

		// 件数の検査
		assertEquals(0, event_num);
	}

	/**
	 * 正常系: 未来のイベントを1つ登録 <br />
	 * 対象: {@link EventModel#registerEvent(String, Date, Date, String, String)} <br />
	 * 条件: EventInitializer.initDB0()で初期化し、
	 *       registerEvent("Event1", newDate(2014, 10, 1, 12, 0, 0),
	 *                     newDate(2014, 9, 1, 12, 0, 0)
	 *                     "desc1", "promoter2014")を呼び出す
	 * 期待する結果: getFutureEvents(0, 0)で取得したイベントリストには（現時点で）1つの要素があり、
	 *             その要素であるEventオブジェクトに対して、メソッド呼び出し結果が、
	 *             getEventName() == "Event1"
	 *             getEventDate() == EventInitializer.newDate(2014, 10, 1, 12, 0, 0)
	 *             getTicketStartDate() == EventInitializer.newDate(2014, 9, 1, 12, 0, 0)
	 *             getDescription() == "desc1"
	 *             getPromoterId() == "promoter2014"
	 *             が成立する
	 * @throws Exception
	 */
	@Test
	public void testRegisterEvent01() throws Exception {
		EventInitializer.initDBN0(); //eventコレクションを初期化．登録件数0件
		EventModel eventModel = new EventModel();
		eventModel.registerEvent("Event1", EventInitializer.newDate(2014, 10, 1, 12, 0, 0),
				EventInitializer.newDate(2014, 9, 1, 12, 0, 0),
				"desc1", "promoter2014");

		int eventNum = eventModel.getFutureEventTotalCount();
		// 件数の検査
		assertEquals(1, eventNum);
		List<Event> eventList = eventModel.getFutureEvents(0,  eventNum);
		Event event = eventList.get(0);
		assertEquals("Event1", event.getEventName());
		assertEquals(EventInitializer.newDate(2014, 10, 1, 12, 0, 0), event.getEventDate());
		assertEquals(EventInitializer.newDate(2014,  9, 1, 12, 0, 0), event.getTicketStartDate());
		assertEquals("desc1", event.getDescription());
		assertEquals("promoter2014", event.getPromoterId());
	}

	/**
	 * 正常系: 未来のイベントが6つあるところに新たに未来のイベントを1つ登録 <br />
	 * 対象: {@link EventModel#registerEvent(String, Date, Date, String, String)} <br />
	 * 条件: EventInitializer.initDB()で初期化し、(8件登録、うち6件が未来のイベント）
	 *       registerEvent("Event1", newDate(2014, 10, 1, 12, 0, 0),
	 *                     newDate(2014, 9, 1, 12, 0, 0)
	 *                     "desc1", "promoter2014")を呼び出す
	 * 期待する結果: 未来のイベント件数が7である
	 * @throws Exception
	 */
	@Test
	public void testRegisterEvent02() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件のうち、未来のイベントが6件
		EventModel eventModel = new EventModel();
		eventModel.registerEvent("Event1", EventInitializer.newDate(2014, 10, 1, 12, 0, 0),
				EventInitializer.newDate(2014, 9, 1, 12, 0, 0),
				"desc1", "promoter2014");

		int eventNum = eventModel.getFutureEventTotalCount();
		// 件数の検査
		assertEquals(7, eventNum);
	}

	/**
	 * 正常系: 存在するイベントに座席種別を1つ登録する <br />
	 * 対象: {@link EventModel#registerSeatCatgory
	 *             (String, String, int, int)} <br />
	 * 条件: EventInitializer.initDB()で初期化し、(8件登録、うち6件が未来のイベント）、
	 *       その中に"Cloud Spiral 2014"というイベントが含まれている
	 *       registerSeatCategory("Cloud Spiral 2014", "A", 100, 2000)を呼び出す
	 * 期待する結果: 正常に座席種別が登録され、その座席種別情報が返される
	 * @throws Exception
	 */
	@Test
	public void testRegisterSeatCategory01() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件のうち、未来のイベントが6件
		EventModel eventModel = new EventModel();
		SeatCategory category = eventModel.registerSeatCategory("000000000000000000000003", "A", 100, 2000);

		assertEquals("A", category.getSeatName());
		assertEquals(100, category.getCount());
		assertEquals(2000, category.getFee());
	}

	/**
	 * 異常系: 同じ座席種別の二重登録 <br />
	 * 対象: {@link EventModel#registerSeatCatgory
	 *             (String, String, int, int)} <br />
	 * 条件: EventInitializer.initDB()で初期化し、(8件登録、うち6件が未来のイベント）、
	 *       その中に"Cloud Spiral 2014"というイベントが含まれている
	 *       registerSeatCategory("Cloud Spiral 2014", "A", 100, 2000)を呼び出し、その後に、
	 *       registerSeatCategory("Cloud Spiral 2014", "A", 200, 3000)を呼び出す
	 * 期待する結果: {@link SeatCategoryAlreadyRegisteredException}が発生
	 * @throws Exception
	 */
	@Test(expected = SeatCategoryAlreadyRegisteredException.class)
	public void testRegisterSeatCategory02() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件のうち、未来のイベントが6件
		EventModel eventModel = new EventModel();
		eventModel.registerSeatCategory("000000000000000000000003", "A", 100, 2000);
		eventModel.registerSeatCategory("000000000000000000000003", "A", 200, 3000);
	}

	/**
	 * 異常系: 存在しないイベントに座席種別を登録しようとする <br />
	 * 対象: {@link EventModel#registerSeatCatgory
	 *             (String, String, int, int)} <br />
	 * 条件: EventInitializer.initDB0()で初期化し、(イベント件数が0になる）、
	 *       registerSeatCategory("Cloud Spiral 2014", "A", 100, 2000)を呼び出す
	 * 期待する結果: {@link EventNotFoundException}が発生
	 * @throws Exception
	 */
	@Test(expected = EventNotFoundException.class)
	public void testRegisterSeatCategory03() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数0件
		EventModel eventModel = new EventModel();
		eventModel.registerSeatCategory("Cloud Spiral 2014", "A", 100, 2000);
	}

	/**
	 * 正常系: イベント数取得成功 <br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *          startIndexが0, lengthが5, "promoter2014"
	 * 期待する結果: イベント取得件数が5件
	 */
	@Test
	public void testGetEvents01() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．promoterIdが "promoter2014" のイベントは5件

		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(0, 5, "promoter2014");

		// 件数の検査
		assertEquals(5, eventList.size());
		// 中身の検査
		assertEquals("000000000000000000000003", eventList.get(0).getEventId());
		assertEquals("000000000000000000000067", eventList.get(1).getEventId());
		assertEquals("000000000000000000000065", eventList.get(2).getEventId());
		assertEquals("000000000000000000000066", eventList.get(3).getEventId());
		assertEquals("000000000000000000000064", eventList.get(4).getEventId());
	}

	/**
	 * 正常系:登録イベントが0件の場合 <br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDBN0()関数で初期化.登録イベント0件
	 *          startIndexが0, lengthが0, promoterIdが "promoter2014"
	 * 期待する結果: イベント取得件数が0件
	 */
	@Test
	public void testGetEvents02() throws Exception {
		EventInitializer.initDBN0(); //eventコレクションを初期化. 登録件数0件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(0, 0, "promoter2014");
		// 件数の検査
		assertEquals(0, eventList.size());
	}

	/**
	 * 正常系:startIndexがイベント数と同じ(境界値)<br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *          startIndexが4, lengthが1, promoterIdが "promoter2014"
	 * 期待する結果: イベント取得件数が1件
	 */
	@Test
	public void testGetEvents03() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(4, 1, "promoter2014");
		// 件数の検査
		assertEquals(1, eventList.size());
	}

	/**
	 * 例外: 不正なstartIndex<br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *         startIndexが-1, lengthが2, promoterIdが "promoter2014"
	 * 期待する結果: {@link TEMFatalException}が発生
	 */
	@Test(expected = TEMFatalException.class)
	public void testGetEvents04() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		eventModel.getEvents(-1, 2, "promoter2014");
		// TEMFatalExceptionが発生しなければ失敗
	}

	/**
	 * 例外: startIndexがイベント数より大きい<br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *          startIndexが5, lengthが3, promoterIdが "promoter2014"
	 * 期待する結果: イベント取得件数が0件
	 */
	@Test
	public void testGetEvents05() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(5, 3, "promoter2014");
		assertEquals(0, eventList.size());
	}


	/**
	 * 例外: lengthがイベント数より大きい<br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *          startIndexが0, lengthが6, promoterIdが "promoter2014"
	 * 期待する結果: イベント取得件数が6件
	 */
	@Test
	public void testGetEvents06() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(0, 6, "promoter2014");
		assertEquals(5, eventList.size());
	}

	/**
	 * 例外: 不正なlength<br />
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *         startIndexが0, lengthが"2", promoterIdが "promoter2014"
	 * 期待する結果: {@link TEMFatalException}が発生
	 */
//	@Test(expected = TEMFatalException.class)
//	public void testGetEvents07() throws Exception {
//		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
//		EventModel eventModel = new EventModel();
//		eventModel.getEvents(0, -1, "promoter2014");
//		// TEMFatalExceptionが発生しなければ失敗
//	}

	/**
	 * 例外: 存在しないユーザーID
	 * 対象: {@link EventModel#getEvents(int, int, String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *         startIndexが0, lengthが10, promoterIdが "promoter2014"
	 * 期待する結果: getEventsメソッドが空のリストを返す
	 */
	@Test
	public void testGetEvents08() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．登録件数8件
		EventModel eventModel = new EventModel();
		List<Event> eventList = eventModel.getEvents(0, 10, "__unregistered__");
		assertEquals(0, eventList.size());
	}

	/**
	 * 正常系. イベント数取得成功 <br />
	 * 対象: {@link EventModel#getTotalCount(String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "promoter2014" のイベントは5件
	 *          getTotalCount("promoter2014")を呼び出す
	 * 期待する結果: イベント取得件数が5件
	 */
	@Test
	public void testTotalCount01() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．promoterIdが "promoter2014" のイベントは5件

		EventModel eventModel = new EventModel();
		int expected = 5;
		int actual = eventModel.getTotalCount("promoter2014");
		assertEquals(expected, actual);
	}

	/**
	 * 正常系. イベント一覧に登録されていないpromoterId (イベントが0件)<br />
	 * 対象: {@link EventModel#getTotalCount(String)} <br />
	 * 条件: EventInitalizerのinitDB()関数で初期化.
	 *          登録8件の内, promoterIdが "__unregistered__" のイベントは0件
	 *          getTotalCount("__unregistered__")を呼び出す
	 * 期待する結果: イベント取得件数が0件
	 */
	@Test
	public void testTotalCount02() throws Exception {
		EventInitializer.initDB(); //eventコレクションを初期化．promoterIdが "promoter2014" のイベントは5件

		EventModel eventModel = new EventModel();
		int expected = 0;
		int actual = eventModel.getTotalCount("__unregistered__");
		assertEquals(expected, actual);
	}

}
