package gpsUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GpsUtilApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GpsUtilApplication.class, args);
	}

}
