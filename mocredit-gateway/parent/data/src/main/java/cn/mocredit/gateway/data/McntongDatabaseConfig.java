package cn.mocredit.gateway.data;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "cn.mocredit.gateway.data.mcntongrepository",
        entityManagerFactoryRef = "mcntongEntityManager", transactionManagerRef = "transactionManager")
public class McntongDatabaseConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Value("${mcntong.datasource.url}")
    private String url;
    @Value("${mcntong.datasource.username}")
    private String username;
    @Value("${mcntong.datasource.password}")
    private String password;

    @Bean(name = "mcntongDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource mcntongDataSource() {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("xads2");
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

    @Bean(name = "mcntongEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean mcntongEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(mcntongDataSource());
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setPackagesToScan("cn.mocredit.gateway.data.mcntongentities");
        em.setPersistenceUnitName("mcntongPersistenceUnit");
        em.setJpaPropertyMap(properties);
        return em;
    }

}
