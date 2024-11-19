package yandex.objects;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private int userStatus;

    public UserResponse () { }

    public UserResponse (long id, String username, String firstname,
                        String lastname, String email, String password,
                        String phone, int userStatus) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    public UserResponse (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
