package test.films;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilmRepository extends JpaRepository<FilmModel,Long>{
    List<FilmModel> getByIdGreaterThan(int id);
}
