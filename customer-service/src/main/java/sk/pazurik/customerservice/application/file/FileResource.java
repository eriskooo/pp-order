package sk.pazurik.customerservice.application.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

@Path("file")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class FileResource {
    
    @Inject
    private Logger logger;

    @POST
    @Path("upload")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpeg", "image/jpg"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadPicture(File file) {
        try (Reader reader = new FileReader(file)) {

            //employee.setPicture(Files.readAllBytes(Paths.get(file.toURI())));
            ///persistenceService.saveFile(employee);

            int totalsize = 0;
            int count = 0;
            final char[] buffer = new char[256];
            while ((count = reader.read(buffer)) != -1) {
                totalsize += count;
            }
            return Response.ok(totalsize).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }    
}
