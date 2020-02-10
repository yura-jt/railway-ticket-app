package com.railway.booking.dao.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.impl.Util.JdbcUtil;
import com.railway.booking.dao.impl.Util.TestDatabaseConnector;
import com.railway.booking.entity.Ticket;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TicketDaoImplTest {
    private static CrudDao<Ticket> ticketDao;
    private static DatabaseConnector dataSource;
    private static final String SCHEMA_SQL_PATH = "src/test/resources/db/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/db/sql/data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryStringFromFile(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryStringFromFile(DATA_SQL_PATH);

    @BeforeClass
    public static void init() {
        dataSource = new TestDatabaseConnector();
        ticketDao = new TicketDaoImpl(dataSource);
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

    private Ticket generateTestEntity(Integer id) {
        return Ticket.builder()
                .withId(1)
                .withDepartureStation("Київ")
                .withDestinationStation("Львів")
                .withPassengerName("Іван Миколайчук")
                .withPrice(new BigDecimal(154.15))
                .withFlightId(1)
                .withSeatId(20)
                .withUserId(1)
                .withBillId(1)
                .withCreatedOn(LocalDateTime.now())
                .build();
    }

    private List<Ticket> generateListOfEntities(int quantity) {
        List<Ticket> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = Ticket.builder()
                    .withId(i + 1)
                    .withDepartureStation("Київ #" + i)
                    .withDestinationStation("Львів #" + i)
                    .withPassengerName("Іван Миколайчук #" + i)
                    .withPrice(new BigDecimal(154.15 + i))
                    .withFlightId(1 + i)
                    .withSeatId(20 + i)
                    .withUserId(1 + i)
                    .withBillId(1 + i)
                    .build();
            list.add(ticket);
        }
        return list;
    }

    @Test
    public void saveShouldCorrectSaveUserToDatabase() {
        int countBeforeInsert = (int) ticketDao.count();
        Ticket expected = generateTestEntity(countBeforeInsert + 1);

        ticketDao.save(expected);
        Ticket actual = ticketDao.findById(expected.getId()).orElse(null);

        assertNotNull(actual.getId());
        assertEquals(countBeforeInsert + 1, ticketDao.count());
    }

    @Test
    public void findByIdShouldReturnCorrectUser() throws SQLException {
        createTables(dataSource);
        int countBeforeInsert = (int) ticketDao.count();
        Ticket expected = generateTestEntity(countBeforeInsert + 1);
        ticketDao.save(expected);

        Ticket actual = ticketDao.findById(countBeforeInsert + 1).orElse(null);

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDepartureStation(), actual.getDepartureStation());
        assertEquals(expected.getDestinationStation(), actual.getDestinationStation());
        assertEquals(expected.getPassengerName(), actual.getPassengerName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getFlightId(), actual.getFlightId());
        assertEquals(expected.getSeatId(), actual.getSeatId());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getBillId(), actual.getBillId());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForFirstPage() throws SQLException {
        createTables(dataSource);
        int quantity = 9;
        int pageNumber = 1;
        int itemPerPage = 4;

        List<Ticket> tickets = generateListOfEntities(quantity);
        tickets.forEach(ticketDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Ticket> actual = ticketDao.findAll(page);
        List<Ticket> expected = tickets.stream()
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
        int quantity = 9;
        int pageNumber = 3;
        int itemPerPage = 4;

        List<Ticket> list = generateListOfEntities(quantity);
        list.forEach(ticketDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Ticket> actual = ticketDao.findAll(page);
        List<Ticket> expected = list.stream()
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

        List<Ticket> entities = generateListOfEntities(quantity);
        entities.forEach(ticketDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Ticket> actual = ticketDao.findAll(page);
        List<Ticket> expected = entities.stream()
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

        List<Ticket> entities = generateListOfEntities(quantity);
        entities.forEach(ticketDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<Ticket> actual = ticketDao.findAll(page);
        List<Ticket> expected = entities.stream()
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
        int userCountBeforeInsert = (int) ticketDao.count();
        int id = userCountBeforeInsert + 1;
        Ticket testEntity = generateTestEntity(id);
        ticketDao.save(testEntity);

        int countAfterInsert = (int) ticketDao.count();

        Ticket expected = Ticket.builder()
                .withId(1)
                .withDepartureStation("Кривий РіГ")
                .withDestinationStation("Житомир")
                .withPassengerName("Аїда Антревепен")
                .withPrice(new BigDecimal(500.00))
                .withFlightId(5)
                .withSeatId(25)
                .withUserId(2)
                .withBillId(3)
                .build();

        ticketDao.update(expected);
        int countAfterUpdate = (int) ticketDao.count();
        Ticket actual = ticketDao.findById(id).orElse(null);

        assertEquals(expected, actual);
        assertEquals(countAfterUpdate, countAfterInsert);
    }

    @Test
    public void deleteById() {
        int userCountBeforeInsert = (int) ticketDao.count();
        int id = userCountBeforeInsert + 1;
        Ticket testEntity = generateTestEntity(id);
        ticketDao.save(testEntity);
        int beforeDeleteSize = (int) ticketDao.count();

        ticketDao.deleteById(id);
        int actualSize = (int) ticketDao.count();


        Ticket actual = ticketDao.findById(id).orElse(null);
        assertNull(actual);
        assertEquals(beforeDeleteSize - 1, actualSize);
        assertNotEquals(beforeDeleteSize, actualSize);
    }

    @Test
    public void countShouldReturnZeroOnEmptyTable() throws SQLException {
        createTables(dataSource);
        int actualCount = (int) ticketDao.count();

        assertEquals(0, actualCount);
    }

    @Test
    public void countShouldReturnValidValueOnFilledTable() throws SQLException {
        createTables(dataSource);
        int expected = 9;

        List<Ticket> entities = generateListOfEntities(expected);
        entities.forEach(ticketDao::save);
        int actualCount = (int) ticketDao.count();
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