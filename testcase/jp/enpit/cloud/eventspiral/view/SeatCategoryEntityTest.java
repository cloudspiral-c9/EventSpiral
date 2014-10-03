package jp.enpit.cloud.eventspiral.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * UC[DisplayEventDetail]
 * testSetCount01(正常系)
 * testSetCount02(異常系:countが0未満)
 * testSetFee01(正常系)
 * testSetFee02(異常系:feeが0未満)
 * testSetSeatName01(正常系)
 * testSetSeatName02(異常系:null)
 * testSetSeatName03(異常系:空文字)
 * @author 2014016
 *
 */
public class SeatCategoryEntityTest {

    /**
     * 正常系
     * 対象: {@link SeatCategoryEntity#setCount()} <br/>
     * 条件: 事前状態としてcountに10をセット. <br/>
     * 期待する結果: 戻り値が<code>10</code>. <br/>
     * */
    @Test
    public void testSetCount01() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setCount(10);
        int actual = seatCategoryEntity.getCount();
        assertEquals(10, actual);
    }

    /**
     * 異常系:countが0未満
     * 対象: {@link SeatCategoryEntity#setCount()}<br/>
     * 条件: 事前状態としてcountに-1をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetCount02() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setCount(-1);
        seatCategoryEntity.validate();
    }

    /**
     * 正常系
     * 対象: {@link SeatCategoryEntity#setFee()} <br/>
     * 条件: 事前状態としてfeeに10をセット. <br/>
     * 期待する結果: 戻り値が<code>10</code>. <br/>
     * */
    @Test
    public void testSetFee01() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setFee(10);
        int actual = seatCategoryEntity.getFee();
        assertEquals(10, actual);
    }

    /**
     * 異常系:feeが0未満
     * 対象: {@link SeatCategoryEntity#setFee()} <br/>
     * 条件: 事前状態としてfeeに-1をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetFee02() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setFee(-1);
        seatCategoryEntity.validate();
    }

    /**
     * 正常系
     * 対象: {@link SeatCategoryEntity#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに"test"をセット. <br/>
     * 期待する結果: 戻り値が<code>test</code>. <br/>
     * */
    @Test
    public void testSetSeatName01() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setSeatName("test");
        String actual = seatCategoryEntity.getSeatName();
        assertEquals("test", actual);
    }

    /**
     * 異常系:seatNameがnull
     * 対象: {@link SeatCategoryEntity#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに-1をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetName02() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setSeatName(null);
        seatCategoryEntity.validate();
    }

    /**
     * 異常系:seatNameが空文字
     * 対象: {@link SeatCategoryEntity#setSeatName()} <br/>
     * 条件: 事前状態としてseatNameに""をセット. <br/>
     * 期待する結果: <code>TEMValidationException</code>が発生. <br/>
     * */
    @Test(expected = TEMValidationException.class)
    public void testSetName03() throws Exception {
        SeatCategoryEntity seatCategoryEntity = new SeatCategoryEntity();
        seatCategoryEntity.setSeatName("");
        seatCategoryEntity.validate();
    }
}
