package com.projet7.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe utilitaire de connexion a la base de donnees PostgreSQL.
 */
public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "/application.properties";
    private static final Properties PROPERTIES = loadProperties();
    private static boolean schemaChecked = false;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        ensureSchema(connection);
        return connection;
    }

    private static String getUrl() {
        return getConfig("DB_URL", "spring.datasource.url");
    }

    private static String getUser() {
        return getConfig("DB_USER", "spring.datasource.username");
    }

    private static String getPassword() {
        return getConfig("DB_PASSWORD", "spring.datasource.password");
    }

    private static String getConfig(String envKey, String propertyKey) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }

        String propertyValue = PROPERTIES.getProperty(propertyKey);
        if (propertyValue == null || propertyValue.isBlank()) {
            throw new IllegalStateException("Configuration manquante: " + propertyKey);
        }
        return propertyValue.trim();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (var input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Fichier introuvable: " + PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        return properties;
    }

    private static synchronized void ensureSchema(Connection connection) throws SQLException {
        if (schemaChecked) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    DO $$
                    BEGIN
                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tarif' AND column_name = 'numtarif')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tarif' AND column_name = 'num_tarif') THEN
                            ALTER TABLE tarif RENAME COLUMN numtarif TO num_tarif;
                        END IF;

                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'personne' AND column_name = 'prenom')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'personne' AND column_name = 'prenoms') THEN
                            ALTER TABLE personne RENAME COLUMN prenom TO prenoms;
                        END IF;

                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'fk_im')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'im') THEN
                            ALTER TABLE payer RENAME COLUMN fk_im TO im;
                        END IF;

                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'fk_numtarif')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'num_tarif') THEN
                            ALTER TABLE payer RENAME COLUMN fk_numtarif TO num_tarif;
                        END IF;

                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'date_paiement')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'date') THEN
                            ALTER TABLE payer RENAME COLUMN date_paiement TO date;
                        END IF;

                        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'conjoint' AND column_name = 'fk_im')
                           AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'conjoint' AND column_name = 'im') THEN
                            ALTER TABLE conjoint RENAME COLUMN fk_im TO im;
                        END IF;

                        IF EXISTS (
                            SELECT 1
                            FROM information_schema.columns
                            WHERE table_name = 'personne'
                              AND column_name = 'situation'
                              AND udt_name = 'statut_prsn'
                        ) THEN
                            ALTER TABLE personne ALTER COLUMN situation TYPE VARCHAR(20) USING situation::text;
                        END IF;
                    END
                    $$;
                    """);

            statement.execute("ALTER TABLE personne ADD COLUMN IF NOT EXISTS diplome VARCHAR(50)");
            statement.execute("ALTER TABLE personne ADD COLUMN IF NOT EXISTS nomconjoint VARCHAR(25)");
            statement.execute("ALTER TABLE personne ADD COLUMN IF NOT EXISTS prenomconjoint VARCHAR(25)");
            statement.execute("ALTER TABLE personne ADD COLUMN IF NOT EXISTS fk_numtarif VARCHAR(10)");
            statement.execute("""
                    UPDATE personne p
                    SET diplome = t.diplome
                    FROM tarif t
                    WHERE p.diplome IS NULL
                      AND p.fk_numtarif = t.num_tarif
                    """);
        }

        schemaChecked = true;
    }
}
