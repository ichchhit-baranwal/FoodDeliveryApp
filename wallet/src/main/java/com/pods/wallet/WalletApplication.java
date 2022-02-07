package com.pods.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * The main class which is invoked first
 * when spring boot application is run
 * It also enables the auto configuration,
 * component scan in the package and configuration
 * of extra beans or configuration classes  
 */
@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}
	
}
