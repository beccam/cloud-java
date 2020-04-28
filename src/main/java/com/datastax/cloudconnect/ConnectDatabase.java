<<<<<<< HEAD:src/main/java/com/datastax/cloudconnect/ConnectDatabase.java
package com.datastax.cloudconnect;
=======
package connectdatabase;
>>>>>>> ae13cd4cdfdfa64477122b1adfbac2fdc56abf77:src/main/java/ConnectDatabase.java

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.*;
import java.nio.file.Paths;

public class ConnectDatabase {

   public static void main(String[] args) {
       // Create the CqlSession object:
       try (CqlSession session = CqlSession.builder()
           .withCloudSecureConnectBundle(Paths.get("/path/to/secure-connect-database_name.zip"))
           .withAuthCredentials("username","password")
           .withKeyspace("keyspace_name")
           .build()) {
           // Select the release_version from the system.local table:
           ResultSet rs = session.execute("select release_version from system.local");
           Row row = rs.one();
           //Print the results of the CQL query to the console:
           if (row != null) {
               System.out.println(row.getString("release_version"));
           } else {
               System.out.println("An error occurred.");
           }
       }
       System.exit(0);
   }
}