package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.IngredientCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Ingredient;
import throne.springreacto.spring5mongodb.recipe.domain.Recipe;
import throne.springreacto.spring5mongodb.recipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientToIngredientCommandTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final String UOM_ID = "2";
    public static final String ID_VALUE = "1";


    IngredientToIngredientCommand converter;

    @Before
    public void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {

        //given
        Ingredient ingredient = new Ingredient();
        Recipe recipe = new Recipe();

        //when
        IngredientCommand convertedCmd = converter.convert(ingredient);

        //then
        assertNotNull(convertedCmd);
    }

    @Test
    public void testConvertNullUOM() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUnitOfMeasure(null);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }

    @Test
    public void testConvertWithUom() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);

        ingredient.setUnitOfMeasure(uom);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
        // assertEquals(RECIPE, ingredientCommand.get);
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }
}