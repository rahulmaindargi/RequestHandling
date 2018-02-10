package rahul.api.userdetails.dba.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "user_details")
@ToString
@EqualsAndHashCode(of = {"id"})
public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Getter
    @Setter
    @Column(name = "loginId")
    //@Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an login Id")
    private String loginId;

    @Getter
    @Setter
    @Column(name = "password")
    @Length(min = 8, message = "*Your password must have at least 8 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Getter
    @Setter
    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Getter
    @Setter
    @Column(name = "active")
    private boolean active;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name
            = "role_id"))
    private Set<Role> roles;

    @Getter
    @Setter
    @Email
    @Column(name = "email")
    private String email;


}
