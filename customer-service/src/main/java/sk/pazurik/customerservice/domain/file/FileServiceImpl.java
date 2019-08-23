package sk.pazurik.customerservice.domain.file;

import java.io.File;
import java.sql.Blob;
import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

@Service
public class FileServiceImpl implements FileService{

    @Inject
    private Logger logger;

    @Inject
    FileRepository repository;

    @Override
    public FileDTO saveFile(byte[] file) {
        FileEntity fileEntity = new FileEntity(file);
        repository.saveOrUpdateFile(fileEntity);
        logger.info("saveFile ok, {}", fileEntity);
        return new FileDTO(fileEntity);
    }
    
    @Override
    public void updateFile(FileDTO fileDTO) {
        FileEntity fileEntity = repository.getFileById(fileDTO.getId());
        if (fileEntity == null) {
            throw new EntityNotFoundException("File not found");
        }
        fileEntity = new FileEntity(fileDTO);        
        repository.saveOrUpdateFile(fileEntity);
        logger.info("updateFile ok, {}", fileEntity);
    }
    
    @Override
    public void deleteFile(Long id) throws EntityNotFoundException {
        repository.deleteFile(id);
        logger.info("deleteFile ok, {}", id);
    }

    @Override
    public byte[] getFileById(Long id) {
        FileEntity fileEntity = repository.getFileById(id);
        if (fileEntity == null) {
            throw new EntityNotFoundException("File not found");
        }

        byte[] stream = fileEntity.getFile();

        logger.info("getFileById ok, {}", id);
        return stream;
    }
}