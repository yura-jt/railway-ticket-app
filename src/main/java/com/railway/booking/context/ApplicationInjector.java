package com.railway.booking.context;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.HikariConnectionPool;
import com.railway.booking.dao.TariffDao;
import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.impl.BillDaoImpl;
import com.railway.booking.dao.impl.OrderDaoImpl;
import com.railway.booking.dao.impl.TariffDaoImpl;
import com.railway.booking.dao.impl.TrainDaoImpl;
import com.railway.booking.dao.impl.UserDaoImpl;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.Order;
import com.railway.booking.service.BillService;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.TariffService;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.UserService;
import com.railway.booking.service.impl.BillServiceImpl;
import com.railway.booking.service.impl.OrderServiceImpl;
import com.railway.booking.service.impl.TariffServiceImpl;
import com.railway.booking.service.impl.TrainServiceImpl;
import com.railway.booking.service.impl.UserServiceImpl;
import com.railway.booking.service.validator.BillValidator;
import com.railway.booking.service.validator.OrderValidator;
import com.railway.booking.service.validator.TariffValidator;
import com.railway.booking.service.validator.TrainValidator;
import com.railway.booking.service.validator.UserValidator;

public class ApplicationInjector {
    private static ApplicationInjector applicationInjector;

    private static final UserValidator USER_VALIDATOR = new UserValidator();
    private static final TrainValidator TRAIN_VALIDATOR = new TrainValidator();
    private static final TariffValidator TARIFF_VALIDATOR = new TariffValidator();
    private static final OrderValidator ORDER_VALIDATOR = new OrderValidator();
    private static final BillValidator BILL_VALIDATOR = new BillValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final DatabaseConnector DATABASE_CONNECTOR = new HikariConnectionPool();

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final TrainDao TRAIN_DAO = new TrainDaoImpl(DATABASE_CONNECTOR);
    private static final TariffDao TARIFF_DAO = new TariffDaoImpl(DATABASE_CONNECTOR);
    private static final CrudDao<Order> ORDER_DAO = new OrderDaoImpl(DATABASE_CONNECTOR);
    private static final CrudDao<Bill> BILL_DAO = new BillDaoImpl(DATABASE_CONNECTOR);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR, PASSWORD_ENCRYPTOR);
    private static final TrainService TRAIN_SERVICE = new TrainServiceImpl(TRAIN_DAO, TRAIN_VALIDATOR);
    private static final TariffService TARIFF_SERVICE = new TariffServiceImpl(TARIFF_DAO, TARIFF_VALIDATOR);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_DAO, ORDER_VALIDATOR);
    private static final BillService BILL_SERVICE = new BillServiceImpl(BILL_DAO, BILL_VALIDATOR);

    private ApplicationInjector() {
    }

    public static ApplicationInjector getInstance() {
        if (applicationInjector == null) {
            synchronized (ApplicationInjector.class) {
                if (applicationInjector == null) {
                    applicationInjector = new ApplicationInjector();
                }
            }
        }
        return applicationInjector;
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static TrainService getTrainService() {
        return TRAIN_SERVICE;
    }

    public static TariffService getTariffService() {
        return TARIFF_SERVICE;
    }

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static BillService getBillService() {
        return BILL_SERVICE;
    }
}