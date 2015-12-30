package cn.mocredit.gateway.task;

import cn.mocredit.gateway.data.mcntongrepository.BankRepository;
import cn.mocredit.gateway.data.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Order(2)
public class MyTask implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private BankRepository bankRepository;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void keepJdbcContected() {
        logger.info("任务开始：每隔半小时连接一下数据库");
        deviceRepository.keepJdbcContected();
        bankRepository.keepJdbcContected();
        logger.info("任务结束：每隔半小时连接一下数据库");
    }

    @Override
    public void run(String... arg0) throws Exception {
        logger.info("启动后立刻连接一下数据库");
        deviceRepository.contecteNow();
        bankRepository.contecteNow();
        logger.info("启动后立刻连接一下数据库，完毕。");
    }

}
