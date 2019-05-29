package throne.springreacto.spring5mongodb.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeCommandToRecipe;
import throne.springreacto.spring5mongodb.recipe.converters.RecipeToRecipeCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.exception.NotFoundException;
import throne.springreacto.spring5mongodb.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImpTest {

    public static final String RECIPE_ID = "1";
    public static final String CMD_ID = "2";
    RecipeServiceImp sut;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new RecipeServiceImp(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeById() {
        Recipe recipeById = new Recipe();
        recipeById.setId(RECIPE_ID);
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipeById));

        //WHEN
        Recipe recipe = sut.getById(RECIPE_ID);

        verify(recipeRepository).findById(any());
        assertEquals(RECIPE_ID, recipe.getId());
        assertNotNull("Null recipe returned", recipe);
        verify(recipeRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFound() {

        //given
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(any())).thenReturn(recipeOptional);

        //when
        Recipe recipe = sut.getById(RECIPE_ID);

        //then expect exception
    }

    @Test
    public void findCommandById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(CMD_ID);
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = sut.findCommandById(RECIPE_ID);

        assertEquals(CMD_ID, commandById.getId());
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeToRecipeCommand).convert(recipe);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = sut.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteById() {

        //given
        String id = "2";

        //when
        sut.deleteById(id);

        //then
        verify(recipeRepository, times(1)).deleteById(id);
    }
}