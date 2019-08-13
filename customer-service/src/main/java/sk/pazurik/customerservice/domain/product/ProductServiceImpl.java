package sk.pazurik.customerservice.domain.product;

import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository repository;

    @Override
    public Collection<ProductDTO> getAllProducts() {
        return repository.getAllProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById() {
        return new ProductDTO(repository.getProductById());
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
        //productEntity.setPicture(productDTO.getPicture());
        
        repository.saveProduct(productEntity);
    }
}
