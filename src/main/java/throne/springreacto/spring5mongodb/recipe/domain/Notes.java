package throne.springreacto.spring5mongodb.recipe.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
public class Notes {
    @Id
    private String id;
    private String recipeNotes;
}
