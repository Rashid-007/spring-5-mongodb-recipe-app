package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.CategoryCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CategoryToCategoryCommandTest {

    public static final String ID_VALUE = "1";
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCommand convter;

    @Before
    public void setUp() {
        convter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(convter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }

}