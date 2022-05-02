package ch.heig.examples.bim;

import io.agroal.api.AgroalDataSource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/files")
public class FileResource {

    @Inject
    AgroalDataSource dataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String findFile() {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM files");
            while(resultSet.next()){
                System.out.println(resultSet.getInt(1)+"  "+resultSet.getString(2));
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new NotFoundException("Could not connect to datasouce!");
        }
        return "Tested connection";
    }
}