package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final String DESCRIPTION = "description";
    public static final String ID_VALUE = "1";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(ID_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}