import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * GenreThoughts class that initializes methods necessary for the genre_thoughts table
 * 
 * @author adivakharia
 *
 */
public class GenreThoughts {
  public static int user_id = 0;

  /**
   * Method to set the currUserID based on the given username
   * 
   * @param username
   * @return
   */
  public static boolean setCurrUserID(String username) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String query =
          "SELECT user_id FROM project.users WHERE username = " + "\"" + username + "\";";
      result = statement.executeQuery(query);

      if (result.next()) {
        user_id = result.getInt("user_id");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // close resources
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return false;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }

  /**
   * sets the rows inside the genre_thoughts table based on a specific users input
   * 
   * @param genreID
   * @param likes
   * @param username
   * @return
   */
  public static boolean setGenrePref(int genreID, boolean likes, String username) {
    setCurrUserID(username);

    int val = (likes) ? 1 : 0;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet result = null;
    ResultSet checkUserIDSet = null;
    ResultSet checkGenreIDSet = null;


    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);
      statement = connection.createStatement();

      String checkUsername = "SELECT EXISTS(SELECT * FROM project.genre_thoughts WHERE user_id = "
          + "\"" + user_id + "\") AS 'check';";
      checkUserIDSet = statement.executeQuery(checkUsername);

      if (checkUserIDSet.next()) {
        int count = checkUserIDSet.getInt("check");
        if (count == 0) { // user_id does not already exist in the game_thoughts table
          String insertRating =
              "INSERT INTO project.genre_thoughts (genre_id, user_id, likes) VALUES (?, ?, ?)";
          preparedStatement = connection.prepareStatement(insertRating);


          preparedStatement.setInt(1, genreID);
          preparedStatement.setInt(2, user_id);
          preparedStatement.setInt(3, val);
          // preparedStatement.setNull(4, java.sql.Types.NULL);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("Preference added successfully!");
          } else {
            System.out.println("Failed to add the preference.");
          }


        } else { // user_id exists in the game_thoughts table

          String checkGenreID =
              "SELECT EXISTS(SELECT * FROM project.genre_thoughts WHERE genre_id = " + "\""
                  + genreID + "\" AND user_id = " + "\"" + user_id + "\") AS 'check';";
          checkGenreIDSet = statement.executeQuery(checkGenreID);
          if (checkGenreIDSet.next()) {
            int countGame = checkGenreIDSet.getInt("check");
            if (countGame == 0) {
              String insertRating =
                  "INSERT INTO project.genre_thoughts (genre_id, user_id, likes) VALUES (?, ?, ?)";
              preparedStatement = connection.prepareStatement(insertRating);


              preparedStatement.setInt(1, genreID);
              preparedStatement.setInt(2, user_id);
              preparedStatement.setInt(3, val);
              // preparedStatement.setNull(4, java.sql.Types.NULL);

              int rowsAffected = preparedStatement.executeUpdate();
              if (rowsAffected > 0) {
                System.out.println("Preference added successfully!");
              } else {
                System.out.println("Failed to add the preference.");
              }
            } else {
              String updateRating = "UPDATE project.genre_thoughts " + "SET likes = " + "\"" + val
                  + "\" " + "WHERE user_id = " + "\"" + user_id + "\" " + "AND genre_id = " + "\""
                  + genreID + "\";";
              statement.executeUpdate(updateRating);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // close resources
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return false;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }

  /**
   * gets the likes/ does not like preferenece for a game for a user if they have set one does not
   * matter if user tries to call on pref they have not set, since default is always "does not like"
   * 
   * @param genreID
   * @return
   */
  public static Integer getPref(int genreID, String username) {
    setCurrUserID(username);
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    Integer pref = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPref = "SELECT likes FROM project.genre_thoughts WHERE user_id = " + "\"" + user_id
          + "\" AND genre_id = " + "\"" + genreID + "\";";
      result = statement.executeQuery(getPref);

      if (result.next()) {
        pref = result.getInt("likes");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close resources
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return null;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return null;
        }
      }
    }
    return pref;
  }

  /**
   * gets a list of all the genres that a specific user has liked
   * 
   * @param username
   * @return
   */
  public static ArrayList<Integer> getLikedGenres(String username) {
    setCurrUserID(username);
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    ArrayList<Integer> resultArray = new ArrayList<>();

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String searchLiked = "SELECT genre_id FROM project.genre_thoughts " + "WHERE user_id = "
          + "\"" + user_id + "\"" + " AND likes = 1";

      result = statement.executeQuery(searchLiked);

      while (result.next()) {
        resultArray.add(result.getInt("genre_id"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close resources
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return null;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return null;
        }
      }
    }

    return resultArray;
  }
}
