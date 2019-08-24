package sk.pazurik.customerservice.domain.file;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileDTOTest {

    @Test
    public void convertDTOtoEntity() {
        FileDTO dto = TestFile.FILE_1_DTO();
        FileEntity entity = new FileEntity(dto);
        assertThat(dto.getId()).isEqualTo(entity.getId());
    }

    @Test
    public void convertEntityToDTO() {
        FileEntity entity = TestFile.FILE_1_ENTITY();
        FileDTO dto = new FileDTO(entity);
        assertThat(dto.getId()).isEqualTo(entity.getId());
    }
}
