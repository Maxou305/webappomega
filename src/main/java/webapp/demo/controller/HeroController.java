package webapp.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import webapp.demo.model.DAO;
import webapp.demo.model.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Controller de Hero. Va s'occuper de tout ce qui est lié au Hero. Pour le moment intègre les méthodes getters et display
 * des Hero. Possède en attribut une List de Hero en ArrayList.
 */
@RestController
@Repository
public class HeroController implements DAO {
    public static List<Hero> heroesList = new ArrayList<>();

    public HeroController() {
    }

    static {
        heroesList.add(new Hero(1, "JB", "Warrior", 5));
        heroesList.add(new Hero(2, "Nathalie", "Wizard", 8));
        heroesList.add(new Hero(3, "Massimo", "Warrior", 5));
    }


    @Operation(summary = "Permet de remplacer un héros par un nouveau", description = "Un JSON est attendu en RequestBody")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Héros remplacé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Héros non remplacé car le format n'es pas bon",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Héros non remplacé car le héros à remplacer n'existe pas",
                    content = @Content)})
    @PutMapping("/heroes/{id}")
    public void updateHero(@RequestBody Hero hero, @PathVariable int id) {
        heroesList.set(id, hero);
    }

    @Operation(summary = "Supprime un héros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Héros supprimé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Héros à supprimer non supprimé car entrée invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Héros à supprimer non trouvé",
                    content = @Content)})
    @DeleteMapping("/heroes/{id}")
    public List deleteHero(@PathVariable int id) {
        try {
            heroesList.remove(findById(id));
        } catch (NoSuchElementException e) {
        }
        return heroesList;
    }

    /**
     * Méthode permettant d'afficher tous les héros en format JSON dans le navigateur grâce à la mention @GetMapping.
     *
     * @return une Liste de Hero
     */
    @Operation(summary = "Retourne la liste des héros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Liste non trouvée",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Liste non trouvée",
                    content = @Content)})
    @Override
    @GetMapping("/heroes")
    public List<Hero> findAll() {
        return heroesList;
    }

    /**
     * Permet de récupérer les héros via leur id. Le int "id" mis en paramètre avec la mention @PathVariable est le même que l'id
     * présent dans l'extension de l'URL "/heroes/{id}". Cette méthode utilise un filter et retourne un Hero. La mention Optionnal est importante à spécifier
     * car, potentiellement, l'id demandé ne correspond à rien.
     *
     * @param id - id du héros recherché
     * @return un Hero
     */
    @Operation(summary = "Retourne un héros selon un id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Héros trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Héros non trouvée",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Héros non accessible",
                    content = @Content)})
    @GetMapping("/heroes/{id}")
    @Override
    public Hero findById(@PathVariable int id) {
        for (Hero hero : heroesList) {
            if (hero.getId() == id) {
                return hero;
            }
        }
        return null;

        //  return getHeroes().stream()
        //            .filter(hero -> hero.getId() == id)
        //              .findFirst().orElseThrow();
    }

    @Operation(summary = "Ajoute un héros à la liste")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Héros ajouté à la liste",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Héros non ajouté, le format n'est pas bon",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Héros non ajouté car la liste n'existe pas",
                    content = @Content)})
    @Override
    @PostMapping("/heroes")
    public Hero save(@RequestBody Hero hero) {
        heroesList.add(hero);
        return hero;
    }
}