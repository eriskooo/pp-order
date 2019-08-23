package sk.pazurik.customerservice.domain.file;

public interface FileService {
    void saveFile(byte[] file);
    
    void updateFile(FileDTO file);
    
    void deleteFile(Long id);
}
