package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final String ID_VALUE = "1";
    public static final String DESCRIPTION = "description";
    UnitOfMeasureCommandToUnitOfMeasure sut;

    @Before
    public void setUp() throws Exception {
        sut = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(sut.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(sut.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

        unitOfMeasureCommand.setId(ID_VALUE);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = sut.convert(unitOfMeasureCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
        assertEquals(ID_VALUE, unitOfMeasure.getId());

    }
}