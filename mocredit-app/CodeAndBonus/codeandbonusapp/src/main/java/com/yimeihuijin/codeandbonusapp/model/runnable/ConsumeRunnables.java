package com.yimeihuijin.codeandbonusapp.model.runnable;

import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.model.vo.VO;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;

/**
 * Created by Chanson on 2015/12/18.
 *
 * 一些数据库操作的Runnable
 */
public class ConsumeRunnables {

    /**
     * 存储验码订单到数据库
     */
    public static class StoreCodeResponseRunnable implements Runnable{

        private VO.CodeConsumeResponseObject ccro;

        public  StoreCodeResponseRunnable( VO.CodeConsumeResponseObject ccro){
            this.ccro = ccro;
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().insertCode(ccro);
        }
    }
    /**
     * 存储积分订单到数据库
     */
    public static class StoreBonusRunnable implements Runnable{

        private VO.BonusConsumeObject bco;
        private String flag;

        public  StoreBonusRunnable( VO.BonusConsumeObject bco,int flag){
            this.bco = bco;
            this.flag = String.valueOf(flag);
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().insertOrder(bco, flag);
        }
    }
    /**
     * 删除积分订单
     */
    public static class DeleteOrderRunnable implements Runnable{

        private String orderId;

        public  DeleteOrderRunnable(String orderId){
            this.orderId = orderId;
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().deleteOrder(orderId);
        }
    }


    /**
     * 存储交易信息（积分笔数，积分金额，撤销笔数，撤销金额，验码笔数，验码金额）
     */
    public static class StoreTradeRecordRunnable implements Runnable{

        private String mode;
        private String state;
        private int amt;

        public StoreTradeRecordRunnable(String amt,int mode,int state){
            this.mode = String.valueOf(mode);
            this.state = String .valueOf(state);
            this.amt = StringUtils.getInt(amt,0);
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().insertTradeRecord(amt, mode, state);
        }
    }

}
