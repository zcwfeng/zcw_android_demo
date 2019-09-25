package top.zcwfeng.usedatas;

public class Strategies {

    private String stringField;

    private int intField;

    private double doubleField;

    public Strategies(String stringField, int intField, double doubleField) {
        this.stringField = stringField;
        this.intField = intField;
        this.doubleField = doubleField;
    }

    @Override
    public String toString() {
        return "Strategies{" +
                "stringField='" + stringField + '\'' +
                ", intField=" + intField +
                ", doubleField=" + doubleField +
                '}';
    }

}