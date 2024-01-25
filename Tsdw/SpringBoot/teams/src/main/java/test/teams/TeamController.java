package test.teams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class TeamController {
    private final TeamRepository repo;

    public TeamController(TeamRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path="/")
    public String getAll(Model model){
        model.addAttribute("teams", repo.findAll());
        return "index";
    }

    @GetMapping(path="/create")
    public String createTeam(Model model){
        model.addAttribute("team", new TeamModel());
        return "create";
    }

    @PostMapping(path="/store")
    public String storeTeam(Model model, @ModelAttribute TeamModel t){
        repo.save(t);
        return "redirect:/";
    }

    @GetMapping(path="/edit")
    public String editTeam(Model model, Long id){
        model.addAttribute("team", repo.getReferenceById(id));
        return "edit";
    }

    @GetMapping(path="/delete")
    public String deleteTeam(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/";
    }

    @GetMapping(path="/search")
    public String searchByName(Model model, String name){
        model.addAttribute("teams", repo.findByNome(name));
        return "filter";
    }
}
