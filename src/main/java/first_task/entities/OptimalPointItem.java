package first_task.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OptimalPointItem extends Item {
    private Map<String, Double> optimalPoint;

    public OptimalPointItem(Item item) {
        super(item.getId(), item.getName(), new HashMap<>(item.getAttributes()));
    }

    public Map<String, Double> getOptimalPoint() {
        return optimalPoint;
    }

    public void setOptimalPoint(Map<String, Double> optimalPoint) {
        this.optimalPoint = optimalPoint;
    }

    public Double getEuclideanDistance() {
        Map<String, Attribute> attributeMap =  this.getAttributes();
        Set<String> namesOfAttributes = attributeMap.keySet();
        double sumSquaredDifferences = 0;
        for (String nameOfAttribute : namesOfAttributes) {
            sumSquaredDifferences += Math.pow((attributeMap.get(nameOfAttribute).getValue() -
                    optimalPoint.get(nameOfAttribute)),2);
        }
        return Math.sqrt(sumSquaredDifferences);
    }
}
