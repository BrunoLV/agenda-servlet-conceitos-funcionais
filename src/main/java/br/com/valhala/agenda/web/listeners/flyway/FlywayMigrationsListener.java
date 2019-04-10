package br.com.valhala.agenda.web.listeners.flyway;

import br.com.valhala.agenda.db.FabricaConexoesDataSouce;
import org.flywaydb.core.Flyway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FlywayMigrationsListener implements ServletContextListener {

    public FlywayMigrationsListener() {
        super();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Do nothing
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Flyway flyway = Flyway.configure().dataSource(FabricaConexoesDataSouce.getIntance().getDataSource()).load();
        flyway.repair();
        flyway.migrate();
    }

}
