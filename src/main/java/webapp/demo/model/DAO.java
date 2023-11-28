package webapp.demo.model;

import java.util.List;

public interface DAO {

    List<Hero> findAll();

    Hero findById(int id);

    Hero save(Hero hero);

}
