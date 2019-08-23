package sk.pazurik.customerservice.domain.file;

public interface FileService {
    FileDTO getFileById(Long id);
    
    void saveFile(byte[] file);
    
    void updateFile(FileDTO file);
    
    void deleteFile(Long id);
}
