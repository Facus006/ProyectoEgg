package com.equipoC.Trendytouch.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String categoria;
    private String descripcion;
    //private Reporte reporte;
   
    //@OneToMany
    //private List<Comentario> comentarios;
    //@OneToMany
    //private List<Imagen> fotos;
    
    /* No se si sera necesario estos otros atributos
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;   
    */
}
