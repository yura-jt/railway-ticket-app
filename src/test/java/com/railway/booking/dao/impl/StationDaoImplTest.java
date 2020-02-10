package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.StationDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.impl.Util.JdbcUtil;
import com.railway.booking.dao.impl.Util.TestDatabaseConnector;
import com.railway.booking.entity.Station;
import com.railway.booking.entity.StationType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class StationDaoImplTest {
    private static StationDao stationDao;
    private static DatabaseConnector dataSource;
    private static final String SCHEMA_SQL_PATH = "src/test/resources/db/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/db/sql/data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryStringFromFile(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryStringFromFile(DATA_SQL_PATH);
    private static final String INSERT_TRAIN_QUERY = "INSERT INTO trains (code, name) " +
            "VALUES ('43K', 'Подільський експрес'), ('122', 'Срібна стріла');";

    @BeforeClass
    public static void init() {
        dataSource = new TestDatabaseConnector();
        stationDao = new StationDaoImpl(dataSource);
    }

    @Before
    public void dataBaseInit() throws SQLException {
        createTables(dataSource);
        fillTestData(dataSource);
    }

    private static void createTables(DatabaseConnector dataSource) throws SQLException {
        executeQuery(dataSource, CREATE_TABLES_QUERY);
    }

    private static void fillTestData(DatabaseConnector dataSource) throws SQLException {
        executeQuery(dataSource, GENERATE_DATA_QUERY);
    }

    private static void insertTrain(DatabaseConnector dataSource) throws SQLException {
        executeQuery(dataSource, INSERT_TRAIN_QUERY);
    }

    private static void executeQuery(DatabaseConnector dataSource, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(query);
        }
    }

    private Station generateTestEntity(Integer id) {
        return Station.builder()
                .withId(1)
                .withName("Київ")
                .withStationType(StationType.DEPARTURE_STATION)
                .withTime(LocalTime.now())
                .withDistance(25)
                .withTrainId(1)
                .build();
    }

    private List<Station> generateListOfEntities(int quantity) {
        List<Station> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            StationType type = StationType.values()[
                    ThreadLocalRandom.current().nextInt(0, StationType.values().length)];

            Station station = Station.builder()
                    .withId(1 + i)
                    .withName("Київ #" + i)
                    .withStationType(type)
                    .withTime(LocalTime.now().minusMinutes(20))
                    .withDistance(25 + i)
                    .withTrainId(1)
                    .build();
            list.add(station);
        }
        return list;
    }

    @Test
    public void saveShouldCorrectSaveUserToDatabase() {
        int countBeforeInsert = (int) stationDao.count();
        Station expected = generateTestEntity(countBeforeInsert + 1);

        stationDao.save(expected);
        Station actual = stationDao.findById(expected.getId()).orElse(null);

        assertNotNull(actual.getId());
        assertEquals(countBeforeInsert + 1, stationDao.count());
    }

    @Test
    public void findByIdShouldReturnCorrectUser() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);
        int countBeforeInsert = (int) stationDao.count();
        Station expected = generateTestEntity(countBeforeInsert + 1);
        stationDao.save(expected);

        Station actual = stationDao.findById(countBeforeInsert + 1).orElse(null);

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getStationType(), actual.getStationType());
        assertEquals(expected.getDistance(), actual.getDistance());
        assertEquals(expected.getTrainId(), actual.getTrainId());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForFirstPage() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int quantity = 9;
        int pageNumber = 1;
        int itemPerPage = 4;

        List<Station> stations = generateListOfEntities(quantity);
        stations.forEach(stationDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Station> actual = stationDao.findAll(page);
        List<Station> expected = stations.stream()
                .limit(itemPerPage)
                .collect(Collectors.toList());
        System.out.println(actual);
        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
        assertTrue(actual.size() <= itemPerPage);
    }

    @Test
    public void findAllShouldReturnCorrectPagesForLastPageWhenItsNotFull() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int quantity = 9;
        int pageNumber = 3;
        int itemPerPage = 4;

        List<Station> list = generateListOfEntities(quantity);
        list.forEach(stationDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Station> actual = stationDao.findAll(page);
        List<Station> expected = list.stream()
                .skip((pageNumber - 1) * itemPerPage)
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForLastPageWhenItIsFull() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int quantity = 9;
        int pageNumber = 3;
        int itemPerPage = 3;

        List<Station> entities = generateListOfEntities(quantity);
        entities.forEach(stationDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Station> actual = stationDao.findAll(page);
        List<Station> expected = entities.stream()
                .skip((pageNumber - 1) * itemPerPage)
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForMiddlePage() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int quantity = 9;
        int pageNumber = 2;
        int itemPerPage = 3;

        List<Station> entities = generateListOfEntities(quantity);
        entities.forEach(stationDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Station> actual = stationDao.findAll(page);
        List<Station> expected = entities.stream()
                .skip((pageNumber - 1) * itemPerPage)
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void findAllStationsByTrainIdShouldReturnCorrectPagesForLastPageWhenItsNotFull() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int quantity = 9;
        List<Station> list = generateListOfEntities(quantity);
        list.forEach(stationDao::save);

        List<Station> actual = stationDao.findAllStationsByTrainId(1);
        List<Station> expected = new ArrayList<>(list);

        int expectedSize = quantity;

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void update() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int userCountBeforeInsert = (int) stationDao.count();
        int id = userCountBeforeInsert + 1;
        Station testEntity = generateTestEntity(id);
        stationDao.save(testEntity);

        int countAfterInsert = (int) stationDao.count();

        Station expected = Station.builder()
                .withId(1)
                .withName("Львів")
                .withStationType(StationType.ARRIVING_STATION)
                .withTime(LocalTime.now().plusHours(1))
                .withDistance(490)
                .withTrainId(1)
                .build();

        stationDao.update(expected);
        int countAfterUpdate = (int) stationDao.count();
        Station actual = stationDao.findById(id).orElse(null);

        assertEquals(expected, actual);
        assertEquals(countAfterUpdate, countAfterInsert);
    }

    @Test
    public void deleteById() {
        int userCountBeforeInsert = (int) stationDao.count();
        int id = userCountBeforeInsert + 1;
        Station testEntity = generateTestEntity(id);
        stationDao.save(testEntity);
        int beforeDeleteSize = (int) stationDao.count();

        stationDao.deleteById(id);
        int actualSize = (int) stationDao.count();


        Station actual = stationDao.findById(id).orElse(null);
        assertNull(actual);
        assertEquals(beforeDeleteSize - 1, actualSize);
        assertNotEquals(beforeDeleteSize, actualSize);
    }

    @Test
    public void countShouldReturnZeroOnEmptyTable() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int actualCount = (int) stationDao.count();

        assertEquals(0, actualCount);
    }

    @Test
    public void countShouldReturnValidValueOnFilledTable() throws SQLException {
        createTables(dataSource);
        insertTrain(dataSource);

        int expected = 9;

        List<Station> entities = generateListOfEntities(expected);
        entities.forEach(stationDao::save);
        int actualCount = (int) stationDao.count();
        assertEquals(expected, actualCount);
    }

    private int calculateExpectedSize(int pageNumber, int itemPerPage, int quantity) {
        if (pageNumber == 1) {
            return Math.min(itemPerPage, quantity);
        }
        if (pageNumber * itemPerPage < quantity) {
            return itemPerPage;
        }
        return quantity - ((pageNumber - 1) * itemPerPage);
    }
}