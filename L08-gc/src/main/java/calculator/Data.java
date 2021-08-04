package calculator;

@lombok.Data
public class Data {
    private Integer value;

    public Data(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
