package webapp.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import webapp.demo.model.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Controller de Hero. Va s'occuper de tout ce qui est lié au Hero. Pour le moment intègre les méthodes getters et display
 * des Hero. Possède en attribut une List de Hero en ArrayList.
 */
@RestController
public class HeroController {
    private List<Hero> heroesList = new ArrayList<>();

    public HeroController() {
        initHeroesList();
    }

    /**
     * Permet de récupérer la liste des héros sous forme de List de Hero. Pour le moment, les Hero sont ajoutés à la main dans
     * l'ArrayList heroesList présente en attribut de la classe.
     *
     * @return heroesList, List de Hero
     */

    public List<Hero> getHeroesList() {
        return heroesList;
    }

    public void initHeroesList() {
        heroesList.add(new Hero(1, "JB", "warrior", 5));
        heroesList.add(new Hero(2, "Nathalie", "wizard", 8));
        heroesList.add(new Hero(3, "Massimo", "warrior", 5));
    }


    /**
     * Méthode permettant d'afficher tous les héros en format JSON dans le navigateur grâce à la mention @GetMapping.
     *
     * @return une Liste de Hero
     */
    @GetMapping("/heroes")
    public List<Hero> displayHeroes() {
        return getHeroesList();
    }

    /**
     * Permet de récupérer les héros via leur id. Le int "id" mis en paramètre avec la mention @PathVariable est le même que l'id
     * présent dans l'extension de l'URL "/heroes/{id}". Cette méthode utilise un filter et retourne un Hero. La mention Optionnal est importante à spécifier
     * car, potentiellement, l'id demandé ne correspond à rien.
     *
     * @param id - id du héros recherché
     * @return un Hero
     */
    @GetMapping("/heroes/{id}")
    public Hero getHeroByID(@PathVariable int id) throws NoSuchElementException {
        return getHeroesList().stream()
                .filter(hero -> hero.getId() == id)
                .findFirst().orElseThrow();
    }

    @PostMapping("/heroes")
    public void addHero(@RequestBody Hero hero) {
        heroesList.add(hero);
    }

    @PutMapping("/heroes/{id}")
    public void updateHero(@RequestBody Hero hero, @PathVariable int id) {
        heroesList.set(id, hero);
    }

    @Operation(summary = "deleteHero()", description = "permet de supprimer un hero de la liste")
    @DeleteMapping("/heroes/{id}")
    public List deleteHero(@PathVariable int id) {
        try {
            heroesList.remove(getHeroByID(id));
        } catch (NoSuchElementException e) {
        }
        return heroesList;
    }
}