package sk.pazurik.customerservice.domain.file;

import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class FileDTO extends AbstractValueObject {

    private Long id;

    public FileDTO() {
        super();
    }
    
    public FileDTO(FileEntity entity) {
        id = entity.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Object[] values() {
        return new Object[]{id};
    }
    
    @Override
    public String toString() {
        return "FileDTO{" + 
                "id=" + id +
                '}';
    } 
}
