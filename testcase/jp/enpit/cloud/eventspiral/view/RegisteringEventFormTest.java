package jp.enpit.cloud.eventspiral.view;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * testSetDescription01(正常系)
 * testSetDescription02(異常系:空文字)
 * testSetEventDate01(正常系)
 * testSetEventDate02(異常系:null)
 * testSetEventDate03(異常系:イベント開催日時が過去)
 * testSetEventName01(正常系)
 * testSetEventName02(異常系:空文字)
 * testSetTicketStartDate01(正常系)
 * testSetTicketStartDate02(異常系:null)
 * testSetTicketStartDate03(異常系:チケット販売日時が過去)
 * testValidate01(異常系:チケット販売日がイベントの開始日時の後)
 * @author 2014016
 *
 */
public class RegisteringEventFormTest {

    /**
     * 正常系
     * 対象: {@link:RegisteringEventForm#setDescription()} <br />
     * 条件: descriptionに"test"をセット<br />
     * 期待する結果: {@link:RegisteringEventForm#getDescription()}で取得した
     *  descriptionがsetした値と等しい <br />
     */
    @Test
    public void testSetDescription01() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setDescription("test");
        assertEquals("test", eForm.getDescription());
    }

    /**
     * 異常系:空文字
     * 対象: {@link:RegisteringEventForm#setDescription()} <br />
     * 条件: descriptionに""をセット<br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetDescription02() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setDescription("");
        eForm.validate();
    }

    /**
     * 正常系
     * 対象: {@link:RegisteringEventForm#setEventDate()} <br />
     * 条件: 事前状態としてeventDateにDate型(2020/1/1/ 0:00:00)をセット. <br />
     * 期待する結果: {@link:RegisteringEventForm#getEventDate()}で取得した
     *  値が<code>2020/1/1/ 0:00:00</code>と等しい <br />
     */
    @Test
    public void testSetEventDate01() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 1, 1, 0, 00, 00);
        // Calendar形からDate形に変換
        Date date = cal.getTime();
        eForm.setEventDate(date);

        Date actual = eForm.getEventDate();
        assertEquals(date, actual);
    }

    /**
     * 異常系:null
     * 対象: {@link:RegisteringEventForm#setEventDate()} <br />
     * 条件: eventDateがnulll <br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetEventDate02() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setEventDate(null);
        eForm.validate();
    }

    /**
     * 異常系:イベント開始日時が過去
     * 対象: {@link:RegisteringEventForm#setEventDate()} <br />
     * 条件: 事前状態としてeventDateにDate型(1970/1/1/ 0:00:00)をセット. <br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetEventDate03() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setEventDate(new Date(0));
        eForm.validate();
    }

    /**
     * 正常系
     * 対象: {@link:RegisteringEventForm#setEventName()} <br />
     * 条件: eventNameに"test"をセット<br />
     * 期待する結果: {@link:RegisteringEventForm#getEventName()}で取得した
     *  イベント名がsetした値と等しい <br />
     */
    @Test
    public void testSetEventName01() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setEventName("test");
        assertEquals("test", eForm.getEventName());
    }

    /**
     * 異常系:空文字
     * 対象: {@link:RegisteringEventForm#setEventName()} <br />
     * 条件: eventNameに空文字をセット<br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetEventName02() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setEventName("");
        eForm.validate();
    }

    /**
     * 正常系
     * 対象: {@link:RegisteringEventForm#setTicketStartDate()} <br />
     * 条件: 事前状態としてticketStartDateにDate型(2020/1/1/ 0:00:00)をセット. <br />
     * 期待する結果: {@link:RegisteringEventForm#getTicketStartDate()}で取得した
     *  値が<code>2020/1/1/ 0:00:00</code>と等しい <br />
     */
    @Test
    public void testSetTicketStartDate01() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 1, 1, 0, 00, 00);
        // Calendar形からDate形に変換
        Date date = cal.getTime();
        eForm.setTicketStartDate(date);
        Date actual = eForm.getTicketStartDate();
        assertEquals(date, actual);
    }

    /**
     * 異常系:null
     * 対象: {@link:RegisteringEventForm#setTicketStartDate()} <br />
     * 条件: ticketStartDateがnulll <br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetTicketStartDate02() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setTicketStartDate(null);
        eForm.validate();
    }

    /**
     * 異常系:チケット販売開始日時が過去
     * 対象: {@link:RegisteringEventForm#setTicketStartDate()} <br />
     * 条件: 事前状態としてticketStartDateにDate型(1970/1/1/ 0:00:00)をセット. <br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testSetTicketStartDate03() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();
        eForm.setTicketStartDate(new Date(0));
        eForm.validate();
    }


    /**
     * 異常系:チケット販売日がイベントの開始日時の後
     * 対象: {@link:RegisteringEventForm#setTicketStartDate()} <br />
     * 条件: 事前状態としてeventDateにDate型(2014/1/1 0:00:00),ticketStartDateにDate型(2015/1/1/ 0:00:00)をセット. <br />
     * 期待する結果: <code>TEMValidationException</code>が発生<br />
     */
    @Test(expected = TEMValidationException.class)
    public void testValidate01() throws Exception {
        RegisteringEventForm eForm = new RegisteringEventForm();

        Calendar eventCal = Calendar.getInstance();
        Calendar ticketCal = Calendar.getInstance();
        eventCal.set(2014, 1, 1, 0, 00, 00);
        ticketCal.set(2015, 1, 1, 0, 00, 00);
        // Calendar形からDate形に変換
        Date eventDate = eventCal.getTime();
        Date ticketDate = ticketCal.getTime();

        eForm.setEventDate(eventDate);
        eForm.setTicketStartDate(ticketDate);

        eForm.validate();
    }
}
