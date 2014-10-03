package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.*;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

/**
 * UC: 一般ユーザを登録する <br/>
 *   testExecute01(): 正常系 <br/>
 *   testExecute02(): 既に存在するユーザID <br/>
 * @author y-takata
 *
 */
public class SignUpControllerTest {
	
	/**
	 * 正常なユーザ登録 <br/>
	 * 対象: {@link SignUpController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化では登録されないユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "hogehoge", pass: "pass1") <br/>
	 * 期待する結果: 事後条件として，accountコレクションの上記ユーザIDに
	 *       対するドキュメントのpassフィールドが上記pass文字列("pass1")．<br/>
	 */
	@Test
	public void testExecute01() throws Exception {
		// DB初期化
		AccountInitializer.initDB();

		// ユーザ登録
		String userId = "hogehoge";
		String pass   = "pass1";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		SignUpController sut = new SignUpController();
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
	 * 例外：既に存在するユーザID <br/>
	 * 対象: {@link SignUpController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       引数は，初期化で登録済みのユーザを表す {@link IdentifyingAccountForm}
	 *       (userId: "user1", pass: "fuga") <br/>
	 * 期待する結果: メッセージが "指定されたユーザIDは既に存在します"
	 *       である {@link TEMViewException} が発生する． <br/>
	 */
	@Test
	public void testExecute02() throws Exception {
		// DB初期化
		AccountInitializer.initDB();

		// ユーザ登録
		String userId = "user1";
		String pass   = "fuga";
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		SignUpController sut = new SignUpController();
		try {
			sut.execute(account);
			fail("TEMViewException was expected");
		} catch (TEMViewException e) {
			assertEquals("指定されたユーザIDは既に存在します", e.getMessage());
		}
	}

}
