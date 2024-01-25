package test.continents;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContinentRepository extends JpaRepository<ContinentModel,Long> {
    List<ContinentModel> findByAbitantiLessThan(int abitanti);
    List<ContinentModel> findByNome(String nome);
}
