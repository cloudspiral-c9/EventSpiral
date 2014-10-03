package jp.enpit.cloud.eventspiral.testutil;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class AccountInitializerUCSignUpPromoter {
	private static String host = "localhost:27017";
	//private static String host = "133.1.236.131:9271";

	/**
	 * DB接続先ホストを設定する．
	 * @param host ホスト名[:ポート番号]
	 */
	public static void setHost(String host) {
		AccountInitializerUCSignUpPromoter.host = host;
	}

	/**
	 * DBに接続し，accountコレクションの中身を空にする．
	 * 接続ホストは，デフォルトでは "localhost:27017"．
	 * {@link #setHost} を使って変更できる．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	private static DBCollection initAccountColl() throws UnknownHostException {
        MongoClient mongo = new MongoClient(host);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("account");
		coll.drop(); // 中身を消去
		return coll;
	}

	/**
	 * accountコレクションを初期化．登録ユーザ数12件．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDB() throws UnknownHostException {
		DBCollection coll = initAccountColl();
		addAccount(coll, "user0",     "pass0",     "administrator", "");
	}

	/**
	 * accountコレクションに1件追加する．
	 */
	private static void addAccount(DBCollection coll,
			String userId, String pass, String role, String sessionId)
	{
		DBObject account = new BasicDBObject();
		account.put("userId",    userId);
		account.put("pass",      pass);
		account.put("role",      role);
		account.put("sessionId", sessionId);
		coll.insert(account);
	}
}
