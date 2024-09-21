import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/foods";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Devil2012.";

    // Get a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // get calories per 100g for a specific food
    public int getCaloriesPer100g(String foodName) {
        String query = "SELECT numcalories FROM Foods WHERE foodname = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, foodName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("numcalories"); // Return the calories per 100g
                } else {
                    return -1; // Food not found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // In case of error, return -1
        }
    }

    // Retrieve all foods
    public void listFoods() {
        String query = "SELECT * FROM Foods";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String foodName = resultSet.getString("foodname");
                int numCalories = resultSet.getInt("numcalories");
                System.out.println("ID: " + id + ", Food Name: " + foodName + ", Calories: " + numCalories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a new food item
    public void addFood(String foodName, int numCalories) {
        String query = "INSERT INTO Foods (foodname, numcalories) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(query)) {

            insertStmt.setString(1, foodName);
            insertStmt.setInt(2, numCalories);
            int rowsInserted = insertStmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing food item
    public void updateFood(String foodName, int numCalories) {
        String query = "UPDATE Foods SET numcalories = ? WHERE foodname = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, numCalories);
            preparedStatement.setString(2, foodName);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a food item
    public void deleteFood(int id) {
        String query = "DELETE FROM Foods WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(query)) {

            deleteStmt.setInt(1, id);
            int rowsDeleted = deleteStmt.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reorderFoodIDs() {
        String selectQuery = "SELECT id FROM Foods ORDER BY id";
        String updateQuery = "UPDATE Foods SET id = ? WHERE id = ?";

        try (Connection connection = getConnection();
             Statement selectStatement = connection.createStatement();
             ResultSet resultSet = selectStatement.executeQuery(selectQuery)) {

            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }

            connection.setAutoCommit(false);

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                int newID = 1;
                for (int oldID : ids) {
                    updateStatement.setInt(1, newID);
                    updateStatement.setInt(2, oldID);
                    updateStatement.executeUpdate();
                    newID++;
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
