package throne.springreacto.spring5mongodb.recipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.exception.NotFoundException;
import throne.springreacto.spring5mongodb.recipe.services.RecipeService;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController sut;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void getRecipeByIdStatusOk() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeService.getById(any())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", hasProperty("id", notNullValue())))
                .andExpect(view().name("recipe/show"));

        verify(recipeService).getById(any());
    }

    @Test
    public void getRecipeByIdStatusNotFound() throws Exception {

        //when
        when(recipeService.getById(any())).thenThrow(NotFoundException.class);

        //when
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void requestRecipeFormStatusOk() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testSaveUpdateRecipeStatusOk() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        //when --> then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
                .param("directions", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));

        verify(recipeService).saveRecipeCommand(any());
    }

    @Test
    public void testSaveUpdateRecipeValidationFail() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "2000"))

                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        when(recipeService.findCommandById(any())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("recipe", hasProperty("id", is("2"))))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void deleteById() throws Exception {

        //given
        String id = "2";

        //when
        mockMvc.perform(get("/recipe/" + id + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        //then

    }
}