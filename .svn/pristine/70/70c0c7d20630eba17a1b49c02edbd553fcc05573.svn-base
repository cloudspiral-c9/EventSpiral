package jp.enpit.cloud.eventspiral.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.*;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;


/**
 * UC: ログアウトする <br/>
 *   testExecute01(): 正常系 <br/>
 * TODO:
 *   ログインしていないときに{@link LogoutController#execute}を
 *   呼び出しても特に何も起こらないので，異常系のテストをしていない．
 * 
 * @author y-takata
 */
public class LogoutControllerTest {
	
	/**
	 * 正常なログアウト <br/>
	 * 対象: {@link LogoutController#execute} <br/>
	 * 条件: {@link AccountInitializer#initDB} でDBを初期化済み．
	 *       かつ，{@link LoginController#execute} を使って
	 *       一般ユーザ ("user1") としてログイン済み．<br/>
	 * 期待する結果: 事後条件として，accountコレクションの上記ユーザIDに
	 *       対するドキュメントのsessionIdフィールドが空文字列．<br/>
	 */
	@Test
	public void testExecute01() throws Exception {
		// accountコレクションを初期化
		AccountInitializer.initDB();

		// とりあえず一般ユーザでログインする
		String userId = "user1";
		String pass   = "pass1";
		LoginController loginCtr = new LoginController();
		IdentifyingAccountForm account = new IdentifyingAccountForm();
		account.setUserId(userId);
		account.setPass(pass);
		loginCtr.execute(account);
	
		// ログアウトする
		LogoutController logoutCtr = new LogoutController();
		logoutCtr.execute();

		//消えているかDBで確認 
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("account");
		DBObject query = new BasicDBObject();
		query.put("userId", userId);
		DBObject result = coll.findOne(query);
		assertEquals("", result.get("sessionId"));
	}

}
