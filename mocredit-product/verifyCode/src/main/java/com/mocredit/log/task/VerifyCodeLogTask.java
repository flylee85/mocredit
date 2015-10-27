package com.mocredit.log.task;

import com.mocredit.verifyCode.dao.ActActivitySynLogMapper;
import com.mocredit.verifyCode.dao.VerifiedCodeMapper;
import com.mocredit.verifyCode.dao.VerifyLogMapper;
import com.mocredit.verifyCode.model.ActActivitySynLog;
import com.mocredit.verifyCode.model.TVerifyLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YHL on 15/7/21 12:07.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
@Component
public class VerifyCodeLogTask {
    private Logger logger=Logger.getLogger(VerifyCodeLogTask.class.getName());

    /**
     * 存放要保存的日志对象
     */
    public static List<TVerifyLog>  verifyogList = new ArrayList<TVerifyLog>();

    public static List<ActActivitySynLog> actActivitySynLogList = new ArrayList<ActActivitySynLog>();

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private VerifyLogMapper verifyLogMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ActActivitySynLogMapper actActivitySynLogMapper;

    private static boolean isDoing = false;

    @Transactional
    @Scheduled(cron = "0/15 * * * * ?")
    public void doLog() throws Exception{
        if( !isDoing ) {

            logger.debug("##### ----- log----------#### ::: \n 验码日志大小：" + verifyogList.size() + " \t 活动日志大小：" + actActivitySynLogList.size());
            isDoing=true;
            try {
                if (verifyogList.size() > 0) {
                    List<TVerifyLog> copyList = verifyogList;
                    int c = verifyLogMapper.batchSave(copyList);
                    verifyogList.removeAll(copyList);
                    //System.out.println("记录日志："+c);
                }

                if (actActivitySynLogList.size() > 0) {
                    List<ActActivitySynLog> copyList = actActivitySynLogList;
                    int c = actActivitySynLogMapper.batchSave(copyList);
                    actActivitySynLogList.removeAll(copyList);
                    //System.out.println("记录日志："+c);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw  e;
            }finally {
                isDoing=false;
            }
        }

    }

    public  void cleanUp(){
        //如果还有日志没有记录，则记录日志
        if( verifyogList.size()>0){
            List<TVerifyLog> copyList=verifyogList;
            int c= verifyLogMapper.batchSave(copyList);
            verifyogList.removeAll(copyList);
        }

        if( actActivitySynLogList.size()>0){
            List<ActActivitySynLog> copyList=actActivitySynLogList;
            int c= actActivitySynLogMapper.batchSave(copyList);
            actActivitySynLogList.removeAll(copyList);
        }
    }
}
