package jp.enpit.cloud.eventspiral.model;

import java.util.Date;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * チケットの購入履歴用ロガー
 * チケット購入とチケットキャンセルの2つをロギング
 *
 */
public class TicketLogger {
	private final static String DB_LOG_COLLECTION = "log";

	/**
	 * <ol>
     * <li>パラメータに記録日時を追加してデータベース(log)にログを保存する．</li>
	 * </ol>
	 * @param eventId イベントID
	 * @param seatName 座席種別
	 * @param owner	ユーザID
	 * @param count	チケット枚数
	 * @param operation	操作
	 */
	public static void log(String eventId, String seatName, String owner, int count, String operation) {
		DBCollection coll = DBUtils.getInstance().getDb().getCollection(DB_LOG_COLLECTION);
		try {
			DBObject o = new BasicDBObject();
			o.put("date", new Date());
			o.put("eventId", eventId);
			o.put("seatName", seatName);
			o.put("owner", owner);
			o.put("count", count);
			o.put("operation", operation);

			coll.insert(o);
		} catch (MongoException e) {
			return; // ignore this exception
		}
	}
}
