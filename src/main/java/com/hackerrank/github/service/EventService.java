package com.hackerrank.github.service;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shubham Sharma
 * @date 6/6/20
 */

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ActorRepository actorRepository;

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }


    public ResponseEntity createEvent(Event event) {
        return eventRepository.findById(event.getId())
                .map(eventToBeSaved -> {
                    eventRepository.save(event);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public ResponseEntity<List<Event>> getAllEvents() {
        Sort sortingOrder = new Sort(Sort.Direction.ASC, "id");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventRepository.findAll(sortingOrder));
    }

    public ResponseEntity<List<Event>> getActorEvents(Long actorId) {
        return actorRepository.findById(actorId)
                .map(actor -> {
                    List<Event> events = eventRepository.findByActor(actor);
                    events.sort(Comparator.comparingLong(Event::getId));
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(events);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity updateActor(Actor actor) {
        return actorRepository.findById(actor.getId())
                .map(actorToBeUpdated -> {
                    boolean isLoginEqual = Optional.ofNullable(actor.getLogin())
                                                    .map(s -> s.equals(actorToBeUpdated.getLogin()))
                                                    .orElse(Objects.isNull(actorToBeUpdated.getLogin()));
                    if(isLoginEqual){
                      actorToBeUpdated.setAvatar(actor.getAvatar());
                      actorRepository.save(actorToBeUpdated);
                      return ResponseEntity.status(HttpStatus.OK).build();
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }

                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<Actor>> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        Collections.sort(actors, new Comparator<Actor>() {
            @Override
            public int compare(Actor a, Actor b) {
                if (a.getEvents().size() != b.getEvents().size())
                    return b.getEvents().size() - a.getEvents().size();
                Long timestampOfA = a.getEvents().stream().map(event -> event.getCreatedAt())
                        .max(Timestamp::compareTo).get().getTime();
                Long timestampOfB = a.getEvents().stream().map(event -> event.getCreatedAt())
                        .max(Timestamp::compareTo).get().getTime();
                if (!timestampOfA.equals(timestampOfB))
                    return timestampOfB.intValue() - timestampOfA.intValue();

                return b.getLogin().compareTo(a.getLogin());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(actors);
    }
}
