package throne.springreacto.spring5mongodb.recipe.services;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeCommandToRecipe;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeToRecipeCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.repositories.RecipeRepository;

import static org.junit.Assert.assertEquals;
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
    public static final String NEW_DESCRIPTION = "New description";
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    RecipeService sut;

    @After
    public void cleanUp(){
        recipeRepository.deleteAll();
    }

    @Test
    public void testDescription() {
        //given
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        Recipe testRecipe = allRecipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = sut.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }

}
