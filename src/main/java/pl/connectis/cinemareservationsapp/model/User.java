package pl.connectis.cinemareservationsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "username")
@Entity(name = "app_user")
public class User {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active = 1;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Permissions permissions;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Transient
    private List<String> permissionsList;

    @Transient
    private List<String> roleList;

    public List<Permissions> getPermissionsList() {
        return Arrays.asList(permissions);
    }

    public List<Role> getRoleList() {
        return Arrays.asList(role);
    }
}
