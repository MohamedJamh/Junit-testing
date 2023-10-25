package ma.youcode.gathergrid.service;

import jakarta.enterprise.inject.spi.WithAnnotations;
import jakarta.inject.Inject;
import ma.youcode.gathergrid.domain.Event;
import ma.youcode.gathergrid.domain.TicketType;
import ma.youcode.gathergrid.repositories.EventRepository;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableWeld
@AddPackages(EventService.class)
class EventServiceTest {
    @Inject
    private EventService eventService;

    @Test
    void validate() {
        List<Error> errors = eventService.validate(Event.builder().name("testEvent").build());
        assertEquals(0,errors.size());
    }
}