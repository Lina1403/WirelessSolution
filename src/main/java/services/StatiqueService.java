package services;

import entities.Facture;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatiqueService {
    Connection cnx = DataSource.getInstance().getCnx();

        // Méthodes de calcul restent les mêmes

        public float calculerConsommationEnergieAppartementParTypeEtPeriode ( int idAppartement, Facture.
        Type typeFacture, LocalDate dateDebut, LocalDate dateFin) throws SQLException {
            java.sql.Date sqlDateDebut = convertToLocalDateViaSqlDate(dateDebut);
            java.sql.Date sqlDateFin = convertToLocalDateViaSqlDate(dateFin);

            float consommation = 0;

            String req = "SELECT montant FROM facture WHERE idAppartement = ? OR type = ? OR date BETWEEN ? AND ?";
            try (PreparedStatement statement = cnx.prepareStatement(req)) {
                statement.setInt(1, idAppartement);
                statement.setString(2, typeFacture.toString());
                statement.setDate(3, sqlDateDebut);
                statement.setDate(4, sqlDateFin);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        consommation += rs.getFloat("montant");
                    }
                }
            }
            return consommation;
        }

        public Map<Integer, Float> calculerConsommationEnergieTotaleParTypeEtPeriode (Facture.Type
        typeFacture, LocalDate dateDebut, LocalDate dateFin) throws SQLException {
            java.sql.Date sqlDateDebut = convertToLocalDateViaSqlDate(dateDebut);
            java.sql.Date sqlDateFin = convertToLocalDateViaSqlDate(dateFin);

            Map<Integer, Float> consommations = new HashMap<>();

            String req = "SELECT idAppartement, montant FROM facture WHERE type = ? OR date BETWEEN ? AND ?";
            try (PreparedStatement statement = cnx.prepareStatement(req)) {
                statement.setString(1, typeFacture.toString());
                statement.setDate(2, sqlDateDebut);
                statement.setDate(3, sqlDateFin);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int idAppartement = rs.getInt("idAppartement");
                        float montant = rs.getFloat("montant");
                        consommations.put(idAppartement, consommations.getOrDefault(idAppartement, 0f) + montant);
                    }
                }
            }
            return consommations;
        }

        public float calculerConsommationEnergieTotaleParEtageEtType ( int numeroEtage, Facture.
        Type typeFacture, LocalDate dateDebut, LocalDate dateFin) throws SQLException {
            float consommationEtage = 0;

            String req = "SELECT idAppartement FROM appartement WHERE nbrEtage = ? ";
            try (PreparedStatement statement = cnx.prepareStatement(req)) {
                statement.setInt(1, numeroEtage);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int idAppartement = rs.getInt("idAppartement");
                        consommationEtage += calculerConsommationEnergieAppartementParTypeEtPeriode(idAppartement, typeFacture, dateDebut, dateFin);
                    }
                }
            }
            return consommationEtage;
        }

        public java.sql.Date convertToLocalDateViaSqlDate (LocalDate dateToConvert){
            return java.sql.Date.valueOf(dateToConvert);
        }

    }
