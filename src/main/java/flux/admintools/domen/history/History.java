package flux.admintools.domen.history;

import flux.admintools.domen.users.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Data

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class History {

    @Id
    Long id;

    HistoryEvent event;

    String requestMessage;
    String requestDate;
    Long userChatId;

    User user;
}
