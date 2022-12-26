package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    private int value;

    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private Case aCase;

    public Order(int value, OrderType orderType, Case aCase) {
        this.value = value;
        this.orderType = orderType;
        this.aCase = aCase;
    }

    public Order() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
