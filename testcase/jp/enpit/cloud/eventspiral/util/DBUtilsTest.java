package jp.enpit.cloud.eventspiral.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import jp.enpit.cloud.eventspiral.testutil.AccountInitializer;
import jp.enpit.cloud.eventspiral.testutil.EventInitializer;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * DBUtilsのテスト．
 * 見た目上の網羅率を上げるためなので分岐網羅優先．assertion文はかなり適当．
 * @author shin
 *
 */
public class DBUtilsTest {

	private DBObject validDBObject;
	private NestedTestBean validNestedBean;
	
	@Before
	public void setUp() throws Exception {
		AccountInitializer.initDB();
		EventInitializer.initDB();

		// 正しいDBObject型インスタンスを生成
		BasicDBList beans = new BasicDBList();
		beans.add(new BasicDBObject("age", 10).append("name", "hoge01"));
		beans.add(new BasicDBObject("age", 20).append("name", "hoge02"));
		validDBObject = new BasicDBObject()
				.append("id", 999)
				.append("testBeans", beans);
		
		// 正しいNestedBean型インスタンスを生成
		ArrayList<TestBean> testBeans = new ArrayList<TestBean>();
		testBeans.add(new TestBean(10, "hoge01"));
		testBeans.add(new TestBean(20, "hoge02"));
		validNestedBean = new NestedTestBean(999, testBeans);
	}

	// 正常系
	@Test
	public void testSanitize01() {
		String str = DBUtils.sanitize("msg");
		assertEquals("msg", str);
	}

	// 空文字サニタイズ
	@Test
	public void testSanitize02() {
		String str = DBUtils.sanitize("");
		assertEquals("", str);
	}

	
	// 正常系
	@Test
	public void testAttachProperties01() {
		NestedTestBean dest = new NestedTestBean();
		DBUtils.attachProperties(dest, validDBObject);
		assertEquals(20, dest.getTestBeans().get(1).getAge());
	}

	// DBObject側にありえないプロパティを追加．System.errは出力されるが他のプロパティは正常に移し替えられる．
	@Test
	public void testAttachProperties02() {
		validDBObject.put("dummy", 1);
		NestedTestBean dest = new NestedTestBean();
		DBUtils.attachProperties(dest, validDBObject);
		assertEquals(20, dest.getTestBeans().get(1).getAge());
	}

	// 型のあわないデータ．System.errは出力されるが他のプロパティは正常に移し替えられる．
	@Test
	public void testAttachProperties03() throws Exception {
		validDBObject.put("id", "1"); // type mismatch
		NestedTestBean dest = new NestedTestBean();
		DBUtils.attachProperties(dest, validDBObject);
		assertEquals(20, dest.getTestBeans().get(1).getAge());
	}

	// Mongo固有フィールドのテスト1
	@Test
	public void testAttachProperties04() throws Exception {
		validDBObject.put("_id", 1); // _スタート
		NestedTestBean dest = new NestedTestBean();
		DBUtils.attachProperties(dest, validDBObject);
		assertEquals(20, dest.getTestBeans().get(1).getAge());
	}

	// Mongo固有フィールドのテスト1
	@Test
	public void testAttachProperties05() throws Exception {
		validDBObject.put("$id", 1); // $スタート
		NestedTestBean dest = new NestedTestBean();
		DBUtils.attachProperties(dest, validDBObject);
		assertEquals(20, dest.getTestBeans().get(1).getAge());
	}
	
	// 正常
	@Test
	public void testConvertToDBObject01() {
		DBObject dest = new BasicDBObject();
		DBUtils.convertToDBObject(dest, validNestedBean);
		assertEquals(20, ((DBObject)((BasicDBList)dest.get("testBeans")).get(1)).get("age"));
	}

	// nullオブジェクト
	@Test
	public void testConvertToDBObject02() {
		DBObject dest = new BasicDBObject();
		validNestedBean.setTestBeans(null);
		DBUtils.convertToDBObject(dest, validNestedBean);
	}
}
