package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;


import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializer;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializerUCBuyTicket;
import jp.enpit.cloud.eventspiral.view.BuyingTicketForm;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMValidationException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;


import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class BuyTicketControllerTest {

	// アカウント情報、イベント情報を初期化する
		private void init() throws Exception {
			AccountInitializer.initDB();
			EventInitializer.initDBForIntegrationTestUCDisplayEventDetail();
			TicketInitializerUCBuyTicket.initDBForbuyTicketsTestUCBuyTickets();
		}

		// 一般ユーザーとしてログインさせる
		private void login() throws Exception {

			String userId = "user0";
			String pass   = "pass0";
			IdentifyingAccountForm account = new IdentifyingAccountForm();
			account.setUserId(userId);
			account.setPass(pass);
			LoginController loginController = new LoginController();
			loginController.execute(account);

		}

		// ログアウトさせる
		private void logout() throws Exception {
			LogoutController controller = new LogoutController();
			controller.execute();
		}

		/**
		 * 正常なチケット購入 <br/>
		 * 対象: {@link BuyTicketController#execute} <br/>
		 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
		 * 期待する結果: ステータスがblankのチケットの枚数が１減っている。 <br/>
		 */
		@Test
		public void testExecute01() throws Exception {
			init();
			login();
			MongoClient mongo = new MongoClient("localhost:27017");
			DB db = mongo.getDB("tem");;
			DBCollection coll = db.getCollection("ticket");
			DBObject query = new BasicDBObject();
			query.put("eventId", "000000000000000000000001");
			query.put("eventName", "event0");
			query.put("seatName", "S");
			query.put("eventDate", TicketInitializer.newDate(2014,  10,  2,  0,  0,  0));
			query.put("ticketStartDate", TicketInitializer.newDate(2014, 8, 2, 0, 0, 0));
			query.put("fee", 50000);
			query.put("status", "blank");
			int ticketnum = coll.find(query).count();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000001");
			buyingTicketForm.setSeatName("S");
	        BuyTicketController controller = new BuyTicketController();
	        controller.execute(buyingTicketForm);

	        int ticketnum2 = coll.find(query).count();
	        assertEquals(ticketnum - 1 , ticketnum2);

		}

		/**
		 * 例外:formのデータが正しくない。 (validateを行っているかの確認)<br/>
		 * 対象: {@link RegisterSeatCategory#execute} <br/>
		 * 条件: 一般ユーザーとしてログインさせるが、フォームを設定せずにexecute<br/>
		 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
		 */
		@Test(expected = TEMValidationException.class)
		public void testExecute02() throws Exception {

			init();
			login();
			BuyingTicketForm form = new BuyingTicketForm();
			BuyTicketController controller = new BuyTicketController();
			controller.execute(form);
		}

		/**
		 * 例外:ログインしていない。 <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: アカウント情報を初期化し、ログインしてない状態で座席情報を登録しようと試みる <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute03() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000001");
			buyingTicketForm.setSeatName("S");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);

			logout();
			controller.execute(buyingTicketForm);
		}

		/**
		 * 例外:売り切れのチケットを購入する <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: データベースを初期化し、一般ユーザーでログインした状態で売り切れのチケットを購入する <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute04() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000001");
			buyingTicketForm.setSeatName("S");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);
	        controller.execute(buyingTicketForm);
		}

		/**
		 * 例外:残席数以上の購入枚数で購入する <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: データベースを初期化し、一般ユーザーでログインした状態で残席数以上の枚数のチケットを購入する <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute05() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(2);
	        buyingTicketForm.setEventId("000000000000000000000001");
			buyingTicketForm.setSeatName("S");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);
		}

		/**
		 * 例外:販売開始日時が未来のチケットを購入する <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: データベースを初期化し、一般ユーザーでログインした状態で残席数以上の枚数のチケットを購入する <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute06() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000002");
			buyingTicketForm.setSeatName("A");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);
		}

		/**
		 * 例外:販売開始日時が未来のチケットを購入する <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: データベースを初期化し、一般ユーザーでログインした状態で残席数以上の枚数のチケットを購入する <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute07() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000001");
			buyingTicketForm.setSeatName("B");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);
		}

		/**
		 * 例外:販売開始日時が未来のチケットを購入する <br/>
		 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
		 * 条件: データベースを初期化し、一般ユーザーでログインした状態で残席数以上の枚数のチケットを購入する <br/>
		 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
		 */
		@Test(expected = TEMViewException.class)
		public void testExecute08() throws Exception {

			init();
			login();

			BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
	        buyingTicketForm.setCount(1);
	        buyingTicketForm.setEventId("000000000000000000000004");
			buyingTicketForm.setSeatName("S");
	        BuyTicketController controller = new BuyTicketController();

	        controller.execute(buyingTicketForm);
		}
}