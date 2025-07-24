package org.example.user_service.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 4)
    @Column(unique = true)
    private String username;

    @NotNull @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String passwordHash;





}
