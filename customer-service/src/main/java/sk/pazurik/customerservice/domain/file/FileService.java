package sk.pazurik.customerservice.domain.file;

public interface FileService {
    FileDTO saveFile(byte[] file);
    
    void updateFile(Long id, byte[] file);
    
    void deleteFile(Long id);

    byte[] getFileById(final Long id);
}
