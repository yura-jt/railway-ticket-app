package com.railway.booking.dao.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.impl.Util.JdbcUtil;
import com.railway.booking.dao.impl.Util.TestDatabaseConnector;
import com.railway.booking.entity.Tariff;
import com.railway.booking.entity.CarriageType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TariffDaoImplTest {
    private static CrudDao<Tariff> tariffDao;
    private static DatabaseConnector dataSource;
    private static final String SCHEMA_SQL_PATH = "src/test/resources/db/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/db/sql/data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryStringFromFile(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryStringFromFile(DATA_SQL_PATH);

    @BeforeClass
    public static void init() {
        dataSource = new TestDatabaseConnector();
        tariffDao = new TariffDaoImpl(dataSource);
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

    private Tariff generateTestEntity(Integer id) {
        return new Tariff(1, CarriageType.LUX, new BigDecimal(451.15));
    }

    private List<Tariff> generateListOfEntities() {
        int quantity = CarriageType.values().length;
        List<Tariff> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int id = i + 1;
            CarriageType carriageType = CarriageType.values()[i];
            BigDecimal rate = new BigDecimal(35 + (i * 10));
            Tariff tariff = new Tariff((id), carriageType, rate);
            list.add(tariff);
        }
        return list;
    }

    @Test
    public void saveShouldCorrectSaveUserToDatabase() throws SQLException {
        createTables(dataSource);
        int countBeforeInsert = (int) tariffDao.count();
        Tariff expected = generateTestEntity(countBeforeInsert + 1);

        tariffDao.save(expected);
        Tariff actual = tariffDao.findById(expected.getId()).orElse(null);

        assertNotNull(actual.getId());
        assertEquals(countBeforeInsert + 1, tariffDao.count());
    }

    @Test
    public void findByIdShouldReturnCorrectUser() throws SQLException {
        createTables(dataSource);
        int countBeforeInsert = (int) tariffDao.count();
        Tariff expected = generateTestEntity(countBeforeInsert + 1);
        tariffDao.save(expected);

        Tariff actual = tariffDao.findById(countBeforeInsert + 1).orElse(null);

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCarriageType(), actual.getCarriageType());
        assertEquals(expected.getRate(), actual.getRate());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForFirstPage() throws SQLException {
        createTables(dataSource);
        int pageNumber = 1;
        int itemPerPage = 4;

        List<Tariff> entities = generateListOfEntities();
        int quantity = entities.size();

        entities.forEach(tariffDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Tariff> actual = tariffDao.findAll(page);
        List<Tariff> expected = entities.stream()
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
        int pageNumber = 2;
        int itemPerPage = 2;

        List<Tariff> entities = generateListOfEntities();
        int quantity = entities.size();
        entities.forEach(tariffDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Tariff> actual = tariffDao.findAll(page);
        List<Tariff> expected = entities.stream()
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
        int pageNumber = 3;
        int itemPerPage = 1;

        List<Tariff> entities = generateListOfEntities();
        int quantity = entities.size();
        entities.forEach(tariffDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Tariff> actual = tariffDao.findAll(page);
        List<Tariff> expected = entities.stream()
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
        int pageNumber = 1;
        int itemPerPage = 2;

        List<Tariff> entities = generateListOfEntities();
        int quantity = entities.size();
        entities.forEach(tariffDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Tariff> actual = tariffDao.findAll(page);
        List<Tariff> expected = entities.stream()
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
        int userCountBeforeInsert = (int) tariffDao.count();
        int id = userCountBeforeInsert + 1;
        Tariff testEntity = generateTestEntity(id);
        tariffDao.save(testEntity);

        int countAfterInsert = (int) tariffDao.count();

        Tariff expected = new Tariff(1, CarriageType.COUPE, new BigDecimal("54.00"));

        tariffDao.update(expected);
        int countAfterUpdate = (int) tariffDao.count();
        Tariff actual = tariffDao.findById(id).orElse(null);

        assertEquals(expected, actual);
        assertEquals(countAfterUpdate, countAfterInsert);
    }

    @Test
    public void deleteById() throws SQLException {
        createTables(dataSource);
        int userCountBeforeInsert = (int) tariffDao.count();
        int id = userCountBeforeInsert + 1;
        Tariff testEntity = generateTestEntity(id);
        tariffDao.save(testEntity);
        int beforeDeleteSize = (int) tariffDao.count();

        tariffDao.deleteById(id);
        int actualSize = (int) tariffDao.count();


        Tariff actual = tariffDao.findById(id).orElse(null);
        assertNull(actual);
        assertEquals(beforeDeleteSize - 1, actualSize);
        assertNotEquals(beforeDeleteSize, actualSize);
    }

    @Test
    public void countShouldReturnZeroOnEmptyTable() throws SQLException {
        createTables(dataSource);
        int actualCount = (int) tariffDao.count();

        assertEquals(0, actualCount);
    }

    @Test
    public void countShouldReturnValidValueOnFilledTable() throws SQLException {
        createTables(dataSource);

        List<Tariff> entities = generateListOfEntities();
        int expected = entities.size();

        entities.forEach(tariffDao::save);
        int actualCount = (int) tariffDao.count();
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