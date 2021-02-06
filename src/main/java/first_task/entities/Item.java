package first_task.entities;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class Item implements Comparable<Item>, FunctionalityCostAnalysis {
    private Long id;
    private String name;
    private Map<String, Attribute> attributes;

    public Item() {
    }

    public Item(Long id, String name, Map<String, Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Item(Long id, String name, Attribute[] attributes) {
        this.id = id;
        this.name = name;
        for (Attribute attribute : attributes) {
            this.attributes.put(attribute.getName(),attribute);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attribute[] getAttributesArray() {
        return attributes.values().toArray(new Attribute[attributes.size()]);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.id);
        stringBuilder.append(" ");

        stringBuilder.append(this.name);
        stringBuilder.append(" ");

        for (Attribute attribute : getAttributesArray()) {
            stringBuilder.append(attribute);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Arrays.equals(getAttributesArray(), item.getAttributesArray());
    }

    @Override
    public int compareTo(Item object) {
        int countGreater = 0;
        int countEqual = 0;
        int countLower = 0;
        int countAttributes = this.getAttributesArray().length;

        for (int i = 0; i < this.getAttributesArray().length; i++) {
            if (this.getAttributesArray()[i].compareTo(object.getAttributesArray()[i]) > 0) {
                countGreater++;
            } else if (this.getAttributesArray()[i].compareTo(object.getAttributesArray()[i]) == 0) {
                countEqual++;
            } else {
                countLower++;
            }
        }

        if (countGreater + countEqual == countAttributes) {
            return 1;
        } else if (countEqual == countAttributes || (countGreater + countEqual > 0 && countLower > 0)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public Double calculateRate(String nameOfFunctionalAttribute, String nameOfCostAttribute) throws IllegalArgumentException {
        Attribute functionalAttribute = this.attributes.get(nameOfFunctionalAttribute);
        Attribute costAttribute = this.attributes.get(nameOfCostAttribute);

        if (functionalAttribute == null || costAttribute == null) {
            throw new IllegalArgumentException("Wrong name of attributes!");
        }

        if (functionalAttribute.getCriteria() != Criteria.MAX || costAttribute.getCriteria() != Criteria.MIN) {
            throw new IllegalArgumentException("Wrong type of attributes!");
        }

        return functionalAttribute.getValue() / costAttribute.getValue();
    }
}
