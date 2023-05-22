package com.stackoverflow.project.Stackoverflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "score")
    private Float score;

    @Column(name = "banned", columnDefinition = "BIT")
    private Boolean banned;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionVote> questionVotes = new ArrayList();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerVote> answerVotes = new ArrayList();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> userRoles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName().name()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
