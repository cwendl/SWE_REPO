package Server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServerMain {

	private static final Logger log = LoggerFactory.getLogger(ServerMain.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ServerMain.class, args);
		RestTemplate restTemplate = new RestTemplate();
		String s = restTemplate.getForObject("http://localhost:8080/greeting", String.class);
		log.info(s);
	}

}
