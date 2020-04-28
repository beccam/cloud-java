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
           .withAuthCredentials("username","password")
           .build()) {
           session.execute("CREATE KEYSPACE IF NOT EXISTS user_management WITH replication = {'class': 'NetworkTopologyStrategy', 'caas-dc': '1'}  AND durable_writes = true;");
           session.execute("CREATE TABLE IF NOT EXISTS users (last_name text, first_name  text, email text, address text,PRIMARY KEY((last_name), first_name, email)");
           session.execute("INSERT INTO users (last_name, first_name, email, address) VALUES ('Smith', 'Alex','asmith@gmail.com','123 Main st.')");
           ResultSet rs = session.execute("select first_name, last_name from user_management.users");
           Row row = rs.one();
           //Print the results of the CQL query to the console:
           if (row != null) {
               System.out.println(row.getString("first_name"));
           } else {
               System.out.println("An error occurred.");

       System.exit(0);
   }
}
