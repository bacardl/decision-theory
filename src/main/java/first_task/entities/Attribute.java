package first_task.entities;

import java.util.Objects;

public class Attribute implements Comparable<Attribute> {
    private Double value;
    private String name;
    private Criteria criteria;

    public Attribute(String name, Double value, Criteria criteria) {
        this.name = name;
        this.value = value;
        this.criteria = criteria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return getValue().equals(attribute.getValue()) &&
                Objects.equals(getName(), attribute.getName()) &&
                getCriteria() == attribute.getCriteria();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getName(), getCriteria());
    }

    @Override
    public int compareTo(Attribute object) {
        if (criteria == Criteria.MAX) {
            return this.value.compareTo(object.value);
        } else {
            return (-1) * this.value.compareTo(object.value);
        }
    }
}
