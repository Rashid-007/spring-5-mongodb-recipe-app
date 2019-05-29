package throne.springreacto.spring5mongodb.recipe.services;

import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(String id);

    void deleteById(String id);
}
