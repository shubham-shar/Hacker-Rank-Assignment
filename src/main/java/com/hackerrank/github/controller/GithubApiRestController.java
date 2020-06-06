package com.hackerrank.github.controller;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

@RestController
public class GithubApiRestController {

    @Autowired
    EventService eventService;

    @PostMapping("/events")
    public ResponseEntity createNewEntity(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events/actors/{actorID}")
    public ResponseEntity<List<Event>> getAllEventsBasedOnActor(@PathVariable("actorID") Long actorId){
        return eventService.getActorEvents(actorId);
    }

    @PutMapping("/actors")
    public ResponseEntity updateActor(@RequestBody Actor actor){
        return eventService.updateActor(actor);
    }

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> getActors(){
        return eventService.getAllActors();
    }

    /*
    * TODO: Pending logic
    * */
    @GetMapping("/actors/streak")
    public ResponseEntity<List<Actor>> getActorsStreak(){
        return ResponseEntity.ok().body(Collections.emptyList());
    }

    /*
    *  Events should not be hard deleted,
    *  it should be soft deleted.
    * */
    @DeleteMapping("/erase")
    public ResponseEntity deleteAllEvents(){
        eventService.deleteAllEvents();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
