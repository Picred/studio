package test.project;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
    List<ProjectModel> findByTitle(String title);
}
