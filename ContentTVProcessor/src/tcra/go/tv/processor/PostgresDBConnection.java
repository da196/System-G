package tcra.go.tv.processor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDBConnection {
 
    //private static final String url = "jdbc:postgresql://localhost:5432/mobile_money_prod";
    private static final String user = "postgres";
    private static final String password = "TcmS_54321"; //"TcmS_54321";
    private static final String databaseName = "tcms";

    public static Connection connect() {
        Connection connection = null;
        try {
              Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://10.200.221.15:5432/"+databaseName, user, password);
        } catch (SQLException e) {
			System.out.println(e.getMessage());	
        } finally {			
		}
        return connection;
    }
}

