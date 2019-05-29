package throne.springreacto.spring5mongodb.recipe.services;

import throne.springreacto.spring5mongodb.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> getUnitOfMeasureList();
}
