package sk.pazurik.customerservice.domain.file;

public interface FileService {
    /**
     * nahra chujovinu.
     *
     * @param file
     * @return
     */
    FileDTO saveFile(byte[] file);

    /**
     * updatene cosi
     *
     * @param id
     * @param file
     */
    void updateFile(Long id, byte[] file);

    /**
     * zmaze cosi
     *
     * @param id
     */
    void deleteFile(Long id);

    /**
     * cosi nacita
     *
     * @param id
     * @return
     */
    byte[] getFileById(final Long id);
}
