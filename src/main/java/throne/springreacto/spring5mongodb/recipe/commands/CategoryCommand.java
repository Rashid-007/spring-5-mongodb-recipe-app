package throne.springreacto.spring5mongodb.recipe.commands;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
}
