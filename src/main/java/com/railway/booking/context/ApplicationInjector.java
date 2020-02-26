package com.railway.booking.context;

import com.railway.booking.command.Command;
import com.railway.booking.command.admin.AdminBillCommand;
import com.railway.booking.command.admin.AdminOrderCommand;
import com.railway.booking.command.admin.AdminRemoveUserCommand;
import com.railway.booking.command.admin.AdminTicketCommand;
import com.railway.booking.command.admin.AdminTrainCommand;
import com.railway.booking.command.admin.AdminUserCommand;
import com.railway.booking.command.admin.PanelCommand;
import com.railway.booking.command.error.AccessDeniedCommand;
import com.railway.booking.command.error.PageNotFoundCommand;
import com.railway.booking.command.user.BillCommand;
import com.railway.booking.command.user.LoginCommand;
import com.railway.booking.command.user.LoginFormCommand;
import com.railway.booking.command.user.LogoutCommand;
import com.railway.booking.command.user.OrderCommand;
import com.railway.booking.command.user.ProfileCommand;
import com.railway.booking.command.user.RegistrationCommand;
import com.railway.booking.command.user.RegistrationFormCommand;
import com.railway.booking.command.user.TicketCommand;
import com.railway.booking.command.user.TrainCommand;
import com.railway.booking.command.user.booking.ChooseTrainCommand;
import com.railway.booking.command.user.booking.ConfirmOrderCommand;
import com.railway.booking.command.user.booking.ConfirmTrainCommand;
import com.railway.booking.command.user.booking.MakeOrderCommand;
import com.railway.booking.command.user.booking.OrderFormCommand;
import com.railway.booking.dao.BillDao;
import com.railway.booking.dao.CarriageDao;
import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.FlightDao;
import com.railway.booking.dao.HikariConnectionPool;
import com.railway.booking.dao.OrderDao;
import com.railway.booking.dao.StationDao;
import com.railway.booking.dao.TariffDao;
import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.impl.BillDaoImpl;
import com.railway.booking.dao.impl.CarriageDaoImpl;
import com.railway.booking.dao.impl.FlightDaoImpl;
import com.railway.booking.dao.impl.OrderDaoImpl;
import com.railway.booking.dao.impl.SeatDaoImpl;
import com.railway.booking.dao.impl.StationDaoImpl;
import com.railway.booking.dao.impl.TariffDaoImpl;
import com.railway.booking.dao.impl.TicketDaoImpl;
import com.railway.booking.dao.impl.TrainDaoImpl;
import com.railway.booking.dao.impl.UserDaoImpl;
import com.railway.booking.entity.Seat;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.BillService;
import com.railway.booking.service.CarriageService;
import com.railway.booking.service.FlightService;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.SeatService;
import com.railway.booking.service.StationService;
import com.railway.booking.service.TariffService;
import com.railway.booking.service.TicketService;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.UserService;
import com.railway.booking.service.impl.BillServiceImpl;
import com.railway.booking.service.impl.CarriageServiceImpl;
import com.railway.booking.service.impl.FlightServiceImpl;
import com.railway.booking.service.impl.OrderServiceImpl;
import com.railway.booking.service.impl.SeatServiceImpl;
import com.railway.booking.service.impl.StationServiceImpl;
import com.railway.booking.service.impl.TariffServiceImpl;
import com.railway.booking.service.impl.TicketServiceImpl;
import com.railway.booking.service.impl.TrainServiceImpl;
import com.railway.booking.service.impl.UserServiceImpl;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.util.PasswordEncryptor;
import com.railway.booking.service.validator.BillValidator;
import com.railway.booking.service.validator.OrderValidator;
import com.railway.booking.service.validator.TariffValidator;
import com.railway.booking.service.validator.TrainValidator;
import com.railway.booking.service.validator.UserValidator;

import java.util.HashMap;
import java.util.Map;

public class ApplicationInjector {
    private static ApplicationInjector applicationInjector;

    private static final UserValidator USER_VALIDATOR = new UserValidator();
    private static final TrainValidator TRAIN_VALIDATOR = new TrainValidator();
    private static final TariffValidator TARIFF_VALIDATOR = new TariffValidator();
    private static final OrderValidator ORDER_VALIDATOR = new OrderValidator();
    private static final BillValidator BILL_VALIDATOR = new BillValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final DatabaseConnector DATABASE_CONNECTOR = new HikariConnectionPool();

    private static final PageProvider PAGE_PROVIDER = new PageProvider();

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final TrainDao TRAIN_DAO = new TrainDaoImpl(DATABASE_CONNECTOR);
    private static final TariffDao TARIFF_DAO = new TariffDaoImpl(DATABASE_CONNECTOR);
    private static final OrderDao ORDER_DAO = new OrderDaoImpl(DATABASE_CONNECTOR);
    private static final BillDao BILL_DAO = new BillDaoImpl(DATABASE_CONNECTOR);
    private static final FlightDao FLIGHT_DAO = new FlightDaoImpl(DATABASE_CONNECTOR);
    private static final StationDao STATION_DAO = new StationDaoImpl(DATABASE_CONNECTOR);
    private static final CarriageDao CARRIAGE_DAO = new CarriageDaoImpl(DATABASE_CONNECTOR);
    private static final CrudDao<Ticket> TICKET_DAO = new TicketDaoImpl(DATABASE_CONNECTOR);
    private static final CrudDao<Seat> SEAT_DAO = new SeatDaoImpl(DATABASE_CONNECTOR);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR,
            PASSWORD_ENCRYPTOR, PAGE_PROVIDER);
    private static final TrainService TRAIN_SERVICE = new TrainServiceImpl(TRAIN_DAO, TRAIN_VALIDATOR,
            PAGE_PROVIDER);
    private static final TariffService TARIFF_SERVICE = new TariffServiceImpl(TARIFF_DAO, TARIFF_VALIDATOR);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_DAO, ORDER_VALIDATOR,
            PAGE_PROVIDER);
    private static final BillService BILL_SERVICE = new BillServiceImpl(BILL_DAO, BILL_VALIDATOR, PAGE_PROVIDER);
    private static final TicketService TICKET_SERVICE = new TicketServiceImpl(TICKET_DAO, PAGE_PROVIDER);
    private static final FlightService FLIGHT_SERVICE = new FlightServiceImpl(FLIGHT_DAO);
    private static final StationService STATION_SERVICE = new StationServiceImpl(STATION_DAO);
    private static final CarriageService CARRIAGE_SERVICE = new CarriageServiceImpl(CARRIAGE_DAO);
    private static final SeatService SEAT_SERVICE = new SeatServiceImpl(SEAT_DAO);


    private static final Map<String, Command> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("login", new LoginCommand(USER_SERVICE));
        COMMANDS.put("logout", new LogoutCommand());
        COMMANDS.put("registration", new RegistrationCommand(USER_SERVICE));
        COMMANDS.put("registrationForm", new RegistrationFormCommand());
        COMMANDS.put("loginForm", new LoginFormCommand());
        COMMANDS.put("profile", new ProfileCommand());
        COMMANDS.put("trains", new TrainCommand(TRAIN_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("bills", new BillCommand(BILL_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("orders", new OrderCommand(ORDER_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("tickets", new TicketCommand(TICKET_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminPanel", new PanelCommand());
        COMMANDS.put("access_denied", new AccessDeniedCommand());
        COMMANDS.put("error", new PageNotFoundCommand());
        COMMANDS.put("orderForm", new OrderFormCommand());
        COMMANDS.put("makeOrder", new MakeOrderCommand(ORDER_VALIDATOR));
        COMMANDS.put("chooseTrain", new ChooseTrainCommand(TRAIN_SERVICE, CARRIAGE_SERVICE,
                FLIGHT_SERVICE, STATION_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("confirmTrain", new ConfirmTrainCommand(ORDER_SERVICE));
        COMMANDS.put("confirmOrder", new ConfirmOrderCommand(STATION_SERVICE, ORDER_SERVICE,
                BILL_SERVICE, TARIFF_SERVICE, CARRIAGE_SERVICE, SEAT_SERVICE));
        COMMANDS.put("adminTrains", new AdminTrainCommand(TRAIN_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminBills", new AdminBillCommand(BILL_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminOrders", new AdminOrderCommand(ORDER_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminTickets", new AdminTicketCommand(TICKET_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminUsers", new AdminUserCommand(USER_SERVICE, PAGE_PROVIDER));
        COMMANDS.put("adminRemoveUser", new AdminRemoveUserCommand(USER_SERVICE));
    }

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

    public static Map<String, Command> getCommands() {
        return COMMANDS;
    }

}