package throne.springreacto.spring5mongodb.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeCommandToRecipe;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeToRecipeCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.exception.NotFoundException;
import throne.springreacto.spring5mongodb.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImp implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImp(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getById(String id) {
        Optional<Recipe> foundById = recipeRepository.findById(id);
        return foundById.orElseThrow(() -> new NotFoundException("Recipe not found for id value: " + id.toString()));//Customized exception instead of RuntimeException
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
        log.debug("saved RecipeId" + savedRecipe.getId());
        RecipeCommand savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);
        return savedRecipeCommand;
    }

    @Override
    //Added because we do conversion outside of spring initiated transaction. In case of lazy loading, without this it will throw exception
    public RecipeCommand findCommandById(String id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new IllegalArgumentException("No recipe is found with this ID"));
        RecipeCommand returnedRecipeCommand = recipeToRecipeCommand.convert(recipe);
        return returnedRecipeCommand;
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
