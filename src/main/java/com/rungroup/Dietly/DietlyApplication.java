package com.rungroup.Dietly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;


@SpringBootApplication
public class DietlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietlyApplication.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}
	@Configuration
	public class H2ServerConfiguration {

		@Bean(initMethod = "start", destroyMethod = "stop")
		@Profile("dev")
		public Server h2Server() throws SQLException {
			return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
		}
	}
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
}
