package jp.enpit.cloud.eventspiral.integrationtesting;

import com.mongodb.DBCollection;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer2;
import jp.enpit.cloud.eventspiral.testutil.TicketInitializer;

public class UCRegisterSeatCategoryInit {

	/**
	 * ステージング環境のDBを初期化する．各班のステージング環境に合わせてポート番号を修正すること．
	 */

	public static void main(String[] args) throws Exception {
		DBCollection coll;
		AccountInitializer.setHost("133.1.236.131:9271");
		coll = AccountInitializer.initAccountColl();
		AccountInitializer.addAccount(coll, "user0", "pass0", "administrator", "");
		AccountInitializer.addAccount(coll, "promoter1", "promoter1", "promoter", "");

		EventInitializer.setHost("133.1.236.131:9271");
		coll = EventInitializer.initEventColl();

		TicketInitializer.setHost("133.1.236.131:9271");
		coll = TicketInitializer.initTicketColl();
		TicketInitializer.addTicket(coll, "000000000000000000000001",
				                    "event0", "A",
				                    TicketInitializer.newDate(2014, 10,  2,  0,  0,  0),
				                    TicketInitializer.newDate(2014,  9,  2,  0,  0,  0),
				                    10000);
	}

}
