package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.RoleEntity;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * UC: ログインする．<br/>
 *   testExecute01(): 正常系 <br/>
 *   testExecute02(): パスワード間違い <br/>
 *   testExecute03(): ユーザID間違い <br/>
 * TODO:
 *   正常系は，user1以外のテストも追加すべきかも知れない．
 * 
 * @author y-takata
 */
public class LoginControllerTest {

	/**
	 * 正常なユーザログイン <br/>
	 * 対象: {@link LoginController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化で登録済みの一般ユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "user1", pass: "pass1") <br/>
	 * 期待する結果: 戻り値である{@link RoleEntity}のroleが "user" <br/>
	 */
	@Test
	public void testExecute01() throws Exception {
		// accountコレクションを初期化
		AccountInitializer.initDB();
		// 一般ユーザでログインする
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId("user1");
		account.setPass("pass1");
		String expected = "user";
		RoleEntity re = loginCtr.execute(account);
		assertEquals(expected, re.getRole());
	}
	 
	/**
	 * 例外：パスワード間違い <br/>
	 * 対象: {@link LoginController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化で登録済みの一般ユーザを表す {@link IdentifyingAccountForm}．
	 *       ただしpassが誤っている．
	 *       (userId: "user1", pass: "pass") <br/>
	 * 期待する結果: メッセージが "ユーザIDもしくはパスワードが正しくありません"
	 *       である {@link TEMViewException} が発生する． <br/>
	 */
	@Test
	public void testExecute02() throws Exception {
		// accountコレクションを初期化
		AccountInitializer.initDB();

		// 一般ユーザでログインする（パス間違い）
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId("user");
		account.setPass("pass");
		try{
			loginCtr.execute(account);
			fail(); // 例外が発生しなければ失敗
		} catch (TEMViewException e) {
		    assertEquals("ユーザIDもしくはパスワードが正しくありません", e.getMessage());
		}
	}

	/**
	 * 例外：ユーザID間違い <br/>
	 * 対象: {@link LoginController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化で登録されない一般ユーザを表す {@link IdentifyingAccountForm}．
	 *       (userId: "userx", pass: "pass1") <br/>
	 * 期待する結果: メッセージが "ユーザIDもしくはパスワードが正しくありません"
	 *       である {@link TEMViewException} が発生する． <br/>
	 */
	@Test
	public void testExecute03() throws Exception {
		// accountコレクションを初期化
		AccountInitializer.initDB();

		// 一般ユーザでログインする（パス間違い）
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId("userx");
		account.setPass("pass1");
		try{
			loginCtr.execute(account);
			fail(); // 例外が発生しなければ失敗
		} catch (TEMViewException e) {
		    assertEquals("ユーザIDもしくはパスワードが正しくありません", e.getMessage());
		}
	}
}
