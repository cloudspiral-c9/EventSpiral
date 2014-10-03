package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * @author 2014003
 *
 */
public class BuyingTicketFormTest {

	/**
     * 正常系
     * 対象: {@link BuyingTicketForm#setCount()} <br/>
     * 条件: 事前状態としてcountに1をセット. <br/>
     * 期待する結果: 戻り値が<code>1</code>. <br/>
     * */
    @Test
    public void testSetCount01() throws Exception {
        BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
        buyingTicketForm.setCount(1);
        buyingTicketForm.setEventId("000000000000000000000001");
		buyingTicketForm.setSeatName("test");
        int actual = buyingTicketForm.getCount();
        assertEquals(1, actual);
    }

    /**
     * 異常系:countが0未満
     * 対象: {@link BuyingTicketForm#setCount()}<br/>
     * 条件: 事前状態としてcountに0をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetCount02() throws Exception {
        BuyingTicketForm buyingTicketForm = new BuyingTicketForm();
        buyingTicketForm.setCount(0);
        buyingTicketForm.setEventId("000000000000000000000001");
		buyingTicketForm.setSeatName("test");
        buyingTicketForm.validate();
    }

    /**
     * 正常系
     * 対象: {@link BuyingTicketForm#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに"test"をセット. <br/>
     * 期待する結果: 戻り値が<code>test</code>. <br/>
     * */
    @Test
    public void testSetSeatName01() throws Exception {
        BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
        BuyingTicketForm.setEventId("000000000000000000000001");
        BuyingTicketForm.setSeatName("test");
        BuyingTicketForm.setCount(1);
        String actual = BuyingTicketForm.getSeatName();
        assertEquals("test", actual);
    }

    /**
     * 異常系:seatNameがnull
     * 対象: {@link BuyingTicketForm#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに-1をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetSeatName02() throws Exception {
        BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
        BuyingTicketForm.setEventId("000000000000000000000001");
        BuyingTicketForm.setSeatName(null);
        BuyingTicketForm.setCount(1);
        BuyingTicketForm.validate();
    }

    /**
     * 異常系:seatNameが空文字
     * 対象: {@link BuyingTicketForm#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに""をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetSeatName03() throws Exception {
        BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
        BuyingTicketForm.setEventId("000000000000000000000001");
        BuyingTicketForm.setSeatName("");
        BuyingTicketForm.setCount(1);
        BuyingTicketForm.validate();
    }

    /**
	 * 正常系 <br/>
	 * 対象: {@link BuyingTicketForm#validate} <br/>
	 * 条件: 事前状態として eventIdに000000000000000000000001をセット．<br/>
	 * 期待する結果: 戻り値が<code>true</code>．<br/>
	 */
	@Test
	public void testSetEventId01() throws Exception {
		BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
		BuyingTicketForm.setEventId("000000000000000000000001");
		BuyingTicketForm.setSeatName("test");
		BuyingTicketForm.setCount(1);
		boolean actual = BuyingTicketForm.validate();
		assertTrue(actual);
	}

	/**
	 * eventIdが空文字列 <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として eventIdに空文字をセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testSetEventId02() throws TEMValidationException {
		BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
		BuyingTicketForm.setEventId("");
		BuyingTicketForm.setSeatName("test");
		BuyingTicketForm.setCount(1);
		BuyingTicketForm.validate();
	}

	/**
	 * eventIdがnull <br/>
	 * 対象: {@link IdentifyingAccountForm#validate} <br/>
	 * 条件: 事前状態として eventIdにnullをセット．<br/>
	 * 期待する結果: {@link TEMValidationException}が発生．<br/>
	 */
	@Test(expected = TEMValidationException.class)
	public void testSetEventId03() throws TEMValidationException {
		BuyingTicketForm BuyingTicketForm = new BuyingTicketForm();
		BuyingTicketForm.setEventId(null);
		BuyingTicketForm.setSeatName("test");
		BuyingTicketForm.setCount(1);
		BuyingTicketForm.validate();
	}


}
