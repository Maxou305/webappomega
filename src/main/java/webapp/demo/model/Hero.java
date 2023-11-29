package webapp.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

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
@Table(name = "hero")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank (message = "Le nom est obligatoire !")
    private String name;
    @NotBlank (message = "Le type est obligatoire !")
    private String type;
    @Max(value = 11, message = "La vie ne doit pas excéder 10 !")
    private int life;

    public Hero() {
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

}
