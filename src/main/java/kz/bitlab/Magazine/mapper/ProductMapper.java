package kz.bitlab.Magazine.mapper;

import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.ProductDto;
import kz.bitlab.Magazine.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto (Product product);
    Product toEntity(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> products);
    List<Product> toEntityList(List<ProductDto> productDtoList);
}
