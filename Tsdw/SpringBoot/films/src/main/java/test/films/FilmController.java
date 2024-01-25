package test.films;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class FilmController {
    private final FilmRepository repo;

    public FilmController(FilmRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping(path="/")
    public String getAllFilms(Model model){
        model.addAttribute("films", repo.findAll());
        return "index";
    }

    @GetMapping(path="/create")
    public String createFilm(Model model){
        model.addAttribute("film", new FilmModel());
        return "create";
    }

    @PostMapping(path="/store")
    public String storeFilm(Model model, @ModelAttribute FilmModel f){
        repo.save(f);
        return "redirect:/";
    }

    @GetMapping(path="/show")
    public String showFilm(Model model, Long id){
        model.addAttribute("film", repo.getReferenceById(id));
        return "show";
    }

    @GetMapping(path="/edit")
    public String editFilm(Model model, Long id){
        model.addAttribute("film", repo.getReferenceById(id));
        return "edit";
    }

    @GetMapping(path="/delete")
    public String deleteFilm(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/";
    }

    @GetMapping(path="/filter")
    public String Film(Model model){
        model.addAttribute("films", repo.getByIdGreaterThan(6));
        return "index";
    }
}
