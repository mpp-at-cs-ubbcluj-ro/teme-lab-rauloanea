package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry("Getting cars made by {}", manufacturerN);

        var str = "select * from cars where manufacturer = ?";
        var conn = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(str)) {
            stmt.setString(1, manufacturerN);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String manufacturer = rs.getString("manufacturer");
                String model = rs.getString("model");
                int year = rs.getInt("year");

                var car = new Car(manufacturer, model, year);
                car.setId(id);
                cars.add(car);
            }

            logger.traceExit();
            return cars;
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry("Getting cars between {} and {}", min, max);

        var str = "select * from cars where year > ? and year < ?";
        var conn = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(str)) {
            stmt.setInt(1, min);
            stmt.setInt(2, max);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String manufacturer = rs.getString("manufacturer");
                String model = rs.getString("model");
                int year = rs.getInt("year");

                var car = new Car(manufacturer, model, year);
                car.setId(id);
                cars.add(car);
            }

            logger.traceEntry();
            return cars;
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("Saving car {}", elem);

        String sql = "insert into cars (manufacturer, model, year) values (?, ?, ?)";
        var con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, elem.getManufacturer());
            stmt.setString(2, elem.getModel());
            stmt.setInt(3, elem.getYear());

            var res = stmt.executeUpdate();
            logger.traceExit("Saved {} instances.", res);
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e.getMessage());
        }

        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry("Updating car with id {}", integer);

        String sql = "update cars set manufacturer = ?, model = ?, year = ? where id = ?";
        var con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, elem.getManufacturer());
            stmt.setString(2, elem.getModel());
            stmt.setInt(3, elem.getYear());
            stmt.setInt(4, integer);

            var res = stmt.executeUpdate();
            logger.traceExit("Updated {} instances.", res);
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e.getMessage());
        }
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry("Getting all cars from DB");

        List<Car> cars = new ArrayList<>();
        var str = "select * from cars";
        var conn = dbUtils.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(str);
        ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                int id = rs.getInt("id");
                String manufacturer = rs.getString("manufacturer");
                String model = rs.getString("model");
                int year = rs.getInt("year");

                Car car = new Car(manufacturer, model, year);
                car.setId(id);
                cars.add(car);
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e.getMessage());
        }

        logger.traceExit(cars);
        return cars;
    }
}