package throne.springreacto.spring5mongodb.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5mongodb.recipe.commands.NotesCommand;
import throne.springreacto.spring5mongodb.recipe.domain.Notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NotesToNotesCommandTest {

    public static final String ID_VALUE = "1";
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }

}