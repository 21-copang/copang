package com.sparta.user.entity;

import com.sparta.user.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "p_users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Email
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Column(nullable = false, length = 255)
    private String email;

    @NotBlank(message = "사용자 이름은 필수 항목입니다.")
    @Column(nullable = false, length = 100)
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.setCreatedBy(username);
    }

}