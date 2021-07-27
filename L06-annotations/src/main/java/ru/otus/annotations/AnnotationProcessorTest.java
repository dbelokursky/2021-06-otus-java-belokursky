package ru.otus.annotations;

import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class AnnotationProcessorTest {

    List<Integer> nums = new ArrayList<>();

    @Before
    public void fillNums() {
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
    }

    @Test
    public void addElement() {
        int expected = 6;
        nums.add(6);
        int result = nums.size();
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @After
    public void clearNums() {
        int expected = 0;
        nums.clear();
        int result = nums.size();
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void failedTestExample() {
        int expected = 4;
        int result = nums.size();
        Assertions.assertThat(result).isEqualTo(expected);
    }

}