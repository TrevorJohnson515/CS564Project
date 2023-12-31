import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.lang.Math;

/**
 * HomePage class the contains the constructor for the Home Page, its specifications and related
 * methods
 * 
 * @author avakharia, kjhunjhunwa2, tmjohnson32
 *
 */
public class HomePage extends JFrame {

  // field declarations
  private static JButton myAcc;
  private static final JLabel video_games = new JLabel("Video Games");
  private static final String[] types = {"Game Name", "Genre", "Platform", "Publisher"};
  private static JComboBox searchType;
  private static JTextField searchField;
  private static JButton search;
  private static String username;
  private static JList<String> list;
  private static JList<String> platformsList;
  private static JList<String> genresList;
  private static JList<String> publishersList;
  private static JButton homeBtn = new JButton("Back to Home");
  // private JTextField rating;

  /**
   * Method to switch the view to account page
   */
  void switchAccountPage() { 
    AccountPage accountPage = new AccountPage(username);
    accountPage.show();
    this.dispose();
  }

  /**
   * Method to switch the view to home page
   */
  void switchHomePage() {
    // method to switch to Home Page
    HomePage homePage = new HomePage(username);
    homePage.show();
    this.dispose();
  }

  /**
   * Method to search for game and return string array of names
   * @return
   */
  String[] search() {
    String searchVal = searchField.getText();
    String searchFor = searchType.getSelectedItem().toString();

    if (searchFor.equals("Game Name")) {

      ArrayList<Integer> resultGames = VideoGames.searchByName(searchVal); // call backend method to
                                                                           // query
      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0]; // get name of each game
        index++;
      }

      return gameNames;

    } else if (searchFor.equals("Genre")) {

      ArrayList<Integer> resultGames = Genre.searchByGenre(searchVal); // call backend method to
                                                                       // query

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0]; // get name of each game
        index++;
      }

      return gameNames;


    } else if (searchFor.equals("Platform")) {

      ArrayList<Integer> resultGames = Platform.searchByPlatform(searchVal); // call backend method
                                                                             // to query

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0]; // get name of each game
        index++;
      }

      return gameNames;


    } else {

      // Publisher is selected

      ArrayList<Integer> resultGames = Publisher.searchByPublisher(searchVal); // call backend
                                                                               // method to query

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0]; // get name of each game
        index++;
      }

      return gameNames;

    }

  }

  /**
   * Constructor for the Home Page with its specifications
   * 
   * @param username - username of the user that is currently logged in
   */
  public HomePage(String username) {

    this.username = username;

    // frame setup
    setTitle("Home Page"); // set title of app
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // set search bar panel
    JPanel searchBar = new JPanel(new FlowLayout());

    // set results panel
    JPanel results = new JPanel();

    // set bototm panel
    JPanel bottom = new JPanel();

    // add label for our project
    video_games.setFont(new Font("SansSerif", Font.BOLD, 25));
    video_games.setPreferredSize(new Dimension(200, 25));
    searchBar.add(video_games);

    // add type of search dropdown
    searchType = new JComboBox(types);
    searchType.setPreferredSize(new Dimension(150, 25));
    searchBar.add(searchType);

    // add search field and button
    searchField = new JTextField();
    searchField.setPreferredSize(new Dimension(400, 40));
    searchBar.add(searchField);

    search = new JButton("Search");
    search.setPreferredSize(new Dimension(100, 40));
    searchBar.add(search);

    search.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String[] gamesFound = search(); // call backend methods to search and display results
        list = new JList<String>(gamesFound);
        list.setVisibleRowCount(15);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter() {

          // method to handle user selection and display game data
          public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {

              results.removeAll(); // remove list of games to replace with info about selected game
              results.setBackground(Color.WHITE);
              results.setLayout(null);
              results.setPreferredSize(new Dimension(1000, 587));
              String gameChosen = list.getSelectedValue(); // get name of game chosen

              // get info about game
              String[] game = VideoGames.returnAllData(VideoGames.getGameID(gameChosen));

              JLabel gameName = new JLabel(game[0]);
              gameName.setFont(new Font("Helvetica Neue", Font.BOLD, 35));
              gameName.setBounds(88, 6, 823, 64);
              gameName.setHorizontalAlignment(SwingConstants.CENTER);
              results.add(gameName);

              JLabel platformLabel = new JLabel("Released On: ");
              platformLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              platformLabel.setBounds(411, 90, 132, 16);
              results.add(platformLabel);

              JLabel platformName = new JLabel(game[1]);
              platformName.setFont(new Font("SansSerif", Font.PLAIN, 20));
              platformName.setBounds(555, 79, 132, 38);
              results.add(platformName);

              JLabel yearLabel = new JLabel("Year of Release:");
              yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              yearLabel.setBounds(384, 143, 159, 16);
              results.add(yearLabel);

              JLabel year = new JLabel(game[2].substring(0, 4));
              year.setFont(new Font("SansSerif", Font.PLAIN, 20));
              year.setBounds(555, 143, 132, 16);
              results.add(year);

              JLabel genreLabel = new JLabel("Genre:");
              genreLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              genreLabel.setBounds(434, 197, 62, 16);
              results.add(genreLabel);

              JLabel genre = new JLabel(game[3]);
              genre.setFont(new Font("SansSerif", Font.PLAIN, 20));
              genre.setBounds(519, 190, 132, 31);
              results.add(genre);

              JSeparator separator = new JSeparator();
              separator.setForeground(Color.BLACK);
              separator.setBounds(85, 58, 830, 12);
              results.add(separator);

              JButton likeGenre = new JButton();
              likeGenre.setBounds(411, 225, 192, 29);

              // switch text of like button based on user value
              if (GenreThoughts.getPref(Genre.getGenreID(game[3]), username) == null
                  || GenreThoughts.getPref(Genre.getGenreID(game[3]), username) == 0) {
                likeGenre.setText("Like");
              } else {
                likeGenre.setText("Unlike");
              }

              likeGenre.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  if (likeGenre.getText().equals("Like")) {
                    // like the game
                    GenreThoughts.setGenrePref(Genre.getGenreID(game[3]), true, username);
                    likeGenre.setText("Unlike");
                  } else {
                    // unlike the game
                    GenreThoughts.setGenrePref(Genre.getGenreID(game[3]), false, username);
                    likeGenre.setText("Like");
                  }
                }

              });

              results.add(likeGenre);

              JLabel publisherLabel = new JLabel("Published By:");
              publisherLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              publisherLabel.setBounds(395, 276, 137, 31);
              results.add(publisherLabel);

              JLabel publisher = new JLabel(game[4]);
              publisher.setFont(new Font("SansSerif", Font.PLAIN, 20));
              publisher.setBounds(534, 276, 372, 31);
              results.add(publisher);

              JLabel globalSalesLabel = new JLabel("Global Sales (millions) :");
              globalSalesLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              globalSalesLabel.setBounds(364, 336, 226, 31);
              results.add(globalSalesLabel);

              JLabel globalSales = new JLabel(game[5]);
              globalSales.setFont(new Font("SansSerif", Font.PLAIN, 20));
              globalSales.setBounds(602, 336, 95, 31);
              results.add(globalSales);

              JLabel ratingLabel = new JLabel("Rating:");
              ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              ratingLabel.setBounds(395, 393, 73, 31);
              results.add(ratingLabel);

              Integer userRating =
                  GameThoughts.getRating(VideoGames.getGameID(gameChosen), username);

              JLabel avgLabel = new JLabel("Average Rating:");
              avgLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              avgLabel.setBounds(423, 439, 159, 31);
              results.add(avgLabel);

              String finalRating = "-";

              if (userRating == null) {
                // do nothing
              } else {
                finalRating = String.valueOf(userRating);
              }

              JTextField rating = new JTextField(finalRating, 1);
              rating.setHorizontalAlignment(SwingConstants.CENTER);
              rating.setBounds(478, 396, 26, 31);
              results.add(rating);

              JLabel outOf = new JLabel("/ 5");
              outOf.setFont(new Font("SansSerif", Font.PLAIN, 20));
              outOf.setBounds(506, 393, 33, 31);
              results.add(outOf);

              JSeparator separator_1 = new JSeparator();
              separator_1.setForeground(Color.BLACK);
              separator_1.setBounds(88, 569, 830, 12);
              add(separator_1);

              JButton save = new JButton("Save");
              save.setBounds(551, 395, 117, 29);
              save.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  // save new rating
                  GameThoughts.setGameRating(VideoGames.getGameID(gameChosen),
                      Integer.parseInt(rating.getText()), username);
                  save.setText("Saved!");

                  revalidate();
                  repaint();
                }

              });
              results.add(save);

              // add average rating
              Double avgRating = GameThoughts.getAvgRating(VideoGames.getGameID(gameChosen));
              String avgString = String.format("%.2f", avgRating.doubleValue());
              JLabel avg = new JLabel(avgString);
              avg.setFont(new Font("SansSerif", Font.PLAIN, 20));
              avg.setBounds(584, 439, 103, 31);
              results.remove(avg);
              results.add(avg);

              JButton likeGame = new JButton("");
              likeGame.setBounds(411, 482, 192, 29);

              // switch text of like button based on user value
              if (GameThoughts.getPref(VideoGames.getGameID(gameChosen), username) == null
                  || GameThoughts.getPref(VideoGames.getGameID(gameChosen), username) == 0) {
                likeGame.setText("Like");
              } else {
                likeGame.setText("Unlike");
              }

              likeGame.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  if (likeGame.getText().equals("Like")) {
                    GameThoughts.setGamePref(VideoGames.getGameID(gameChosen), true, username);
                    likeGame.setText("Unlike");
                  } else {
                    GameThoughts.setGamePref(VideoGames.getGameID(gameChosen), false, username);
                    likeGame.setText("Like");
                  }
                }

              });

              results.add(likeGame);

              Integer number = GameThoughts.numLikes(VideoGames.getGameID(gameChosen));

              if (number == null) {
                number = 0;
              }

              JLabel numLikesLabel = new JLabel("Number of Likes:");
              numLikesLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
              numLikesLabel.setBounds(421, 523, 172, 31);
              results.add(numLikesLabel);

              JLabel numLikes = new JLabel(Integer.toString(number));
              numLikes.setFont(new Font("SansSerif", Font.PLAIN, 20));
              numLikes.setBounds(596, 523, 33, 31);
              results.add(numLikes);

              revalidate();
              repaint();
            }
          }
        });
        results.removeAll(); // remove previous results if any before adding new ones
        results.setLayout(new FlowLayout());
        JScrollPane games = new JScrollPane(list);
        games.setPreferredSize(new Dimension(800, 500));
        results.add(games); // show results

        // switch to home page button add to screen
        homeBtn.setPreferredSize(new Dimension(180, 25));
        bottom.add(homeBtn);
        homeBtn.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            switchHomePage();
          }

        });

        revalidate(); // refresh the results
        repaint(); // refresh the results
      }
    });

    // default top genres, publishers and platforms tables
    Object[] col = {"Name", "Sales"};
    DefaultTableModel platformModel = new DefaultTableModel(col, 0);

    JTable platformsTable = new JTable(platformModel);
    ArrayList<String[]> platformsFound = Platform.topByPlatform(); // call backend methods
    for (String[] row : platformsFound) {
      Object[] rowAdd = {row[0], row[1]};
      platformModel.addRow(rowAdd);
    }
    platformsTable.setPreferredSize(new Dimension(300, 500));
    JPanel platformsPanel = new JPanel(new BorderLayout());
    platformsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Top Platforms", TitledBorder.CENTER, TitledBorder.TOP));
    platformsPanel.add(platformsTable, BorderLayout.CENTER);
    platformsPanel.add(platformsTable.getTableHeader(), BorderLayout.NORTH);
    results.add(platformsPanel);

    DefaultTableModel genreModel = new DefaultTableModel(col, 0);

    JTable genreTable = new JTable(genreModel);
    ArrayList<String[]> genresFound = Genre.topByGenre(); // call backend methods
    for (String[] row : genresFound) {
      Object[] rowAdd = {row[0], row[1]};
      genreModel.addRow(rowAdd);
    }
    genreTable.setPreferredSize(new Dimension(300, 500));
    JPanel genrePanel = new JPanel(new BorderLayout());
    genrePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Top Genres", TitledBorder.CENTER, TitledBorder.TOP));
    genrePanel.add(genreTable, BorderLayout.CENTER);
    genrePanel.add(genreTable.getTableHeader(), BorderLayout.NORTH);
    results.add(genrePanel);

    DefaultTableModel publisherModel = new DefaultTableModel(col, 0);

    JTable publisherTable = new JTable(publisherModel);
    ArrayList<String[]> publishersFound = Publisher.topByPublisher(); // call backend methods
    for (String[] row : publishersFound) {
      Object[] rowAdd = {row[0], row[1]};
      publisherModel.addRow(rowAdd);
    }
    publisherTable.setPreferredSize(new Dimension(300, 500));
    JPanel publisherPanel = new JPanel(new BorderLayout());
    publisherPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Top Publishers", TitledBorder.CENTER, TitledBorder.TOP));
    publisherPanel.add(publisherTable, BorderLayout.CENTER);
    publisherPanel.add(publisherTable.getTableHeader(), BorderLayout.NORTH);
    results.add(publisherPanel);

    // add my account button
    myAcc = new JButton(username);
    myAcc.setPreferredSize(new Dimension(80, 25));
    searchBar.add(myAcc);

    myAcc.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        switchAccountPage(); // call method to switch pages
      }

    });

    add(searchBar, BorderLayout.NORTH);
    add(results, BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);

    setVisible(true);

  }
}
