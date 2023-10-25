package ma.youcode.gathergrid.service;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.spi.WithAnnotations;
import jakarta.inject.Inject;
import ma.youcode.gathergrid.domain.Event;
import ma.youcode.gathergrid.domain.TicketType;
import ma.youcode.gathergrid.repositories.EventRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private static Event event;

    @BeforeAll
    static void setUp(){
        event = new Event();
    }

    @Test
    void validate() {
        List<Error> errors = event.validate();
        assertEquals(0,errors.size());
    }
}