package sk.pazurik.customerservice.application.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.file.FileDTO;
import sk.pazurik.customerservice.domain.file.FileService;

@Path("file")
public class FileResource {
    
    @Inject
    private Logger logger;

    @Inject
    private FileService fileService;
    
    @POST
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpeg", "image/jpg"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response saveFile(File file) {
        logger.info("called uploadPicture");

        byte[] fileBytes = getImage(file);
        FileDTO fileDTO = fileService.saveFile(fileBytes);

        return Response.ok(fileDTO).status(Response.Status.CREATED).build();
    }
    
    public static byte[] getImage(File file) {
      if(file.exists()){
         try {
            String extension = "";
            int i = file.getAbsolutePath().lastIndexOf('.');
            if (i > 0) {
                extension = file.getAbsolutePath().substring(i+1);
            }
            BufferedImage bufferedImage=ImageIO.read(file);
            ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, byteOutStream);
            return byteOutStream.toByteArray();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return null;
   }
}
