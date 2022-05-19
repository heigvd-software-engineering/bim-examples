package ch.heig.examples.bim.controllers;

import ch.heig.examples.bim.dtos.FileDto;
import ch.heig.examples.bim.dtos.FileSummaryDto;
import ch.heig.examples.bim.entities.FileEntity;
import ch.heig.examples.bim.services.FileService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/files")
public class FileResource {

    @Inject
    FileService fileService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<FileSummaryDto> fileEntityList = fileService.findAllSummaries();
        return Response.ok(fileEntityList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces()
    @Transactional
    public Response create(FileDto dto) {
        FileEntity entity = fileService.createEntity(dto);
        return Response.created(URI.create("/files/" + entity.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces()
    @Transactional
    public Response update(@PathParam("id") long id, byte[] fileBlob) {
        fileService.updateFileBlob(id, fileBlob);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Produces()
    public Response findById(@PathParam("id") long id) {
        FileEntity entity = fileService.findById(id);
        return Response.ok(entity).build();
    }
}