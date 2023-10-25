package ma.youcode.gathergrid.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Time hour;

    private String location;

    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Organization organization;

    @OneToMany
    private List<Ticket> tickets=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TicketPack> ticketPacks=new ArrayList<>();

    @Column(name = "available_tickets")
    private int numberOfTicketsAvailable;





    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", hour=" + hour +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category.getName() +
                ", organiser=" + organization.getName() +
                '}';
    }


    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
        ticket.setEvent(this);
    }
    public int getTotalOfTicketsAvailable(){
        return ticketPacks
                .stream()
                .reduce(0,(acc, ticketPack) -> acc + ticketPack.getQuantity(), Integer::sum);
    }
    public List<Error> validate(){
        List<Error> errors = new ArrayList<>();
        if( this.getName() == null || this.getName().isEmpty()) errors.add(new Error("Name Field is required"));
        if( this.getLocation() == null || this.getLocation().isEmpty()) errors.add(new Error("Location Field is required"));
        if( this.getDescription() == null ||this.getDescription().isEmpty()) errors.add(new Error("Description Field is required"));

        if(this.getCategory() == null || this.getOrganization() == null){
            errors.add(new Error("Invalid Category or organization"));
        }
        if(this.date == null) errors.add(new Error("Date filed is required"));
        else if(this.date.before(java.sql.Date.valueOf(LocalDate.now()))) errors.add(new Error("Event date should not be not be old"));
        if( this.getNumberOfTicketsAvailable() <= 10) errors.add(new Error("Event should have at least 10 places"));
        return errors;
    }
}
