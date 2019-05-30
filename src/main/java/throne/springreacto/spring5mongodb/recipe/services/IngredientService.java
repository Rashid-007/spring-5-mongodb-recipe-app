package throne.springreacto.spring5mongodb.recipe.services;

import throne.springreacto.spring5mongodb.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdByIngredientId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(String recipeId, String idToDelete);
}
