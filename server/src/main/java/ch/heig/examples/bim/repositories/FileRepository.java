package ch.heig.examples.bim.repositories;

import ch.heig.examples.bim.entities.FileEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileRepository implements PanacheRepository<FileEntity> {
}
