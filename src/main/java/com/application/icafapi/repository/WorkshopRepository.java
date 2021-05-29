package com.application.icafapi.repository;

import com.application.icafapi.model.WorkshopConductor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopRepository  extends MongoRepository<WorkshopConductor, String> {
    public WorkshopConductor findByEmail(String email); //Useful in spring security initialization
}
