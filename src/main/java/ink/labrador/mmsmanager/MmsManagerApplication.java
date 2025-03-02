package ink.labrador.mmsmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ink.labrador.mmsmanager.mapper")
public class MmsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmsManagerApplication.class, args);
	}

}
