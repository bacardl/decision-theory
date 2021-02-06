package first_task;

import first_task.algo.Util;
import first_task.entities.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Item> listOfItems = new ArrayList<>();
        Path path = Paths.get("src/resources/data.txt");
        try {
            List<String> contents = Files.readAllLines(path, StandardCharsets.UTF_16);
            for (String line : contents) {
                Item item = new Item();

                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");

                Long id = Long.parseLong(stringTokenizer.nextToken().substring(1));

                String name = stringTokenizer.nextToken();

                Attribute price = new Attribute(
                        "PRICE",
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MIN);
                Attribute flyingTime = new Attribute("FLYING_TIME",
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MAX);
                Attribute capacity = new Attribute("CAPACITY",
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MAX);

                Map<String, Attribute> attributes = new HashMap<>();
                attributes.put("PRICE", price);
                attributes.put("FLYING_TIME", flyingTime);
                attributes.put("CAPACITY", capacity);

                item.setId(id);
                item.setName(name);
                item.setAttributes(attributes);

                listOfItems.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Item item : listOfItems) {
            System.out.print(item);
        }

//######################################################################################################################
        List<Item> compromisedItems = Util.findCompromiseArea(listOfItems);
//######################################################################################################################


        System.out.println("Compromised area:");
        for (Item item : compromisedItems) {
            System.out.println(item);
        }

//######################################################################################################################
        System.out.println("Dominated area:");
        for (Item item : Util.findDominatedItems(listOfItems)) {
            System.out.println(item);
        }
//######################################################################################################################

        Map<String, Boundary> firstBoundaries = new HashMap<>();
        firstBoundaries.put("PRICE", new Boundary(2000d, Sign.GREATER_AND_EQUAL));
        firstBoundaries.put("FLYING_TIME", new Boundary(25d, Sign.LOWER));

        List<Item> firstMainByCriteria = Util.mainCriteriaMethod(compromisedItems, "CAPACITY", firstBoundaries);

        System.out.println("(1)Alternatives by main criteria method: ");
        for (Item item : firstMainByCriteria) {
            System.out.println(item);
        }

//------------------------------------------------------------------------------------------
        Map<String, Boundary> secondBoundaries = new HashMap<>();
        secondBoundaries.put("CAPACITY", new Boundary(500d, Sign.GREATER_AND_EQUAL));
        secondBoundaries.put("FLYING_TIME", new Boundary(7d, Sign.GREATER));

        List<Item> mainByCriteriaSecond = Util.mainCriteriaMethod(compromisedItems, "PRICE", secondBoundaries);

        System.out.println("(2)Alternatives by main criteria method: ");
        for (Item item : mainByCriteriaSecond) {
            System.out.println(item);
        }
//------------------------------------------------------------------------------------------
        Map<String, Boundary> thirdBoundaries = new HashMap<>();
        thirdBoundaries.put("CAPACITY", new Boundary(3_500d, Sign.LOWER));
        thirdBoundaries.put("PRICE", new Boundary(40_000d, Sign.LOWER_AND_EQUAL));

        List<Item> mainByCriteriaThird = Util.mainCriteriaMethod(compromisedItems, "FLYING_TIME", thirdBoundaries);

        System.out.println("(3)Alternatives by main criteria method: ");
        for (Item item : mainByCriteriaThird) {
            System.out.println(item);
        }

//######################################################################################################################

        Interval firstPriceInterval = new Interval(
                new Boundary(1500d, Sign.GREATER_AND_EQUAL),
                new Boundary(20000d, Sign.LOWER)
        );

        Interval firstFlyingTimeInterval = new Interval(
                new Boundary(Util.getMinByAttribute(compromisedItems, "FLYING_TIME"), Sign.GREATER_AND_EQUAL),
                new Boundary(Util.getMaxByAttribute(compromisedItems, "FLYING_TIME"), Sign.LOWER_AND_EQUAL)
        );

        Interval firstCapacityInterval = new Interval(
                new Boundary(Util.getMinByAttribute(compromisedItems, "CAPACITY"), Sign.GREATER_AND_EQUAL),
                new Boundary(Util.getMaxByAttribute(compromisedItems, "CAPACITY"), Sign.LOWER)
        );


        Map<String, Interval> firstIntervalByAttribute = new HashMap<>();
        firstIntervalByAttribute.put("PRICE", firstPriceInterval);
        firstIntervalByAttribute.put("FLYING_TIME", firstFlyingTimeInterval);
        firstIntervalByAttribute.put("CAPACITY", firstCapacityInterval);

        String[] firstAttributeOrder = new String[]{"PRICE", "FLYING_TIME", "CAPACITY"};

        List<Item> firstAlternativesBySequentialOptimization = Util.sequentialOptimization(
                compromisedItems,
                firstIntervalByAttribute,
                firstAttributeOrder
        );

        System.out.println("(1)Alternatives by sequential optimization method: ");
        for (Item item : firstAlternativesBySequentialOptimization) {
            System.out.println(item);
        }

        //------------------------------------------------------------------------------------------

        Interval secondPriceInterval = new Interval(
                new Boundary(Util.getMinByAttribute(compromisedItems, "PRICE"), Sign.GREATER_AND_EQUAL),
                new Boundary(Util.getMaxByAttribute(compromisedItems, "PRICE"), Sign.LOWER)
        );

        Interval secondFlyingTimeInterval = new Interval(
                new Boundary(15d, Sign.GREATER),
                new Boundary(31d, Sign.LOWER_AND_EQUAL)
        );

        Interval secondCapacityInterval = new Interval(
                new Boundary(Util.getMinByAttribute(compromisedItems, "CAPACITY"), Sign.GREATER_AND_EQUAL),
                new Boundary(Util.getMaxByAttribute(compromisedItems, "CAPACITY"), Sign.LOWER_AND_EQUAL)
        );


        Map<String, Interval> secondIntervalByAttribute = new HashMap<>();
        secondIntervalByAttribute.put("PRICE", secondPriceInterval);
        secondIntervalByAttribute.put("FLYING_TIME", secondFlyingTimeInterval);
        secondIntervalByAttribute.put("CAPACITY", secondCapacityInterval);

        String[] secondAttributeOrder = new String[]{"FLYING_TIME", "CAPACITY", "PRICE"};

        List<Item> secondAlternativesBySequentialOptimization = Util.sequentialOptimization(
                compromisedItems,
                secondIntervalByAttribute,
                secondAttributeOrder
        );

        System.out.println("(2)Alternatives by sequential optimization method: ");
        for (Item item : secondAlternativesBySequentialOptimization) {
            System.out.println(item);
        }

//######################################################################################################################
        Map<String, Boundary> firstBoundariesForFCA = new HashMap<>();
        firstBoundariesForFCA.put("CAPACITY", new Boundary(1800d, Sign.GREATER_AND_EQUAL));

        List<Item> firstAlternativesByFunctionalityCostAnalysis = Util.functionalityCostAnalyze(compromisedItems,
                "FLYING_TIME", "PRICE", firstBoundariesForFCA);

        System.out.println("(1)Alternatives by functionality-cost analyze method: ");
        for (Item item : firstAlternativesByFunctionalityCostAnalysis) {
            System.out.println(item);
        }
//------------------------------------------------------------------------------------------
        Map<String, Boundary> secondBoundariesForFCA = new HashMap<>();
        secondBoundariesForFCA.put("FLYING_TIME", new Boundary(25d, Sign.LOWER));

        List<Item> secondAlternativesByFunctionalityCostAnalysis = Util.functionalityCostAnalyze(compromisedItems,
                "CAPACITY", "PRICE", secondBoundariesForFCA);

        System.out.println("(2)Alternatives by functionality-cost analyze method: ");
        for (Item item : secondAlternativesByFunctionalityCostAnalysis) {
            System.out.println(item);
        }

//######################################################################################################################

        Map<String, Double> coefficients = new HashMap<>();
        coefficients.put("PRICE", 0.6);
        coefficients.put("FLYING_TIME", 0.2);
        coefficients.put("CAPACITY", 0.2);

        List<UtilityItem> alternativesByGeneralUtilityMethod = Util.generalUtilityMethod(compromisedItems, coefficients);

        System.out.println("Alternatives by general utility method: ");
        for (Item item : alternativesByGeneralUtilityMethod) {
            System.out.println(item);
        }
//######################################################################################################################
        List<UtilityItem> alternativesByGeneralMaxMinUtilityMethod = Util.generalUtilityMaxMinMethod(compromisedItems);

        System.out.println("Alternatives by general max-min utility method: ");
        for (Item item : alternativesByGeneralMaxMinUtilityMethod) {
            System.out.println(item);
        }
//######################################################################################################################
        List<OptimalPointItem> alternativesByOptimalPointMethod = Util.optimalPointMethod(compromisedItems);

        System.out.println("Alternatives by optimal point method: ");
        for (Item item : alternativesByOptimalPointMethod) {
            System.out.println(item);
        }
//######################################################################################################################

    }
}
