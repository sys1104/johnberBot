package com.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.song.controller.MyAmazingBot;


//@MapperScan(value={"com.song.dao"})
@SpringBootApplication
@ComponentScan
@EnableScheduling
public class SpringWebServiceApplication {
	
	public static void main(String[] args) {
	    ApiContextInitializer.init();
	    TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
	    MyAmazingBot bot =  new MyAmazingBot();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		SpringApplication.run(SpringWebServiceApplication.class, args);
	}

}
