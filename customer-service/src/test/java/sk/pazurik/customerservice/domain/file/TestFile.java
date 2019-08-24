package sk.pazurik.customerservice.domain.file;

public class TestFile {

    public static FileEntity FILE_1_ENTITY() {
        FileEntity fileEntity = new FileEntity(FILE_1_DTO());
        fileEntity.setFile("04fd020ea3a6910a2d808002b30309".getBytes());
        return fileEntity;
    }

    public static FileDTO FILE_1_DTO() {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(1L);
        return fileDTO;
    }
}
