package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;



import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializer;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.IdentifyingEventForm;
import jp.enpit.cloud.eventspiral.view.TEMValidationException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UC: イベント情報の詳細を表示する．
 *
 * @author 2014003
 *
 */
public class DisplayEventDetailControllerTest {

	@BeforeClass
	public static void setUpClass() {
		try {
			EventInitializer.initDBN0();
			AccountInitializer.initDBN0();
		} catch (Exception e) {
			// 何もしません。
		}
	}

	@After
	public void tearDown() {
		try {
			EventInitializer.initDBN0();
			AccountInitializer.initDBN0();

			LogoutController logoutController = new LogoutController();
			logoutController.execute();
		} catch (Exception e) {
			// 何もしません。
		}
	}

	/**
	 * 正常系:イベント情報あり <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB()},{@link EventInitializer#initDBForDisplayEventDetailControllerFortestExecute01()},{@link TicketInitializer#initDBForDisplayEventDetailControllerFortestExecute01()} でDBを初期化済み．
	 * 期待する結果: 戻り値である{@link EventDetailEntity}型のオブジェクトが初期化したもの等しい <br/>
	 */
	@Test
	public void testExecute01() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayEventDetailControllerFortestExecute01();
		TicketInitializer.initDBForDisplayEventDetailControllerFortestExecute01();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);


		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("eveId");

		EventDetailEntity eventDetailEntity = displayEventDetailController.execute(identifyingEventForm);

		assertEquals(1, eventDetailEntity.getBoughtSeats().get(0).getCount());
		assertEquals(2, eventDetailEntity.getBoughtSeats().get(1).getCount());
		assertEquals(0, eventDetailEntity.getBoughtSeats().get(2).getCount());//購入していないのでcountは0

		assertEquals(0, eventDetailEntity.getBoughtSeats().get(0).getFee());
		assertEquals(1000, eventDetailEntity.getBoughtSeats().get(1).getFee());
		assertEquals(2000, eventDetailEntity.getBoughtSeats().get(2).getFee());

		assertEquals("seatName0", eventDetailEntity.getBoughtSeats().get(0).getSeatName());
		assertEquals("seatName1", eventDetailEntity.getBoughtSeats().get(1).getSeatName());
		assertEquals("seatName2", eventDetailEntity.getBoughtSeats().get(2).getSeatName());

		try {
			eventDetailEntity.getBoughtSeats().get(3);
			assertFalse(true);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

		assertEquals("desc", eventDetailEntity.getDescription());
		assertEquals(EventInitializer.newDate(3999, 11, 11, 11, 11, 11), eventDetailEntity.getEventDate());
		assertEquals("eveId", eventDetailEntity.getEventId());
		assertEquals("eveName", eventDetailEntity.getEventName());
		assertEquals("promoter1", eventDetailEntity.getPromoterId());

		assertEquals(0, eventDetailEntity.getRemainSeats().get(0).getCount());//購入しているのでcountは0
		assertEquals(0, eventDetailEntity.getRemainSeats().get(0).getFee());
		assertEquals("seatName0", eventDetailEntity.getRemainSeats().get(0).getSeatName());

		assertEquals(0, eventDetailEntity.getRemainSeats().get(1).getCount());//購入しているのでcountは0
		assertEquals(1000, eventDetailEntity.getRemainSeats().get(1).getFee());
		assertEquals("seatName1", eventDetailEntity.getRemainSeats().get(1).getSeatName());

		assertEquals(3, eventDetailEntity.getRemainSeats().get(2).getCount());
		assertEquals(2000, eventDetailEntity.getRemainSeats().get(2).getFee());
		assertEquals("seatName2", eventDetailEntity.getRemainSeats().get(2).getSeatName());

		try {
			eventDetailEntity.getRemainSeats().get(3);
			assertFalse(true);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

		assertEquals(EventInitializer.newDate(2999, 11, 11, 11, 11, 11), eventDetailEntity.getTicketStartDate());

		assertEquals(1, eventDetailEntity.getTotalSeats().get(0).getCount());
		assertEquals(0, eventDetailEntity.getTotalSeats().get(0).getFee());
		assertEquals("seatName0", eventDetailEntity.getTotalSeats().get(0).getSeatName());

		assertEquals(2, eventDetailEntity.getTotalSeats().get(1).getCount());
		assertEquals(1000, eventDetailEntity.getTotalSeats().get(1).getFee());
		assertEquals("seatName1", eventDetailEntity.getTotalSeats().get(1).getSeatName());

		assertEquals(3, eventDetailEntity.getTotalSeats().get(2).getCount());
		assertEquals(2000, eventDetailEntity.getTotalSeats().get(2).getFee());
		assertEquals("seatName2", eventDetailEntity.getTotalSeats().get(2).getSeatName());

		try {
			eventDetailEntity.getTotalSeats().get(3);
			assertFalse(true);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

	}

	/**
	 * 異常系:formのデータが正しくない。 (validateを行っているかの確認) <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生する。<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute02() throws Exception {

		AccountInitializer.initDB();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);


		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("");

		displayEventDetailController.execute(identifyingEventForm);
	}


	/**
	 * 正常系:未ログイン・イベント情報あり <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB()},{@link EventInitializer#initDBForDisplayEventDetailControllerFortestExecute01()},{@link TicketInitializer#initDBForDisplayEventDetailControllerFortestExecute01()} でDBを初期化済み．
	 * 期待する結果: 戻り値である{@link EventDetailEntity}型のオブジェクトが初期化したもの等しい <br/>
	 */
	@Test
	public void testExecute03() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayEventDetailControllerFortestExecute01();
		TicketInitializer.initDBForDisplayEventDetailControllerFortestExecute01();

		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("eveId");

		EventDetailEntity eventDetailEntity = displayEventDetailController.execute(identifyingEventForm);


		assertNull(eventDetailEntity.getBoughtSeats());


		assertEquals("desc", eventDetailEntity.getDescription());
		assertEquals(EventInitializer.newDate(3999, 11, 11, 11, 11, 11), eventDetailEntity.getEventDate());
		assertEquals("eveId", eventDetailEntity.getEventId());
		assertEquals("eveName", eventDetailEntity.getEventName());
		assertEquals("promoter1", eventDetailEntity.getPromoterId());

		assertEquals(0, eventDetailEntity.getRemainSeats().get(0).getCount());//購入しているのでcountは0
		assertEquals(0, eventDetailEntity.getRemainSeats().get(0).getFee());
		assertEquals("seatName0", eventDetailEntity.getRemainSeats().get(0).getSeatName());

		assertEquals(0, eventDetailEntity.getRemainSeats().get(1).getCount());//購入しているのでcountは0
		assertEquals(1000, eventDetailEntity.getRemainSeats().get(1).getFee());
		assertEquals("seatName1", eventDetailEntity.getRemainSeats().get(1).getSeatName());

		assertEquals(3, eventDetailEntity.getRemainSeats().get(2).getCount());
		assertEquals(2000, eventDetailEntity.getRemainSeats().get(2).getFee());
		assertEquals("seatName2", eventDetailEntity.getRemainSeats().get(2).getSeatName());

		try {
			eventDetailEntity.getRemainSeats().get(3);
			assertFalse(true);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

		assertEquals(EventInitializer.newDate(2999, 11, 11, 11, 11, 11), eventDetailEntity.getTicketStartDate());

		assertEquals(1, eventDetailEntity.getTotalSeats().get(0).getCount());
		assertEquals(0, eventDetailEntity.getTotalSeats().get(0).getFee());
		assertEquals("seatName0", eventDetailEntity.getTotalSeats().get(0).getSeatName());

		assertEquals(2, eventDetailEntity.getTotalSeats().get(1).getCount());
		assertEquals(1000, eventDetailEntity.getTotalSeats().get(1).getFee());
		assertEquals("seatName1", eventDetailEntity.getTotalSeats().get(1).getSeatName());

		assertEquals(3, eventDetailEntity.getTotalSeats().get(2).getCount());
		assertEquals(2000, eventDetailEntity.getTotalSeats().get(2).getFee());
		assertEquals("seatName2", eventDetailEntity.getTotalSeats().get(2).getSeatName());

		try {
			eventDetailEntity.getTotalSeats().get(3);
			assertFalse(true);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

	}

	/**
	 * 異常系:eventIdに対応するeventが存在しない。 <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB()},{@link EventInitializer#initDBForDisplayEventDetailControllerFortestExecute01()},{@link TicketInitializer#initDBForDisplayEventDetailControllerFortestExecute01()} でDBを初期化済み．
	 * 期待する結果: {@link TEMViewException}が発生する。<br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute04() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayEventDetailControllerFortestExecute01();
		TicketInitializer.initDBForDisplayEventDetailControllerFortestExecute01();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);


		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("__unregistered__");

		displayEventDetailController.execute(identifyingEventForm);
	}

	/**
	 * 異常系:Eventに誤ったデータが存在する。(ログイン時) <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB()},{@link EventInitializer#initDBForDisplayEventDetailControllerFortestExecute05()},{@link TicketInitializer#initDBForDisplayEventDetailControllerFortestExecute01()} でDBを初期化済み．
	 * 期待する結果: {@link TEMValidationException}が発生する。<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute05() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayEventDetailControllerFortestExecute05();
		TicketInitializer.initDBForDisplayEventDetailControllerFortestExecute01();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);


		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("eveId");

		displayEventDetailController.execute(identifyingEventForm);
	}

	/**
	 * 異常系:Eventに誤ったデータが存在する。(未ログイン時) <br/>
	 * 対象: {@link DisplayEventDetailController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB()},{@link EventInitializer#initDBForDisplayEventDetailControllerFortestExecute05()},{@link TicketInitializer#initDBForDisplayEventDetailControllerFortestExecute01()} でDBを初期化済み．
	 * 期待する結果: {@link TEMValidationException}が発生する。<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute06() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayEventDetailControllerFortestExecute05();
		TicketInitializer.initDBForDisplayEventDetailControllerFortestExecute01();

		DisplayEventDetailController displayEventDetailController = new DisplayEventDetailController();

		IdentifyingEventForm identifyingEventForm = new IdentifyingEventForm();
		identifyingEventForm.setEventId("eveId");

		displayEventDetailController.execute(identifyingEventForm);
	}

}
