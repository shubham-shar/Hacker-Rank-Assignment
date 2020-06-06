package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    public Optional<Actor> findById(Long id);
}
