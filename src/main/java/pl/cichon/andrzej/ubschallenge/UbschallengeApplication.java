package pl.cichon.andrzej.ubschallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class UbschallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbschallengeApplication.class, args);
	}

}
