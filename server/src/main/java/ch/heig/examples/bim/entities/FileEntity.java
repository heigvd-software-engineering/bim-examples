package ch.heig.examples.bim.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class FileEntity extends PanacheEntity {
    public Long id;
    public String name;
    public Date creationDate;
}
