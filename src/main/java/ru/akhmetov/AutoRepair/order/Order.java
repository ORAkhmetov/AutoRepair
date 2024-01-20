package ru.akhmetov.AutoRepair.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.appeal.Appeal;
/** Сущность для работы с таблицей "aorder" **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "aorder")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "avalue")
    @Min(value = 0, message = "Стоимость не должна быть меньше нуля")
    private int value;

    @Column(name = "order_type")
    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "appeal_id", referencedColumnName = "id")
    private Appeal appeal;

    public Order(int value, OrderType orderType) {
        this.value = value;
        this.orderType = orderType;
    }
}
