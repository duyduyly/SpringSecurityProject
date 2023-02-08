package com.bezkoder.springjwt.services;

import com.bezkoder.springjwt.exception.ResourceException;
import com.bezkoder.springjwt.models.dto.ProfileDTO;
import com.bezkoder.springjwt.models.entity.Profile;
import com.bezkoder.springjwt.models.entity.Role;
import com.bezkoder.springjwt.models.entity.User;
import com.bezkoder.springjwt.models.request.LoginRequest;
import com.bezkoder.springjwt.models.request.SignupRequest;
import com.bezkoder.springjwt.models.role_enum.ERole;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.repository.ProfileRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bezkoder.springjwt.constant.Constant.*;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ProfileRepository profileRepository;


    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public void signUp(SignupRequest signUpRequest) throws NoSuchFieldException {
        this.checkEmailAndUsernameExist(signUpRequest);

        //get Role
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        /**
         * check role
         if role is null then Set role is  ROLE_USER
         if role ROLE_ADMIN or ROLE_MODERATOR then it will add in Set Role
         * */
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ResourceException(ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceException(ROLE_NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "moderator":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new ResourceException(ROLE_NOT_FOUND));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceException(ROLE_NOT_FOUND));
                        roles.add(userRole);
                        break;
                }
            });
        }


        User user = userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(roles)
                .build());
        Profile profile = profileRepository.save(Profile.builder()
                .user(user)
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .build());

    }

    private void checkEmailAndUsernameExist(SignupRequest signUpRequest) throws NoSuchFieldException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceException(USER_EXIST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceException(EMAIL_USED);
        }
    }

    public ProfileDTO getProfile(String username) throws NoSuchFieldException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceException(USER_NOT_FOUND));
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new ResourceException(PROFILE_NOT_FOUND));

        return ProfileDTO.builder()
                .id(profile.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(profile.getAddress())
                .lastName(profile.getFirstName())
                .firstName(profile.getLastName())
                .old(profile.getOld())
                .build();
    }
}
