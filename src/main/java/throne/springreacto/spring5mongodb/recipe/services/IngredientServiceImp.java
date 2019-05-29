package throne.springreacto.spring5mongodb.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import throne.springreacto.spring5mongodb.recipe.commands.IngredientCommand;
import throne.springreacto.spring5mongodb.recipe.converters.IngredientCommandToIngredient;
import throne.springreacto.spring5mongodb.recipe.converters.IngredientToIngredientCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Ingredient;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.repositories.RecipeRepository;
import throne.springreacto.spring5mongodb.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImp implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImp(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdByIngredientId(String recipeId, String ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional
                .orElseThrow(() -> new IllegalArgumentException("There is no recipe with this id " + recipeId));

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        IngredientCommand ingredientCommand = ingredientCommandOptional
                .orElseThrow(() -> new IllegalArgumentException("There is no ingredient item with this id " + ingredientId));
        ingredientCommand.setRecipeId(recipeId);
        return ingredientCommand;
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand, String recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new IllegalArgumentException("The unit of measurement not found for this ingredient"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        } else {
            log.error("Recipe not found for id: " + recipeId);
            return new IngredientCommand();
        }


    }

    @Override
    public void deleteById(String recipeId, String idToDelete) {

        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("found Ingredient");
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
    }
}
