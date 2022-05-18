package ch.heig.examples.bim.controllers;

import ch.heig.examples.bim.services.FileService;
import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.entities.FileEntity;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/files")
public class FileResource {

    @Inject
    FileService fileService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(FileDto dto) {
        FileEntity entity = fileService.createEntity(dto);
        return Response.created(URI.create("/files/" + entity.id)).build();
    }
}