package sk.pazurik.customerservice.domain.file;

import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

import javax.persistence.*;

@Entity
public class FileEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic(fetch = FetchType.LAZY)
    private byte[] file;
    
    public FileEntity() {
        super();
    }
    
    public FileEntity(byte[] file) {
        this.file = file;
    }
    
    public FileEntity(FileDTO dto) {
        id = dto.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileEntity{" + 
                "id=" + id +
                '}';
    }
}
