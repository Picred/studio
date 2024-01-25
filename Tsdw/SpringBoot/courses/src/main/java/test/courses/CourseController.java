package test.courses;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class CourseController {
    private final CourseRepository repo;

    public CourseController(CourseRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path="/")
    public String getAll(Model model){
        model.addAttribute("courses", repo.findAll());
        return "index";
    }

    @GetMapping(path="/create")
    public String createCourse(Model model){
        model.addAttribute("course", new CourseModel());
        return "create";
    }

    @PostMapping(path="/store")
    public String storeCourse(@ModelAttribute CourseModel m, Model model){
        repo.save(m);
        return "redirect:/";
    }

    @GetMapping(path="/edit")
    public String editCourse(Model model, Long id){
        model.addAttribute("course", repo.findById(id));
        return "edit";
    }

    @GetMapping(path="/show")
    public String showCourse(Model model, Long id){
        model.addAttribute("course", repo.getReferenceById(id));
        return "show";
    }

    @GetMapping(path="/delete")
    public String deleteCourse(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/";
    }


}