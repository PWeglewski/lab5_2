package edu.iis.mto.integrationtest.config;


import edu.iis.mto.integrationtest.utils.ModeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration //wskazuje, że klasa zawiera konfigurację bean’ów Spring
@EnableTransactionManagement //umożlwia konfigurowanie mechanizmów transakcji z wykorzystaniem adnotacji
@EnableJpaRepositories(basePackages = {"edu.iis.mto.integrationtest.repository"})
//określa gdzie szukać klas definiujących repozytoria Spring Data.
public class PersistenceConfig {
    public static final String SQL_SCHEMA_SCRIPT_PATH = "sql/schema-script.sql";
    public static final String DATA_SCRIPT_FILENAME_SUFFIX = ".sql";
    public static final String SQL_FOLDER_NAME = "sql";
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);
    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.user}")
    private String databaseUser;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${database.driver}")
    private String databaseDriverClass;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DataSource dataSource = createDataSource();
        DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
        return dataSource;
    }

    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScripts(new ClassPathResource(
                SQL_SCHEMA_SCRIPT_PATH), new ClassPathResource(SQL_FOLDER_NAME
                + ModeUtils.getMode().getModeName()
                + DATA_SCRIPT_FILENAME_SUFFIX));
        return databasePopulator;
    }

    private SimpleDriverDataSource createDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        Class<? extends Driver> driverClass = getDriverClass();
        simpleDriverDataSource.setDriverClass(driverClass);
        simpleDriverDataSource.setUrl(databaseUrl);
        simpleDriverDataSource.setUsername(databaseUser);
        simpleDriverDataSource.setPassword(databasePassword);
        return simpleDriverDataSource;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Driver> getDriverClass() {
        try {
            Class<?> driverClass = Class.forName(databaseDriverClass);
            if (Driver.class.isAssignableFrom(driverClass)) {
                return (Class<? extends Driver>) driverClass;
            } else {
                LOGGER.error("database driver class is not the SQL driver ");
                return null;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("database driver class not found", e);
            return null;
        }
    }

}
