package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

public class InitTxTest {

    @Autowired
    Hello hello;

    @Test
    void go(){
        // 초기화 코드는 초기화 시점에 호출된다. @PostConstruct
    }

    @TestConfiguration
    static class InitTxTestConfig{
        @Bean
        Hello hello(){
            return new Hello();
        }
    }

    @Slf4j
    @SpringBootTest
    static class Hello{

        @PostConstruct
        @Transactional
        public void initV1(){
            boolean isActive =  TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("hello init @PostConstruct tx active = {}", isActive);
        }


        /*스프링 컨테이너가 전부 완성되어 떳을때 호출*/
        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2(){
            boolean isActive =  TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("hello init @ApplicationReadyEvent tx active = {}", isActive);
        }


    }
}
