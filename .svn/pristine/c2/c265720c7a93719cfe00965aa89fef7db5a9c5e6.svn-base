package jp.enpit.cloud.eventspiral.integrationtesting;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;

public class UCRegisterEvent {

	private final static String host = "133.1.236.131:9271";

	/**
	 * ステージング環境のDBを初期化する．各班のステージング環境に合わせてポート番号を修正すること．
	 */
	public static void main(String[] args) throws Exception {

		EventInitializer.setHost(host);
		EventInitializer.initDBForIntegrationTestUCRegisterEvent();

		AccountInitializer.setHost(host);
		AccountInitializer.initDBForIntegrationTestUCRegisterEvent();
	}

}
