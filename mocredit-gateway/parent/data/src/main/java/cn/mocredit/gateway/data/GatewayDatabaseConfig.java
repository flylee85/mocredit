package cn.mocredit.gateway.data;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;

import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "cn.mocredit.gateway.data.repository",
        entityManagerFactoryRef = "gatewayEntityManager", transactionManagerRef = "transactionManager")
public class GatewayDatabaseConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Value("${gateway.datasource.url}")
    private String url;
    @Value("${gateway.datasource.username}")
    private String username;
    @Value("${gateway.datasource.password}")
    private String password;
    @Bean(name = "gatewayDataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource gatewayDataSource() {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("xads1");
        xaDataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties xaProperties = new Properties();
        xaProperties.put("URL",url);
        xaProperties.put("user",username);
        xaProperties.put("password",password);
        xaProperties.put("pinGlobalTxToPhysicalConnection","true");
        xaDataSource.setXaProperties(xaProperties);
        xaDataSource.setMaxPoolSize(100);
        xaDataSource.setMinPoolSize(10);
        xaDataSource.setTestQuery("Select 1");
        return xaDataSource;
    }
    @Bean(name = "gatewayEntityManager")
    @DependsOn("transactionManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean gatewayEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(gatewayDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("cn.mocredit.gateway.data.entities");
        entityManager.setPersistenceUnitName("gatewayPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

}
