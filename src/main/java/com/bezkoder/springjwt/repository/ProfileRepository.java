package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.entity.Profile;
import com.bezkoder.springjwt.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, User> {


    Optional<Profile> findById(Long userId);
}
