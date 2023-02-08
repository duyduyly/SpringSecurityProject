package com.bezkoder.springjwt.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String old;
    private String address;
    private String email;
}
