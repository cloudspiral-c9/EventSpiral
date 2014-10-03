package jp.enpit.cloud.eventspiral.model;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializer;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializerUCBuyTicket;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * UC:イベントの座席種別情報を登録する <br />
 * testGenerateTickets01(): 正常系. <br />
 * testGenerateTickets02(): 正常系. <br />
 * testGenerateTickets03(): 正常系. <br />
 *
 * testGetRemainSeatCategories01(): 正常系: イベント1件，座席種別1件，チケット数100，残席数0
 * testGetRemainSeatCategories02(): 正常系: イベント1件，座席種別1件，チケット数100，残席数50
 * testGetRemainSeatCategories03(): 正常系: イベント1件，座席種別1件，チケット数100，残席数100
 * testGetRemainSeatCategories04(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ0
 * testGetRemainSeatCategories05(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ50
 * testGetRemainSeatCategories06(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ100
 * testGetRemainSeatCategories07(): 正常系: イベント1件，座席種別0件
 *
 * testGetBoughtSeatCategories01(): 正常系: イベント1件，座席種別1件，チケット数100，購入数0
 * testGetBoughtSeatCategories02(): 正常系: イベント1件，座席種別1件，チケット数100，購入数50
 * testGetBoughtSeatCategories03(): 正常系: イベント1件，座席種別1件，チケット数100，購入数100
 * testGetBoughtSeatCategories04(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ0
 * testGetBoughtSeatCategories05(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ0
 * testGetBoughtSeatCategories06(): 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ0
 * testGetBoughtSeatCategories07(): 正常系: イベント1件，座席種別0件
 *
 * UC:チケットを購入する<br />
 * testBuyTickets01():正常系. <br />
 * testBuyTickets02():例外：チケットが存在しない. <br />
 * testBuyTickets03():例外：購入する座席種別が存在しない. <br />
 * testBuyTickets04():例外：購入枚数が不足する. <br />
 * testBuyTickets05():例外：購入チケットの発売日が未来である場合. <br />
 *
 * @author 2014001(s-egawa)
 * @author 2014040 (a-miura)
 * @author 2014024(y-nishi)
 * @author 2014001(s-egawa)
 *
 */

public class TicketModelTest {

	/**
     * 正常系: チケット1件登録<br />
     * 対象: {@link TicketModel#generateTickets(Event, SeatCategory)} <br />
     * 条件: {@link TicketInitializer#initDBN0} でDBを初期化済み．
     * 期待する結果: データベースに登録されたチケットの件数が1
	 * @throws UnknownHostException
	 * @throws TEMFatalException
     */
	@Test
	public void testGenerateTickets01() throws UnknownHostException, TEMFatalException {
		TicketInitializer.initDBN0();
		TicketModel ticketModel = new TicketModel();

		//イベント情報と座席数を設定しチケットを作成
		Event event = new Event();
		event.setEventId("0001");
		event.setEventName("event1");
		event.setEventDate(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0));
		event.setTicketStartDate(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0));

		SeatCategory seat = new SeatCategory();
		seat.setCount(1); //座席数1 = チケット数1
		seat.setFee(1000);
		seat.setSeatName("A");

		ticketModel.generateTickets(event, seat);

		//DBを確認し正しくチケットが登録されているか調べる
		MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("ticket");
		DBObject query = new BasicDBObject();
		query.put("eventId", "0001");
		DBCursor result = coll.find(query);
		assertEquals(1,result.count()); //1件だけ登録されているはず

		//チケットの情報を確認
		for(DBObject dbo : result){
			assertEquals("event1", dbo.get("eventName"));
			assertEquals(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0), dbo.get("eventDate"));
			assertEquals(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0), dbo.get("ticketStartDate"));
			assertEquals(1000, dbo.get("fee"));
			assertEquals("A", dbo.get("seatName"));
			assertEquals("blank", dbo.get("status"));
		}
	}

	/**
     * 正常系: チケット5件登録<br />
     * 対象: {@link TicketModel#generateTickets(Event, SeatCategory)} <br />
     * 条件: {@link TicketInitializer#initDBN0} でDBを初期化済み．
     * 期待する結果: データベースに登録されたチケットの件数が5
	 * @throws UnknownHostException
	 * @throws TEMFatalException
     */
	@Test
	public void testGenerateTickets02() throws UnknownHostException, TEMFatalException {
		TicketInitializer.initDBN0();
		TicketModel ticketModel = new TicketModel();

		//イベント情報と座席数を設定しチケットを作成
		Event event = new Event();
		event.setEventId("0001");
		event.setEventName("event1");
		event.setEventDate(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0));
		event.setTicketStartDate(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0));

		SeatCategory seat = new SeatCategory();
		seat.setCount(5);
		seat.setFee(1000);
		seat.setSeatName("A");

		ticketModel.generateTickets(event, seat);

		//DBを確認し正しくチケットが登録されているか調べる
		MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("ticket");
		DBObject query = new BasicDBObject();
		query.put("eventId", "0001");
		DBCursor result = coll.find(query);
		assertEquals(5,result.count());

		//チケットの情報を確認
		for(DBObject dbo : result){
			assertEquals("event1", dbo.get("eventName"));
			assertEquals(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0), dbo.get("eventDate"));
			assertEquals(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0), dbo.get("ticketStartDate"));
			assertEquals(1000, dbo.get("fee"));
			assertEquals("A", dbo.get("seatName"));
			assertEquals("blank", dbo.get("status"));
		}
	}

	/**
     * 正常系: チケット10件登録<br />
     * 対象: {@link TicketModel#generateTickets(Event, SeatCategory)} <br />
     * 条件: {@link TicketInitializer#initDBN0} でDBを初期化済み．
     * 期待する結果: データベースに登録されたチケットの件数が5
	 * @throws UnknownHostException
	 * @throws TEMFatalException
     */
	@Test
	public void testGenerateTickets03() throws UnknownHostException, TEMFatalException {
		TicketInitializer.initDBN0();
		TicketModel ticketModel = new TicketModel();

		//イベント情報と座席数を設定しチケットを作成
		Event event = new Event();
		event.setEventId("0001");
		event.setEventName("event1");
		event.setEventDate(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0));
		event.setTicketStartDate(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0));

		SeatCategory seat = new SeatCategory();
		seat.setCount(10);
		seat.setFee(1000);
		seat.setSeatName("A");

		ticketModel.generateTickets(event, seat);

		//DBを確認し正しくチケットが登録されているか調べる
		MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("ticket");
		DBObject query = new BasicDBObject();
		query.put("eventId", "0001");
		DBCursor result = coll.find(query);
		assertEquals(10,result.count());

		//チケットの情報を確認
		for(DBObject dbo : result){
			assertEquals("event1", dbo.get("eventName"));
			assertEquals(TicketInitializer.newDate(2014, 10, 1, 12, 0, 0), dbo.get("eventDate"));
			assertEquals(TicketInitializer.newDate(2014, 9, 1, 12, 0, 0), dbo.get("ticketStartDate"));
			assertEquals(1000, dbo.get("fee"));
			assertEquals("A", dbo.get("seatName"));
			assertEquals("blank", dbo.get("status"));
		}
	}


	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，残席数0<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}でチケットを100枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、残席数は0となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories01() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);

		//チケットを100枚購入
		ticketModel.buyTickets(event.getEventId(), category.getSeatName(), "owner1", 100);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，残席数50<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}でチケットを50枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、残席数は50となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories02() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);

		//チケットを50枚購入
		ticketModel.buyTickets(event.getEventId(), category.getSeatName(), "owner1", 50);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，残席数100<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、残席数は100となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories03() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);
		//チケットは購入しない

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数は100
		assertEquals(2000, remain.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ0<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}で5種類の座席についてチケットを100枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，残席数はそれぞれ0となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories04() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);

		//チケットを100枚ずつ購入
		ticketModel.buyTickets(event.getEventId(), category1.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category2.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category3.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category4.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category5.getSeatName(), "owner1", 100);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());
		//座席種別2
		remain = list.get(1);
		assertEquals("B", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());
		//座席種別3
		remain = list.get(2);
		assertEquals("C", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());
		//座席種別4
		remain = list.get(3);
		assertEquals("D", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());
		//座席種別5
		remain = list.get(4);
		assertEquals("E", remain.getSeatName());
		assertEquals(0, remain.getCount()); //残席数は0
		assertEquals(2000, remain.getFee());

	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ50<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}で5種類の座席についてチケットを50枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，残席数はそれぞれ50となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories05() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);

		//チケットを50枚ずつ購入
		ticketModel.buyTickets(event.getEventId(), category1.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category2.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category3.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category4.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category5.getSeatName(), "owner1", 50);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());
		//座席種別2
		remain = list.get(1);
		assertEquals("B", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());
		//座席種別3
		remain = list.get(2);
		assertEquals("C", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());
		//座席種別4
		remain = list.get(3);
		assertEquals("D", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());
		//座席種別5
		remain = list.get(4);
		assertEquals("E", remain.getSeatName());
		assertEquals(50, remain.getCount()); //残席数は50
		assertEquals(2000, remain.getFee());

	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，残席数それぞれ100<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，残席数はそれぞれ100となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories06() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);
		//チケットは購入しない

		//ここからテスト
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory remain = list.get(0);
		assertEquals("A", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数100
		assertEquals(2000, remain.getFee());
		//座席種別2
		remain = list.get(1);
		assertEquals("B", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数100
		assertEquals(2000, remain.getFee());
		//座席種別3
		remain = list.get(2);
		assertEquals("C", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数100
		assertEquals(2000, remain.getFee());
		//座席種別4
		remain = list.get(3);
		assertEquals("D", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数100
		assertEquals(2000, remain.getFee());
		//座席種別5
		remain = list.get(4);
		assertEquals("E", remain.getSeatName());
		assertEquals(100, remain.getCount()); //残席数100
		assertEquals(2000, remain.getFee());

	}

	/**
     * 正常系: イベント1件、座席種別0件<br />
     * 対象: {@link TicketModel#getRemainSeatCategory(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加しするが、座席情報を登録していない状態
     * 期待する結果: getRemainSeatCategoriesメソッドが空のリストを返す
	 * @throws TEMFatalException
     */
	@Test
	public void testGetRemainSeatCategories07() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");

		//eventオブジェクトに座席情報（空のリスト）を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		event.setTotalSeats(seatCategoryList);

		TicketModel ticketModel = new TicketModel();
		List<SeatCategory> list = ticketModel.getRemainSeatCategories(event);
		assertEquals(0, list.size());
	}

	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，購入数100<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}でチケットを100枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、購入数は100となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories01() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);

		//チケットを100枚購入
		ticketModel.buyTickets(event.getEventId(), category.getSeatName(), "owner1", 100);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，購入数50<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}でチケットを50枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、購入数は50となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories02() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);

		//チケットを50枚購入
		ticketModel.buyTickets(event.getEventId(), category.getSeatName(), "owner1", 50);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別1件，チケット数100，購入数0<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を1つ追加し、チケットを生成する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは1、購入数は0となる
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories03() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");
		//座席情報の登録
		SeatCategory category = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category);
		//チケットは購入しない

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(1, list.size()); //座席の種類は1つ
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数は0
		assertEquals(2000, bought.getFee());
	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ100<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}で5種類の座席についてチケットを100枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，購入数はそれぞれ100となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories04() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);

		//チケットを100枚ずつ購入
		ticketModel.buyTickets(event.getEventId(), category1.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category2.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category3.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category4.getSeatName(), "owner1", 100);
		ticketModel.buyTickets(event.getEventId(), category5.getSeatName(), "owner1", 100);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());
		//座席種別2
		bought = list.get(1);
		assertEquals("B", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());
		//座席種別3
		bought = list.get(2);
		assertEquals("C", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());
		//座席種別4
		bought = list.get(3);
		assertEquals("D", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());
		//座席種別5
		bought = list.get(4);
		assertEquals("E", bought.getSeatName());
		assertEquals(100, bought.getCount()); //購入数は100
		assertEquals(2000, bought.getFee());

	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ50<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     *      {@link TicketModel#buyTickets(String, String, String, int)}で5種類の座席についてチケットを50枚購入する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，購入数はそれぞれ50となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories05() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(-1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);

		//チケットを50枚ずつ購入
		ticketModel.buyTickets(event.getEventId(), category1.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category2.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category3.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category4.getSeatName(), "owner1", 50);
		ticketModel.buyTickets(event.getEventId(), category5.getSeatName(), "owner1", 50);

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());
		//座席種別2
		bought = list.get(1);
		assertEquals("B", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());
		//座席種別3
		bought = list.get(2);
		assertEquals("C", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());
		//座席種別4
		bought = list.get(3);
		assertEquals("D", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());
		//座席種別5
		bought = list.get(4);
		assertEquals("E", bought.getSeatName());
		assertEquals(50, bought.getCount()); //購入数は50
		assertEquals(2000, bought.getFee());

	}

	/**
     * 正常系: イベント1件，座席種別5件，チケット数それぞれ100，購入数それぞれ0<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加し、そのイベントIDで座席情報を5つ追加し、チケットを生成する
     * 期待する結果: getRemainSeatCategoriesメソッドがSeatCategoryのリストを返し、
     *            サイズは5，購入数はそれぞれ0となる．
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories06() throws Exception {
		//データベース初期化
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		//イベント情報の登録
		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");

		//座席情報の登録
		SeatCategory category1 = eventModel.registerSeatCategory(event.getEventId(), "A", 100, 2000);
		SeatCategory category2 = eventModel.registerSeatCategory(event.getEventId(), "B", 100, 2000);
		SeatCategory category3 = eventModel.registerSeatCategory(event.getEventId(), "C", 100, 2000);
		SeatCategory category4 = eventModel.registerSeatCategory(event.getEventId(), "D", 100, 2000);
		SeatCategory category5 = eventModel.registerSeatCategory(event.getEventId(), "E", 100, 2000);

		//eventオブジェクトに座席情報を設定
		List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		seatCategoryList.add(category1);
		seatCategoryList.add(category2);
		seatCategoryList.add(category3);
		seatCategoryList.add(category4);
		seatCategoryList.add(category5);
		event.setTotalSeats(seatCategoryList);

		//チケット情報の登録
		TicketModel ticketModel = new TicketModel();
		ticketModel.generateTickets(event,  category1);
		ticketModel.generateTickets(event,  category2);
		ticketModel.generateTickets(event,  category3);
		ticketModel.generateTickets(event,  category4);
		ticketModel.generateTickets(event,  category5);
		//チケットは購入しない

		//ここからテスト
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(5, list.size()); //座席の種類は5つ
		//座席種別1
		SeatCategory bought = list.get(0);
		assertEquals("A", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数0
		assertEquals(2000, bought.getFee());
		//座席種別2
		bought = list.get(1);
		assertEquals("B", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数0
		assertEquals(2000, bought.getFee());
		//座席種別3
		bought = list.get(2);
		assertEquals("C", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数0
		assertEquals(2000, bought.getFee());
		//座席種別4
		bought = list.get(3);
		assertEquals("D", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数0
		assertEquals(2000, bought.getFee());
		//座席種別5
		bought = list.get(4);
		assertEquals("E", bought.getSeatName());
		assertEquals(0, bought.getCount()); //購入数0
		assertEquals(2000, bought.getFee());

	}

	/**
     * 正常系: イベント1件、座席種別0件<br />
     * 対象: {@link TicketModel#getBoughtSeatCategories(Event)} <br />
     * 条件: アカウント情報、イベント情報、座席種別情報を初期化
     *      興行主アカウントでイベントを1つ追加しするが、座席情報を登録していない状態
     * 期待する結果: getRemainSeatCategoriesメソッドが空のリストを返す
	 * @throws TEMFatalException
     */
	@Test
	public void testGetBoughtSeatCategories07() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializer.initDBN0();

		EventModel eventModel = new EventModel();
		Event event = eventModel.registerEvent("event1", TicketInitializer.dayAfter(2),
				                               TicketInitializer.dayAfter(1),
				                               "desc1", "promoter1");

		//eventオブジェクトに座席情報（空のリスト）を設定
		//List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
		//event.setTotalSeats(seatCategoryList);

		TicketModel ticketModel = new TicketModel();
		List<SeatCategory> list = ticketModel.getBoughtSeatCategories(event, "owner1");
		assertEquals(0, list.size());
	}

	/**
     * 正常系<br />
     * 対象: {@link TicketModel#buyTickets(java.lang.String eventId, java.lang.String seatName,
			java.lang.String owner, int count)} <br />
     * 条件: {@link TicketInitializerUCBuyTicket#initDBForbuyTicketsTestUCBuyTickets} でDBを初期化済み．
     * 期待する結果: 購入したチケットのステータスが"reserved"になる
	 * @throws TEMFatalException
     */
	@Test
	public void testBuyTickets01() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();

		TicketModel ticketModel = new TicketModel();
		ticketModel.buyTickets("000000000000000000000001", "S", "user01", 1);
		MongoClient mongo = new MongoClient("localhost:27017");
		DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("ticket");
		DBObject query = new BasicDBObject();
		query.put("status", "reserved");
		int reservedlist = coll.find(query).count();
		assertEquals(1,reservedlist);
	}

	/**
	 * 例外: チケットが存在しない場合<br />
	 * 対象: {@link TicketModel#buyTickets(java.lang.String eventId, java.lang.String seatName,
			java.lang.String owner, int count)} <br />
	 * 条件: {@link TicketInitializerUCBuyTicket#initDBForbuyTicketsTestUCBuyTickets} でDBを初期化済み．
	 * 期待する結果: {@link TicketNotFoundException}が発生
	 */
	@Test (expected = TicketNotFoundException.class)
	public void testBuyTickets02() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();

		TicketModel ticketModel = new TicketModel();
		ticketModel.buyTickets("00000000d0000000000000004", "S", "user01", 1);

	}

	/**
	 * 例外: 購入する座席種別が存在しない場合<br />
	 * 対象: {@link TicketModel#buyTickets(java.lang.String eventId, java.lang.String seatName,
			java.lang.String owner, int count)} <br />
	 * 条件: {@link TicketInitializerUCBuyTicket#initDBForbuyTicketsTestUCBuyTickets} でDBを初期化済み．
	 * 期待する結果: {@link SeatCategoryNotFoundException}が発生
	 */
	@Test (expected = SeatCategoryNotFoundException.class)
	public void testBuyTickets03() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();


		TicketModel ticketModel = new TicketModel();
		ticketModel.buyTickets("000000000000000000000001", "B", "user01", 1);

	}

	/**
	 * 例外: 購入枚数が不足する場合<br />
	 * 対象: {@link TicketModel#buyTickets(java.lang.String eventId, java.lang.String seatName,
			java.lang.String owner, int count)} <br />
	 * 条件: {@link TicketInitializerUCBuyTicket#initDBForbuyTicketsTestUCBuyTickets} でDBを初期化済み．
	 * 期待する結果: {@link TicketSoldOutException}が発生
	 */
	@Test (expected = TicketSoldOutException.class)
	public void testBuyTickets04() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();


		TicketModel ticketModel = new TicketModel();
		ticketModel.buyTickets("000000000000000000000001", "S", "user01", 3);
	}
	/**
	 * 例外: 購入チケットの発売日が未来である場合<br />
	 * 対象: {@link TicketModel#buyTickets(java.lang.String eventId, java.lang.String seatName,
			java.lang.String owner, int count)} <br />
	 * 条件: {@link TicketInitializerUCBuyTicket#initDBForbuyTicketsTestUCBuyTickets} でDBを初期化済み．
	 * 期待する結果: {@link OutOfDateException}が発生
	 */
	@Test (expected = OutOfDateException.class)
	public void testBuyTickets05() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
		TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();


		TicketModel ticketModel = new TicketModel();
		ticketModel.buyTickets("000000000000000000000002", "A", "user01", 3);

	}
}
