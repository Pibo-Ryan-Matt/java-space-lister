package com.codeup.spacelister.dao;

import com.codeup.spacelister.models.Ad;
import com.codeup.spacelister.models.Planet;
import com.codeup.spacelister.util.Config;
import com.mysql.cj.jdbc.Driver;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAdsDao implements Ads {
    private Connection connection = null;

    public MySQLAdsDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                config.getUrl(),
                config.getUser(),
                config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

    @Override
    public Ad selectedAd(long id) {
        try {
            String insertQuery = "SELECT * FROM ad WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return extractAd(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving selected ad.", e);
        }
    }

    @Override
    public List<Ad> all() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM ad");
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all ads.", e);
        }
    }

    @Override
    public List<Ad> search(String searchTerm, int searchLocation) {
        try {
            String insertQuery;
            if (searchLocation == 1){
                insertQuery = "SELECT * FROM ad WHERE title LIKE ?";
            } else if (searchLocation == 2){
                insertQuery = "SELECT * FROM ad WHERE category LIKE ?";
            } else {
                insertQuery = "SELECT * FROM ad WHERE id in (select ad_id from ad_planet WHERE planet_id in (select id from planet WHERE name LIKE ? ))";
            }

            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            String searchTermWithWildcards = "%" + searchTerm + "%";
            stmt.setString(1, searchTermWithWildcards);
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching through ads.", e);
        }
    }

    @Override
    public Long insert(Ad ad) {
        try {
            String insertQuery = "INSERT INTO ad(user_id, title, description, category) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, ad.getUserId());
            stmt.setString(2, ad.getTitle());
            stmt.setString(3, ad.getDescription());
            stmt.setString(4, ad.getCategory());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new ad.", e);
        }
    }

    private Ad extractAd(ResultSet rs) throws SQLException {
        return new Ad(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("category")
        );
    }

    private List<Ad> createAdsFromResults(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAd(rs));
        }
        return ads;
    }

    public void update(Ad ad) {
        String query = "UPDATE ad SET title = ?, description = ?, category = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ad.getTitle());
            stmt.setString(2, ad.getDescription());
            stmt.setString(3, ad.getCategory());
            stmt.setLong(4, ad.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating ad!", e);
        }
    }

    @Override
    public int getPlanetID (String planet) {
        String query = "SELECT id from planet WHERE name = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, planet);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException("Error getting planet ID", e);
        }
    }

    @Override
    public void addToPlanetAds (int planet, Long ID) {
        String query = "INSERT INTO ad_planet (planet_id, ad_id) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, planet);
            stmt.setLong(2, ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating planet_ad", e);
        }
    }

    @Override
    public void deleteEntry (Long ID, int queryString){
        String query;
        if (queryString == 1){
            query = "DELETE from ad_planet where ad_id = ?";
        } else {
            query = "DELETE from ad where id = ?";
        }
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating planet_ad", e);
        }
    }

    @Override
    public List<Planet> getAdPlanets (Long ID){
        String query = "SELECT url, name FROM planet WHERE id in (select planet_id from ad_planet WHERE ad_id in (select id from ad WHERE ad.id = ? ))";
        List<Planet> planetUrls = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                planetUrls.add(new Planet(rs.getString("name"), rs.getString("url")));
            }
            return planetUrls;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating planet_ad", e);
        }
    }


}
