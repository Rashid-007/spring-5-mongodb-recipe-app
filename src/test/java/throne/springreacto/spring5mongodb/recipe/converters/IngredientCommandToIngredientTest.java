package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.IngredientCommand;
import throne.springreacto.spring5mongodb.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Ingredient;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientCommandToIngredientTest {
    public static final String IG_ID = "1";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    public static final String DESCRIPTION = "description";
    public static final String UOM_ID = "1";
    public static final String UOM_DESCRIPTION = "Uom description";

    IngredientCommandToIngredient sut;

    @Before
    public void setUp() {
        sut = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter() {
        assertNull(sut.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(sut.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(IG_ID);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        unitOfMeasureCommand.setDescription(UOM_DESCRIPTION);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredient ingredient = sut.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getId());
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }
}