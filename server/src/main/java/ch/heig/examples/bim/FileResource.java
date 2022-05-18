package ch.heig.examples.bim;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.entities.FileEntity;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(FileDto dto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.name = dto.name();
        fileEntity.creationDate = new Date();

        fileEntity.persist();
        return Response.created(URI.create("/files/" + fileEntity.id)).build();
    }
}