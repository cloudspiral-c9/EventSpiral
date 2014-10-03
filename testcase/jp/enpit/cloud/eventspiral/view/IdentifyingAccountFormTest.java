package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * UC: ログインする．
 * UC: アカウントを登録する．<br/>
 *   testSetUserId01(): nullを入力 <br/>
 *   testSetUserId02(): 空文字列 <br/>
 *   testSetUserId03(): 変換対象1個 <br/>
 *   testSetUserId04(): 同じ変換対象2個 <br/>
 *   testSetUserId05(): 変換対象のみ全種 <br/>
 *   testSetUserId06(): 非変換対象1字 <br/>
 *   testSetUserId07(): 非変換対象のみ5字 <br/>
 *   testSetUserId08(): 変換対象と非変換対象が混在 <br/>
 *   testSetUserId09(): 多バイト文字混在 <br/>
 *   testSetPass01():   変換対象と非変換対象が混在 <br/>
 *   testValidate01(): 正常系(4字) <br/>
 *   testValidate02(): 正常系(12字) <br/>
 *   testValidate03(): userIdが空文字列 <br/>
 *   testValidate04(): userIdがnull <br/>
 *   testValidate05(): userIdが4字未満 <br/>
 *   testValidate06(): userIdが12字超 <br/>
 *   testValidate07(): userIdが英数字以外を含む <br/>
 *   testValidate08(): passが空文字列 <br/>
 *   testValidate09(): passがnull <br/>
 *   testValidate10(): passが4字未満 <br/>
 *   testValidate11(): passが12字超 <br/>
 *   testValidate12(): passが英数字以外を含む <br/>
 *
 * @author y-takata
 */
public class IdentifyingAccountFormTest {
	/**
	 * nullを入力 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数がnull <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが空文字列．<br/>
	 */
	@Test
	public void testSetUserId01() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId(null);
		assertEquals("", sut.getUserId());
	}

	/**
	 * 空文字列 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が空文字列 ("") <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが空文字列．<br/>
	 */
	@Test
	public void testSetUserId02() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("");
		assertEquals("", sut.getUserId());
	}

	/**
	 * 変換対象1個 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "&" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "&amp;" に等しい．<br/>
	 */
	@Test
	public void testSetUserId03() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("&");
		assertEquals("&amp;", sut.getUserId());
	}

	/**
	 * 同じ変換対象2個 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "&&" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "&amp;&amp;" に等しい．<br/>
	 */
	@Test
	public void testSetUserId04() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("&&");
		assertEquals("&amp;&amp;", sut.getUserId());
	}

	/**
	 * 変換対象のみ全種 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "'\"><&" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "&#39;&quot;&gt;&lt;&amp;" に等しい．<br/>
	 */
	@Test
	public void testSetUserId05() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("'\"><&");
		assertEquals("&#39;&quot;&gt;&lt;&amp;", sut.getUserId());
	}

	/**
	 * 非変換対象1字 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "\\" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "\\" に等しい．<br/>
	 */
	@Test
	public void testSetUserId06() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("\\");
		assertEquals("\\", sut.getUserId());
	}

	/**
	 * 非変換対象のみ5字 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "0@\r\\_" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "0@\r\\_" に等しい．<br/>
	 */
	@Test
	public void testSetUserId07() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("0@\r\\_");
		assertEquals("0@\r\\_", sut.getUserId());
	}

	/**
	 * 変換対象と非変換対象が混在 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "0'@\"\r>\\<_&" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "0&#39;@&#quot;\r&gt;\\&lt;_&amp;" に等しい．<br/>
	 */
	@Test
	public void testSetUserId08() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("0'@\"\r>\\<_&");
		assertEquals("0&#39;@&quot;\r&gt;\\&lt;_&amp;", sut.getUserId());
	}

	/**
	 * 多バイト文字混在 <br/>
	 * 対象: {@link IdentifyingAccountForm#setUserId} <br/>
	 * 条件: 引数が "あ＞&" <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getUserId}で取得した
	 *       ユーザIDが "あ＞&amp;" に等しい．<br/>
	 */
	@Test
	public void testSetUserId09() throws Exception {
		IdentifyingAccountForm sut = new IdentifyingAccountForm();
		sut.setUserId("あ＞&");
		assertEquals("あ＞&amp;", sut.getUserId());
	}

	/**
	 * 変換対象と非変換対象が混在 <br/>
	 * 対象: {@link IdentifyingAccountForm#setPass} <br/>
	 * 条件: 引数はサニタイズ対象文字を含む文字列 ("\"<&'abc'>\"") <br/>
	 * 期待する結果: {@link IdentifyingAccountForm#getPass}で取得した
	 *       ユーザIDが "&quot;&lt;&amp;&#39;abc&#39;&gt;&quot;" に等しい．<br/>
	 */
	@Test
	public void testSetPass01() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setPass("\"<&'abc'>\"");
		String expected = "&quot;&lt;&amp;&#39;abc&#39;&gt;&quot;";
		String actual = idAccForm.getPass();
		assertEquals(expected, actual);
	}

	/**
	 * 正常系(4字) <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に4字の文字列 ("0000"),
	 *       pass にも4字の文字列 ("AAAA") をセット．<br/>
	 * 期待する結果: 戻り値が<code>true</code>．<br/>
	 */
	@Test
	public void testValidate01() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0000");
		idAccForm.setPass  ("AAAA");
		boolean actual = idAccForm.validate();
		assertTrue(actual);
	}

	/**
	 * 正常系(12字) <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に12字の文字列 ("0123456789AB"),
	 *       pass にも12字の文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: 戻り値が<code>true</code>．<br/>
	 */
	@Test
	public void testValidate02() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  ("abcdefg01234");
		boolean actual = idAccForm.validate();
		assertTrue(actual);
	}

	/**
	 * userIdが空文字列 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に空文字列,
	 *       pass に適合文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate03() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("");
		idAccForm.setPass  ("abcdefg01234");
		idAccForm.validate();
	}
	
	/**
	 * userIdがnull <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に<code>null</code>,
	 *       pass に適合文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate04() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId(null);
		idAccForm.setPass  ("abcdefg01234");
		idAccForm.validate();
	}

	/**
	 * userIdが4字未満 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に4字未満の文字列 ("012"),
	 *       pass に適合文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate05() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("012");
		idAccForm.setPass  ("abcdefg01234");
		idAccForm.validate();
	}

	/**
	 * userIdが12字超 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に12字超の文字列 ("0123456789ABC"),
	 *       pass に適合文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate06() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789ABC");
		idAccForm.setPass  ("abcdefg01234");
		idAccForm.validate();
	}

	/**
	 * userIdが英数字以外を含む <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に英数字以外を含む文字列 ("0123456789A_"),
	 *       pass に適合文字列 ("abcdefg01234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate07() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789A_");
		idAccForm.setPass  ("abcdefg01234");
		idAccForm.validate();
	}

	/**
	 * passが空文字列 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に適合文字列 ("0123456789AB"),
	 *       pass に空文字列 ("") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate08() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  ("");
		idAccForm.validate();
	}
	
	/**
	 * passがnull <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に適合文字列 ("0123456789AB"),
	 *       pass に<code>null</code>をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate09() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  (null);
		idAccForm.validate();
	}

	/**
	 * passが4字未満 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に適合文字列 ("0123456789AB"),
	 *       pass に4字未満の文字列 ("abc") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate10() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  ("abc");
		idAccForm.validate();
	}

	/**
	 * passが12字超 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に適合文字列 ("0123456789AB"),
	 *       pass に12字超の文字列 ("abcdefg012345") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate11() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  ("abcdefg012345");
		idAccForm.validate();
	}

	/**
	 * passが英数字以外を含む <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として userId に適合文字列 ("0123456789AB"),
	 *       pass に英数字以外を含む文字列 ("abcdefg@1234") をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate12() throws Exception {
		IdentifyingAccountForm idAccForm = new IdentifyingAccountForm();
		idAccForm.setUserId("0123456789AB");
		idAccForm.setPass  ("abcdefg@1234");
		idAccForm.validate();
	}

}
