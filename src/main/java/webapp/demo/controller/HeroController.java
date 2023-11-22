package webapp.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.demo.model.Hero;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeroController {
    private List<Hero> heroesList = new ArrayList<>();

    public HeroController() {
    }

    public List<Hero> getHeroesList() {
        heroesList.add(new Hero(1, "JB", "warrior", 5));
        heroesList.add(new Hero(2, "Nathalie", "wizard", 8));
        heroesList.add(new Hero(3, "Massimo", "warrior", 5));
        return heroesList;
    }

    @GetMapping("/heroes")
    public List<Hero> displayHeroes() {
        return getHeroesList();
    }
}
