package yandex.objects;
import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
