package com.railway.booking.dao.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.impl.Util.JdbcUtil;
import com.railway.booking.dao.impl.Util.TestDatabaseConnector;
import com.railway.booking.entity.Train;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TrainDaoImplTest {
    private static CrudDao<Train> trainDao;
    private static DatabaseConnector dataSource;
    private static final String SCHEMA_SQL_PATH = "src/test/resources/db/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/db/sql/data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryStringFromFile(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryStringFromFile(DATA_SQL_PATH);

    @BeforeClass
    public static void init() {
        dataSource = new TestDatabaseConnector();
        trainDao = new TrainDaoImpl(dataSource);
    }

    @Before
    public void dataBaseInit() throws SQLException {
        createTables(dataSource);
        fillTestData(dataSource);
    }

    private static void createTables(DatabaseConnector dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(CREATE_TABLES_QUERY);
        }
    }

    private static void fillTestData(DatabaseConnector dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(GENERATE_DATA_QUERY);
        }
    }

    private Train generateTestEntity(Integer id) {
        return new Train(1, "43K", "Подільський експрес");
    }

    private List<Train> generateListOfEntities(int quantity) {
        List<Train> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int id = i + 1;
            String code = "43K v" + i;
            String name = "експрес №" + i;
            Train train = new Train((id), code, name);
            list.add(train);
        }
        return list;
    }

    @Test
    public void saveShouldCorrectSaveUserToDatabase() {
        int countBeforeInsert = (int) trainDao.count();
        Train expected = generateTestEntity(countBeforeInsert + 1);

        trainDao.save(expected);
        Train actual = trainDao.findById(expected.getId()).orElse(null);

        assertNotNull(actual.getId());
        assertEquals(countBeforeInsert + 1, trainDao.count());
    }

    @Test
    public void findByIdShouldReturnCorrectUser() throws SQLException {
        createTables(dataSource);
        int countBeforeInsert = (int) trainDao.count();
        Train expected = generateTestEntity(countBeforeInsert + 1);
        trainDao.save(expected);

        Train actual = trainDao.findById(countBeforeInsert + 1).orElse(null);

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForFirstPage() throws SQLException {
        createTables(dataSource);
        int quantity = 9;
        int pageNumber = 1;
        int itemPerPage = 4;

        List<Train> trains = generateListOfEntities(quantity);
        trains.forEach(trainDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Train> actual = trainDao.findAll(page);
        List<Train> expected = trains.stream()
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
        assertTrue(actual.size() <= itemPerPage);
    }

    @Test
    public void findAllShouldReturnCorrectPagesForLastPageWhenItsNotFull() throws SQLException {
        createTables(dataSource);
        int quantity = 9;
        int pageNumber = 3;
        int itemPerPage = 4;

        List<Train> list = generateListOfEntities(quantity);
        list.forEach(trainDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Train> actual = trainDao.findAll(page);
        List<Train> expected = list.stream()
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
        int quantity = 9;
        int pageNumber = 3;
        int itemPerPage = 3;

        List<Train> entities = generateListOfEntities(quantity);
        entities.forEach(trainDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Train> actual = trainDao.findAll(page);
        List<Train> expected = entities.stream()
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
        int quantity = 9;
        int pageNumber = 2;
        int itemPerPage = 3;

        List<Train> entities = generateListOfEntities(quantity);
        entities.forEach(trainDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Train> actual = trainDao.findAll(page);
        List<Train> expected = entities.stream()
                .skip((pageNumber - 1) * itemPerPage)
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void update() throws SQLException {
        createTables(dataSource);
        int userCountBeforeInsert = (int) trainDao.count();
        int id = userCountBeforeInsert + 1;
        Train testEntity = generateTestEntity(id);
        trainDao.save(testEntity);

        int countAfterInsert = (int) trainDao.count();

        Train expected = new Train(1, "43K", "Подільський експрес");

        trainDao.update(expected);
        int countAfterUpdate = (int) trainDao.count();
        Train actual = trainDao.findById(id).orElse(null);

        assertEquals(expected, actual);
        assertEquals(countAfterUpdate, countAfterInsert);
    }

    @Test
    public void deleteById() {
        int userCountBeforeInsert = (int) trainDao.count();
        int id = userCountBeforeInsert + 1;
        Train testEntity = generateTestEntity(id);
        trainDao.save(testEntity);
        int beforeDeleteSize = (int) trainDao.count();

        trainDao.deleteById(id);
        int actualSize = (int) trainDao.count();


        Train actual = trainDao.findById(id).orElse(null);
        assertNull(actual);
        assertEquals(beforeDeleteSize - 1, actualSize);
        assertNotEquals(beforeDeleteSize, actualSize);
    }

    @Test
    public void countShouldReturnZeroOnEmptyTable() throws SQLException {
        createTables(dataSource);
        int actualCount = (int) trainDao.count();

        assertEquals(0, actualCount);
    }

    @Test
    public void countShouldReturnValidValueOnFilledTable() throws SQLException {
        createTables(dataSource);
        int expected = 9;

        List<Train> entities = generateListOfEntities(expected);
        entities.forEach(trainDao::save);
        int actualCount = (int) trainDao.count();
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