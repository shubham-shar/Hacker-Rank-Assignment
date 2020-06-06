package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public Optional<Event> findById(Long id);

    public List<Event> findByActor(Actor actor);
}
