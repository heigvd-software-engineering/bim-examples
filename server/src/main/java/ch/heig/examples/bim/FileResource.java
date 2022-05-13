package ch.heig.examples.bim;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/files")
public class FileResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        return "Hello on the files endpoint";
    }
}