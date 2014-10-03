package jp.enpit.cloud.eventspiral.testutil;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mongodb.*;

/**
 * DBのeventコレクションを初期化するユーティリティクラス．
 * @author y-takata
 * @author 2014043
 *
 */
public class EventInitializer2 {
	private static String host = "localhost:27017";

	/**
	 * DB接続先ホストを設定する．
	 * @param host ホスト名[:ポート番号]
	 */
	public static void setHost(String host) {
		EventInitializer2.host = host;
	}

	/**
	 * DBに接続し，eventコレクションの中身を空にする．
	 * 接続ホストは，デフォルトでは "localhost:27017"．
	 * {@link #setHost} を使って変更できる．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	private static DBCollection initEventColl() throws UnknownHostException {
        MongoClient mongo = new MongoClient(host);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("event");
		coll.drop(); // 中身を消去
		return coll;
	}

	/**
	 * eventコレクションを初期化．登録件数0件．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBN0() throws UnknownHostException {
		initEventColl();
	}

	/**
	 * eventコレクションを初期化．登録件数1件．
	 * ただし，2013年以前のイベント．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBOld1() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "000000000000000000000002", "Cloud Spiral 2013",
			newDate(2013, 6, 1, 12, 0, 0), // 2013年6月1日12時JST
			newDate(2012, 6, 1, 12, 0, 0), // 2012年6月1日12時JST
			"2013年のイベント"
		);
	}

	/**
	 * eventコレクションを初期化．登録件数1件．
	 * ただし，2014年10月以降のイベント．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBNew1() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "000000000000000000000003", "Cloud Spiral 2014",
			newDate(2014, 10, 1, 12, 0, 0), // 2014年10月1日12時JST
			newDate(2013, 10, 1, 12, 0, 0), // 2013年10月1日12時JST
			"2014年のイベント"
		);
	}

	/**
	 * eventコレクションを初期化．登録件25数件．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDB() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "event0", "0",
			newDate(2014, 10, 1, 0, 0, 0), // 2014年10月1日0時JST
			newDate(2014, 10, 1, 0, 0, 0), // 2015年10月1日0時JST
			"desc0"
		);
		addEvent(coll, "event1", "1",
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			"desc1"
		);
		addEvent(coll, "event2", "2",
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			"desc2"
		);
		addEvent(coll, "event3", "3",
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			"desc3"
		);
		addEvent(coll, "event4", "4",
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			"desc4"
		);
		addEvent(coll, "event5", "5",
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			"desc5"
		);
		addEvent(coll, "event6", "6",
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			"desc6"
		);
		addEvent(coll, "event7", "7",
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			"desc7"
		);
		addEvent(coll, "event8", "8",
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			"desc8"
		);
		addEvent(coll, "event9", "9",
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			"desc9"
		);
		addEvent(coll, "event10", "10",
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			"desc10"
		);
		addEvent(coll, "event11", "11",
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			"desc11"
		);
		addEvent(coll, "event12", "12",
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			"desc12"
		);
		addEvent(coll, "event13", "13",
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			"desc13"
		);
		addEvent(coll, "event14", "14",
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			"desc14"
		);
		addEvent(coll, "event15", "15",
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			"desc15"
		);
		addEvent(coll, "event6", "16",
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			"desc16"
		);
		addEvent(coll, "event17", "17",
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			"desc17"
		);
		addEvent(coll, "event18", "18",
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			"desc18"
		);
		addEvent(coll, "event19", "19",
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			"desc19"
		);
		addEvent(coll, "event20", "20",
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			"desc20"
		);
		addEvent(coll, "event21", "21",
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			"desc21"
		);
		addEvent(coll, "event22", "22",
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			"desc22"
		);
		addEvent(coll, "event23", "23",
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			"desc23"
		);
		addEvent(coll, "event24", "24",
			newDate(2014, 10, 2, 24, 0, 0), // 2015年10月2日24時JST
			newDate(2014, 10, 1, 24, 0, 0), // 2015年10月1日24時JST
			"desc24"
		);
	}
	private static Date newDate(int year, int month, int mday, int hour, int min, int sec) {
		return new GregorianCalendar(year, month - 1, mday, hour, min, sec).getTime();
	}

	private static DBObject newSeat(String seatName, int fee, int count) {
		DBObject seat = new BasicDBObject();
		seat.put("seatName", seatName);
		seat.put("fee", fee);
		seat.put("count", count);
		return seat;
	}

	/**
	 * eventコレクションに1件追加する．
	 */
	private static void addEvent(DBCollection coll,
			String eventId, String eventName, Date eventDate, Date ticketStartDate,
			String description)
	{
		DBObject event = new BasicDBObject();
		event.put("eventId",     eventId);
		event.put("eventName",   eventName);
		event.put("eventDate",   eventDate);
		event.put("ticketStartDate", ticketStartDate);
		event.put("description", description);
		coll.insert(event);
	}

	/**
	 * UC用の随時変更するメソッド．
	 * 安定しないのでこのメソッドは単体テストなどでは使用しないように
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDB4UC() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "event0", "0",
			newDate(2014, 10, 1, 0, 0, 0), // 2014年10月1日0時JST
			newDate(2014, 10, 1, 0, 0, 0), // 2015年10月1日0時JST
			"desc0"
		);
		addEvent(coll, "event1", "1",
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			"desc1"
		);
		addEvent(coll, "event2", "2",
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			"desc2"
		);
		addEvent(coll, "event3", "3",
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			"desc3"
		);
		addEvent(coll, "event4", "4",
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			"desc4"
		);
		addEvent(coll, "event5", "5",
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			"desc5"
		);
		addEvent(coll, "event6", "6",
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			"desc6"
		);
		addEvent(coll, "event7", "7",
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			"desc7"
		);
		addEvent(coll, "event8", "8",
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			"desc8"
		);
		addEvent(coll, "event9", "9",
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			"desc9"
		);
		addEvent(coll, "event10", "10",
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			"desc10"
		);
		addEvent(coll, "event11", "11",
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			"desc11"
		);
		addEvent(coll, "event12", "12",
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			"desc12"
		);
		addEvent(coll, "event13", "13",
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			"desc13"
		);
		addEvent(coll, "event14", "14",
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			"desc14"
		);
		addEvent(coll, "event15", "15",
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			"desc15"
		);
		addEvent(coll, "event6", "16",
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			"desc16"
		);
		addEvent(coll, "event17", "17",
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			"desc17"
		);
		addEvent(coll, "event18", "18",
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			"desc18"
		);
		addEvent(coll, "event19", "19",
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			"desc19"
		);
		addEvent(coll, "event20", "20",
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			"desc20"
		);
		addEvent(coll, "event21", "21",
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			"desc21"
		);
		addEvent(coll, "event22", "22",
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			"desc22"
		);
		addEvent(coll, "event23", "23",
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			"desc23"
		);
		addEvent(coll, "event24", "24",
			newDate(2014, 10, 2, 24, 0, 0), // 2015年10月2日24時JST
			newDate(2014, 10, 1, 24, 0, 0), // 2015年10月1日24時JST
			"desc24"
		);
	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[Login]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCLogin() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "event0", "0",
			newDate(2014, 10, 1, 0, 0, 0), // 2014年10月1日0時JST
			newDate(2014, 10, 1, 0, 0, 0), // 2015年10月1日0時JST
			"desc0"
		);
		addEvent(coll, "event1", "1",
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			"desc1"
		);
		addEvent(coll, "event2", "2",
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			"desc2"
		);
		addEvent(coll, "event3", "3",
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			"desc3"
		);
		addEvent(coll, "event4", "4",
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			"desc4"
		);
		addEvent(coll, "event5", "5",
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			"desc5"
		);
		addEvent(coll, "event6", "6",
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			"desc6"
		);
		addEvent(coll, "event7", "7",
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			"desc7"
		);
		addEvent(coll, "event8", "8",
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			"desc8"
		);
		addEvent(coll, "event9", "9",
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			"desc9"
		);
		addEvent(coll, "event10", "10",
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			"desc10"
		);
		addEvent(coll, "event11", "11",
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			"desc11"
		);
		addEvent(coll, "event12", "12",
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			"desc12"
		);
		addEvent(coll, "event13", "13",
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			"desc13"
		);
		addEvent(coll, "event14", "14",
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			"desc14"
		);
		addEvent(coll, "event15", "15",
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			"desc15"
		);
		addEvent(coll, "event6", "16",
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			"desc16"
		);
		addEvent(coll, "event17", "17",
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			"desc17"
		);
		addEvent(coll, "event18", "18",
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			"desc18"
		);
		addEvent(coll, "event19", "19",
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			"desc19"
		);
		addEvent(coll, "event20", "20",
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			"desc20"
		);
		addEvent(coll, "event21", "21",
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			"desc21"
		);
		addEvent(coll, "event22", "22",
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			"desc22"
		);
		addEvent(coll, "event23", "23",
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			"desc23"
		);
		addEvent(coll, "event24", "24",
			newDate(2014, 10, 2, 24, 0, 0), // 2015年10月2日24時JST
			newDate(2014, 10, 1, 24, 0, 0), // 2015年10月1日24時JST
			"desc24"
		);
	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[DisplayEventList]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCDisplayEventList() throws UnknownHostException {
		DBCollection coll = initEventColl();
		addEvent(coll, "event0", "0",
			newDate(2014, 10, 1, 0, 0, 0), // 2014年10月1日0時JST
			newDate(2014, 10, 1, 0, 0, 0), // 2015年10月1日0時JST
			"desc0"
		);
		addEvent(coll, "event1", "1",
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			newDate(2014, 10, 1, 1, 0, 0), // 2015年10月1日1時JST
			"desc1"
		);
		addEvent(coll, "event2", "2",
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			newDate(2014, 10, 1, 2, 0, 0), // 2015年10月1日2時JST
			"desc2"
		);
		addEvent(coll, "event3", "3",
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			newDate(2014, 10, 1, 3, 0, 0), // 2015年10月1日3時JST
			"desc3"
		);
		addEvent(coll, "event4", "4",
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			newDate(2014, 10, 1, 4, 0, 0), // 2015年10月1日4時JST
			"desc4"
		);
		addEvent(coll, "event5", "5",
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			newDate(2014, 10, 1, 5, 0, 0), // 2015年10月1日5時JST
			"desc5"
		);
		addEvent(coll, "event6", "6",
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			newDate(2014, 10, 1, 6, 0, 0), // 2015年10月1日6時JST
			"desc6"
		);
		addEvent(coll, "event7", "7",
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			newDate(2014, 10, 1, 7, 0, 0), // 2015年10月1日7時JST
			"desc7"
		);
		addEvent(coll, "event8", "8",
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			newDate(2014, 10, 1, 8, 0, 0), // 2015年10月1日8時JST
			"desc8"
		);
		addEvent(coll, "event9", "9",
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			newDate(2014, 10, 1, 9, 0, 0), // 2015年10月1日9時JST
			"desc9"
		);
		addEvent(coll, "event10", "10",
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			newDate(2014, 10, 1, 10, 0, 0), // 2015年10月1日10時JST
			"desc10"
		);
		addEvent(coll, "event11", "11",
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			newDate(2014, 10, 1, 11, 0, 0), // 2015年10月1日11時JST
			"desc11"
		);
		addEvent(coll, "event12", "12",
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			newDate(2014, 10, 1, 12, 0, 0), // 2015年10月1日12時JST
			"desc12"
		);
		addEvent(coll, "event13", "13",
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			newDate(2014, 10, 1, 13, 0, 0), // 2015年10月1日13時JST
			"desc13"
		);
		addEvent(coll, "event14", "14",
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			newDate(2014, 10, 1, 14, 0, 0), // 2015年10月1日14時JST
			"desc14"
		);
		addEvent(coll, "event15", "15",
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			newDate(2014, 10, 1, 15, 0, 0), // 2015年10月1日15時JST
			"desc15"
		);
		addEvent(coll, "event6", "16",
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			newDate(2014, 10, 1, 16, 0, 0), // 2015年10月1日16時JST
			"desc16"
		);
		addEvent(coll, "event17", "17",
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			newDate(2014, 10, 1, 17, 0, 0), // 2015年10月1日17時JST
			"desc17"
		);
		addEvent(coll, "event18", "18",
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			newDate(2014, 10, 1, 18, 0, 0), // 2015年10月1日18時JST
			"desc18"
		);
		addEvent(coll, "event19", "19",
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			newDate(2014, 10, 1, 19, 0, 0), // 2015年10月1日19時JST
			"desc19"
		);
		addEvent(coll, "event20", "20",
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			newDate(2014, 10, 1, 20, 0, 0), // 2015年10月1日20時JST
			"desc20"
		);
		addEvent(coll, "event21", "21",
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			newDate(2014, 10, 1, 21, 0, 0), // 2015年10月1日21時JST
			"desc21"
		);
		addEvent(coll, "event22", "22",
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			newDate(2014, 10, 1, 22, 0, 0), // 2015年10月1日22時JST
			"desc22"
		);
		addEvent(coll, "event23", "23",
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			newDate(2014, 10, 1, 23, 0, 0), // 2015年10月1日23時JST
			"desc23"
		);
		addEvent(coll, "event24", "24",
			newDate(2014, 10, 2, 24, 0, 0), // 2015年10月2日24時JST
			newDate(2014, 10, 1, 24, 0, 0), // 2015年10月1日24時JST
			"desc24"
		);
	}
}
