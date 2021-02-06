package first_task.entities;

public interface FunctionalityCostAnalysis {
    Double calculateRate(String nameOfFunctionalAttribute, String nameOfCostAttribute) throws IllegalArgumentException;
}
