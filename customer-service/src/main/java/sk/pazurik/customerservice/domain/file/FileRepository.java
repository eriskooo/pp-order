package sk.pazurik.customerservice.domain.file;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Repository
public class FileRepository {
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;
    
    public FileEntity getFileById(Long id) {
        return entityManager.find(FileEntity.class, id);
    }
   
    public void saveOrUpdateFile(FileEntity file) {
        if (file.getId() == null) {
            entityManager.persist(file);
            logger.info("persisted, {}" , file);
        } else {
            entityManager.merge(file);
            logger.info("merged, {}" ,file);
        }
    }

    public void deleteFile(Long id) {
        FileEntity file = entityManager.find(FileEntity.class, id);
        if (file == null) {
            throw new EntityNotFoundException("File not found");
        }
        entityManager.remove(file);
    }
}