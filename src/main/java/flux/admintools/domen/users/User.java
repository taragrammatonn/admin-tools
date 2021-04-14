package flux.admintools.domen.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    Long id;
    Long chatId;
    String fName;
    String lName;
    String userNickName;
    String userGroup;
    String userLanguage;
    Boolean active;
    Boolean adminEntity;
    Boolean isDefined;

    String username;
    String password;

    @JsonIgnore
    UserRole role;

    public User(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getFullName() {
        if (getUserNickName() != null) {
            return (getFName() != null ? getFName() : "") + " \"" + getUserNickName() + "\" " + (getLName() != null ? getLName() : "");
        }
        return (!getFName().isEmpty() ? getFName() : "") + " " + (!getLName().isEmpty() ?  getLName() : "");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
