package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * UC: ログインする．<br/>
 *   testValidate01(): 正常系(1字) <br/>
 *   testValidate02(): 正常系(10字) <br/>
 *   testValidate03(): 空文字列 <br/>
 *   testValidate04(): null <br/>
 *
 * @author y-takata
 */
public class RoleEntityTest {

	/**
	 * 正常系(1字) <br/>
	 * 対象: {@link RoleEntity#validate} <br/>
	 * 条件: 事前状態として role に1字の文字列 "0" をセット．<br/>
	 * 期待する結果: 戻り値が<code>true</code>．<br/>
	 */
	@Test
	public void testValidate01() throws Exception {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRole("0");
		boolean actual = roleEntity.validate();
		assertTrue(actual);
	}

	/**
	 * 正常系(10字) <br/>
	 * 対象: {@link RoleEntity#validate} <br/>
	 * 条件: 事前状態として role に10字の文字列 "abcdef0123" をセット．<br/>
	 * 期待する結果: 戻り値が<code>true</code>．<br/>
	 */
	@Test
	public void testValidate02() throws Exception {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRole("abcdef0123");
		boolean actual = roleEntity.validate();
		assertTrue(actual);
	}

	/**
	 * 空文字列 <br/>
	 * 対象: {@link RoleEntity#validate} <br/>
	 * 条件: 事前状態として role に空文字列をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate03() throws Exception {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRole("");
		roleEntity.validate();
	}

	/**
	 * null <br/>
	 * 対象: {@link RoleEntity#validate} <br/>
	 * 条件: 事前状態として role に<code>null</code>をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testValidate04() throws Exception {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRole(null);
		roleEntity.validate();
	}
}
