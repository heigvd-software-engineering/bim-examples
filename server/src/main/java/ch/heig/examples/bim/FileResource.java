package ch.heig.examples.bim;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.entities.FileEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/files")
public class FileResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public FileEntity create(FileDto dto) {
        return new FileEntity(1, dto.name(), new Date());
    }
}