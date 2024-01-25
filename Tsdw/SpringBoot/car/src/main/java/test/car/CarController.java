package test.car;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class CarController {
    private final CarRepository repo;

    public CarController(CarRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping(path="/")
    public String getAllCars(Model model){
        model.addAttribute("cars", repo.findAll());
        return "index";
    }
    
    @GetMapping(path="/show")
    public String showDetails(Model model, Long id){
        model.addAttribute("car", repo.getReferenceById(id));
        return "show";
    }

    @GetMapping(path="/create")
    public String createCar(Model model){
        return "create";
    }

    @PostMapping(path="/store")
    public String storeCar(@ModelAttribute CarModel p, Model model){
        repo.save(p);
        return "redirect:/";
    }

    @GetMapping(path="/edit")
    public String editCar(Model model, Long id){
        model.addAttribute("car", repo.getReferenceById(id));
        return "edit";
    }

    @PostMapping(path="/update")
    public String updateCar(@ModelAttribute CarModel p, Model model){
        repo.save(p);
        return "redirect:/";
    }

    @GetMapping(path="/delete")
    public String deleteCar(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/";
    }
}
