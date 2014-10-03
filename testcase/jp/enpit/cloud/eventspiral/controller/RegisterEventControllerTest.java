package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;

import java.util.*;


import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.view.EventInfoEntity;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.RegisteringEventForm;
import jp.enpit.cloud.eventspiral.view.TEMValidationException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UC: イベントを登録する．<br/>
 *
 * @author 2014003
 */
public class RegisterEventControllerTest {

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
	 * 正常なイベント登録 <br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: 戻り値である{@link EventInfoEntity}型のオブジェクトのデータが登録したものと等しい。 <br/>
	 */
	@Test
	public void testExecute01() throws Exception {

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, 1);
		Date ticketStartDate = now.getTime();

		now.add(Calendar.DATE, 1);
		Date eventDate = now.getTime();


		RegisteringEventForm registeringEventForm = new RegisteringEventForm();
		registeringEventForm.setDescription("desc01");
		registeringEventForm.setEventDate(eventDate);
		registeringEventForm.setEventName("eveName01");
		registeringEventForm.setTicketStartDate(ticketStartDate);

		RegisterEventController registerEventController = new RegisterEventController();
		EventInfoEntity actual = new EventInfoEntity();
		actual= registerEventController.execute(registeringEventForm);

		EventInfoEntity expected = new EventInfoEntity();
		expected.setDescription("desc01");
		expected.setEventDate(eventDate);
		expected.setEventName("eveName01");
		expected.setTicketStartDate(ticketStartDate);
		expected.setPromoterId("promoter1");

		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getEventDate(), actual.getEventDate());
		assertNotNull(actual.getEventId());;
		assertEquals(expected.getEventName(), actual.getEventName());
		assertEquals(expected.getPromoterId(), actual.getPromoterId());
		assertEquals(expected.getTicketStartDate(), actual.getTicketStartDate());

	}

	/**
	 * 例外:formのデータが正しくない。 (validateを行っているかの確認)<br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute02() throws Exception {

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		RegisteringEventForm registeringEventForm = new RegisteringEventForm();

		RegisterEventController registerEventController = new RegisterEventController();
		registerEventController.execute(registeringEventForm);
	}

	/**
	 * 例外:興行主以外でログインしている。 <br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute03() throws Exception {

		AccountInitializer.initDB();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, 1);
		Date ticketStartDate = now.getTime();

		now.add(Calendar.DATE, 1);
		Date eventDate = now.getTime();


		RegisteringEventForm registeringEventForm = new RegisteringEventForm();
		registeringEventForm.setDescription("desc01");
		registeringEventForm.setEventDate(eventDate);
		registeringEventForm.setEventName("eveName01");
		registeringEventForm.setTicketStartDate(ticketStartDate);

		RegisterEventController registerEventController = new RegisterEventController();
		registerEventController.execute(registeringEventForm);
	}

	/**
	 * 例外:ログインしていない。 <br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute04() throws Exception {

		AccountInitializer.initDB();

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, 1);
		Date ticketStartDate = now.getTime();

		now.add(Calendar.DATE, 1);
		Date eventDate = now.getTime();


		RegisteringEventForm registeringEventForm = new RegisteringEventForm();
		registeringEventForm.setDescription("desc01");
		registeringEventForm.setEventDate(eventDate);
		registeringEventForm.setEventName("eveName01");
		registeringEventForm.setTicketStartDate(ticketStartDate);

		RegisterEventController registerEventController = new RegisterEventController();
		registerEventController.execute(registeringEventForm);
	}

	/**
	 * 例外:イベントが未来の日時ではない。 <br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute05() throws Exception {

		AccountInitializer.initDB();

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, 1);
		Date ticketStartDate = now.getTime();

		now.add(Calendar.DATE, -1);
		Date eventDate = now.getTime();


		RegisteringEventForm registeringEventForm = new RegisteringEventForm();
		registeringEventForm.setDescription("desc01");
		registeringEventForm.setEventDate(eventDate);
		registeringEventForm.setEventName("eveName01");
		registeringEventForm.setTicketStartDate(ticketStartDate);

		RegisterEventController registerEventController = new RegisterEventController();
		registerEventController.execute(registeringEventForm);
	}

	/**
	 * 例外:イベントが既に存在している。。 <br/>
	 * 対象: {@link RegisterEventController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute06() throws Exception {

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, 1);
		Date ticketStartDate = now.getTime();

		now.add(Calendar.DATE, 1);
		Date eventDate = now.getTime();


		RegisteringEventForm registeringEventForm = new RegisteringEventForm();
		registeringEventForm.setDescription("desc01");
		registeringEventForm.setEventDate(eventDate);
		registeringEventForm.setEventName("eveName01");
		registeringEventForm.setTicketStartDate(ticketStartDate);

		RegisterEventController registerEventController = new RegisterEventController();
		registerEventController.execute(registeringEventForm);
		registerEventController.execute(registeringEventForm);

	}

}
