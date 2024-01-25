package test.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository repo;
    
    public ProjectController(ProjectRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        return "project";
    }

    @PostMapping
    public String storeProject(@ModelAttribute ProjectModel p, Model model){
        repo.save(p);
        return "redirect:/projects";
    }

    @GetMapping(path="/{id}")
    public String editProject(@PathVariable Long id, Model model){
        model.addAttribute("project", repo.getReferenceById(id));
        return "project";
    }

    @GetMapping(path="/delete/{id}")
    public String deleteProject(@PathVariable Long id, Model model){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/projects";
    }
}
