package com.application.icafapi.service;

import com.application.icafapi.model.Analytics;
import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.Workshop;
import com.application.icafapi.repository.AttendeeRepository;
import com.application.icafapi.repository.ResearcherRepository;
import com.application.icafapi.repository.UserRepository;
import com.application.icafapi.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    public final UserRepository userRepository;
    public final AttendeeRepository attendeeRepository;
    public final ResearcherRepository researcherRepository;
    public final WorkshopRepository workshopRepository;

    @Autowired
    public AnalyticsService(UserRepository userRepository, AttendeeRepository attendeeRepository, ResearcherRepository researcherRepository, WorkshopRepository workshopRepository) {
        this.userRepository = userRepository;
        this.attendeeRepository = attendeeRepository;
        this.researcherRepository = researcherRepository;
        this.workshopRepository = workshopRepository;
    }

    @Cacheable(cacheNames = {"analytics"})
    public Analytics getAnalytics() {
        System.out.println("Method executed");
        Analytics analytics = new Analytics();

        Researcher researcher = new Researcher();
        researcher.setPaid("true");
        researcher.setStatus("approved");
        Example<Researcher> example = Example.of(researcher);

        Workshop workshop = new Workshop();
        workshop.setPublish("published");
        Example<Workshop> exampleWorkshop = Example.of(workshop);

        analytics.setAttendees(attendeeRepository.count());
        analytics.setAllResearchers(researcherRepository.count());
        analytics.setAllWorkshops(workshopRepository.count());
        analytics.setPublishedPapers(researcherRepository.count(example));
        analytics.setPublishedWorkshops(workshopRepository.count(exampleWorkshop));

        return analytics;
    }

}
