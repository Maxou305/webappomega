package webapp.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DAO extends JpaRepository<Hero, Integer> {
    Hero findByIdGreaterThan(int id);
}
