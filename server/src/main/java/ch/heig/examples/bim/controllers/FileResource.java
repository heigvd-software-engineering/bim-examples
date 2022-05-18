package ch.heig.examples.bim.controllers;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.services.FileService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/files")
public class FileResource {

    @Inject
    FileService fileService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(FileDto dto) {
        fileService.createEntity(dto);
        return Response.status(Response.Status.CREATED).build();
    }
}