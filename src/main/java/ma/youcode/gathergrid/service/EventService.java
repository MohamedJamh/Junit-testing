package ma.youcode.gathergrid.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ma.youcode.gathergrid.domain.Event;
import ma.youcode.gathergrid.domain.TicketType;
import ma.youcode.gathergrid.repositories.EventRepository;
import ma.youcode.gathergrid.utils.Response;
import org.hibernate.annotations.Cache;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Transactional
public class EventService {
    private EventRepository eventRepository;

    //private List<Error> errors = new ArrayList<>();

    @Inject
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public EventService() {
    }


    public boolean eventIsExist(long id) {
        Optional<Event> optionalEvent = getEventById(id);
        if (optionalEvent.isPresent()) return true;
        return false;
    }

    public Response<Event> createEvent(Event event){
        Response<Event> eventResponse = new Response<>();
        List<Error> errors = validate(event);
        if(errors.isEmpty()) eventResponse.setError(errors);
        else{
            eventRepository.save(event);
            eventResponse.setResult(event);
        }
        return eventResponse;
    }


    public List<Error> validate(Event event){
        List<Error> errors = new ArrayList<>();
        if( event.getName().isEmpty() || event.getLocation().isEmpty() || event.getDescription().isEmpty()){
            errors.add(new Error("All Fields are required"));
        }else if(event.getCategory() == null || event.getOrganization() == null){
            errors.add(new Error("Invalid Category or organization"));
        }else if( event.getNumberOfTicketsAvailable() < 10) errors.add(new Error("Invalid Number of places"));
        return errors;
    }

    public Response<List<Event>> getAllEvents(){
        Response<List<Event>> eventResponse = new Response<>();
        eventResponse.setResult(eventRepository.findAll());
        return eventResponse;
    }

    public Optional<Event> getEventByName(String eventName) {
        return eventRepository.findEventByName(eventName);
    }

    public Optional<Event> getEventById(Long eventId) {
        return Optional.ofNullable(eventRepository.findById(eventId));
    }


    public Response<Event> updateEvent(Event event) {
        Response<Event> eventResponse = new Response<>();
        Optional<Event> optionalEvent =  getEventById(event.getId());
        List<Error> errors = validate(event);
        if(optionalEvent.isPresent()){
            validate(event);
            if(errors.isEmpty()) eventResponse.setError(errors);
            else {
                eventRepository.update(event);
                eventResponse.setResult(event);
            }
        }
        return eventResponse;
    }
    public Response<Event> deleteEvent(long id){
        Response<Event> eventResponse = new Response<>();
        if(eventIsExist(id)){
            Event event = eventRepository.findById(id);
            eventRepository.delete(event);
            eventResponse.setResult(event);
        }else eventResponse.setError(List.of(new Error("Invalid Event")));
        return eventResponse;
    }

    public void updateQuantityOfTicket(Event event, TicketType ticketType, boolean isIncrement) {
        int increment = isIncrement ? 1 : -1;
        event.setNumberOfTicketsAvailable(event.getNumberOfTicketsAvailable() + increment);
        event.getTicketPacks()
                .stream()
                .filter(ticketPack -> ticketPack.getTicketType().equals(ticketType))
                .findAny()
                .ifPresent(ticketPack -> ticketPack.setQuantity(ticketPack.getQuantity() + increment));
        eventRepository.update(event);
    }
}
