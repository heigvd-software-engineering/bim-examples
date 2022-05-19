package ch.heig.examples.bim.services;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.dtos.FileSummaryDto;
import ch.heig.examples.bim.entities.FileEntity;
import ch.heig.examples.bim.repositories.FileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class FileService {

    @Inject
    FileRepository fileRepository;

    public List<FileSummaryDto> findAllSummaries() {
        return fileRepository.findAll().stream()
                .map(this::toFileSummaryDto)
                .toList();
    }

    public FileEntity findById(long id) throws NotFoundException {
        FileEntity entity = fileRepository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Could not find file with id " + id);
        }

        return entity;
    }

    public FileEntity createEntity(FileDto dto) {
        FileEntity entity = new FileEntity();
        entity.name = dto.name();
        entity.creationDate = new Date();
        entity.lastUpdate = entity.creationDate;

        fileRepository.persist(entity);
        return entity;
    }

    public void updateFileBlob(long id, byte[] fileBlob) throws NotFoundException {
        FileEntity entity = this.findById(id);

        entity.file = fileBlob;
        entity.lastUpdate = new Date();

        fileRepository.persist(entity);
    }

    private FileSummaryDto toFileSummaryDto(FileEntity entity) {
        return new FileSummaryDto(entity.id, entity.name);
    }
}
