package first_task.algo;

import first_task.entities.Attribute;
import first_task.entities.Criteria;
import first_task.entities.Item;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    private  static  final List<Item> LIST_OF_ITEMS = new ArrayList<>();

    private static final String PATH_TO_DATA = "src/resources/data.txt";
    private static final String DELIM_IN_FILE = ";";

    private static final String PRICE_ATTRIBUTE = "PRICE";
    private static final String FLYING_TIME_ATTRIBUTE = "FLYING_TIME";
    private static final String CAPACITY_ATTRIBUTE = "CAPACITY";
    public static final double EPSILON = 0.0000001;

    @BeforeClass
    public static void setUpData() {
        Path path = Paths.get(PATH_TO_DATA);
        try {
            List<String> contents = Files.readAllLines(path, StandardCharsets.UTF_16);
            for (String line : contents) {
                Item item = new Item();

                StringTokenizer stringTokenizer = new StringTokenizer(line, DELIM_IN_FILE);

                Long id = Long.parseLong(stringTokenizer.nextToken().substring(1));

                String name = stringTokenizer.nextToken();

                Attribute price = new Attribute(
                        PRICE_ATTRIBUTE,
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MIN);
                Attribute flyingTime = new Attribute(FLYING_TIME_ATTRIBUTE,
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MAX);
                Attribute capacity = new Attribute(CAPACITY_ATTRIBUTE,
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Criteria.MAX);

                Map<String, Attribute> attributes = new HashMap<>();
                attributes.put(PRICE_ATTRIBUTE, price);
                attributes.put(FLYING_TIME_ATTRIBUTE, flyingTime);
                attributes.put(CAPACITY_ATTRIBUTE, capacity);

                item.setId(id);
                item.setName(name);
                item.setAttributes(attributes);

                LIST_OF_ITEMS.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findCompromiseArea() {
        assertEquals(8, Util.findCompromiseArea(LIST_OF_ITEMS).size());
    }

    @Test
    public void findDominatedItems() {
        assertEquals(2, Util.findDominatedItems(LIST_OF_ITEMS).size());
    }
    
    @Test
    public void getMinByAttribute() {
        assertEquals(47820d, Util.getMinByAttribute(LIST_OF_ITEMS, PRICE_ATTRIBUTE), EPSILON);
        assertEquals(6d, Util.getMinByAttribute(LIST_OF_ITEMS, FLYING_TIME_ATTRIBUTE), EPSILON);
        assertEquals(180d, Util.getMinByAttribute(LIST_OF_ITEMS, CAPACITY_ATTRIBUTE), EPSILON);
    }

    @Test
    public void getMaxByAttribute() {
        assertEquals(999d, Util.getMaxByAttribute(LIST_OF_ITEMS, PRICE_ATTRIBUTE), EPSILON);
        assertEquals(31d, Util.getMaxByAttribute(LIST_OF_ITEMS, FLYING_TIME_ATTRIBUTE), EPSILON);
        assertEquals(3850d, Util.getMaxByAttribute(LIST_OF_ITEMS, CAPACITY_ATTRIBUTE), EPSILON);
    }
}