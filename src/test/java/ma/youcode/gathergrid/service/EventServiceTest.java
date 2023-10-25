package ma.youcode.gathergrid.service;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.spi.WithAnnotations;
import jakarta.inject.Inject;
import ma.youcode.gathergrid.domain.Category;
import ma.youcode.gathergrid.domain.Event;
import ma.youcode.gathergrid.domain.Organization;
import ma.youcode.gathergrid.domain.TicketType;
import ma.youcode.gathergrid.repositories.EventRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
    private static Event event;

    @BeforeAll
    static void setUp(){
        event = new Event();
    }

    @Test
    @DisplayName("Event validation should throw all errors")
    void event_validation_should_throw_all_errors() {
        List<Error> errors = event.validate();
        assertEquals(5,errors.size());
        assertEquals("Name Field are required",errors.get(0).getMessage());
        assertEquals("Location Field are required",errors.get(1).getMessage());
        assertEquals("Description Field are required",errors.get(2).getMessage());
        assertEquals("Invalid Category or organization",errors.get(3).getMessage());
        assertEquals("Invalid Number of places",errors.get(4).getMessage());
    }

    @Test
    @DisplayName("Event validation should pass")
    void event_validation_should_pass(){
        event = Event.builder()
                .name("My Event").location("agadir").description("Couple Wedding")
                .category(
                        Category.builder().id(Long.parseLong("1")).build()
                )
                .organization(
                        Organization.builder().id(Long.parseLong("1")).build()
                )
                .date(java.sql.Date.valueOf(LocalDate.now()))
                .hour(java.sql.Time.valueOf(LocalTime.now()))
                .numberOfTicketsAvailable(15)
                .build();
        assertEquals(0,event.validate().size());
    }
}