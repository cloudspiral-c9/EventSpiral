package jp.enpit.cloud.eventspiral;

import java.util.logging.Logger;

import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.NotLoggedInException;
import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * セッションオブジェクト管理のためのクラス.
 * セッションオブジェクト＝{セッションID，ログイン済みユーザ名}のペア.
 *
 * @author shinsuke-m
 *
 */
public class Session {
	private final String DB_SESSION_COLLECTION = "account";

	/**
	 * Loggerオブジェクト
	 */
	private Logger logger;

	/**
	 * DBオブジェクト
	 */
	private DB db;

	/**
	 * DBCollectionオブジェクト
	 */
	private DBCollection coll;

	/**
	 * Logger、DB、DBCollectionフィールドに各オブジェクトを設定する．
	 */
	public Session() {
		logger = Logger.getLogger(getClass().getName());
		db = DBUtils.getInstance().getDb();
		coll = db.getCollection(DB_SESSION_COLLECTION);
	}

	/**
	 * 現在ログインしているアカウント情報を取得する.
	 * <ol>
	 *   <li>DWRの発行するSessionIdを取得する．</li>
	 *   <li>sessionIdをキーにして、データベースを検索する()．</li>
	 *     {@code db.account.findOne({ "sessionId" : sessionId })}
	 *   <li>データがヒットしなかった場合、NotLoggedInExceptionを投げる．</li>
	 *   <li>Accountオブジェクトを生成して、データベースの検索結果をアタッチする</li>
	 *   <li>Accountオブジェクトを返す．</li>
	 *   <li><tt>MongoException</tt>が発生した場合：
	 *     <ol>
	 *       <li>発生した例外を<tt>TEMFatalException</tt>にラップして投げる．</li>
	 *     </ol>
	 *   </li>
	 * </ol>
	 * @return sessionIdに対応するアカウント情報（Accountオブジェクト）
	 * @throws TEMFatalException MongoExceptionが発生した場合．
	 * @throws NotLoggedInException セッションIDが登録されていない場合．
	 */
	public Account getCurrentAccount() throws TEMFatalException, NotLoggedInException {
		logger.info("Session.getCurrentAccount");

		// DWRの発行するSessionIdの取得
		String sessionId = getSessionId();

		// クエリの作成
		DBObject query = new BasicDBObject();
		query.put("sessionId", sessionId);

		try {
			// セッションがDBに登録されている＝ログイン
			DBObject result = coll.findOne(query);
			if (result == null) {
				String msg = "SessionId:" + sessionId + " does not logged in.";
				logger.warning(msg);
				throw new NotLoggedInException(msg);
			}
			Account account = new Account();
			DBUtils.attachProperties(account, result);
			return account;
		} catch (MongoException e) {
			logger.severe(e.getMessage());
			throw new TEMFatalException(e);
		}
	}

	/**
	 * 現在ログインしているセッションIDをデータベースに登録する.
	 * <ol>
	 * <li>DWRの発行するSessionIdを取得する．</li>
	 * <li>deleteSessionIdを呼び出す．</li>
	 * <li>ユーザIDをキーにして、一致するデータのセッションIDを更新する．</li>
	 * {@code db.account.update({ "userId" : userId }, { "$set" : { "sessionId" : sessionId }})}
	 * <li><tt>MongoException</tt>が発生した場合：
	 *   <ol>
	 *     <li>発生した例外を<tt>TEMFatalException</tt>にラップして投げる．</li>
	 *   </ol>
	 * </li>
	 * </ol>
	 * @param userId ユーザID
	 * @throws TEMFatalException MongoExceptionが発生した場合．
	 * @throws NotLoggedInException セッションIDが登録されていない場合．
	 */
	public void registerSessionId(String userId) throws TEMFatalException {
		logger.info("Session.registerSessionId");

		// DWRの発行するSessionIdの取得
		String sessionId = getSessionId();

		// 登録済みのセッションオブジェクトを消す
		deleteSessionId();

		DBObject query = new BasicDBObject();
		query.put("userId", userId);

		DBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("sessionId", sessionId));

		// DB登録
		try {
			coll.update(query, update);
		} catch (MongoException e) {
			logger.severe(e.getMessage());
			throw new TEMFatalException(e);
		}
	}

	/**
	 * 現在ログインしているセッションIDをデータベースから削除する.
	 * <ol>
	 * <li>DWRの発行するSessionIdを取得する．</li>
	 * <li>sessionIDをキーにして、一致するデータのセッションIDを消す．</li>
	 * {@code db.account.update({ "sessionId" : sessionId }, { "$set" : { "sessionId", "" }})}
	 * <li><tt>MongoException</tt>が発生した場合：
	 *   <ol>
	 *     <li>発生した例外を<tt>TEMFatalException</tt>にラップして投げる．</li>
	 *   </ol>
	 * </li>
	 * </ol>
	 * @throws TEMFatalException MongoExceptionが発生した場合．
	 * @throws NotLoggedInException セッションIDが登録されていない場合．
	 */
	public void deleteSessionId() throws TEMFatalException {
		logger.info("Session.deleteSessionId");

		// DWRの発行するSessionIdの取得．
		String sessionId = getSessionId();

		// query
		DBObject query = new BasicDBObject();
		query.put("sessionId", sessionId);

		// update セッションを空にする．
		DBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("sessionId", ""));

		try {
			// 認証済みの同一セッションを探し全て消す．
			coll.update(query, update);
		} catch (MongoException e) {
			logger.severe(e.getMessage());
			throw new TEMFatalException(e);
		}
	}

	/**
	 * <ol>
	 * <li>DWRの発行するSessionIdを取得する．</li>
	 * </ol>
	 * @return セッションID
	 */
	private String getSessionId() {
		WebContext ctx = WebContextFactory.get();

		// ローカルテスト時
		if (ctx == null) {
			return "THIS_IS_A_TEST_SESSION_ID";
		}

		return ctx.getScriptSession().getId().split("/")[0];
	}
}
