package test.magazzon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{
    List<ProductModel> getByPriceGreaterThan(int price);
}
