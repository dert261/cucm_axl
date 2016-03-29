package ru.obelisk.cucmaxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.obelisk.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class CucmApplication {
	
	/*private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
    	SpringApplication app = new SpringApplication(CucmApplication.class);
        app.setWebEnvironment(false);
        setCtx(app.run(args));
    }

	public static ConfigurableApplicationContext getCtx() {
		return ctx;
	}

	public static void setCtx(ConfigurableApplicationContext ctx) {
		CucmApplication.ctx = ctx;
	}*/
	
	public static void main(String[] args) {
    	SpringApplication.run(CucmApplication.class, args);
    }
}
