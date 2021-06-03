package com.application.icafapi.common.scheduled;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@EnableCaching
@Log4j2
public class CacheConfig {
    @CacheEvict(allEntries = true, cacheNames = {"analytics"})
    @Scheduled(fixedDelay = 20 * 60 * 1000, initialDelay = 500)
    public void reportCacheEvict() {
        log.info("Analytics cache flushed: " + new Date());
    }
}
