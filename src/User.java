import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;

    ArrayList<Food> foodList = new ArrayList<>();


    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addFood(Food food) {
        foodList.add(food);
    }

    public void printFoodList() {
        for(Food food : foodList) {
            System.out.println("Food Name: " + food.getFoodName() + ", Calories: " + food.getNumCalories());
        }
    }
    public void removeFood(Food food) {
        foodList.remove(food);
    }

    public void sumCalories() {
        int sum = 0;
        for(Food food : foodList) {
            sum += food.getNumCalories();
        }
        System.out.println("Total Calories: " + sum);
    }

}
