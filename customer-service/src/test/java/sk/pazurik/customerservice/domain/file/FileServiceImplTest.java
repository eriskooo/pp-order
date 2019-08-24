package sk.pazurik.customerservice.domain.file;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private Logger logger;

    @Mock
    private FileRepository repository;

    @Test
    public void getFileByIdShouldReturnFile() {
        Mockito.when(repository.getFileById(anyLong())).thenReturn(TestFile.FILE_1_ENTITY());
        byte[] file = fileService.getFileById(TestFile.FILE_1_ENTITY().getId());
        Mockito.verify(repository, Mockito.times(1)).getFileById(anyLong());
        assertThat(file).isEqualTo(TestFile.FILE_1_ENTITY().getFile());
    }

    @Test
    public void getFileByIdWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getFileById(anyLong())).thenReturn(null);
        byte[] file = fileService.getFileById(777l);
        Mockito.verify(repository, Mockito.times(1)).getFileById(anyLong());
    }

    @Test
    public void saveFile() {
        fileService.saveFile(TestFile.FILE_1_ENTITY().getFile());
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateFile(any());
    }
    
    @Test
    public void updateFile() {
        Mockito.when(repository.getFileById(anyLong())).thenReturn(TestFile.FILE_1_ENTITY());
        fileService.updateFile(TestFile.FILE_1_ENTITY().getId(), TestFile.FILE_1_ENTITY().getFile());
        Mockito.verify(repository, Mockito.times(1)).getFileById(anyLong());
    }

    @Test
    public void updateFileWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getFileById(anyLong())).thenReturn(null);
        fileService.updateFile(TestFile.FILE_1_ENTITY().getId(), TestFile.FILE_1_ENTITY().getFile());
        Mockito.verify(repository, Mockito.times(1)).getFileById(anyLong());
    }
    
    @Test
    public void deleteFile() {
        fileService.deleteFile(anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteFile(anyLong());
    }

    @Test
    public void deleteFileWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteFile(anyLong());
        fileService.deleteFile(anyLong());
        Mockito.verify(repository, Mockito.times(1)).getFileById(anyLong());        
    }
}