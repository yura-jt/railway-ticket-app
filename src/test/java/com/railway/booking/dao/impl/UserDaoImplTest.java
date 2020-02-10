package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.impl.Util.JdbcUtil;
import com.railway.booking.dao.impl.Util.TestDatabaseConnector;
import com.railway.booking.entity.User;
import com.railway.booking.entity.RoleType;
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

public class UserDaoImplTest {
    private static UserDao userDao;
    private static DatabaseConnector dataSource;
    private static final String SCHEMA_SQL_PATH = "src/test/resources/db/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/db/sql/data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryStringFromFile(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryStringFromFile(DATA_SQL_PATH);

    @BeforeClass
    public static void init() {
        dataSource = new TestDatabaseConnector();
        userDao = new UserDaoImpl(dataSource);
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

    private User generateTestUser(Integer id) {
        return User.builder()
                .withId(id)
                .withFirstName("Canute")
                .withLastName("Ithidriel")
                .withEmail("canute@gmail.com")
                .withPhoneNumber("+3804565478754")
                .withPassword("password")
                .withRoleType(RoleType.PASSENGER)
                .build();
    }

    private List<User> generateListUser(int quantity) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            User user = User.builder()
                    .withId(i + 1)
                    .withFirstName("Canute" + i)
                    .withLastName("Ithidriel" + i)
                    .withEmail("canute@gmail.com" + i)
                    .withPhoneNumber("+3804565478754" + i)
                    .withPassword("password" + i)
                    .withRoleType(RoleType.PASSENGER)
                    .build();
            list.add(user);
        }
        return list;
    }


    @Test
    public void saveShouldCorrectSaveUserToDatabase() {
        int userCountBeforeInsert = (int) userDao.count();
        User expectedUser = generateTestUser(userCountBeforeInsert + 1);

        userDao.save(expectedUser);
        User actualUser = userDao.findByEmail(expectedUser.getEmail()).orElse(null);

        assertNotNull(actualUser.getId());
        assertEquals(userCountBeforeInsert + 1, userDao.count());
    }

    @Test
    public void findByIdShouldReturnCorrectUser() {
        int userCountBeforeInsert = (int) userDao.count();
        User expectedUser = generateTestUser(userCountBeforeInsert + 1);
        userDao.save(expectedUser);

        User actualUser = userDao.findById(userCountBeforeInsert + 1).orElse(null);

        assertEquals(expectedUser, actualUser);
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPhoneNumber(), actualUser.getPhoneNumber());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getRoleType(), actualUser.getRoleType());
    }

    @Test
    public void findByEmail() {
        int userCountBeforeInsert = (int) userDao.count();
        User expectedUser = generateTestUser(userCountBeforeInsert + 1);
        userDao.save(expectedUser);

        User actualUser = userDao.findByEmail(expectedUser.getEmail()).orElse(null);

        assertEquals(expectedUser, actualUser);
        assertEquals(expectedUser.getFirstName(), expectedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), expectedUser.getLastName());
        assertEquals(expectedUser.getEmail(), expectedUser.getEmail());
        assertEquals(expectedUser.getPhoneNumber(), expectedUser.getPhoneNumber());
        assertEquals(expectedUser.getPassword(), expectedUser.getPassword());
        assertEquals(expectedUser.getRoleType(), expectedUser.getRoleType());
    }

    @Test
    public void findAllShouldReturnCorrectPagesForFirstPage() throws SQLException {
        createTables(dataSource);
        int quantity = 9;
        int pageNumber = 1;
        int itemPerPage = 4;

        List<User> users = generateListUser(quantity);
        users.forEach(userDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<User> actual = userDao.findAll(page);
        List<User> expected = users.stream()
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

        List<User> users = generateListUser(quantity);
        users.forEach(userDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<User> actual = userDao.findAll(page);
        List<User> expected = users.stream()
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

        List<User> users = generateListUser(quantity);
        users.forEach(userDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<User> actual = userDao.findAll(page);
        List<User> expected = users.stream()
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

        List<User> users = generateListUser(quantity);
        users.forEach(userDao::save);

        Page page = new Page(pageNumber, itemPerPage);
        List<User> actual = userDao.findAll(page);
        List<User> expected = users.stream()
                .skip((pageNumber - 1) * itemPerPage)
                .limit(itemPerPage)
                .collect(Collectors.toList());

        int expectedSize = calculateExpectedSize(pageNumber, itemPerPage, quantity);

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void update() {
        int userCountBeforeInsert = (int) userDao.count();
        int id = userCountBeforeInsert + 1;
        User testUser = generateTestUser(id);
        userDao.save(testUser);

        int userCountAfterInsert = (int) userDao.count();

        User expectedUser = User.builder()
                .withId(id)
                .withFirstName("Михайло")
                .withLastName("Клинобородий")
                .withEmail("maximus@gmail.com")
                .withPhoneNumber("+43457056578")
                .withPassword("password")
                .withRoleType(RoleType.PASSENGER)
                .build();

        userDao.update(expectedUser);
        int userCountAfterUpdate = (int) userDao.count();
        User actualUser = userDao.findById(id).orElse(null);

        assertEquals(expectedUser, actualUser);
        assertEquals(userCountAfterUpdate, userCountAfterInsert);
    }

    @Test
    public void deleteById() {
        int userCountBeforeInsert = (int) userDao.count();
        int id = userCountBeforeInsert + 1;
        User testUser = generateTestUser(id);
        userDao.save(testUser);
        int beforeDeleteSize = (int) userDao.count();

        userDao.deleteById(id);
        int actualSize = (int) userDao.count();


        User actualUser = userDao.findById(id).orElse(null);
        assertNull(actualUser);
        assertEquals(beforeDeleteSize - 1, actualSize);
        assertNotEquals(beforeDeleteSize, actualSize);
    }

    @Test
    public void countShouldReturnZeroOnEmptyTable() throws SQLException {
        createTables(dataSource);
        int actualCount = (int) userDao.count();

        assertEquals(0, actualCount);
    }

    @Test
    public void countShouldReturnValidValueOnFilledTable() throws SQLException {
        createTables(dataSource);
        int expected = 9;

        List<User> users = generateListUser(expected);
        users.forEach(userDao::save);
        int actualCount = (int) userDao.count();
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