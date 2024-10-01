package kg.kutman.smanov.sumsarproject.sso.config;

import kg.kutman.smanov.sumsarproject.sso.service.OAuth2ClientService;
import kg.kutman.smanov.sumsarproject.sso.service.UserEventService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final UserEventService userEventService;
    private final OAuth2ClientService oAuth2ClientService;

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        return new RedisLockProvider(connectionFactory);
    }

    /**
     * Задача удаления устаревших событий безопасности пользователей.
     */
    @Scheduled(cron = "${scheduled-tasks.delete-old-events.cron}")
    @SchedulerLock(name = "deleteOldEvents_lock", lockAtMostFor = "5m", lockAtLeastFor = "5m")
    public void deleteOldEvents() {
        userEventService.deleteOldEvents();
    }

    /**
     * Задача уведомлений OAuth2 клиентов об удалении аккаунтов пользователей.
     */
    @Scheduled(cron = "${scheduled-tasks.notify-delete-users.cron}")
    @SchedulerLock(name = "deleteUsersNotifier_lock", lockAtMostFor = "5s", lockAtLeastFor = "5s")
    public void notifyClientsAboutDeleteUser() {
        oAuth2ClientService.notifyClientsAndClear();
    }
}
