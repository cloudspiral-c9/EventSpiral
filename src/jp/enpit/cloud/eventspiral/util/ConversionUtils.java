package jp.enpit.cloud.eventspiral.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;

public class ConversionUtils {

	private static Logger logger = Logger.getLogger(DBUtils.class.getName());

	/**
	 * <p>
	 * Object ←→ Objectの変換（form→model あるいは model←entity のデータ変換に利用）．
	 * 指定されたfromオブジェクトのプロパティを，別の型のdestオブジェクトに移し替える．
	 * destが持たないfromのプロパティは無視される．
	 * </p>
	 *
	 * <pre>
	 * 利用例：
	 *   Event event = getEvent();
	 *   EventDetailEntity entity = new EventDetailEntity();
	 *   ConversionUtils.convert(entity, event);
	 *   return entity;
	 * </pre>
	 * 
	 * @param dest 変換先のオブジェクト
	 * @param from 変換元のオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static void convert(Object dest, Object from){

		// 指定要素のインタフェースを全て調べる
		for (Class<?> c : dest.getClass().getInterfaces()) {
			// List を発見した場合，諦める
			if (c == java.util.List.class) {
				logger.severe("ConversionUtils.convert() はList<T>型オブジェクトの変換に対応していません．");
				throw new RuntimeException();
			}
		}

		// fromの持つ全ての属性をそれぞれ探索
		for (Field fromField : from.getClass().getDeclaredFields()) {
			String fieldName = fromField.getName();
			// カバレッジツールが埋め込む"$jaccoData"というフィールドを無視
			if(fieldName == "$jacocoData") continue;
			
			try {
				// 属性値を取得
				Object fromProperty = PropertyUtils.getProperty(from, fieldName);
				if (fromProperty == null) continue;

				// destでも取得してみる（例外起きるかどうかチェック）．
				try {
					PropertyUtils.getProperty(dest, fieldName);
				} catch (NoSuchMethodException e) {
					// fromにあってdestに対応するプロパティがない場合．無視して次のプロパティを処理する．
					continue;
				}

				// 継承先を全て探索
				for (Class<?> fromImplementedInterface : fromProperty.getClass().getInterfaces()) {

					// List を発見した場合
					if (fromImplementedInterface == java.util.List.class) {
						// dest側のgenericsのClassオブジェクトを取得
						ParameterizedType ptype = (ParameterizedType)dest.getClass().getDeclaredField(fieldName).getGenericType();
						Class<?> cls = (Class<?>)ptype.getActualTypeArguments()[0];

						List<Object> list = new ArrayList<Object>();
						// 全てのリストを変換
						for (Object obj : (List<Object>)fromProperty) {
							Object o = cls.newInstance();
							convert(o, obj);
							list.add(o);
						}
						fromProperty = list;

						// 他の継承クラスは無視
						break;
					}
				}
				// メイン．属性を貼る
				PropertyUtils.setProperty(dest, fieldName, fromProperty);
			} catch (IllegalArgumentException | NoSuchMethodException e) {
				// fromにはあってdest側に対応するプロパティが見つからない場合はエラーを出力してから継続
				logger.warning("対応するプロパティが見つからない，あるいは呼び出すことができませんが処理を引き続き実行します．\n念のため差し替える2つのオブジェクト（destとfrom）のプロパティが一致しているか確認してください．");
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException e) {
				// 基本発生しない
				System.err.println("予期せぬ例外が発生しました．");
				throw new RuntimeException(e);
			}
		}
	}


}
