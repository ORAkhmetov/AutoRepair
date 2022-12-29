package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "aOrder")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "aValue")
    @Min(value = 0, message = "Стоимость не должна быть меньше нуля")
    private int value;

    @Column(name = "order_type")
    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private Case aCase;

    public Order(int value, OrderType orderType) {
        this.value = value;
        this.orderType = orderType;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Case getaCase() {
        return aCase;
    }

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }
}
