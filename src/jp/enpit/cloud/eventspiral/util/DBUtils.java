package jp.enpit.cloud.eventspiral.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;


/**
 * Mongodbを利用するためのシングルトンUtilityクラス
 * @author shinsuke-m
 *
 */
public class DBUtils {
	private static Mongo m;
	private static DB db;
	private static final String dbName = "tem";
	private static Logger logger;

	private static DBUtils singleton = new DBUtils();
	/**
	 * シングルトンインスタンスの取得．
	 * @return シングルトンインスタンス
	 */
	public static DBUtils getInstance() {
		return singleton;
	}
	private DBUtils() {
		try {
			m = new MongoClient("localhost", 27017);
//			m = new MongoClient("133.1.236.131", 9271);
			db = m.getDB(dbName);
			logger = Logger.getLogger(DBUtils.class.getName());
		} catch (UnknownHostException | MongoException e) {
			// TODO 自動生成された catch ブロック
			// どうすんだろこれ
			e.printStackTrace();
		}
	}

	/**
	 * DBコネクションの取得．
	 * @return DBコネクションオブジェクト
	 */
	public DB getDb() {
		return db;
	}

	/**
	 * <p>
	 * HTML特殊文字のサニタイジング．
	 * ユーザが入力した文字列のうち，セキュリティ上危険な特殊タグ文字を全て置換する．
	 * 基本的に String に対する setter の全てで呼び出すこと．<br>
	 * ※ユーザが入力した & や <> などの特殊文字をそのまま利用すると，
	 * DBへの命令を直接埋め込まれる危険性がある（XSS，SQLインジェクション）．
	 * </p>
	 *
	 * <pre>
	 * 利用例：
	 *   public void setMessage(String message) {
	 *     this.message = DBUtils.getInstance().sanitize(message);
	 *
	 *     // 直接代入しないこと
	 *     // this.message = message;
	 *   }
	 * </pre>
	 *
	 * @param str サニタイズ対象の文字列
	 * @return サニタイズ済み文字列
	 */
	public static String sanitize(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("&" , "&amp;" );
		str = str.replaceAll("<" , "&lt;"  );
		str = str.replaceAll(">" , "&gt;"  );
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("'" , "&#39;" );
		return str;
	}

	/**
	 * <p>
	 * DBObject → Objectの変換．
	 * MongoDBから取得したDBObjectのプロパティを，対応するObjectの属性に移し替える．
	 * </p>
	 *
	 * <pre>
	 * 利用手順：
	 *   1. DBObjectをMongoDBから取り出す．
	 *   2. 対応するObjectのインスタンス（空）を作る．
	 *   3. 本メソッドを呼び出す．
	 * </pre>
	 *
	 * <pre>
	 * 利用例：
	 *   DBCollection coll = DBUtils.getInstance().getDb().getCollection("event");
	 *   DBObject object = coll.findOne(query);
	 *   Event event = new Event();
	 *   DBUtils.attachProperties(event, object);
	 * </pre>
	 *
	 * @param dest 変換先のObject
	 * @param from 変換元のDBObject
	 */
	@SuppressWarnings("unchecked")
	public static void attachProperties(Object dest, DBObject from){
		ArrayList<String> mismatches = new ArrayList<String>();

		for (String fieldName : from.keySet()) {
			try {
				// Mongo固有のフィールド名を無視する．
				if (fieldName.startsWith("_") || fieldName.startsWith("$")) continue;

				Object fromProperty = from.get(fieldName);

				// ネストオブジェクトのインタフェース全てを再起探索
				for (Class<?> fromImplementedInterface : fromProperty.getClass().getInterfaces()) {

					// DBObject型のネストオブジェクトの場合
					//if (fromImplementedInterface == java.util.List.class) { // DBから取り出す以上List継承クラスはありえない．DBList型で取り出される＝DBObject型で〃
					if (fromImplementedInterface == com.mongodb.DBObject.class) {

						// genericsのClassオブジェクトを取得
						ParameterizedType ptype = (ParameterizedType)dest.getClass()
								.getDeclaredField(fieldName)
								.getGenericType();
						Class<?> destGenericsClass = (Class<?>)ptype.getActualTypeArguments()[0];

						List<Object> list = new ArrayList<Object>();

						// List内の全てのオブジェクトを再起差し替え
						for (DBObject nestedObject : (List<DBObject>)from.get(fieldName)) {
							Object o = destGenericsClass.newInstance();
							attachProperties(o, nestedObject);
							list.add(o);
						}
						fromProperty = list;

						// Listを発見したら他のクラスは無視
						break;
					}
				}

				// メイン部分．実際にdest側にプロパティを差し替え
				PropertyUtils.setProperty(dest, fieldName, fromProperty);
			} catch (IllegalArgumentException | NoSuchMethodException e) {
				// fromにはあってdest側に対応するプロパティが見つからない場合はひとまずメモ．
				mismatches.add(fieldName);
				continue;
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException e) {
				// 基本発生しない
				System.err.println("予期せぬ例外が発生しました．");
				throw new RuntimeException(e);
			}
		}

		// mismatchがあった場合のみwarning
		if (mismatches.size() > 0) {
			logger.warning("以下のプロパティに対して，対応するプロパティが見つからないか呼び出すことができませんでした．\n" +
						"念のため差し替える2つのオブジェクト（destとfrom）のプロパティが一致しているか確認してください．" +
						mismatches
					);
		}
	}
	/**
	 * <p>
	 * Object → DBObjectの変換．
	 * 作成したObjectのプロパティを，MongoDBのDBObjectに移し替える．
	 * </p>
	 *
	 * <pre>
	 * 利用手順：
	 *   1. 変換したいObjectを取得する．
	 *   2. DBObject（空）を生成する．
	 *   3. 本メソッドを呼び出す．
	 * </pre>
	 *
	 * <pre>
	 * 利用例：
	 *   Event event = getEvent(query);
	 *   DBObject object = new BasicDBObject();
	 *   DBUtils.convertToDBObject(object, event);
	 * </pre>
	 *
	 * @param dest 変換先のDBObject
	 * @param from 変換元のObject
	 */
	@SuppressWarnings("unchecked")
	public static void convertToDBObject(DBObject dest, Object from){
		ArrayList<String> mismatches = new ArrayList<String>();

		// from側の属性から探索する
		Field[] fromFields = from.getClass().getDeclaredFields();
		for (Field fromField : fromFields) {
			String fieldName = fromField.getName();

			// カバレッジツールが埋め込む"$jaccoData"というフィールドを無視
			if(fieldName == "$jacocoData") continue;

			try {
				Object fromProperty = PropertyUtils.getProperty(from, fieldName);

				// nullオブジェクトは無視（デフォルトコンストラクタの振る舞いによっては発生）
				if (fromProperty == null) continue;

				// ネストオブジェクトのインタフェース全てを再起探索
				for (Class<?> fromImplementedInterface : fromProperty.getClass().getInterfaces()) {

					// Listの継承クラスを発見
					if (fromImplementedInterface == java.util.List.class) {
						BasicDBList list = new BasicDBList();

						// 再帰呼び出しで変換
						for (Object p : (List<Object>)fromProperty) {
							DBObject o = new BasicDBObject();
							convertToDBObject(o, p);
							list.add(o);
						}
						fromProperty = list;

						// 他の継承クラスは無視
						break;
					}
				}
				// 差し替え
				dest.put(fieldName, fromProperty);

			} catch (IllegalAccessException | NoSuchMethodException e) {
				mismatches.add(fieldName);
				continue;
			} catch (InvocationTargetException e) {
				// 基本発生しない
				System.err.println("予期せぬ例外が発生しました．");
				throw new RuntimeException(e);
			}
		}
		// mismatchがあった場合のみwarning
		if (mismatches.size() > 0) {
			logger.warning("以下のプロパティに対して，対応するプロパティが見つからないか呼び出すことができませんでした．\n" +
						"念のため差し替える2つのオブジェクト（destとfrom）のプロパティが一致しているか確認してください．" +
						mismatches
					);
		}
	}
}
