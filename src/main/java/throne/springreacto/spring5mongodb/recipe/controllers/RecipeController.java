package throne.springreacto.spring5mongodb.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import throne.springreacto.spring5mongodb.recipe.commands.RecipeCommand;
import throne.springreacto.spring5mongodb.recipe.exception.NotFoundException;
import throne.springreacto.spring5mongodb.recipe.services.RecipeService;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/recipe/{recipe_id}/show", method = RequestMethod.GET)
    public String getRecipeById(@PathVariable("recipe_id") String id, Model model) {
        model.addAttribute("recipe", recipeService.getById(id));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String requesRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";

    }

    @GetMapping(value = "/recipe/{id}/update")
    public String updateRecipe(@PathVariable("id") String id, Model model) {
        RecipeCommand commandById = recipeService.findCommandById(id);
        model.addAttribute("recipe", commandById);
        return "recipe/recipeform";
    }

    @GetMapping(value = "/recipe/{recipe_id}/delete")
    public String deleteRecipe(@PathVariable("recipe_id") String id) {
        recipeService.deleteById(id);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleControllerNotFoundException(Exception ex) {
        log.error("Handling not found Exception");
        ModelAndView notFoundView = new ModelAndView();
        log.error(ex.getMessage());

        notFoundView.setViewName("404error");
        notFoundView.addObject("exception", ex);

        return notFoundView;
    }
}
