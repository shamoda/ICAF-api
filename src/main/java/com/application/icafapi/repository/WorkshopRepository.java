package com.application.icafapi.repository;

import com.application.icafapi.model.Workshop;
import com.application.icafapi.model.WorkshopConductor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopRepository  extends MongoRepository<Workshop, String> {
    public Workshop findByConductor(String conductor); //returns a list of all the workshops by a one conductor
}
