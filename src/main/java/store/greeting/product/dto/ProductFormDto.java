package store.greeting.product.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import store.greeting.enums.SellStatus;
import store.greeting.product.entity.Product;

@Getter
@Setter
public class ProductFormDto {

  private Long id;

  @NotBlank(message = "상품명은 필수 입력 값입니다.")
  private String productName;

  @NotNull(message = "가격은 필수 입력 값입니다.")
  private Integer price;

  @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
  private String productDetail;

  @NotBlank(message = "요청 사항은 필수 입력 값입니다.")
  private String request;

  @NotNull(message = "재고는 필수 입력 값입니다.")
  private Integer stockNumber;

  private SellStatus sellStatus;

  private List<ProductImageDto> productImageDtoList =new ArrayList<>();

  private List<Long> productImageIds = new ArrayList<>();

  private static ModelMapper modelMapper = new ModelMapper();

  private String requestDetail;

  public Product createProduct(){
    return modelMapper.map(this, Product.class);
  }

  public static ProductFormDto of(Product product){
    return modelMapper.map(product, ProductFormDto.class);
  }

}