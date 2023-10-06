package com.example.demo.config.rules

import org.kie.api.runtime.KieContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class NotificationsRuleConfiguration: RuleConfiguration() {
    companion object {
        private const val DRL_FILE = "rules/notification.drl"
    }

    @Bean
    open fun kieContainer(): KieContainer {
        return buildKieContainer(DRL_FILE)
    }
}
