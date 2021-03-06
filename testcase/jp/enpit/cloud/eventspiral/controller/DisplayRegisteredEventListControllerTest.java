package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.view.EventDetailEntity;
import jp.enpit.cloud.eventspiral.view.EventDetailSearchResultEntity;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.SearchingForm;
import jp.enpit.cloud.eventspiral.view.TEMValidationException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UC: 登録されたイベントの一覧を表示する。．<br/>
 *		testExecute01(): イベント情報が登録されていない <br/>
 *   	testExecute02(): 過去のイベント情報のみ登録されている <br/>
 *   	testExecute03(): 未来のイベント情報が1件登録されている <br/>
 *   	testExecute04(): 未来のイベント情報が6件登録されている <br/>
 *
 * @author 2014003
 */
public class DisplayRegisteredEventListControllerTest {

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

	private static Date newDate(int year, int month, int mday, int hour,
			int min, int sec) {
		return new GregorianCalendar(year, month - 1, mday, hour, min, sec)
				.getTime();
	}

	/**
	 * 正常系:イベント情報なし <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0}{@link AccountInitializer#initDB} でDBを初期化済み． 期待する結果: 戻り値である
	 * {@link EventDetailSearchResultEntity}型のオブジェクトが 持つ未来のイベント情報数が0 <br/>
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

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		EventDetailSearchResultEntity actual = drelCtr.execute(form);

		assertEquals(0, actual.getTotalCount());// 登録されているイベント件数が0なので0になるはず
		List<EventDetailEntity> events = actual.getList();
		assertEquals(0, events.size());// リストのサイズも0
	}

	/**
	 * 正常系:過去のイベント情報1 <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBForDisplayRegisteredEventListControllerFortestExecute02},{@link AccountInitializer#initDB} でDBを初期化済み． 期待する結果: 戻り値である
	 * {@link EventDetailSearchResultEntity}型のオブジェクトが 持つイベント情報数が1 <br/>
	 */
	@Test
	public void testExecute02() throws Exception {
		EventInitializer.initDBForDisplayRegisteredEventListControllerFortestExecute02();// 過去のイベント情報を1件登録

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		EventDetailSearchResultEntity actual = drelCtr.execute(form);

		assertEquals(1, actual.getTotalCount());// 未来のイベント件数なので0になるはず
		List<EventDetailEntity> events = actual.getList();
		assertEquals(1, events.size());// リストのサイズも0
	}

	/**
	 * 正常系:未来のイベント情報が1件登録されている <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBForDisplayRegisteredEventListControllerFortestExecute03},{@link AccountInitializer#initDB} でDBを初期化済み． 期待する結果: 戻り値である
	 * {@link EventDetailSearchResultEntity}型のオブジェクトが 持つ未来のイベント情報数が1 <br/>
	 */
	@Test
	public void testExecute03() throws Exception {
		EventInitializer.initDBForDisplayRegisteredEventListControllerFortestExecute03();

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		EventDetailSearchResultEntity actual = drelCtr.execute(form);

		assertEquals(1, actual.getTotalCount());
		List<EventDetailEntity> events = actual.getList();
		assertEquals(1, events.size());
		// リストの中身を確認
		assertEquals("000000000000000000000003", events.get(0).getEventId());
		assertEquals("Cloud Spiral 2014", events.get(0).getEventName());
		assertEquals(
				0,
				newDate(2014, 10, 1, 12, 0, 0).compareTo(
						events.get(0).getEventDate()));
		assertEquals(
				0,
				newDate(2013, 10, 1, 12, 0, 0).compareTo(
						events.get(0).getTicketStartDate()));
		assertEquals("2014年のイベント", events.get(0).getDescription());
	}

	/**
	 * 正常系:未来のイベント情報が6件,過去のイベント情報が2件登録されている <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBForDisplayRegisteredEventListControllerFortestExecute04},{@link AccountInitializer#initDB} でDBを初期化済み． 期待する結果: 戻り値である
	 * {@link EventDetailSearchResultEntity}型のオブジェクトが 持つイベント情報数が8 <br/>
	 */
	@Test
	public void testExecute04() throws Exception {

		AccountInitializer.initDB();
		EventInitializer.initDBForDisplayRegisteredEventListControllerFortestExecute04();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		EventDetailSearchResultEntity actual = drelCtr.execute(form);

		assertEquals(8, actual.getTotalCount());
		List<EventDetailEntity> events = actual.getList();
		assertEquals(8, events.size());
	}

	/**
	 * 異常系:formのデータが正しくない。 (validateを行っているかの確認) <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み．<br/>
	 * 期待する結果: {@link TemValidationException}が発生する。<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testExecute05() throws Exception {

		AccountInitializer.initDB();

		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(-1);
		form.setLength(10);
		drelCtr.execute(form);
	}

	/**
	 * 例外:興行主以外でログインしている。 <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TemValidationException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute06() throws Exception {

		AccountInitializer.initDB();

		String userId = "user0";
		String pass   = "pass0";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		LoginController loginController = new LoginController();
		loginController.execute(account);

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		drelCtr.execute(form);
	}

	/**
	 * 例外:ログインしていない。 <br/>
	 * 対象: {@link DisplayRegisteredEventListController#execute} <br/>
	 * 条件: {@link EventInitializer#initDBN0},{@link AccountInitializer#initDB} でDBを初期化済み． <br/>
	 * 期待する結果: {@link TEMViewException}が発生する。 <br/>
	 */
	@Test(expected = TEMViewException.class)
	public void testExecute07() throws Exception {

		AccountInitializer.initDB();

		DisplayRegisteredEventListController drelCtr = new DisplayRegisteredEventListController();
		SearchingForm form = new SearchingForm();
		form.setStartIndex(0);
		form.setLength(10);
		drelCtr.execute(form);
	}

}
