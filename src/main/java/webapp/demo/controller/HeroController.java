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

import java.util.List;
import java.util.Optional;


@RestController
@Repository
public class HeroController {
    private DAO heroDAO;

    public HeroController(DAO heroDao) {
        this.heroDAO = heroDao;
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
    public List<Hero> updateHero(@RequestBody Hero hero, @PathVariable int id) {
        Hero temp = findById(id);
        temp.setName(hero.getName());
        temp.setLife(hero.getLife());
        temp.setType(hero.getType());
        heroDAO.save(temp);
        return findAll();
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
    public void deleteHero(@PathVariable int id) {
        heroDAO.deleteById(id);
    }

    @Operation(summary = "Retourne la liste des héros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Liste non trouvée",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Liste non trouvée",
                    content = @Content)})
    @GetMapping("/heroes")
    public List<Hero> findAll() {
        return heroDAO.findAll();
    }

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
    public Hero findById(@PathVariable int id) {
       return heroDAO.findById(id).orElseThrow();
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
    @PostMapping("/heroes")
    public Hero save(@RequestBody Hero hero) {
        heroDAO.save(hero);
        return hero;
    }

}