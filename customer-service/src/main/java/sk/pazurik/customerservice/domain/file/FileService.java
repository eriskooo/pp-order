package sk.pazurik.customerservice.domain.file;

public interface FileService {
    FileDTO saveFile(byte[] file);
    
    void updateFile(FileDTO file);
    
    void deleteFile(Long id);
}
