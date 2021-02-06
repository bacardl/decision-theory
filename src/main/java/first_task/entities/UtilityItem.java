package first_task.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UtilityItem extends Item {
    private Map<String, Double> utilitiesValues;
    private Map<String, Double> coefficients;

    private UtilityItem() {

    }

    public UtilityItem(Item item) {
        super(item.getId(), item.getName(), new HashMap<>(item.getAttributes()));
    }

    public Map<String, Double> getUtilitiesValues() {
        return utilitiesValues;
    }

    public void setUtilitiesValues(Map<String, Double> utilitiesValues) {
        this.utilitiesValues = utilitiesValues;
    }

    public Map<String, Double> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Map<String, Double> coefficients) {
        this.coefficients = coefficients;
    }

    public Double calculateUtilityValue() {
        double sum = 0d;
        Set<String> nameOfAttributes = getAttributes().keySet();
        for (String nameOfAttribute : nameOfAttributes) {
            sum += utilitiesValues.get(nameOfAttribute) * utilitiesValues.get(nameOfAttribute);
        }
        return sum;
    }

    public Double minUtilityValue(){
        Set<String> nameOfAttributes = getAttributes().keySet();
        Double minUtilityValue = Double.MAX_VALUE;
        for (String nameOfAttribute : nameOfAttributes) {
            Double utilityValue = utilitiesValues.get(nameOfAttribute);
            if(minUtilityValue > utilityValue) {
                minUtilityValue = utilityValue;
            }
        }
        return minUtilityValue;
    }
}
