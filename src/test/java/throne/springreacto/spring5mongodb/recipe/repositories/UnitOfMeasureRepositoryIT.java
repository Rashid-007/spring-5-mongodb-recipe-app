package throne.springreacto.spring5mongodb.recipe.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import throne.springreacto.spring5mongodb.recipe.bootstrap.RecipeBootstrap;
import throne.springreacto.spring5mongodb.recipe.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;


    @Before
    public void setUp() {
        //As there is no concept of transaction in MongoDB, the database changes are not roll backed after each test.
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @After
    public void cleanUP(){
        //As there is no concept of transaction in MongoDB, the database changes are not roll backed after each test.
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void findByDescription() {
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", teaspoon.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", cupOptional.get().getDescription());
    }
}