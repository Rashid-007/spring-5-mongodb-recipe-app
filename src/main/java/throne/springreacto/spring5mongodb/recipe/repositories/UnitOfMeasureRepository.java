package throne.springreacto.spring5mongodb.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import throne.springreacto.spring5mongodb.recipe.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
