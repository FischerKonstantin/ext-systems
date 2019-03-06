package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonChecException;

import java.sql.*;
import java.time.LocalDate;

public class PersonCheckDao
{
    private static final String SQL_REQUEST = "SELECT temporal FROM cr_address_person ap " +
            "INNER JOIN cr_person p ON p.person_id = ap.person_id " +
            "INNER JOIN cr_address a ON a.address_id = ap.address_id " +
            "WHERE  " +
            "CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date OR ap.end_date is null)" +
            "and upper(p.sur_name) = upper(?)  " +
            "and upper(p.given_name) = upper(?) " +
            "and upper(p.patronic) = upper(?)  " +
            "and p.date_of_birth = ? " +
            "and a.street_code = ?  " +
            "and upper(a.building) = upper(?)  " ;

    public PersonResponse checkPerson (PersonRequest request) throws PersonChecException {
        PersonResponse responce = new PersonResponse();

        String sql = SQL_REQUEST;
        if (request.getExtension() != null) {
            sql += "and upper(extencion) = upper(?)  ";
        } else {
            sql += "and extencion is null ";
        }

        if (request.getApartment() != null) {
            sql += "and upper(apartment) = upper(?)  ";
        } else {
            sql += "and apartment is null ";
        }
        try (Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            int count = 1;
            stmt.setString(count++, request.getSurName());
            stmt.setString(count++, request.getGivenName());
            stmt.setString(count++, request.getPatronimic());
            stmt.setDate(count++, java.sql.Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(count++, request.getStreetCode());
            stmt.setString(count++, request.getBuilding());
            if (request.getExtension() != null) {
                stmt.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null) {
                stmt.setString(count++, request.getApartment());
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                responce.setRegistered(true);
                responce.setTemporal(rs.getBoolean("temporal"));
            }

        } catch (SQLException ex){
            throw new PersonChecException(ex);
        }

        return responce;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost/city-register",
                "postgres", "GeNiUs89i");
    }
}
