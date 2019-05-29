package throne.springreacto.spring5mongodb.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.spring5mongodb.recipe.commands.IngredientCommand;
import throne.springreacto.spring5mongodb.recipe.converters.IngredientCommandToIngredient;
import throne.springreacto.spring5mongodb.recipe.converters.IngredientToIngredientCommand;
import throne.springreacto.spring5mongodb.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import throne.springreacto.spring5mongodb.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Ingredient;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.repositories.RecipeRepository;
import throne.springreacto.spring5mongodb.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImpTest {
    public static final String RECIPE_ID = "1";
    public static final String CMD_ID = "2";
    public static final String RESULT_ING_DESC = "description";
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientServiceImp sut;

    public IngredientServiceImpTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sut = new IngredientServiceImp(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient,
                unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdByIngredientId() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setId("1");
        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setDescription(RESULT_ING_DESC);
        ingredientTwo.setId("2");

        recipe.setIngredients(Set.of(ingredientOne, ingredientTwo)); //Java 9 addition

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(CMD_ID);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        //when
        IngredientCommand result = sut.findByRecipeIdByIngredientId(RECIPE_ID, CMD_ID);

        //then
        assertEquals(CMD_ID, result.getId());
        verify(recipeRepository, times(1)).findById(RECIPE_ID);
    }

    @Test
    public void testSaveIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(any())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = sut.saveIngredientCommand(command, any());

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(any())).thenReturn(recipeOptional);

        //when
        sut.deleteById("1", "3");

        //then
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}