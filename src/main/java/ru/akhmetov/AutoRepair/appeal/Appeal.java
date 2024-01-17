package ru.akhmetov.AutoRepair.appeal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.car.Car;
import ru.akhmetov.AutoRepair.order.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/** Сущность для работы с таблицей "appeal" **/

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "appeal")
public class Appeal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "fault_description")
    private String faultDescription;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "mileage")
    @Min(value = 0, message = "Пробег не должн быть меньше нуля")
    private int mileage;

    @Column(name = "date_of_appeal")
    private LocalDate dateOfAppeal;

    @OneToMany(mappedBy = "appeal")
    private List<Order> orderList;

    @OneToMany(mappedBy = "appeal")
    private List<PhotoAppeal> photoAppealList;

    public Appeal(Car car, int mileage, LocalDate dateOfAppeal) {
        this.car = car;
        this.mileage = mileage;
        this.dateOfAppeal = dateOfAppeal;
        this.orderList = new ArrayList<>();
    }
}
