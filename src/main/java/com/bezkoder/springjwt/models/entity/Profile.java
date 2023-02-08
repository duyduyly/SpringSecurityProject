package com.bezkoder.springjwt.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String old;
    private String address;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
