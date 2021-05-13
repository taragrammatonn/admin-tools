package flux.admintools.domen.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import flux.admintools.domen.Views;
import flux.admintools.domen.history.UserAction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users.\"user\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id @JsonView(Views.GeneralUserInfo.class) Long id;
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
    @JsonIgnore
    String password;

    @JsonIgnore
    UserRole role;

    @Transient
    Boolean isDeleted = Boolean.FALSE;
    @Transient
    UserAction userAction;

    public User(Long id,  Boolean isDeleted) {
        this.id = id;
        this.isDeleted = isDeleted;
    }

    public User(String userNickName) {
        this.userNickName = userNickName;
    }

    @JsonView(Views.GeneralUserInfo.class)
    public String getFullName() {
        if (getUserNickName() != null)
            return (getFName() != null ? getFName() : "") + " \"" + getUserNickName() + "\" " + (getLName() != null ? getLName() : "");
        else if (getIsDeleted())
            return "Is Deleted";
        return (!getFName().isEmpty() ? getFName() : "") + " " + (!getLName().isEmpty() ?  getLName() : "");
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
