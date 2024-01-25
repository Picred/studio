package test.continents;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class ContinentController {
    private final ContinentRepository repo;

    public ContinentController(ContinentRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path="/")
    public String getHome(){
        return "home";
    }

    @GetMapping(path="/index")
    public String getIndex(Model model){
        model.addAttribute("continents", repo.findAll());
        return "index";
    }

    @GetMapping(path="/create")
    public String createContinent(Model model){
        model.addAttribute("continents", new ContinentModel());
        return "create";
    }

    @PostMapping(path="/store")
    public String storeContinent(Model model, @ModelAttribute ContinentModel c){
        repo.save(c);
        return "redirect:/index";
    }

    @GetMapping(path="/show")
    public String showContinent(Model model, Long id){
        model.addAttribute("continent", repo.getReferenceById(id));
        return "show";
    }

    @GetMapping(path="/edit")
    public String editContinent(Model model, Long id){
        model.addAttribute("continent", repo.getReferenceById(id));
        return "edit";
    }

    @GetMapping(path="/delete")
    public String deleteContinent(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/index";
    }

    @GetMapping(path="/abitantimax")
    public String maxAbitantiContinent(Model model, int maxabitanti){
        model.addAttribute("continents", repo.findByAbitantiLessThan(maxabitanti));
        return "index";
    }

    @PostMapping(path="/search")
    public String searchNomeContinent(Model model, String nome){
        model.addAttribute("continents", repo.findByNome(nome));
        return "index";
    }

    @GetMapping(path="/deleteAll")
    public String deleteAllContinents(Model model){
        List<ContinentModel> continents = repo.findAll(); 
        
        for(ContinentModel continent : continents){
            repo.delete(repo.getReferenceById(continent.getId()));
        }
        return "redirect:/index";
    }
}