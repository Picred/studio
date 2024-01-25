package test.teams;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamRepository extends JpaRepository<TeamModel, Long>{
    List<TeamModel> findByNome(String nome);
}
