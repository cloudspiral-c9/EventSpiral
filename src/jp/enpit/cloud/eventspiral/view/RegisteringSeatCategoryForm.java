package jp.enpit.cloud.eventspiral.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 新規シートを登録するときのフォーム
 * @author 2014016
 *
 */
public class RegisteringSeatCategoryForm extends AbstractForm {

    /**
     * イベントID．
     * チェック項目は以下の通り．
     * <ul>
     *      <li>nullではない．</li>
     *      <li>空文字ではない．</li>
     * <ul>
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    String eventId;

    /**
     * 座席名．
     * チェック項目は以下の通り．
     * <ul>
     *      <li>nullではない．</li>
     *      <li>空文字ではない．</li>
     * <ul>
     */
    @NotNull(message="必須項目が未入力です")
    @NotEmpty(message="必須項目が未入力です")
    String seatName;

    /**
     * 単価．
     * チェック項目は以下の通り．
     * <ul>
     *      <li>0以上である．</li>
     *      <li>100万以下である．</li>
     * <ul>
     */
    @Min(value=0L, message="単価は0から100万以内の整数を入力してください")
    @Max(value=1000000L, message="単価は0から100万以内の整数を入力してください")
    int fee;

    /**
     * 席数．
     * チェック項目は以下の通り．
     * <ul>
     *      <li>1以上である．</li>
     *      <li>1億以下である．</li>
     * <ul>
     */
    @Min(value=1L, message="座席数は1から1億以内の整数を入力してください")
    @Max(value=100000000L, message="座席数は1から1億以内の整数を入力してください")
    int count;


    /**
     * <ol>
     *      <li>イベントIDを取得する</li>
     * </ol>
     * @return イベントID
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * <ol>
     *      <li>eventIdをサニタイスしてフィールドへ登録する．</li>
     * </ol>
     * @param eventId イベントID
     */
    public void setEventId(String eventId) {
        this.eventId = DBUtils.sanitize(eventId);;
    }

    /**
     * <ol>
     *      <li>座席名を取得する</li>
     * </ol>
     * @return 座席名
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * <ol>
     *      <li>seatNameをサニタイスしてフィールドへ登録する．</li>
     * </ol>
     * @param seatName 座席名
     */
    public void setSeatName(String seatName) {
        this.seatName = DBUtils.sanitize(seatName);
    }

    /**
     * <ol>
     *      <li>単価を取得する．</li>
     * </ol>
     * @return 単価
     */
    public int getFee() {
        return fee;
    }

    /**
     * <ol>
         *   <li>feeをフィールドへ登録する．</li>
     * </ol>
     * @param fee 単価
     */
    public void setFee(int fee) {
        this.fee = fee;
    }

    /**
     * <ol>
     *      <li>席数を取得する．</li>
     * </ol>
     * @return 席数
     */
    public int getCount() {
        return count;
    }

    /**
     * <ol>
     *      <li>countをフィールドへ登録する．</li>
     * </ol>
     * @param count 席数
     */
    public void setCount(int count) {
        this.count = count;
    }


}
