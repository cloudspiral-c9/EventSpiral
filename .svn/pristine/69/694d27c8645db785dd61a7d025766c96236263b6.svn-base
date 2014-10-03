package jp.enpit.cloud.eventspiral.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Logger;

import org.bson.types.ObjectId;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.util.DBUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * EventコレクションのModelクラス
 * @author s-egawa
 * @author 2014043（荒木）
 * @author 2014024(西)
 */
public class EventModel {
    private final String DB_EVENT_COLLECTION = "event";
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
    public EventModel() {
        logger = Logger.getLogger(getClass().getName());
        db = DBUtils.getInstance().getDb();
        coll = db.getCollection(DB_EVENT_COLLECTION);
    }

    /**
     * 未来のイベント情報一覧を取得する.
     * <ol>
     *   <li>未来のイベント情報（eventDate が現在時刻よりあと）をstartIndex、lengthを指定して、イベント開催日時およびイベント名でソートしてデータベースから取得する．</li>
     *   {@code db.event.find({ "eventDate" : { "$gte" : new Date() }})
                        .sort({ "eventDate" : 1, "eventName" : 1 })
                        .skip(startIndex).limit(length)}
     *   <li>上記コマンドを実現するためには，下記ステップでの実装が必要
     *       DBObject型の変数を作成（ここでは変数名をqueryとする）
     *     <ol>
     *       <li>queryにnew BasicDBObjectを代入．ここでBasicDBObjectのコンストラクタに"eventDate"とnew BasicDBObject("$gte", new Date())を引数として与える</li>
     *       <li>ソート条件となるDBObjectを作成．DBObject sortKeys = new BasicDBObject();といった形でDBObjectを生成し，sortKeys.put("eventDate", 1);のように2つのソート基準を追加すれば良い</li>
     *       <li>必要なDBObjectを生成したら，DBCursorオブジェクトを作成し，findの結果を代入する．DBCursor cursor = coll.find(query).sort(sortKeys).skip(?).limit(?);というメソッドチェーンの形でfindの結果を取得できる</li>
     *     </ol>
     *   </li>
     *   <li>Eventオブジェクトを格納するリスト(List型の変数)を生成する．</li>
     *   <li>検索結果それぞれから，Eventオブジェクトを構築し，List型の変数に登録する．</li>
     *   <li>ここでDBCursor型の変数cursorはfor(DBObject dbo : corsor){}で全データを取得できる</li>
     *   <li>Event型の変数を宣言し，DBUtils.attachProperties(event, dbo);と呼び出すだけで，dboの値をEvent型の変数eventに代入できる(1つ1つ代入しても構わない)</li>
     *   <li>List型の変数を返す．</li>
     *   <li><code>MongoException</code>が発生した場合：
     *     <ol>
     *       <li>発生した例外を<code>TEMFatalException</code>にラップして投げる．</li>
     *     </ol>
     *   </li>
     * </ol>
     * @param startIndex 取得開始インデックス
     * @param length 取得数
     * @return 未来のイベント情報（Eventオブジェクト）のリスト
     * @throws TEMFatalException MongoExceptionが発生した場合．
     */
    public java.util.List<Event> getFutureEvents(int startIndex,int length)
              throws TEMFatalException{
        logger.info("EventModel.getFutureEvents");

        try {
            DBObject query = new BasicDBObject("eventDate",new BasicDBObject("$gte",new Date()));
            DBObject sortKeys = new BasicDBObject();
            sortKeys.put("eventDate", 1);
            sortKeys.put("eventName", 1);

            DBCursor cursor = coll.find(query).sort(sortKeys).skip(startIndex).limit(length);

            List<Event> eventList = new ArrayList<Event>();
            for(DBObject dbo : cursor){
                Event event = new Event();
                DBUtils.attachProperties(event, dbo);
                eventList.add(event);
            }

            return eventList;
        } catch (MongoException e) {
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
    }

    /**
     * 未来のイベントのトータル数を取得する.
     * <ol>
     *   <li>未来のイベント情報（eventDate が現在時刻よりあと）をデータベースから検索し，件数を数える．</li>
     *   {@code db.event.find({ "eventDate" : { "$gte" : new Date() }}).count()}
     *   <li>このmongoクエリをjava実装に変換するには・・・
     *     <ol>
     *       <li>findの中の{}の数に注目する</li>
     *       <li>{}ごとにBasicDBObjectを作成する．ここでは，DBObject query = new BasicDBObject("eventDate", new BasicDBObject("$gte", new Date()));という書き方か，内側のnew BasicDBObjectを別の変数で宣言しておいて利用するのでもどちらでも良い</li>
     *       <li>queryが完成したら，coll.findを呼び出し，引数にqueryを渡す．さらに，count()をfindの後に繋げるだけで，イベントの数を返すことができる</li>
     *     </ol>
     *   </li>
     *   <li><code>MongoException</code>が発生した場合：
     *     <ol>
     *       <li>発生した例外を<code>TEMFatalException</code>にラップして投げる．</li>
     *     </ol>
     *   </li>
     * </ol>
     * @return 未来のイベントのトータル数
     * @throws TEMFatalException MongoExceptionが発生した場合．
     */
    public int getFutureEventTotalCount() throws TEMFatalException{
        logger.info("EventModel.getTotalCount");

        try {
            DBObject query = new BasicDBObject("eventDate", new BasicDBObject("$gte", new Date()));
            return coll.find(query).count();
        } catch (MongoException e) {
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
    }


    /**
     * eventIDに対するイベント情報をデータベースに登録する．
     * @return イベント情報（Eventオブジェクト）
     * @throws TEMFatalException MongoExceptionが発生した場合．
     * @throws EventAlreadyRegistredException すでにイベントが登録済みの場合．
     * @throws OutOfDateException イベントの開催日時が過去の場合
     */
    public Event registerEvent(String eventName, Date eventDate, Date ticketStartDate, String description, String promoterId)
                    throws TEMFatalException, EventAlreadyRegisteredException, OutOfDateException{
        logger.info("EventModel.registerEvent");

        // 重複チェック
        DBObject query = new BasicDBObject();
        query.put("eventName", eventName);
        query.put("eventDate", eventDate);
        if (coll.findOne(query) != null) {
            String msg = "Event:" + eventName + " has arleady registered.";
            logger.warning("EventModel.registerEvent: " + msg);
            throw new EventAlreadyRegisteredException(msg);
        }

        // 未来のイベントかどうかのチェック
        if (!eventDate.after(new Date())){
            String msg = "EventDate:" + eventDate + " is past now.";
            logger.warning("EventModel.registerEvent: " + msg);
            throw new OutOfDateException(msg);
        };

        ObjectId id = new ObjectId();
        String eventId = id.toString();

        Event event = new Event();
        event.setEventId(eventId);
        event.setEventName (eventName);
        event.setEventDate (eventDate);
        event.setTicketStartDate(ticketStartDate);
        event.setDescription(description);
        event.setPromoterId(promoterId);

        DBObject object = new BasicDBObject();
        DBUtils.convertToDBObject(object, event);
        try {
            coll.insert(object);
        } catch (MongoException e){
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
        return event;
    }


    /**
     * eventIdに対応するイベント情報の座席情報をデータベースに登録する．
     * @return 登録したシート情報
     * @throws TEMFatalException MongoExceptionが発生した場合．
     * @throws EventNotFoundException 指定されたeventIdを持つイベントが見つからない場合
     * @throws SeatCategoryAlreadyRegisteredException 指定されれたeventIdを持つイベントにseatNameのシートが既に登録されていた場合
     */
    @SuppressWarnings("unchecked")
    public SeatCategory registerSeatCategory(java.lang.String eventId,
            java.lang.String seatName,
            int count,
            int fee)
              throws TEMFatalException,
                     EventNotFoundException,
                     SeatCategoryAlreadyRegisteredException {
        logger.info("EventModel.getRegisterSeatCategory");

        DBObject query = new BasicDBObject("eventId", eventId);
        // eventIdを持つイベントが見つからなかった場合
        DBObject event = coll.findOne(query);
        if (event == null) {
            String msg = "EventId:" + eventId + " has not found.";
            logger.warning("EventModel.registerEvent: " + msg);
            throw new EventNotFoundException(msg);
        }

        //重複チェック
       List<DBObject> totalSeats = (List<DBObject>)event.get("totalSeats");
       if(totalSeats == null) {
           totalSeats = new ArrayList<DBObject>();
       }
        for(DBObject seat : totalSeats) {
                String sName = seat.get("seatName").toString();
                if(sName.equals(seatName)){
                    String msg = "seatName:" + seatName + " has arleady registered.";
                    logger.warning("EventModel.registerEvent: " + msg);
                    throw new  SeatCategoryAlreadyRegisteredException(msg);
                }
        }

        // seatName, count, feeから座席情報（SeatCategoryオブジェクト）を構築する．
        SeatCategory seat = new SeatCategory();
        seat.setCount(count);
        seat.setFee(fee);
        seat.setSeatName(seatName);

        DBObject object = new BasicDBObject();
        DBUtils.convertToDBObject(object, seat);

        totalSeats.add(object);
        DBObject assign = new BasicDBObject("totalSeats", totalSeats);
        DBObject update = new BasicDBObject("$set", assign);

        try {
            coll.update(query, update, true, false);
        } catch (MongoException e){
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
        return seat;
    }


    /**
     * eventIdに対応するイベント情報を取得する．
     * @return イベント情報
     * @throws TEMFatalException MongoExceptionが発生した場合．
     * @throws EventNotFoundException 指定されたeventIdを持つイベントが見つからない場合
     */
    public Event getEvent(java.lang.String eventId)
            throws TEMFatalException,
                   EventNotFoundException {
        logger.info("EventModel.getFutureEvents");

        DBObject query = new BasicDBObject("eventId", eventId);
        DBObject search = new BasicDBObject();

        if ((search = coll.findOne(query)) == null) {
            String msg = "EventId:" + eventId + " has not found.";
            logger.warning("EventModel.registerEvent: " + msg);
            throw new EventNotFoundException(msg);
        }

        Event event = new Event();
        try {
            DBUtils.attachProperties(event, search);
        } catch (MongoException e){
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
        return event;
    }

    /**
     * userIdに対応するイベント情報の一覧をstartIndexからlength分まで取得する
     * <ol>
     *   <li>userIdをキーにして、startIndex、lengthを指定して、 イベント開催日時およびイベント名でソートしてデータベースから取得する．</li>
     *   {@code db.event.find({ "promoterId" : userId })
     *                          .sort({ "eventDate" : 1, "eventName" : 1 })
     *                          .skip(startIndex).limit(limit)
     *   <li>上記コマンドを実現するためには，下記ステップでの実装が必要
     *       DBObject型の変数を作成（ここでは変数名をqueryとする）
     *     <ol>
     *       <li>queryにnew BasicDBObjectを代入．ここでBasicDBObjectのコンストラクタに"promoterId"と userIdを引数として与える</li>
     *       <li>ソート条件となるDBObjectを作成．DBObject sortKeys = new BasicDBObject();といった形でDBObjectを生成し，sortKeys.put("eventDate", 1);のように2つのソート基準を追加すれば良い</li>
     *       <li>必要なDBObjectを生成したら，DBCursorオブジェクトを作成し，findの結果を代入する．DBCursor cursor = coll.find(query).sort(sortKeys).skip(?).limit(?);というメソッドチェーンの形でfindの結果を取得できる</li>
     *     </ol>
     *   </li>
     *   <li>Eventオブジェクトを格納するリスト(List型の変数)を生成する．</li>
     *   <li>検索結果それぞれから，Eventオブジェクトを構築し，List型の変数に登録する．</li>
     *   <li>ここでDBCursor型の変数cursorはfor(DBObject dbo : corsor){}で全データを取得できる</li>
     *   <li>Event型の変数を宣言し，DBUtils.attachProperties(event, dbo);と呼び出すだけで，dboの値をEvent型の変数eventに代入できる(1つ1つ代入しても構わない)</li>
     *   <li>List型の変数を返す．</li>
     *   <li><code>MongoException</code>が発生した場合：
     *     <ol>
     *       <li>発生した例外を<code>TEMFatalException</code>にラップして投げる．</li>
     *     </ol>
     *   </li>
     * </ol>
     * @param startIndex 取得開始インデックス
     * @param length 取得数
     * @return 指定ユーザの全イベント情報（Eventオブジェクト）のリスト
     * @throws TEMFatalException MongoExceptionが発生した場合．
     */
    public java.util.List<Event> getEvents(int startIndex,int length,
                                            java.lang.String userId)
                                            throws TEMFatalException{
        logger.info("EventModel.getEvents");
        try {
            DBObject query = new BasicDBObject("promoterId",userId);
            DBObject sortKeys = new BasicDBObject();
            sortKeys.put("eventDate", 1);
            sortKeys.put("eventName", 1);

            DBCursor cursor = coll.find(query).sort(sortKeys).skip(startIndex).limit(length);

            List<Event> eventList = new ArrayList<Event>();
            for(DBObject dbo : cursor){
                Event event = new Event();
                DBUtils.attachProperties(event, dbo);
                eventList.add(event);
            }

            return eventList;
        } catch (MongoException e) {
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
    }


    /**
     * userIdに対応するイベント登録数を取得する
     * <ol>
     *   <li>引数として受け取ったuserIdを promoterId としてデータベースからイベントを検索し、件数を数える．</li>
     *   {@code db.event.find({ "promoterId" : userId}).count()}
     *   <li><code>MongoException</code>が発生した場合：
     *     <ol>
     *       <li>発生した例外を<code>TEMFatalException</code>にラップして投げる．</li>
     *     </ol>
     *   </li>
     * </ol>
     * @return 指定ユーザのイベント登録数
     * @throws TEMFatalException MongoExceptionが発生した場合．
     */
    public int getTotalCount(java.lang.String userId)
                            throws TEMFatalException{
        logger.info("getTotalCount");
        try {
            DBObject query = new BasicDBObject("promoterId", userId);
            return coll.find(query).count();
        } catch (MongoException e) {
            logger.severe(e.getMessage());
            throw new TEMFatalException(e);
        }
    }

}
