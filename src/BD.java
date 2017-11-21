
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.swing.DefaultListModel;

public class BD {

    private List<String> tables;
    private Connection con;
    private DatabaseMetaData md;

    public List<String> getTables() {
        return tables;
    }

    public boolean conectar(String user, String passwd) {
        try {
            tables = new ArrayList<>();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mozart.dis.ulpgc.es/PracticaDIU?useSSL=true", user, passwd);
            md = con.getMetaData();

            String[] types = {"TABLE"};
            ResultSet rs = md.getTables(null, null, "%", types);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void mostrar(List<String> tabla, DefaultListModel model) {
        try {
            for (String nombre : tabla) {
                ResultSet rs = md.getColumns(null, null, nombre, null);
                while (rs.next()) {
                    model.addElement(nombre + "." + rs.getString("COLUMN_NAME"));
                }
            }
        } catch (Exception e) {}
    }

    public boolean desconectar() {
        try {
            con.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

}
