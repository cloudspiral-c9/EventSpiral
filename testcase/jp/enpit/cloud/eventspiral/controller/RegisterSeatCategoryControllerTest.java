package jp.enpit.cloud.eventspiral.controller;

/**
 * UC: イベントの座席種別情報を登録する．<br/>
 *
 * @author 2014040
 * @author 2014001
 */

import static org.junit.Assert.*;

import java.util.*;

import jp.enpit.cloud.eventspiral.model.Event;
import jp.enpit.cloud.eventspiral.model.EventModel;
import jp.enpit.cloud.eventspiral.model.SeatCategory;
import jp.enpit.cloud.eventspiral.model.TicketModel;
import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.view.EventInfoEntity;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.RegisteringEventForm;
import jp.enpit.cloud.eventspiral.view.RegisteringSeatCategoryForm;
import jp.enpit.cloud.eventspiral.view.TEMValidationException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

import org.junit.Test;

public class RegisterSeatCategoryControllerTest {

	// アカウント情報、イベント情報を初期化する
	private void init() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDBN0();
	}

	// 興行主としてログインさせる
	private void login() throws Exception {

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

	}

	// 一般ユーザーとしてログインさせる
	private void loginOther() throws Exception {

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

	// イベントを1つ登録する
	private EventInfoEntity registerEventN1() throws Exception {
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
		return registerEventController.execute(registeringEventForm);
	}

	// 座席情報を1つ登録する
	private void registerSeatCategoryN1(String eventId) throws Exception {

		RegisteringSeatCategoryForm form = new RegisteringSeatCategoryForm();
		form.setEventId(eventId);
		form.setSeatName("A");
		form.setCount(100);
		form.setFee(2000);

		RegisterSeatCategoryController controller = new RegisterSeatCategoryController();
		controller.execute(form);

	}

	/**
	 * 正常なイベント登録 <br/>
	 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
	 * 条件: プロモーターとしてログインさせ、イベントを1つ登録、そのイベントIDで座席種別を1つ登録 <br/>
	 * 期待する結果: TicketModelのgetRemainSeatCategoriesメソッドで、正しい座席種別情報が取得できる <br/>
	 */
	@Test
	public void testExecute01() throws Exception {

		init();
		login();
		EventInfoEntity info = registerEventN1();
		registerSeatCategoryN1(info.getEventId());

		Event event = new Event();
		EventModel eventModel = new EventModel();
		event = eventModel.getEvent(info.getEventId());;
		TicketModel ticketModel = new TicketModel();
		List<SeatCategory> seats = ticketModel.getRemainSeatCategories(event);

		assertEquals(1, seats.size());
		SeatCategory actual = seats.get(0);
		assertEquals("A", actual.getSeatName());
		assertEquals(100, actual.getCount());
		assertEquals(2000, actual.getFee());
	}

	/**
	 * 例外:formのデータが正しくない。 (validateを行っているかの確認)<br/>
	 * 対象: {@link RegisterSeatCategory#execute} <br/>
	 * 条件: プロモーターとしてログインさせるが、フォームを設定せずにexecute<br/>
	 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute02() throws Exception {

		init();
		RegisteringSeatCategoryForm form = new RegisteringSeatCategoryForm();
		RegisterSeatCategoryController controller = new RegisterSeatCategoryController();
		controller.execute(form);

	}

	/**
	 * 例外:興行主以外でログインしている。 <br/>
	 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
	 * 条件: アカウント情報を初期化し、一般ユーザーユーザーとしてログインしている<br />
	 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute03() throws Exception {

		init();
		login();
		EventInfoEntity info = registerEventN1();

		loginOther();
		registerSeatCategoryN1(info.getEventId());

	}

	/**
	 * 例外:ログインしていない。 <br/>
	 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
	 * 条件: アカウント情報を初期化し、ログインしてない状態で座席情報を登録しようと試みる <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute04() throws Exception {

		init();
		login();
		EventInfoEntity info = registerEventN1();

		logout();
		registerSeatCategoryN1(info.getEventId());

	}

	/**
	 * 例外:イベントが存在しない <br/>
	 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
	 * 条件: プロモーターとしてログインさせ、イベントを1つ登録するが、異なるイベントIDで座席情報登録を試みる <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute05() throws Exception {

		init();
		login();
		registerEventN1();
		registerSeatCategoryN1("unknown");

	}

	/**
	 * 例外:座席情報が既に存在している。。 <br/>
	 * 対象: {@link RegisterSeatCategoryController#execute} <br/>
	 * 条件: プロモーターとしてログインさせ、イベントを1つ登録、そのイベントIDで座席種別を2回登録を試みる <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute06() throws Exception {

		init();
		login();
		EventInfoEntity info = registerEventN1();
		registerSeatCategoryN1(info.getEventId());
		registerSeatCategoryN1(info.getEventId());

	}

}
