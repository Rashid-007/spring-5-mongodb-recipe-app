package throne.springreacto.spring5mongodb.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import throne.springreacto.spring5mongodb.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import throne.springreacto.spring5mongodb.recipe.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImp implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImp(UnitOfMeasureRepository unitOfMeasureRepository,
                                   UnitOfMeasureToUnitOfMeasureCommand uomToUnitOfMeasureCommand) {

        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomToUnitOfMeasureCommand = uomToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> getUnitOfMeasureList() {
        Set<UnitOfMeasureCommand> collect = StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false).map(uomToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
        return collect;
    }
}
