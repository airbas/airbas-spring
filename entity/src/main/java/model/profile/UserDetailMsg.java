package model.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class UserDetailMsg implements Serializable {
    private String firstname;
    private String secondname;
    private String email;

    public UserDetailMsg(String email, String firstname, String secondname) {
        this.email = email;
        this.firstname = firstname;
        this.secondname = secondname;
    }
}
