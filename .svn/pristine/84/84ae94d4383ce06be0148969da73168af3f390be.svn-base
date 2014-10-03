package jp.enpit.cloud.eventspiral.integrationtesting;

import com.mongodb.DBCollection;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer2;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializer;

public class UCBuyTicketInit {

	/**
	 * ステージング環境のDBを初期化する．各班のステージング環境に合わせてポート番号を修正すること．
	 */

/*
 * ID			Password	Role 			sessionId
-----------------------------------------------------------------------------------------
"admin"     "admin"     "administrator" ""
"promoter1" "promoter1" "promoter" 		""
"user0"     "pass0"     "user"     		""
"user1"     "pass1"     "user"     		""
"user2"     "pass2"     "user"     		""
-----------------------------------------------------------------------------------------

* tem.event
eventId		eventName	eventDate		ticketStartDate		description	promoterId
totalSeats
-----------------------------------------------------------------------------------------
"000"	"event0"		"2014/10/1 00:00"	"2014/8/1 00:00"	"desc0"		"promoter1"
"{“seatName" : "S", "fee" : 50000, "count": 1 },
{"seatName" : "A", "fee" : 10000, "count" : 2 }"
"001"	"event1"		"2014/10/1 00:00"	"2014/9/1 00:00"	"desc0"		"promoter1"
"{“seatName" : "S", "fee" : 50000, "count": 1 }"

 */
	public static void main(String[] args) throws Exception {
		DBCollection coll;
		AccountInitializer.setHost("133.1.236.131:9271");
		coll = AccountInitializer.initAccountColl();
		AccountInitializer.addAccount(coll, "user0", "pass0", "administrator", "");
		AccountInitializer.addAccount(coll, "promoter1", "promoter1", "promoter", "");

		EventInitializer.setHost("133.1.236.131:9271");
		coll = EventInitializer.initEventColl();
		EventInitializer.addEvent(coll, "000", "event0",
				                  EventInitializer.newDate(2014, 10, 1, 0, 0, 0),
				                  EventInitializer.newDate(2014, 8, 1,  0,  0,  0),
				                  "desc0", "promoter1");
		EventInitializer.addEvent(coll, "001", "event1",
				EventInitializer.newDate(2014, 10, 1, 0, 0, 0),
								   EventInitializer.newDate(2014, 9, 1,  0,  0,  0),
								   "desc0", "promoter1");
		EventInitializer.registerSeat(coll, "000",  "S",  50000,  1);
		EventInitializer.registerSeat(coll, "000",  "A",  10000,  2);
		EventInitializer.registerSeat(coll, "001",  "S",  50000,  1);

		TicketInitializer.setHost("133.1.236.131:9271");
		coll = TicketInitializer.initTicketColl();
		TicketInitializer.addTicket(coll, "000",
				                    "event0", "S",
				                    TicketInitializer.newDate(2014, 10,  1,  0,  0,  0),
				                    TicketInitializer.newDate(2014,  8,  1,  0,  0,  0),
				                    50000);
		TicketInitializer.addTicket(coll, "000",
									"event0", "A",
									TicketInitializer.newDate(2014, 10,  1,  0,  0,  0),
									TicketInitializer.newDate(2014,  8,  1,  0,  0,  0),
									10000);
		TicketInitializer.addTicket(coll, "000",
									"event0", "A",
									TicketInitializer.newDate(2014, 10,  1,  0,  0,  0),
									TicketInitializer.newDate(2014,  8,  1,  0,  0,  0),
									10000);
		TicketInitializer.addTicket(coll, "001",
									"event1", "S",
									TicketInitializer.newDate(2014, 10,  1,  0,  0,  0),
									TicketInitializer.newDate(2014,  9,  1,  0,  0,  0),
									50000);

	}

}
