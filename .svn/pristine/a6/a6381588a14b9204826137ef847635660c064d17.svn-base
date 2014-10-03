package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 *
 * @author 2014003
 *
 */
public class EventInfoEntityTest {

	/**
	 * 正常系（6文字） 対象: {@link EventInfoEntity#setDescription()} <br/>
	 * 条件: 事前状態としてdescriptionに6字の文字列("desc01")をセット. <br/>
	 * 期待する結果: 戻り値が<code>desc01</code>. <br/>
	 * */
	@Test
	public void testSetDescription01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setDescription("desc01");
		String actual = eventInfoEntity.getDescription();
		String expected = "desc01";
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（0文字） 対象: {@link EventInfoEntity#setDescription()} <br/>
	 * 条件: 事前状態としてdescriptionに空文字("")をセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetDescription02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setDescription("");
		eventInfoEntity.validate();
	}

	/**
	 * 異常系（null） 対象: {@link EventInfoEntity#setDescription()} <br/>
	 * 条件: 事前状態としてdescriptionにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetDescription03() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setDescription(null);
		eventInfoEntity.validate();
	}

	/**
	 * 正常系 対象: {@link EventInfoEntity#setEventDate()} <br/>
	 * 条件: 事前状態としてeventDateにDate型(1970/1/1/ 0:00:00)をセット. <br/>
	 * 期待する結果: 戻り値が<code>1970/1/1/ 0:00:00</code>. <br/>
	 * */
	@Test
	public void testSetEventDate01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventDate(new Date(0));
		Date actual = eventInfoEntity.getEventDate();
		Date expected = new Date(0);
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（null) 対象: {@link EventInfoEntity#setEventDate()} <br/>
	 * 条件: 事前状態としてeventDateにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetEventDate02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventDate(null);
		eventInfoEntity.validate();
	}

	/**
	 * 正常系（4文字） 対象: {@link EventInfoEntity#setEventId()} <br/>
	 * 条件: 事前状態としてeventIdに4字の文字列("id01")をセット. <br/>
	 * 期待する結果: 戻り値が<code>id01</code>. <br/>
	 * */
	@Test
	public void testSetEventId01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventId("id01");
		String actual = eventInfoEntity.getEventId();
		String expected = "id01";
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（0文字） 対象: {@link EventInfoEntity#setEventId()} <br/>
	 * 条件: 事前状態としてeventIdに空文字("")をセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetEventId02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventId("");
		eventInfoEntity.validate();
	}

	/**
	 * 異常系（null） 対象: {@link EventInfoEntity#setEventId()} <br/>
	 * 条件: 事前状態としてeventIdにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetEventId03() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventId(null);
		eventInfoEntity.validate();
	}


	/**
	 * 正常系（6文字） 対象: {@link EventInfoEntity#setEventName()} <br/>
	 * 条件: 事前状態としてeventNameに6字の文字列("name01")をセット. <br/>
	 * 期待する結果: 戻り値が<code>name01</code>. <br/>
	 * */
	@Test
	public void testSetEventName01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventName("name01");
		String actual = eventInfoEntity.getEventName();
		String expected = "name01";
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（0文字） 対象: {@link EventInfoEntity#setEventName()} <br/>
	 * 条件: 事前状態としてeventNameに空文字("")をセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetEventName02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventName("");
		eventInfoEntity.validate();
	}

	/**
	 * 異常系（null） 対象: {@link EventInfoEntity#setEventName()} <br/>
	 * 条件: 事前状態としてeventNameにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetEventName03() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setEventName(null);
		eventInfoEntity.validate();
	}

	/**
	 * 正常系 対象: {@link EventInfoEntity#setTicketStartDate()} <br/>
	 * 条件: 事前状態としてticketStartDateにDate型(1970/1/1/ 0:00:00)をセット. <br/>
	 * 期待する結果: 戻り値が<code>1970/1/1/ 0:00:00</code>. <br/>
	 * */
	@Test
	public void testSetTicketStartDate01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setTicketStartDate(new Date(0));
		Date actual = eventInfoEntity.getTicketStartDate();
		Date expected = new Date(0);
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（null) 対象: {@link EventInfoEntity#setTicketStartDate()} <br/>
	 * 条件: 事前状態としてticketStartDateにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetTicketStartDate02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setTicketStartDate(null);
		eventInfoEntity.validate();
	}

	/**
	 * 正常系（4文字） 対象: {@link promoterInfoEntity#setPromoterId()} <br/>
	 * 条件: 事前状態としてpromoterIdに4字の文字列("id01")をセット. <br/>
	 * 期待する結果: 戻り値が<code>id01</code>. <br/>
	 * */
	@Test
	public void testSetPromoterId01() {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setPromoterId("id01");
		String actual = eventInfoEntity.getPromoterId();
		String expected = "id01";
		assertEquals(expected, actual);
	}

	/**
	 * 異常系（0文字） 対象: {@link promoterInfoEntity#setPromoterId()} <br/>
	 * 条件: 事前状態としてpromoterIdに空文字("")をセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetPromoterId02() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setPromoterId("");
		eventInfoEntity.validate();
	}

	/**
	 * 異常系（null） 対象: {@link promoterInfoEntity#setPromoterId()} <br/>
	 * 条件: 事前状態としてpromoterIdにnullをセット. <br/>
	 * 期待する結果: <code>TEMValidationException</code>を投げる. <br/>
	 *
	 * @throws TEMValidationException
	 * */
	@Test(expected = TEMValidationException.class)
	public void testSetPromoterId03() throws TEMValidationException {
		EventInfoEntity eventInfoEntity = new EventInfoEntity();
		eventInfoEntity.setPromoterId(null);
		eventInfoEntity.validate();
	}


}
