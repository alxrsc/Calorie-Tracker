public class Food {
    private String foodName;
    private int numCalories;

    public Food(String foodName, int numCalories) {
        this.foodName = foodName;
        this.numCalories = numCalories;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getNumCalories() {
        return numCalories;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setNumCalories(int numCalories) {
        this.numCalories = numCalories;
    }
}
