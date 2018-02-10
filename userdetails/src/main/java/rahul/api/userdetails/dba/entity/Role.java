package rahul.api.userdetails.dba.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "role")
@ToString
@EqualsAndHashCode(of = {"id"})
public class Role {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    @Getter
    @Setter
    @Column(name = "role")
    private String role;
}
