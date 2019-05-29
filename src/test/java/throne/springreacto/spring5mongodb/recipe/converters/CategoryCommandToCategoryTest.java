package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.CategoryCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CategoryCommandToCategoryTest {
    public static final String ID_VALUE = "1";
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory conveter;

    @Before
    public void setUp() {
        conveter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() {
        assertNull(conveter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(conveter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = conveter.convert(categoryCommand);

        //then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}