package flux.admintools.domen.history;

import flux.admintools.domen.users.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Table("users.user_option")
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
