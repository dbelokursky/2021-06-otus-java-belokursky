package ru.otus.dataprocessor;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;

class ProcessorAggregatorTest {

    @Test
    void process() {
        //given
        List<Measurement> measurements = List.of(new Measurement("val1", 1),
                new Measurement("val2", 2),
                new Measurement("val1", 1),
                new Measurement("val2", 2));
        Processor processor = new ProcessorAggregator();

        //when
        Map<String, Double> actual = processor.process(measurements);

        //then
        AssertionsForInterfaceTypes.assertThat(actual).containsEntry("val1", 2D);
        AssertionsForInterfaceTypes.assertThat(actual).containsEntry("val2", 4D);
    }
}