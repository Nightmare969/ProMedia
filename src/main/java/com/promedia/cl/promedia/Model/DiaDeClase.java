package com.promedia.cl.promedia.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diaDeClase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaDeClase {
    public static final DiaDeClase LUNES = null;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    @Column (nullable= false)
    private String nombre; 
}