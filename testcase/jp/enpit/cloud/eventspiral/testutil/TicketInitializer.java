package jp.enpit.cloud.eventspiral.testutil;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import jp.enpit.cloud.eventspiral.view.SeatCategoryEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TicketInitializer {
	private static String host = "localhost:27017";
	//private static String host = "133.1.236.131:9271";

	/**
	 * DB接続先ホストを設定する．
	 * @param host ホスト名[:ポート番号]
	 */
	public static void setHost(String host) {
		TicketInitializer.host = host;
	}

	public static Date newDate(int year, int month, int mday, int hour, int min, int sec) {
		return new GregorianCalendar(year, month - 1, mday, hour, min, sec).getTime();
	}

	// 今から days 日後の日付を返す
	public static Date dayAfter(int days)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		now.add(Calendar.DATE, days);
		return now.getTime();
	}

	/**
	 * ticketコレクションを初期化．登録件数0件．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBN0() throws UnknownHostException {
		initTicketColl();
	}

	/**
	 * DBに接続し，ticketコレクションの中身を空にする．
	 * 接続ホストは，デフォルトでは "localhost:27017"．
	 * {@link #setHost} を使って変更できる．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static DBCollection initTicketColl() throws UnknownHostException {
        MongoClient mongo = new MongoClient(host);
        DB db = mongo.getDB("tem");
		DBCollection coll = db.getCollection("ticket");
		coll.drop(); // 中身を消去
		return coll;
	}

	/**
	 * ticketコレクションを初期化．．
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDB() throws UnknownHostException {
		DBCollection coll = initTicketColl();
	}

	/**
	 *ticketコレクションに1件追加する．
	 */
	public static void addTicket(DBCollection coll,
			String eventId, String eventName, String seatName, Date eventDate, Date ticketStartDate, int fee)
	{
		DBObject ticket = new BasicDBObject();
		ticket.put("eventId", eventId);
		ticket.put("eventName", eventName);
		ticket.put("seatName", seatName);
		ticket.put("eventDate", eventDate);
		ticket.put("ticketStartDate", ticketStartDate);
		ticket.put("fee", fee);
		ticket.put("status", "blank");

		coll.insert(ticket);
	}

	private static void addTicketWithStatus(DBCollection coll,
			String eventId, String eventName, String seatName, Date eventDate, Date ticketStartDate, int fee,String status,String userId)
	{
		DBObject ticket = new BasicDBObject();
		ticket.put("eventId", eventId);
		ticket.put("eventName", eventName);
		ticket.put("seatName", seatName);
		ticket.put("eventDate", eventDate);
		ticket.put("ticketStartDate", ticketStartDate);
		ticket.put("fee", fee);
		ticket.put("status", status);
		ticket.put("owner", userId);

		coll.insert(ticket);
	}



	public static void initDBForDisplayEventDetailControllerFortestExecute01() throws UnknownHostException{

		DBCollection coll = initTicketColl();

		for(int i = 0; i < 3 ; i++){

			String status = "blank";
			String userId = "blank";

			if(i==0) {
				status = "pending";
				userId = "user0";
			}
			if(i==1) {
				status = "reserved";
				userId = "user0";
			}

			for(int j= 0 ;  j<i+1;j++){
				addTicketWithStatus(coll, "eveId", "eveName", "seatName" + Integer.toString(i)
					, newDate(3999, 11, 11, 11, 11, 11)
					, newDate(2999, 11, 11, 11, 11, 11)
					, i*1000 , status, userId);
			}
		}

	}



	/**
	 *ticketコレクションに1件追加する．
	 */
	private static void addTicketOwned(DBCollection coll,
			String eventId, String eventName, String seatName, Date eventDate, Date ticketStartDate, int fee,
			String userId)
	{
		DBObject ticket = new BasicDBObject();
		ticket.put("eventId", eventId);
		ticket.put("eventName", eventName);
		ticket.put("seatName", seatName);
		ticket.put("eventDate", eventDate);
		ticket.put("ticketStartDate", ticketStartDate);
		ticket.put("fee", fee);
		ticket.put("status", "reserved");
		ticket.put("owner", userId);

		coll.insert(ticket);
	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[RegisterEvent]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCRegisterEvent() throws UnknownHostException {

	}

	/**
	 * IntegrationTest用の随時変更するメソッド．
	 * UC[DisplayEventDetail]に対応する。
	 * @throws UnknownHostException 接続先ホスト名を解決できない．
	 */
	public static void initDBForIntegrationTestUCDisplayEventDetail() throws UnknownHostException {

		DBCollection coll = initTicketColl();
		addTicket(coll, "000000000000000000000001", "event0", "S",
				  newDate(2014,  10,  2,  0,  0,  0),
				  newDate(2014,   9,  2,  0,  0,  0),
				  50000);
		addTicket(coll, "000000000000000000000001", "event0", "A",
				  newDate(2014,  10,  2,  0,  0,  0),
				  newDate(2014,   9,  2,  0,  0,  0),
				  10000);
		addTicketOwned(coll, "000000000000000000000001", "event0", "A",
				  newDate(2014,  10,  2,  0,  0,  0),
				  newDate(2014,   9,  2,  0,  0,  0),
				  10000, "user0");


	}

}
