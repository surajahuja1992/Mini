package com.tiny;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//@PropertySource("classpath:application.properties")
@SpringBootApplication
@EnableScheduling
public class TinyApplication {
	static Properties prop = new Properties();

	public static void main(String[] args) throws Exception {
		/*
		 * try { loadDatabaseCredentials(); } catch (Exception e) { // TODO
		 * Auto-generated catch block throw e; }
		 */
		SpringApplication.run(TinyApplication.class, args);
	}

	/*
	 * private static void loadDatabaseCredentials() throws Exception {
	 * 
	 * String fileName =
	 * "D:\\Suraj_WorkSpace\\RBL_TinyTest\\Encrypted Password\\config.txt"; File
	 * file = new File(fileName); FileReader fr = new FileReader(file);
	 * BufferedReader br = new BufferedReader(fr); String password = br.readLine();
	 * byte[] decryptedPasswordInBytes = Base64.getDecoder().decode(password);
	 * String decryptedPassword = new String(decryptedPasswordInBytes); br.close();
	 * 
	 * FileInputStream in = new
	 * FileInputStream("src\\main\\resources\\application.properties"); Properties
	 * props = new Properties(); props.load(in); in.close();
	 * 
	 * FileOutputStream out = new
	 * FileOutputStream("src\\main\\resources\\application.properties");
	 * props.setProperty("spring.datasource.password", decryptedPassword);
	 * props.store(out, null); out.close();
	 * 
	 * }
	 */
}
