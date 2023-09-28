package com.example.cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.cargo.Controladores.ControladorCrontabs;

@SpringBootApplication
@EnableAsync
@Profile("!test")
public class CargoApplication implements CommandLineRunner {

	@Autowired
	ControladorCrontabs controladorCrontabs;

	public static void main(String[] args) {
		SpringApplication.run(CargoApplication.class, args);
	}

	public void run(String... strings) throws Exception {
		controladorCrontabs.start();
	}

}
