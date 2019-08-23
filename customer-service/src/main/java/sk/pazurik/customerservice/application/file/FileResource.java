package sk.pazurik.customerservice.application.file;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.file.FileDTO;
import sk.pazurik.customerservice.domain.file.FileService;
import sun.misc.IOUtils;

@Path("file")
public class FileResource {
    
    @Inject
    private Logger logger;

    @Inject
    private FileService fileService;
    
    @POST
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpeg", "image/jpg"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response saveFile(InputStream stream) {
        logger.info("called uploadPicture");

        byte[] fileBytes = new byte[0];
        try {
            fileBytes = new byte[stream.available()];
            stream.read(fileBytes);
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        FileDTO fileDTO = fileService.saveFile(fileBytes);

        return Response.ok(fileDTO).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{fileId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpeg", "image/jpg"})
    public Response getFile(@PathParam("fileId") @NotNull final Long fileId) {
        logger.info("called getFile");

        byte[] image = fileService.getFileById(fileId);

        return Response.ok(image, MediaType.APPLICATION_OCTET_STREAM).build();
    }
}
