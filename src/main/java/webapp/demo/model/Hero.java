package webapp.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe Hero permettant d'instancier des héros.
 */
@Schema (example = """
        {"id": int,
        "name": String,
        "type": String,
        "life": int}
        """)
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank (message = "Le nom est obligatoire !")
    private String name;
    @NotBlank (message = "Le type est obligatoire !")
    private String type;
    @Max(11)
    private int life;

    public Hero() {
    }

    /**
     * Constructeur complet de Hero à 4 paramètres.
     * @param id id du personnage (int)
     * @param name nom du personnage (String)
     * @param type type du personnage (String)
     * @param life niveau de vie du personnage (int)
     */
    public Hero(int id, String name, String type, int life) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.life = life;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public String toString() {
        return "Hero {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", type = '" + type + '\'' +
                ", life = " + life +
                '}';
    }

    public void setId(Long id) {
        this.id = Math.toIntExact(id);
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
