package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "epil")
@SQLDelete(sql = "UPDATE epil SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor
public class Epil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean deleted = Boolean.FALSE;
    private String name;
    private String number;
    private String date;
    private String zone;
    private String params;
    private String price;
    private String comments;
    private String masterName;

    public Epil(Long id, boolean deleted, String name, String number, String date, String zone, String params, String price,
                String comments, String masterName) {
        this.id = id;
        this.deleted = deleted;
        this.name = name;
        this.number = number;
        this.date = date;
        this.zone = zone;
        this.params = params;
        this.price = price;
        this.comments = comments;
        this.masterName = masterName;
    }
}
