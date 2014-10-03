package jp.enpit.cloud.eventspiral.integrationtesting;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializerUCDisplayRegisteredEventList;

public class UCDisplayRegisteredEventListInit {


    /**
     * ステージング環境のDBを初期化する．各班のステージング環境に合わせてポート番号を修正すること．
     */
    public static void main(String[] args) throws Exception {
        AccountInitializer.setHost("133.1.236.131:9271");
       EventInitializerUCDisplayRegisteredEventList.setHost("133.1.236.131:9271");
       // AccountInitializer.setHost("localhost");
        //EventInitializerUCDisplayRegisteredEventList.setHost("localhost");
        AccountInitializer.initDB();

        if(args[0].equals("first")) {
            EventInitializerUCDisplayRegisteredEventList.initDB13Events();
        } else if (args[0].equals("second")){
            EventInitializerUCDisplayRegisteredEventList.initDBN0();
        } else {
            EventInitializerUCDisplayRegisteredEventList.initDB25Events();
        }
    }
}
