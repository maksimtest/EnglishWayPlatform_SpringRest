package platform.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeUtils {
    @Value("${registration.activate.minute-expiration}")
    private long regMinuteExpiration;

    public LocalDateTime getRegMinuteExpiration() {
        return LocalDateTime.now().plusMinutes(regMinuteExpiration);
    }
    public boolean expiration(LocalDateTime date){
        return LocalDateTime.now().isAfter(date);
    }
}
