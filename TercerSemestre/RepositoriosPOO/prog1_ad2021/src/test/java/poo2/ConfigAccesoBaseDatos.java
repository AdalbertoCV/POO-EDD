package poo2;

import org.dbunit.DefaultOperationListener;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;

public class ConfigAccesoBaseDatos {
    public final static String driverName="com.mysql.cj.jdbc.Driver";
    public final static String url="jdbc:mysql://localhost/controlconcursos";
    public final static String usuario="IngSW";
    public final static String clave="UAZsw2021";

    public static class CustomConfigurationOperationListener extends DefaultOperationListener implements IOperationListener {
        @Override
        public void connectionRetrieved(IDatabaseConnection iDatabaseConnection) {
            super.connectionRetrieved(iDatabaseConnection);
            iDatabaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        }
    }
}
