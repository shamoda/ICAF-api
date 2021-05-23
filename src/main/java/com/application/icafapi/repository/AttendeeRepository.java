package com.application.icafapi.repository;

import com.application.icafapi.model.Attendee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends MongoRepository<Attendee, String> {
}
