package com.projet7.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire de connexion à la base de données PostgreSQL.
 *
 * ⚠️ ADAPTER l'URL, l'utilisateur et le mot de passe selon ta configuration locale
 * (nom de la base créée dans pgAdmin/psql, identifiants de connexion).
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/gestion_pensions";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Amaranthe21I";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
    