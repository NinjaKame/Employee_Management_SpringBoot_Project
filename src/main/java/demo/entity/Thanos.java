package demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Random;

@Getter
@Setter
@Accessors(chain = true)
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Thanos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "department")
    private String department;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "pass_word")
    private String password;

    public Thanos(String firstName, String lastName, String department, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = firstName + "." + lastName + "@"
                + ((department == null) ? "company.com" : department + ".company.com");
        this.password = password;
        if (password == null) {
            this.password = generateRandomPassword();
        }
//        System.out.println("Customized constructor called");
    }


    private static String generateRandomPassword() {
        String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = new Random().nextInt(randomString.length());
            char randomChar = randomString.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }


}
