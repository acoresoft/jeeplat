package com.acoreful.jeeplat.admin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acoreful.jeeplat.commons.init.ApplicationStartListener;

@Configuration
public class ListenerConfig {

    @Bean
    public ApplicationStartListener applicationStartListener(){
        return new ApplicationStartListener();
    }
}