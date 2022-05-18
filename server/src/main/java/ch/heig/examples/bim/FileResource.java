package ch.heig.examples.bim;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.entities.FileEntity;
import ch.heig.examples.bim.repositories.FileRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;

@Path("/files")
public class FileResource {

    @Inject
    FileRepository fileRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(FileDto dto) {
        FileEntity entity = new FileEntity();
        entity.name = dto.name();
        entity.creationDate = new Date();

        fileRepository.persist(entity);
        return Response.created(URI.create("/files/" + entity.id)).build();
    }
}