package com.kjh.wsd.saramIn_crawling.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "users")
@Schema(description = "사용자 엔티티")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 이름", example = "user1")
    private String username;

    @Column(nullable = false)
    @Schema(description = "비밀번호 (암호화)", example = "$2a$10$CjF2/...")
    private String password;
}
