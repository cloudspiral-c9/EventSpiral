package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * @author 2014043
 *
 */
public class SearchingFormTest {
	/**
	 * 表示開始インデックスが0の場合のテスト <br/>
	 * 対象：setStartIndex(int startIndex) <br/>
	 * 条件：引数 0 <br/>
	 * 期待する結果：戻り値 0 <br/>
	 * @throws Exception
	 */
	@Test
	public void testSearchingFrom01() throws Exception {
		SearchingForm Sform = new SearchingForm();
		Sform.setStartIndex (0);
		int test = Sform.getStartIndex ();
		int expected = 0;
		assertEquals(test, expected);
	}
	/**
	 * 表示開始インデックスが0より大きい場合のテスト <br/>
	 * 対象：setStartIndex(int startIndex) <br/>
	 * 条件：引数 99 <br/>
	 * 期待する結果：戻り値 99 <br/>
	 * @throws Exception
	 */
	@Test
	public void testSearchingFrom02() throws Exception {
		SearchingForm Sform = new SearchingForm();
		Sform.setStartIndex (99);
		int test = Sform.getStartIndex ();
		int expected = 99;
		assertEquals(test, expected);
	}
	/**
	 * 表示開始インデックスが負数の場合のテスト <br/>
	 * 対象：setStartIndex(int startIndex) <br/>
	 * 条件：引数 -1 <br/>
	 * 期待する結果： {@link TEMValidationException}が発生 <br/>
	 * @throws TEMValidationException
	 */
	@Test(expected = TEMValidationException.class)
	public void testSearchingFrom03() throws TEMValidationException {
		SearchingForm Sform = new SearchingForm();
		Sform.setLength (-1);
		Sform.validate();
	}
	/**
	 * 1ページあたりの表示件数が10より小さい場合のテスト <br/>
	 * 対象：setLength(int setLength) <br/>
	 * 条件：引数 9 <br/>
	 * 期待する結果： {@link TEMValidationException}が発生  <br/>
	 * @throws TEMValidationException
	 */
	@Test(expected = TEMValidationException.class)
	public void testSearchingFrom04() throws TEMValidationException {
		SearchingForm Sform = new SearchingForm();
		Sform.setLength (9);
		Sform.validate();
	}
	/**
	 * 1ページあたりの表示件数が10の場合のテスト <br/>
	 * 対象：setLength(int setLength) <br/>
	 * 条件：引数 10 <br/>
	 * 期待する結果：戻り値 10 <br/>
	 * @throws Exception
	 */
	@Test
	public void testSearchingFrom05() throws Exception {
		SearchingForm Sform = new SearchingForm();
		Sform.setLength (10);
		int test = Sform.getLength();
		int expected = 10;
		assertEquals(test, expected);
	}
	/**
	 * 1ページあたりの表示件数が100の場合のテスト <br/>
	 * 対象：setLength(int setLength) <br/>
	 * 条件：引数 100 <br/>
	 * 期待する結果：戻り値 100 <br/>
	 * @throws Exception
	 */
	@Test
	public void testSearchingFrom06() throws Exception {
		SearchingForm Sform = new SearchingForm();
		Sform.setLength (100);
		int test = Sform.getLength();
		int expected = 100;
		assertEquals(test, expected);
	}
	/**
	 * 1ページあたりの表示件数が101の場合のテスト <br/>
	 * 対象：setLength(int setLength) <br/>
	 * 条件：引数 101 <br/>
	 * 期待する結果： {@link TEMValidationException}が発生 <br/>
	 * @throws TEMValidationException
	 */
	@Test(expected = TEMValidationException.class)
	public void testSearchingFrom07() throws TEMValidationException {
		SearchingForm Sform = new SearchingForm();
		Sform.setLength (101);
		Sform.validate();
	}
}
