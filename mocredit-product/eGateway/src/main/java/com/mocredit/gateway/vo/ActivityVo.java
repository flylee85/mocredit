package com.mocredit.gateway.vo;

import com.mocredit.gateway.entity.Activity;

/**
 * Created by ytq on 2015/12/29.
 */
public class ActivityVo extends Activity {
    private Integer CardDayMax;
    private Integer CardWeekMax;
    private Integer CardMonthMax;
    private Integer CardYearMax;
    private Integer CardTotalMax;

    public Integer getCardDayMax() {
        return CardDayMax;
    }

    public void setCardDayMax(Integer cardDayMax) {
        CardDayMax = cardDayMax;
    }

    public Integer getCardWeekMax() {
        return CardWeekMax;
    }

    public void setCardWeekMax(Integer cardWeekMax) {
        CardWeekMax = cardWeekMax;
    }

    public Integer getCardMonthMax() {
        return CardMonthMax;
    }

    public void setCardMonthMax(Integer cardMonthMax) {
        CardMonthMax = cardMonthMax;
    }

    public Integer getCardYearMax() {
        return CardYearMax;
    }

    public void setCardYearMax(Integer cardYearMax) {
        CardYearMax = cardYearMax;
    }

    public Integer getCardTotalMax() {
        return CardTotalMax;
    }

    public void setCardTotalMax(Integer cardTotalMax) {
        CardTotalMax = cardTotalMax;
    }
}
