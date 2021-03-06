import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        // Create the CqlSession object:
        try (CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get("/root/creds.zip"))
                .withAuthCredentials("DBUser","DBPassword")
                .withKeyspace("user_management")
                .build()) {

            session.execute("CREATE TABLE IF NOT EXISTS user_management.users (last_name text, first_name text, email text, address text, PRIMARY KEY((last_name), first_name, email))");

            session.execute(
                    SimpleStatement.builder( "INSERT INTO users (last_name, first_name, email, address) VALUES (?,?,?,?)")
                            .addPositionalValues("Smith", "Alex","asmith@gmail.com","123 Main st.")
                            .build());


            ResultSet rs = session.execute(
                    SimpleStatement.builder("SELECT * FROM users WHERE last_name=?")
                            .addPositionalValue("Smith")
                            .build());

            Row row = rs.one();
            System.out.format("%s %s\n", row.getString("first_name"), row.getString("email"));

        }

        System.exit(0);
    }
}
