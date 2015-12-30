package com.yimeihuijin.codeandbonusapp.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.runnable.ConsumeRunnables;
import com.yimeihuijin.codeandbonusapp.model.vo.VO;
import com.yimeihuijin.codeandbonusapp.presenter.ConsumeResultPresenter;
import com.yimeihuijin.codeandbonusapp.utils.PostUtil;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;
import com.yimeihuijin.codeandbonusapp.utils.ThreadMananger;
import com.yimeihuijin.commonlibrary.constants.URLs;
import com.yimeihuijin.commonlibrary.web.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chanson on 2015/12/17.
 *
 * @desc 该类包含了验码，验码撤销，积分消费，积分撤销，积分消费冲正，验码撤销冲正
 *        使用实例化该类时，必须实现IConsumePresenter接口
 */
public class ConsumeModel extends Model{

    /**正常消费*/
    public static final int STATE_CONSUME = 0x1001;
    /**撤销消费*/
    public static final int STATE_REVOKE = 0x1002;
    /**验码模式*/
    public static final int MODE_CODE = 0x2002;
    /**积分模式*/
    public static final int MODE_BONUS = 0x2001;

    /**消费状态（正常消费/消费撤销）*/
    private static int state = STATE_CONSUME;
    /**消费模式（验码/积分）*/
    private static int mode = MODE_CODE;

    /**冲正次数*/
    private Integer correctNum = 0;
    /**最大冲正次数*/
    private final int MAX_CORRECT_NUM = 3;


    private static final String MSG_CODE_CONSUME_SUCCESS = "验码成功\n";
    private static final String MSG_CODE_REVOKE_SUCCESS = "验码撤销成功\n";
    private static final String MSG_BONUS_CONSUME_SUCCESS = "积分消费成功\n";
    private static final String MSG_BONUS_REVOKE_SUCCESS = "积分消费撤销成功\n";

    private static final String MSG_CODE_CONSUME_FAILURE = "验码失败\n";
    private static final String MSG_CODE_REVOKE_FAILURE = "验码撤销失败\n";
    private static final String MSG_BONUS_CONSUME_FAILURE = "积分消费失败\n";
    private static final String MSG_BONUS_REVOKE_FAILURE = "积分消费撤销失败\n";

    private static final String MSG_BONUS_CONSUME_CORRECT_SUCCESS = "请再次尝试消费";
    private static final String MSG_BONUS_REVOKE_CORRECT_SUCCESS = "请再次尝试撤销";

    private IConsumePresenter presenter;

    private DeviceModel.Card card;

    private ActivitiesAdapter adapter;

    public ConsumeModel(IConsumePresenter presenter){
        this.presenter = presenter;
    }

    /**
     * 验码
     * */
    private void codeConsume(String code){
        final VO.CodeConsumeObject cco = new VO.CodeConsumeObject();
        cco.code = code;

        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.CodeConsumeResponseObject cro = GsonUtil.get(response, VO.CodeConsumeResponseObject.class);
                if(cro != null && "0".equals(cro.rtnFlag)){
                    presenter.conusmeComplete(getResult(cro.erweima==null?cco.orderId:cro.erweima,cro.printInfo, MSG_CODE_CONSUME_SUCCESS));
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreCodeResponseRunnable(cro));//验码成功后，存储验码订单，用于撤销
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreTradeRecordRunnable(cro.amount, mode, state));//存储验码订单的金额，用于打印结算小票
                }else if(cro == null){//解析服务器信息失败
                    presenter.conusmeComplete(getResult(MSG_CODE_CONSUME_FAILURE + "服务器返回数据异常"));
                }else if(!"0".equals(cro.rtnFlag)){//验码失败
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_CODE_CONSUME_FAILURE, cro.errorMes)));
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_CODE_CONSUME_FAILURE, error.getMessage())));
            }
        }).post(URLs.URL_CHECKCODE,new PO(cco));
    }

    /**
     * 验码失败调用该方法
     * @param msg 失败信息
     * @return 结果对象
     */
    private ConsumeResultPresenter.ConsumeResultObject getResult(String msg){
        ConsumeResultPresenter.ConsumeResultObject cro = new  ConsumeResultPresenter.ConsumeResultObject();
        cro.msg = msg;
        cro.isSuccess = false;
        return cro;
    }

    /**
     * 验码成功调用该方法
     * @param qr 二维码信息
     * @param data 打印信息（如果jData中包含data字段就传入data字段，否则传入printInfo字段，或者自定义小票模板）
     * @param msg
     * @return
     */
    private ConsumeResultPresenter.ConsumeResultObject getResult(String qr,String data,String msg){
        ConsumeResultPresenter.ConsumeResultObject cro = new  ConsumeResultPresenter.ConsumeResultObject();
        cro.msg = msg;
        cro.isSuccess = true;
        cro.printInfo = getPrintInfo(data);
        cro.QR = qr;
        return cro;
    }

    private String getPrintInfo(String data){
        if (data == null) {
            return null;
        }
        VO.ActivityId activity_id = GsonUtil.get(data,
                VO.ActivityId.class);
        //如果data不是一个包含activityId和orderId的json字串，即打印信息由服务器拼接，则直接把data字串本身作为打印信息
        if (activity_id == null || activity_id.activityId == null
                || activity_id.orderId == null) {
            return data;
        }


        //如果data是一个包含activityId和orderId的json字串，即打印信息需要本地拼接，则直接把通过活动模板来生成打印信息
        VO.ARO ar = App.getInstance().getDBHelper().getActivitiesJson(
                VO.ARO.class);
        VO.AO act_info = null;
        for ( VO.AO ai : ar.data) {
            if (activity_id.activityId.equals(ai.activityId)) {
                act_info = ai;
                break;
            }
        }
        if (act_info == null) {
            return null;
        }

        //getPrintInfo方法根据任务信息中的小票模板来生成最终打印的小票
        String commonInfo = StringUtils
                .getPrintInfo(act_info, activity_id.orderId);
        return commonInfo;
    }


    /**
     * 验码撤销
     *  @param orderid 订单号
     * */
    private void codeRevoke(String orderid){
        final VO.CodeRevoke cr = App.getInstance().getDBHelper().getCodeRevoke(orderid);

        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.CodeConsumeResponseObject cro = GsonUtil.get(response, VO.CodeConsumeResponseObject.class);
                if(cro != null && "0".equals(cro.rtnFlag)){
                    presenter.conusmeComplete(getResult(cro.erweima==null?cr.requestSerialNumber:cro.erweima,"验码撤销成功\n订单号："+cr.requestSerialNumber, MSG_CODE_REVOKE_SUCCESS));
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreCodeResponseRunnable(cro));
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreTradeRecordRunnable("0", mode, state));//存储验码撤销订单的金额，用于打印结算小票
                }else if(cro == null){
                    presenter.conusmeComplete(getResult(MSG_CODE_REVOKE_FAILURE + "服务器返回数据异常"));
                }else if(!"0".equals(cro.rtnFlag)){
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_CODE_REVOKE_FAILURE, cro.errorMes)));
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_CODE_REVOKE_FAILURE, error.getMessage())));
            }
        }).post(URLs.URL_CHECKCODE_CANCEL,new PO(cr));
    }

    /**
     * 积分消费
     * */
    private void bonusConsume(){
        final VO.BonusConsumeObject bco = new VO.BonusConsumeObject();
        VO.AO ao = adapter.getSelectedItem();
        bco.activitId = ao.activityId;
        bco.amt = ao.amt;
        bco.cardNo = card.cardno;
        bco.exp_date = card.expdate;
        ThreadMananger.get().execute(new ConsumeRunnables.StoreBonusRunnable(bco,state)); //存储积分订单，如果交易超时或者冲正失败时，用于订单冲正
        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.BonusConsumeResponseObject bcro = GsonUtil.get(response, VO.BonusConsumeResponseObject.class);
                deleteOrder(bco.orderId);//消费成功，删除存储的积分消费订单
                if(bcro == null){
                    presenter.conusmeComplete(getResult(MSG_BONUS_CONSUME_FAILURE + "服务器返回数据异常"));
                }else if(bcro.success){
                    presenter.conusmeComplete(getResult(bcro.qr,bcro.data, MSG_BONUS_CONSUME_SUCCESS));
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreTradeRecordRunnable(bco.amt, mode, state));//存储积分订单的金额，用于打印结算小票
                }else{
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_CONSUME_FAILURE, bcro.errorMsg)));
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                if(error instanceof TimeoutError){
                    bonusCorrect(bco);//消费超时，发起冲正
                }else {
                    deleteOrder(bco.orderId);//消费确认失败，删除存储的消费订单
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_CONSUME_FAILURE, error.getMessage())));
                }
            }
        }).post(URLs.URL_BONUS_CONSUME,new PO(bco));
    }

    /**
     * 积分消费冲正
     * */
    private void bonusCorrect(final VO.BonusConsumeObject bco){
        if(!canCorrect()){//检查是否冲正超过三次
            return;
        }
        VO.BonusConsumeObject newbco = new VO.BonusConsumeObject();
        newbco.orgOrderId = bco.orderId;
        newbco.cardNo = bco.cardNo;
        newbco.exp_date = bco.exp_date;
        newbco.amt = bco.amt;
        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.BonusConsumeResponseObject bcro = GsonUtil.get(response,VO.BonusConsumeResponseObject.class);
                if(bcro == null || !bcro.success){
                    bonusCorrect(bco);
                }else {
                    resetCorrectNum();
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_CONSUME_FAILURE, MSG_BONUS_CONSUME_CORRECT_SUCCESS)));
                    deleteOrder(bco.orderId);
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                bonusCorrect(bco);
            }
        }).post(URLs.URL_BONUS_CONSUME_CORRECT,new PO(newbco));

    }

    /**
     * 判断是否超过最大冲正次数
     * @return
     */
    private boolean canCorrect(){
        synchronized (correctNum){
            correctNum = (++correctNum)%MAX_CORRECT_NUM;
        }
        return correctNum != 0;
    }

    /**
     * 冲正成功后，重置冲正次数
     */
    private void resetCorrectNum(){
        correctNum = 0;
    }

    /**
     * 根据订单号删除存储的订单
     * @param orderId 订单号
     */
    private void deleteOrder(String orderId){
        ThreadMananger.get().execute(new ConsumeRunnables.DeleteOrderRunnable(orderId));
    }

    /**
     * 积分撤销
     * */
    private void bonusRevoke(String orderId){
        final VO.BonusConsumeObject bco = new VO.BonusConsumeObject();
        bco.orgOrderId = orderId;
        bco.cardNo = card.cardno;
        bco.exp_date = card.expdate;
        ThreadMananger.get().execute(new ConsumeRunnables.StoreBonusRunnable(bco,state)); //撤销前存储撤销订单，在撤销超时或者冲正失败时，用于发起冲正
        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.BonusConsumeResponseObject bcro = GsonUtil.get(response, VO.BonusConsumeResponseObject.class);
                deleteOrder(bco.orderId);
                if(bcro == null){
                    presenter.conusmeComplete(getResult(MSG_BONUS_REVOKE_FAILURE + "服务器返回数据异常"));
                }else if(bcro.success){
                    presenter.conusmeComplete(getResult(bcro.qr,bcro.data, MSG_BONUS_REVOKE_SUCCESS));
                    ThreadMananger.get().execute(new ConsumeRunnables.StoreTradeRecordRunnable(bco.amt, mode, state));  //存储撤销金额，用于打印消费清单
                }else{
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_REVOKE_FAILURE, bcro.errorMsg)));
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                if(error instanceof TimeoutError){
                    bonusRevokeCorrect(bco); //撤销超时，发起冲正
                }else {
                    deleteOrder(bco.orderId); //撤销确认失败，删除存储的撤销订单
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_REVOKE_FAILURE, error.getMessage())));
                }
            }
        }).post(URLs.URL_BONUS_REVOKE, new PO(bco));
    }

    /**
     * 积分撤销冲正
     * */
    private void bonusRevokeCorrect(final VO.BonusConsumeObject bco){
        if(!canCorrect()){  //检查是否冲正超过三次
            return;
        }
        VO.BonusConsumeObject newbco = new VO.BonusConsumeObject();
        newbco.orgOrderId = bco.orderId;
        newbco.cardNo = bco.cardNo;
        newbco.exp_date = bco.exp_date;
        new PostUtil(new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                VO.BonusConsumeResponseObject bcro = GsonUtil.get(response,VO.BonusConsumeResponseObject.class);
                if(bcro == null || !bcro.success){
                    bonusCorrect(bco);
                }else {
                    resetCorrectNum();
                    presenter.conusmeComplete(getResult(StringUtils.combineStrings(MSG_BONUS_REVOKE_FAILURE, MSG_BONUS_REVOKE_CORRECT_SUCCESS)));
                    deleteOrder(bco.orderId);
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag) {
                bonusRevokeCorrect(bco);
            }
        }).post(URLs.URL_BONUS_CONSUME_CORRECT,new PO(newbco));
    }


    /**
     * 消费/撤销动作
     * @param data 消费/撤销所需参数
     */
    public void todo(String data){
        switch (mode){
            case MODE_BONUS:
                if(state == STATE_CONSUME){
                    bonusConsume();
                }else{
                    bonusRevoke(data);
                }
                break;
            case MODE_CODE:
                if(state == STATE_CONSUME){
                    codeConsume(data);
                }else{
                    codeRevoke(data);
                }
                break;
        }
    }


    /**
     * 切换当前状态（消费/撤销）
     * @return 状态对应字符串
     */
    public static String switchState(){
        switch (state){
            case STATE_CONSUME:
                state = STATE_REVOKE;
                return "消费";
            default:
                state = STATE_CONSUME;
                return "撤销";
        }
    }

    /**
     * 根据当前消费模式和状态获取主页面显示的提示
     * @return 提示信息
     */
    public static String getHint(){
        switch (mode){
            case MODE_BONUS:
                if(state == STATE_CONSUME){
                    return "积分消费请刷卡";
                }else{
                    return "积分撤销请输入订单号\n或直接扫描二维码";
                }
            case MODE_CODE:
                if(state == STATE_CONSUME){
                    return "验码请输入券号\n" +
                            "或直接扫描二维码";
                }else{
                    return "验码撤销请输入订单号\n" +
                            "或直接扫描二维码";
                }
        }
        return "积分消费请刷卡\n验码请输入券号";
    }

    /**
     * 获取当前状态
     * @return 状态信息
     */
    public static String getState(){
        switch (state){
            case STATE_CONSUME:
                return "撤销";
            default:
                return "消费";
        }
    }

    /**
     * 切换模式（验码/积分），并返回模式信息
     * @return 模式信息
     */
    public static String switchMode(){
        switch (mode){
            case MODE_BONUS:
                mode = MODE_CODE;
                return "验码模式";
            default:
                mode = MODE_BONUS;
                return "积分模式";
        }
    }

    /**
     * 将模式设置为指定模式
     * @param mode 模式
     * @return 模式信息
     */
    public static String setModeTo(int mode){
        if(mode != MODE_CODE && mode != MODE_BONUS){
            throw new RuntimeException("======Invalid mode======");
        }
        ConsumeModel.mode = mode;
        return getMode();
    }

    public static boolean isCodeConsuming(){
        return mode == MODE_CODE;
    }

    public static boolean isConsuming(){
        return state == STATE_CONSUME;
    }

    public static String getMode(){
        switch (mode){
            case MODE_BONUS:
                return "积分模式";
            default:
                return "验码模式";
        }
    }

    /**
     * 设置银行卡信息
     * @param card 银行卡信息
     */
    public void setCard(DeviceModel.Card card){
        this.card = card;
    }

    /**
     * 获取活动列表适配器
     * @return 活动列表适配器
     */
    public ActivitiesAdapter getAdapter(){
        if(adapter == null) {
            adapter = new ActivitiesAdapter(getActivities(this.card.cardbin));
        }
        return adapter;
    }

    /**
     * 获取指定carBin支持的活动
     * @param cardBin 卡bin
     * @return 活动列表
     */
    private List<VO.AO> getActivities(String cardBin){
        if (cardBin != null && !cardBin.isEmpty()) {
            VO.ARO ar = App.getInstance().getDBHelper().getActivitiesJson(
                    VO.ARO.class);
            ArrayList<VO.AO> ailist = new ArrayList<VO.AO>();
            if(ar != null) {
                for (VO.AO ai : ar.data) {
                    if(ai.cardBin == null){
                        continue;
                    }
                    if ("".equals(ai.cardBin)) { //活动的cardBin字段为空字符串表示适用于所有卡片
                        ailist.add(ai);
                    }else if(ai.cardBin.contains(cardBin)){//活动cardBin字段包含指定cardBin，则添加该活动
                        ailist.add(ai);
                    }
                }
            }
            return ailist;
        }
        return null;
    }



    public class ActivitiesAdapter extends BaseAdapter {

        private List<VO.AO> list;

        public ActivitiesAdapter(List<VO.AO> list){
            this.list = list;
        }

        private int selected = 0;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list == null?0:list.size();
        }

        @Override
        public VO.AO getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public void setSeletecedItem(int position) {
            selected = position;
        }

        public VO.AO getSelectedItem() {
            if(list.size() < 1){
                return null;
            }
            return list.get(selected);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.getInstance()).inflate(
                        R.layout.item_acitivity, null);
            }
            vh = (ViewHolder) convertView.getTag();
            if (vh == null) {
                vh = new ViewHolder();
                vh.date = (TextView) convertView
                        .findViewById(R.id.item_activity_date);
                vh.title = (TextView) convertView
                        .findViewById(R.id.item_activity_title);
                vh.desc = (TextView) convertView
                        .findViewById(R.id.item_activity_desc);
                vh.price = (TextView) convertView
                        .findViewById(R.id.item_activity_price);
                convertView.setTag(vh);
            }
            vh.setHolder(getItem(position));
            if (selected == position) {
                convertView
                        .setBackgroundResource(R.drawable.bg_item_activity_selected);
            } else {
                convertView.setBackgroundResource(R.drawable.bg_item_activity);
            }
            return convertView;
        }

        private class ViewHolder {
            public TextView title, price, date, desc;

            public void setHolder(VO.AO ai) {
                title.setText(ai.activityName);
                price.setText(ai.amt == null ? "0" : ai.amt + "积分");
                StringBuffer datesb = new StringBuffer().append(ai.sTime)
                        .append("到").append(ai.eTime);
                date.setText(datesb.toString());
                desc.setText(ai.remark);
            }
        }

    }

    public interface IConsumePresenter{
        /**
         * 消费成功后调用该方法，返回消费结果，不管成功失败
         * @param data 消费结果
         */
        public void conusmeComplete(ConsumeResultPresenter.ConsumeResultObject data);
    }
}
