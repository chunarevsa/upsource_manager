package com.rtkit.upsource_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		UpsourceManagerApplication.class,
		Jsr310JpaConverters.class
})
public class UpsourceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpsourceManagerApplication.class, args);
	}

}
