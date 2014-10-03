package jp.enpit.cloud.eventspiral.testutil;

import java.net.UnknownHostException;

import com.mongodb.*;

/**
 * DBのaccountコレクションを初期化するユーティリティクラス．
 * @author y-takata
 */
public class AccountInitializer {
	private static String host = "localhost:27017";
	//private static String host = "133.1.236.131:9271";

	/**
	 * DB接続先ホストを設定する．
	 * @param host ホスト名[:ポート番号]
	 */
	public static void setHost(String host) {
		AccountInitializer.host = host;
	}

	/**
	 * accountコレクションを初期化．登録件数0件．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBN0() throws UnknownHostException {
		initAccountColl();
	}

	/**
	 * DBに接続し，accountコレクションの中身を空にする．
	 * 接続ホストは，デフォルトでは "localhost:27017"．
	 * {@link #setHost} を使って変更できる．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static DBCollection initAccountColl() throws UnknownHostException {
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
		addAccount(coll, "admin",     "admin",     "administrator", "");
		addAccount(coll, "promoter1", "promoter1", "promoter", "");
		addAccount(coll, "user0",     "pass0",     "user",     "");
		addAccount(coll, "user1",     "pass1",     "user",     "");
		addAccount(coll, "user2",     "pass2",     "user",     "");
	}

	/**
	 * accountコレクションに1件追加する．
	 */
	public static void addAccount(DBCollection coll,
			String userId, String pass, String role, String sessionId)
	{
		DBObject account = new BasicDBObject();
		account.put("userId",    userId);
		account.put("pass",      pass);
		account.put("role",      role);
		account.put("sessionId", sessionId);
		coll.insert(account);
	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[Login]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCLogin() throws UnknownHostException {
		DBCollection coll = initAccountColl();
		addAccount(coll, "admin",     "admin",     "administrator", "");
		addAccount(coll, "promoter1", "promoter1", "promoter", "");
		addAccount(coll, "user0",     "pass0",     "user",     "");
		addAccount(coll, "user1",     "pass1",     "user",     "");
		addAccount(coll, "user2",     "pass2",     "user",     "");
	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[DisplayEventList]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCDisplayEventList() throws UnknownHostException {
		DBCollection coll = initAccountColl();
		addAccount(coll, "admin",     "admin",     "administrator", "");
		addAccount(coll, "promoter1", "promoter1", "promoter", "");
		addAccount(coll, "user0",     "pass0",     "user",     "");
		addAccount(coll, "user1",     "pass1",     "user",     "");
		addAccount(coll, "user2",     "pass2",     "user",     "");
	}


	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[RegisterEvent]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCRegisterEvent() throws UnknownHostException {

		DBCollection coll = initAccountColl();

		addAccount(coll, "promoter1", "promoter1", "promoter", "");

	}


	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[DisplayEventDetail]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCDisplayEventDetail() throws UnknownHostException {
		DBCollection coll = initAccountColl();
		addAccount(coll, "admin",     "admin",     "administrator", "");
		addAccount(coll, "promoter1", "promoter1", "promoter", "");
		addAccount(coll, "user0",     "pass0",     "user",     "");
		addAccount(coll, "user1",     "pass1",     "user",     "");
		addAccount(coll, "user2",     "pass2",     "user",     "");
	}

}
