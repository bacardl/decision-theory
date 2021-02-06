package first_task.algo;

import first_task.entities.*;

import java.util.*;

public class Util {
    public static List<Item> findCompromiseArea(List<Item> itemList) {
        List<Item> compromiseItems = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            boolean dominated = false;
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(i) != itemList.get(j)) {
                    if (itemList.get(i).compareTo(itemList.get(j)) < 0) {
                        dominated = true;
                        break;
                    }
                }
            }

            if (!dominated) {
                compromiseItems.add(itemList.get(i));
            }
        }
        return compromiseItems;
    }

    public static List<Item> findDominatedItems(List<Item> itemList) {
        List<Item> dominatedItems = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            boolean dominated = false;
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(i) != itemList.get(j)) {
                    if (itemList.get(i).compareTo(itemList.get(j)) < 0) {
                        dominated = true;
                        break;
                    }
                }
            }

            if (dominated) {
                dominatedItems.add(itemList.get(i));
            }
        }
        return dominatedItems;
    }

    public static List<Item> mainCriteriaMethod(List<Item> listOfItems, String mainCriteriaAttr, Map<String, Boundary> boundaries) {
        if (listOfItems.size() == 0) {
            return new ArrayList<>();
        }

        List<Item> filteredList = new ArrayList<>(listOfItems);

        filteredList = getCleanListByBoundaries(filteredList, boundaries);

        Double extremValue = getMaxByAttribute(filteredList, mainCriteriaAttr);


        for (Iterator<Item> itemIterator = filteredList.iterator(); itemIterator.hasNext(); ) {
            Item item = itemIterator.next();
            Attribute mainAttribute = item.getAttributes().get(mainCriteriaAttr);
            if (mainAttribute != null) {
                if (!extremValue.equals(mainAttribute.getValue())) {
                    itemIterator.remove();
                }
            } else {
                throw new IllegalStateException("Not exist main attribute in item");
            }
        }
        return filteredList;
    }

    public static List<Item> sequentialOptimization(List<Item> listOfItems,
                                                    Map<String, Interval> intervals,
                                                    String[] orderForAttributes) {
        if (listOfItems.size() == 0) {
            return new ArrayList<>();
        }
        List<Item> resultList = new ArrayList<>(listOfItems);

        Map<String, Double> extremMap = new HashMap<>();

        for (String attributeOrder : orderForAttributes) {
            for (Iterator<Item> itemIterator = resultList.iterator(); itemIterator.hasNext(); ) {
                Item item = itemIterator.next();
                Attribute purposeAttribute = item.getAttributes().get(attributeOrder);
                if (!intervals.get(attributeOrder).checkNumber(purposeAttribute.getValue())) {
                    itemIterator.remove();
                }
            }

            extremMap.put(attributeOrder, getMaxByAttribute(resultList, attributeOrder));

            int countMaxs = 0;
            for (Iterator<Item> itemIterator = resultList.iterator(); itemIterator.hasNext(); ) {
                Item item = itemIterator.next();
                Attribute attribute = item.getAttributes().get(attributeOrder);
                if (extremMap.get(attributeOrder).equals(attribute.getValue())) {
                    countMaxs++;
                } else {
                    itemIterator.remove();
                }
            }
            if (countMaxs == 1 && resultList.size() == 1) {
                return resultList;
            }
        }

        return resultList;
    }

    public static List<Item> functionalityCostAnalyze(List<Item> listOfItems,
                                                      String functionalAttribute,
                                                      String costAttribute,
                                                      Map<String, Boundary> boundaries) {
        if (listOfItems.size() == 0) {
            return new ArrayList<>();
        }

        List<Item> resultList = new ArrayList<>(listOfItems);

        resultList = getCleanListByBoundaries(resultList, boundaries);

        Double maxRate = Double.MIN_VALUE;

        for (Item item : resultList) {
            Double itemRate = item.calculateRate(functionalAttribute, costAttribute);
            if (maxRate < itemRate) {
                maxRate = itemRate;
            }
        }

        for (Iterator<Item> itemIterator = resultList.iterator(); itemIterator.hasNext(); ) {
            Item item = itemIterator.next();
            if (!item.calculateRate(functionalAttribute, costAttribute).equals(maxRate)) {
                itemIterator.remove();
            }
        }
        return resultList;
    }

    public static List<UtilityItem> generalUtilityMethod(List<Item> listOfItems, Map<String, Double> coefficients) {
        List<UtilityItem> utilityItemList = getUtilisedItems(listOfItems, coefficients);

        Double maxItemUtility = Double.MIN_VALUE;

        for (UtilityItem item : utilityItemList) {
            Double itemUtility = item.calculateUtilityValue();
            if (maxItemUtility < itemUtility) {
                maxItemUtility = itemUtility;
            }
        }

        for (Iterator<UtilityItem> itemIterator = utilityItemList.iterator(); itemIterator.hasNext(); ) {
            Double itemUtility = itemIterator.next().calculateUtilityValue();
            if (!maxItemUtility.equals(itemUtility)) {
                itemIterator.remove();
            }
        }
        return utilityItemList;
    }

    public static List<UtilityItem> generalUtilityMaxMinMethod(List<Item> listOfItems) {
        if (listOfItems.size() == 0) {
            return new ArrayList<>();
        }
        Map<String, Attribute> attributeMap = listOfItems.get(0).getAttributes();
        Set<String> namesOfAttributes = attributeMap.keySet();
        Map<String, Double> coefficients = new HashMap<>();
        for (String namesOfAttribute : namesOfAttributes) {
            coefficients.put(namesOfAttribute, (double) (1d / namesOfAttributes.size()));
        }
        List<UtilityItem> utilityItemList = getUtilisedItems(listOfItems, coefficients);

        Double maxMinUtility = Double.MIN_VALUE;

        for (UtilityItem item : utilityItemList) {
            Double minUtilityValue = item.minUtilityValue();
            if (maxMinUtility < minUtilityValue) {
                maxMinUtility = minUtilityValue;
            }
        }

        for (Iterator<UtilityItem> itemIterator = utilityItemList.iterator(); itemIterator.hasNext(); ) {
            Double itemUtility = itemIterator.next().minUtilityValue();
            if (!maxMinUtility.equals(itemUtility)) {
                itemIterator.remove();
            }
        }
        return utilityItemList;
    }

    public static List<OptimalPointItem> optimalPointMethod(List<Item> listOfItems) {
        List<OptimalPointItem> optimalPointItemList = getOptimalPointItems(listOfItems);
        Double minDistance = Double.MAX_VALUE;

        for (OptimalPointItem optimalPointItem : optimalPointItemList) {
            Double euclideanDistance = optimalPointItem.getEuclideanDistance();
            if (minDistance > euclideanDistance) {
                minDistance = euclideanDistance;
            }
        }

        for (Iterator<OptimalPointItem> itemIterator = optimalPointItemList.iterator(); itemIterator.hasNext(); ) {
            OptimalPointItem item = itemIterator.next();
            if (!item.getEuclideanDistance().equals(minDistance)) {
                itemIterator.remove();
            }
        }
        return optimalPointItemList;
    }

    private static List<OptimalPointItem> getOptimalPointItems(List<Item> listOfItems) {
        List<Item> normalizedItems = getNormalizedItems(listOfItems);
        List<OptimalPointItem> optimalPointItems = new ArrayList<>();
        Map<String, Double> optimalPoint = getOptimalPoint(normalizedItems);
        for (Item item : normalizedItems) {
            OptimalPointItem optimalPointItem = new OptimalPointItem(item);
            optimalPointItem.setOptimalPoint(optimalPoint);
            optimalPointItems.add(optimalPointItem);
        }
        return optimalPointItems;
    }

    private static List<UtilityItem> getUtilisedItems(List<Item> listOfItems, Map<String, Double> coefficients) {
        List<UtilityItem> resultList = new ArrayList<>();
        List<Item> normalizedList = getNormalizedItems(listOfItems);

        Set<String> attributes = coefficients.keySet();

        Map<String, Double> minsByAttribute = new HashMap<>();
        Map<String, Double> maxsByAttribute = new HashMap<>();

        for (String attribute : attributes) {
            minsByAttribute.put(attribute, getMinByAttribute(normalizedList, attribute));
            maxsByAttribute.put(attribute, getMaxByAttribute(normalizedList, attribute));
        }

        for (Item item : normalizedList) {
            UtilityItem uItem = new UtilityItem(item);
            Map<String, Double> utilitiesValues = new HashMap<>();
            Map<String, Attribute> attributeMap = item.getAttributes();
            for (String nameOfAttribute : attributeMap.keySet()) {
                Double attributeUtilityValue = calculateUtilityAttribute(
                        attributeMap.get(nameOfAttribute),
                        minsByAttribute.get(nameOfAttribute),
                        maxsByAttribute.get(nameOfAttribute),
                        coefficients.get(nameOfAttribute));
                utilitiesValues.put(nameOfAttribute, attributeUtilityValue);
            }
            uItem.setUtilitiesValues(utilitiesValues);
            uItem.setCoefficients(coefficients);
            resultList.add(uItem);
        }
        return resultList;
    }

    private static Map<String, Double> getOptimalPoint(List<Item> normalizedList) {
        Map<String, Double> optimalPoint = new HashMap<>();

        Map<String, Attribute> attributesMap = normalizedList.get(0).getAttributes();
        Set<String> nameOfAttributes = attributesMap.keySet();
        for (String nameOfAttribute : nameOfAttributes) {
            optimalPoint.put(nameOfAttribute, getMaxByAttribute(normalizedList, nameOfAttribute));
        }
        return optimalPoint;
    }

    private static List<Item> getNormalizedItems(List<Item> listOfItems) {
        if (listOfItems.size() == 0) {
            return new ArrayList<>();
        }
        List<Item> normalizedList = new ArrayList<>(listOfItems);

        Map<String, Attribute> attributesMap = normalizedList.get(0).getAttributes();
        Set<String> nameOfAttributes = attributesMap.keySet();

        for (Item item : normalizedList) {
            Map<String, Attribute> attributeMap = item.getAttributes();
            for (String nameOfAttribute : nameOfAttributes) {
                Attribute attribute = attributeMap.get(nameOfAttribute);
                if (attribute.getCriteria() == Criteria.MIN) {
                    attribute.setCriteria(Criteria.MAX);
                    attribute.setValue(-1d * attribute.getValue());
                }
            }
        }

        Map<String, Double> minsByAttribute = new HashMap<>();
        Map<String, Double> maxsByAttribute = new HashMap<>();

        for (String attribute : nameOfAttributes) {
            minsByAttribute.put(attribute, getMinByAttribute(normalizedList, attribute));
            maxsByAttribute.put(attribute, getMaxByAttribute(normalizedList, attribute));
        }

        for (Item item : normalizedList) {
            Map<String, Attribute> attributeMap = item.getAttributes();
            for (String nameOfAttribute : nameOfAttributes) {
                Attribute attribute = attributeMap.get(nameOfAttribute);
                attribute.setValue(getMinMaxNormalizedValue(
                        attribute.getValue(),
                        minsByAttribute.get(nameOfAttribute),
                        maxsByAttribute.get(nameOfAttribute)
                ));
            }
        }

        return normalizedList;
    }

    private static Double getMinMaxNormalizedValue(Double value, Double min, Double max) {
        return (value - min) / (max - min);
    }

    public static Double getMinByAttribute(List<Item> listOfItems, String attribute) {
        Double minByMaxDirection = Double.MAX_VALUE;
        Double minByMinDirection = Double.MIN_VALUE;

        for (Item item : listOfItems) {
            Attribute purposeAttribute = item.getAttributes().get(attribute);
            if (purposeAttribute != null) {
                if (purposeAttribute.getCriteria() == Criteria.MAX) {
                    if (minByMaxDirection > purposeAttribute.getValue()) {
                        minByMaxDirection = purposeAttribute.getValue();
                    }
                }

                if (purposeAttribute.getCriteria() == Criteria.MIN) {
                    if (minByMinDirection < purposeAttribute.getValue()) {
                        minByMinDirection = purposeAttribute.getValue();
                    }
                }
            } else {
                throw new IllegalArgumentException("Wrong attribute name!");
            }
        }
        return minByMaxDirection.equals(Double.MAX_VALUE) && !minByMinDirection.equals(Double.MIN_VALUE) ?
                minByMinDirection : minByMaxDirection;
    }

    public static Double getMaxByAttribute(List<Item> listOfItems, String attribute) {
        Double maxByMaxDirection = Double.MIN_VALUE;
        Double maxByMinDirection = Double.MAX_VALUE;

        for (Item item : listOfItems) {
            Attribute purposeAttribute = item.getAttributes().get(attribute);
            if (purposeAttribute != null) {
                if (purposeAttribute.getCriteria() == Criteria.MAX) {
                    if (maxByMaxDirection < purposeAttribute.getValue()) {
                        maxByMaxDirection = purposeAttribute.getValue();
                    }
                }

                if (purposeAttribute.getCriteria() == Criteria.MIN) {
                    if (maxByMinDirection > purposeAttribute.getValue()) {
                        maxByMinDirection = purposeAttribute.getValue();
                    }
                }
            } else {
                throw new IllegalArgumentException("Wrong attribute name!");
            }
        }
        return maxByMaxDirection.equals(Double.MIN_VALUE) && !maxByMinDirection.equals(Double.MAX_VALUE) ?
                maxByMinDirection : maxByMaxDirection;
    }

    private static List<Item> getCleanListByBoundaries(List<Item> originalList, Map<String, Boundary> boundaries) {
        List<Item> cleanedList = new ArrayList<>(originalList);

        itemLoop:
        for (Iterator<Item> itemIterator = cleanedList.iterator(); itemIterator.hasNext(); ) {
            Item item = itemIterator.next();
            Set<String> namesOfBoundedAttributes = boundaries.keySet();
            for (String nameOfAttribute : namesOfBoundedAttributes) {
                Boundary boundary = boundaries.get(nameOfAttribute);
                Attribute boundedAttribute = item.getAttributes().get(nameOfAttribute);
                if (boundedAttribute != null) {
                    if (!boundary.checkNumber(boundedAttribute.getValue())) {
                        itemIterator.remove();
                        continue itemLoop;
                    }
                } else {
                    throw new IllegalStateException("Wrong name of bounded attribute");
                }
            }
        }
        return cleanedList;
    }

    private static Double calculateUtilityAttribute(Attribute attribute, Double min, Double max, Double coefficient) {
        return Math.pow((attribute.getValue() - min) / (max - min), coefficient);
    }
}
