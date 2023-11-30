package webapp.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DAO extends JpaRepository<Hero, Integer> {
    List findByTypeEqualsIgnoreCaseOrderByName(String type);
    List findByLifeGreaterThan(int life);
    List findAllByName(String name);

}