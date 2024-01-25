package test.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjectRepository repo;
    
    public ProjectsController(ProjectRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("projects", repo.findAll());
        return "projects";
    }

    @GetMapping(path="/create")
    public String createProject() {
        return "create";
    }
}
