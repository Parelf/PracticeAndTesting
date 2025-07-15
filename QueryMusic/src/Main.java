import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {

		// Just importing fixed info from a file.

		Properties properties = new Properties();

		try{
			properties.load(Files.newInputStream(Path.of("music.properties"),
					StandardOpenOption.READ));
		}catch (IOException e){
			throw new RuntimeException(e);
		}

		// Setting up our Query. Could be nice to place this logic within a JFrame to accept these strings as input.
		// Will need to validate and check it though. Bad input and all that.

		String albumName = "Tapestry";
		String query = "SELECT * FROM music.albumview WHERE album_name='%s'".formatted(albumName);

		// Making connection.

		var dataSource = new MysqlDataSource();
		dataSource.setServerName(properties.getProperty("serverName"));
		dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
		dataSource.setDatabaseName(properties.getProperty("databaseName"));

		// This is important as this code-block will automatically close the connection for you on completion.
		// This try makes the connection and also opens a statement on that connection.
		// It basically prepares a way to send your Query.
		try(var connection = dataSource.getConnection(
				properties.getProperty("user"),
				System.getenv("MYSQL_PASS"));
			Statement statement = connection.createStatement()
		) {

			// Here we send and receive our Query.
			ResultSet resultSet = statement.executeQuery(query);


			// Here we make a resultSet for our metadata, nicely formatted.
			// This should contain all the most basic metadata we need for easy operation.
			var meta = resultSet.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++){

				// Printing all our column's metadata.

				System.out.printf("%d %s %s %n",
						i,
						meta.getColumnName(i),
						meta.getColumnTypeName(i));

			}

			// Normal separator line.
			System.out.println("-".repeat(30));

			for (int i = 1; i <= meta.getColumnCount(); i++){

				// Printing our column headings with the resultset-metadata.
				System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());

			}

			// New Line.
			System.out.println();

			// Less descriptive method. No named column's
//			while (resultSet.next()){
//				System.out.printf("%d %s %s %n",
//						resultSet.getInt("track_number"),
//						resultSet.getString("artist_name"),
//						resultSet.getString("song_title")
//				);
//			}

			while (resultSet.next()){

				// on the same line, plop down each and every thing in the column
				for (int i = 1; i <= meta.getColumnCount(); i++){

					// Format all the things as String.
					System.out.printf("%-15s", resultSet.getString(i));

				}
				// New line for each row.
				System.out.println();

			}

		} catch (SQLException e){
			throw new RuntimeException(e);
		}

	}

}