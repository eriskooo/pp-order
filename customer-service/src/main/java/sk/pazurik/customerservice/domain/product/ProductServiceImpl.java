package sk.pazurik.customerservice.domain.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private Logger logger;

    @Inject
    ProductRepository repository;

    @Override
    public Collection<ProductDTO> getAllProducts() {
        return repository.getAllProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return new ProductDTO(repository.getProductById(id));
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice_wo_VAT(productDTO.getPrice_wo_VAT());
        productEntity.setPrice_w_VAT(productDTO.getPrice_w_VAT());
        productEntity.setEan(productDTO.getEan());
        productEntity.setPicture(productDTO.getPicture());

        repository.saveProduct(productEntity);

        logger.info("saveProduct ok, {}", productEntity);

    }
}
