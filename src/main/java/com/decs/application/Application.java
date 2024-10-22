package com.decs.application;

import com.decs.application.data.person.SamplePersonRepository;
import com.decs.application.utils.confFile.ProblemFileManager;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */

/**
 * <b>Application Class</b>
 * <p>
 *     This class represents the entry point of the Spring Boot application.
 *     In order to make the application installable on phones, tablets and some
 *     desktop browsers, include the @PWA annotation.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@SpringBootApplication
@Push
@EnableAsync
@NpmPackage(value = "@fontsource/cousine", version = "4.5.0")
@Theme(value = "decs")
public class Application implements AppShellConfigurator {

    /**
     * Main method of the Java application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Uncomment to perform manual problem configuration file (.conf) management
        //ProblemFileManager.createFile();

        SpringApplication.run(Application.class, args);
    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource,
            SqlInitializationProperties properties, SamplePersonRepository repository) {
        // This bean ensures the database is only initialized when empty
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                if (repository.count() == 0L) {
                    return super.initializeDatabase();
                }
                return false;
            }
        };
    }
}
