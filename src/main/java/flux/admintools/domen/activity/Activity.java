package flux.admintools.domen.activity;

import flux.admintools.domen.history.UserAction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("activity_logs.user_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Activity {

    @Id
    Long id;

    UserAction action;
    Long userId;
    LocalDateTime actionTime;
    LocalDateTime loginTime;
    LocalDateTime logoutTime;

    public Activity(UserAction userAction, Long userId, LocalDateTime actionTime, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.action = userAction;
        this.userId = userId;
        this.actionTime = actionTime;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
}
