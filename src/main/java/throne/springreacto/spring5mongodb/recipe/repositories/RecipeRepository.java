package throne.springreacto.spring5mongodb.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
