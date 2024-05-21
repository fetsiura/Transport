package svk.transrest.core.picture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import svk.transrest.core.car.Car;

/**
 * Purpose:
 * The Picture class represents an image associated with a particular car in the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private boolean priority;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}
