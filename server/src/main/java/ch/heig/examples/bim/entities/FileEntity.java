package ch.heig.examples.bim.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class FileEntity extends PanacheEntity {
    public String name;
    public Date creationDate;
    public Date lastUpdate;
    @Column(columnDefinition = "BLOB")
    public byte[] file;
}
