package test.magazzon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path="/")
    public String getAll(Model model){
        model.addAttribute("products", repo.findAll());
        return "index";
    }

    @GetMapping(path="/create")
    public String createProduct(Model model){
        model.addAttribute("product", new ProductModel());
        return "create";
    }

    @PostMapping(path="/store")
    public String createProduct(Model model, @ModelAttribute ProductModel p){
        repo.save(p);
        return "redirect:/";
    }


    @GetMapping(path="/show")
    public String showProduct(Model model, Long id){
        model.addAttribute("product", repo.getReferenceById(id));
        return "show";
    }

    @GetMapping(path="/edit")
    public String editProduct(Model model, Long id){
        model.addAttribute("product", repo.getReferenceById(id));
        return "edit";
    }


    @GetMapping(path="/delete")
    public String deleteProduct(Model model, Long id){
        repo.delete(repo.getReferenceById(id));
        return "redirect:/";
    }

    @GetMapping(path="/filter")
    public String filterProduct(Model model, Long id){
        model.addAttribute("products", repo.getByPriceGreaterThan(10));
        return "index";
    }
}
