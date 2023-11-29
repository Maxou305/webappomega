package webapp.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import webapp.demo.model.DAO;
import webapp.demo.model.Hero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Hero> updateHero(@Valid @RequestBody Hero hero, @Valid @PathVariable int id) {
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
    public void deleteHero(@Valid @PathVariable int id) {
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
    public Hero findById(@Valid @PathVariable int id) {
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
    public Hero save(@Valid @RequestBody Hero hero) {
        heroDAO.save(hero);
        return hero;
    }

    @Operation(summary = "Retourne tous les héros en vie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Aucun héros en vie",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Liste non retournée car introuvable",
                    content = @Content)})
    @GetMapping("/heroes-alive")
    public List getAliveHeroes() {
        return heroDAO.findByLifeGreaterThan(0);
    }

    @Operation(summary = "Retourne tous les héros du même type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hero.class))}),
            @ApiResponse(responseCode = "400", description = "Le type n'existe pas",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Liste non retournée car introuvable",
                    content = @Content)})
    @GetMapping("/heroes-type/{type}")
    public List getHeroesByType(@Valid @PathVariable String type) {
        return heroDAO.findByTypeEqualsOrderByName(type);
    }

    @GetMapping("/heroes/")
    public Hero findHeroByName(@Valid @RequestParam String name) {
        return heroDAO.findByName(name);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}