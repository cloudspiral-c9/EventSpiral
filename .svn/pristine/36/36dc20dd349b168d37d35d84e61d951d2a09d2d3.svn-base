package jp.enpit.cloud.eventspiral.integrationtesting;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer2;

public class UCLoginInit {

	/**
	 * ステージング環境のDBを初期化する．各班のステージング環境に合わせてポート番号を修正すること．
	 */
	public static void main(String[] args) throws Exception {
		AccountInitializer.setHost("133.1.236.131:9271");
		AccountInitializer.initDBForIntegrationTestUCLogin();

		EventInitializer.setHost("133.1.236.131:9271");
		EventInitializer.initDBForIntegrationTestUCLogin();

		EventInitializer2.setHost("133.1.236.131:9271");
		EventInitializer2.initDBForIntegrationTestUCLogin();
	}

}
