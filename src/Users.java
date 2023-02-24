import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Users extends JFrame {
  private JPanel mainPanel;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JLabel usernameLabel;
  private  JLabel passwordLabel;
  private JButton signUpBtn;
  private String usernameFieldValue = "";
  private String passwordFieldValue = "";

  private Statement statement = null;

  // constructor
  public Users() {
    this.signUpBtn = new JButton("Sign Up");
    this.usernameField = new JTextField(15);
    this.passwordField = new JPasswordField(15);
    this.usernameLabel = new JLabel("Username:");
    this.passwordLabel = new JLabel("Password:");
    this.mainPanel = new JPanel();
  }

  public void createFrame() {

    this.setTitle("Sign Up");
    this.setSize(300, 150);
    this.setLocation(new Point(500, 300));

    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.add(mainPanel);

    mainPanel.add(usernameLabel);
    mainPanel.add(usernameField);
    mainPanel.add(passwordLabel);
    mainPanel.add(passwordField);
    mainPanel.add(signUpBtn);

    mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    this.setVisible(true);

    // When clicking the button

    this.signUpBtn.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        usernameFieldValue = usernameField.getText();
        passwordFieldValue = passwordField.getText();

        // insert value to the usr table
        insert(usernameFieldValue, passwordFieldValue);
      }
    });
  }

  // Connect to postgres database
  public Connection getConnection() {

    String user = "postgres";
    String password = "";
    String url = "jdbc:postgresql://localhost:5432/postgres";

    Connection connection = null;

    try {
      connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return connection;
  }

  // Create or drop table

  public void createTable() {

    String sql = "CREATE TABLE IF NOT EXISTS USR (id SERIAL PRIMARY KEY, name VARCHAR(40) NOT NULL, password VARCHAR(40) NOT NULL)";

    try {
      statement = getConnection().createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  public void dropTable() {

    String sql = "DROP TABLE IF EXISTS USR";

    try {
      statement = getConnection().createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  // Insert values to the table
  public void insert(String username, String password) {

    String sql = "INSERT INTO USR (name, password) values ( '" + username + "', '" + password + "')";

    try {
      statement = getConnection().createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException exception) {
      System.out.println(exception.getMessage());
    }

  }

  // Main method

  public static void main(String[] args) {

    Users u = new Users();
    // u.dropTable();
    // u.createTable();
    u.createFrame();

  }

}
