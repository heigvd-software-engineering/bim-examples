package ch.heig.examples.bim.services;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.entities.FileEntity;
import ch.heig.examples.bim.repositories.FileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;

@ApplicationScoped
public class FileService {

    @Inject
    FileRepository fileRepository;

    public FileEntity createEntity(FileDto dto) {
        FileEntity entity = new FileEntity();
        entity.name = dto.name();
        entity.creationDate = new Date();
        entity.lastUpdate = entity.creationDate;

        fileRepository.persist(entity);
        return entity;
    }

    public FileEntity updateFileBlob(long id, byte[] fileBlob) {
        FileEntity entity = fileRepository.findById(id);
        entity.file = fileBlob;
        entity.lastUpdate = new Date();

        fileRepository.persist(entity);
        return entity;
    }
}
