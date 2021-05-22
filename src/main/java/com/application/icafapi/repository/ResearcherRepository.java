package com.application.icafapi.repository;

import com.application.icafapi.model.Researcher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearcherRepository extends MongoRepository<Researcher, String> {
    Researcher findByEmail(String email);
}
