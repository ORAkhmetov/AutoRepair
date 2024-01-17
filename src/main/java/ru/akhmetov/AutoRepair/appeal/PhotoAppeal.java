package ru.akhmetov.AutoRepair.appeal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**  Cущность для хранения информации о фотографиях, связанных с обращениями  **/

@Entity
@Getter
@Setter
@Table(name = "Photo")
public class PhotoAppeal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "appeal_id", referencedColumnName = "id")
    private Appeal appeal;

}

