package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;


import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * UC: 興行主を登録する <br/>
 *   testExecute01(): 正常系 <br/>
 *   testExecute02(): 既に存在する興行主ID <br/>
 *   testExecute03(): 管理者アカウントでログインしていない<br/>
 * @author 2014024
 *
 */
public class SignUpPromoterControllerTest {

	/**
	 * 正常な興行主登録 <br/>
	 * 対象: {@link SignUpPromoterController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化では登録されないユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "promoter2", pass: "promoter2") <br/>
	 * 期待する結果: 事後条件として，accountコレクションの上記ユーザIDに
	 *       対するドキュメントのpassフィールドが上記pass文字列("promoter2")．<br/>
	 */
	@Test
	public void testExecute01() throws Exception{
		// DB初期化
		AccountInitializer.initDB();

		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account1 = new IdentifyingAccountForm();
		account1.setUserId("admin");
		account1.setPass("admin");
		loginCtr.execute(account1);

		//興行主登録
		String userId = "promoter2";
		String pass   = "promoter2";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		SignUpPromoterController sut = new SignUpPromoterController();
		sut.execute(account);

	// 正常に登録できたかDBで確認
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("account");
		DBObject query = new BasicDBObject();
		query.put("userId", userId);
		DBObject result = coll.findOne(query);
		assertEquals(pass, result.get("pass"));

	}

	/**
	 * 例外：既に存在する興行主ID <br/>
	 * 対象: {@link SignUpPromoterController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化で登録済みのユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "promoter1", pass: "promoter1") <br/>
	 * 期待する結果: メッセージが "指定されたユーザIDは既に存在します"
	 *       である {@link TEMViewException} が発生する． <br/>
	 */
	@Test
	public void testExecute02() throws Exception{
		// DB初期化
		AccountInitializer.initDB();
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account1 = new IdentifyingAccountForm();
		account1.setUserId("admin");
		account1.setPass("admin");
		loginCtr.execute(account1);

		//興行主登録
		String userId = "promoter1";
		String pass   = "promoter1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		SignUpPromoterController sut = new SignUpPromoterController();
		try {
			sut.execute(account);
			fail("TEMViewException was expected");
		} catch (TEMViewException e) {
			assertEquals("指定されたユーザIDは既に存在します", e.getMessage());
		}
	}

	/**
	 * 例外：管理者アカウントでログインしていない <br/>
	 * 対象: {@link SignUpPromoterController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 * 引数は，初期化で登録済みのユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "promoter1", pass: "promoter1") <br/>
	 * 期待する結果: メッセージが "管理者アカウントでログインしてください"
	 *       である {@link TEMViewException} が発生する． <br/>
	 */
	@Test
	public void testExecute03() throws Exception{
		// DB初期化
		AccountInitializer.initDB();
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account1 = new IdentifyingAccountForm();
		account1.setUserId("user1");
		account1.setPass("pass1");
		String userId = "promoter2";
		String pass   = "promoter2";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		SignUpPromoterController sut = new SignUpPromoterController();
		try{
				loginCtr.execute(account1);
				sut.execute(account);
				fail("TEMViewException was expected");
			} catch(TEMViewException e){
			assertEquals("管理者アカウントでログインしてください", e.getMessage());
		}
	}
}
