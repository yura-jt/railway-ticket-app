package com.railway.booking.command.user.booking;

import com.railway.booking.command.Command;
import com.railway.booking.dto.FlightDto;
import com.railway.booking.entity.Flight;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.Station;
import com.railway.booking.entity.Train;
import com.railway.booking.service.CarriageService;
import com.railway.booking.service.FlightService;
import com.railway.booking.service.StationService;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChooseTrainCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChooseTrainCommand.class);

    private final TrainService trainService;
    private final CarriageService carriageService;
    private final FlightService flightService;
    private final StationService stationService;
    private final PageProvider pageProvider;

    public ChooseTrainCommand(TrainService trainService, CarriageService carriageService,
                              FlightService flightService, StationService stationService,
                              PageProvider pageProvider) {
        this.trainService = trainService;
        this.carriageService = carriageService;
        this.flightService = flightService;
        this.stationService = stationService;
        this.pageProvider = pageProvider;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = (Order) request.getSession().getAttribute("order");
        List<Flight> availableFlights = getAvailableFlights(order);

        if (availableFlights.isEmpty()) {
            request.setAttribute("noFlightError", "true");
            return "/view/chooseTrain.jsp";
        }

        availableFlights = getFlightsWithMatchedStation(availableFlights, order);


        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);
        int size = availableFlights.size();

        List<FlightDto> trains = getTrains(availableFlights, pageNumber, size);


        request.setAttribute("trains", trains);

        int totalPages = pageProvider.getTotalPages(size);

        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", Constants.ITEM_PER_PAGE);

        request.getSession().setAttribute("trains",trains);
        return "/view/chooseTrain.jsp";
    }

    private List<FlightDto> getTrains(List<Flight> flights, int pageNumber, int size) {
        int maxPage = pageProvider.getMaxPage(size, Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        int startIndex = (pageNumber - 1) * Constants.ITEM_PER_PAGE;

        int count = 1;
        List<FlightDto> result = new ArrayList<>();
        for (int i = startIndex; i < flights.size(); i++) {
            if (count++ >= Constants.ITEM_PER_PAGE) {
                break;
            }
            Train train = trainService.getById(flights.get(i).getTrainId());
            String code = train.getCode();
            String name = train.getName();
            LocalDateTime date = flights.get(i).getDepartureDate();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyy-MM-dd"));
            int freeSeats = carriageService.getTotalFreeSeats(flights.get(i).getId());
            FlightDto flightDto = new FlightDto(code, formattedDate, name, freeSeats, flights.get(i));
            result.add(flightDto);
        }
        return result;
    }

    private List<Flight> getFlightsWithMatchedStation(List<Flight> availableFlights, Order order) {
        List<Flight> result = new ArrayList<>();

        for (Flight flight : availableFlights) {
            Optional<Station> departureStation = stationService.getDepartureStationByOrder(flight.getTrainId(), order);
            Optional<Station> destinationStation = stationService.getDestinationStationByOrder(flight.getTrainId(), order);
            boolean isDepartureStationMatch = checkDepartureStation(departureStation, order);
            if (isDepartureStationMatch && destinationStation.isPresent()) {
                result.add(flight);
            }
        }
        return result;
    }

    private boolean checkDepartureStation(Optional<Station> departureStation, Order order) {
        if (!departureStation.isPresent()) {
            return false;
        }

        LocalTime stationTime = departureStation.get().getTime();
        LocalTime requestFromTime = order.getFromTime();
        LocalTime requestToTime = order.getToTime();
        return stationTime.isAfter(requestFromTime) && stationTime.isBefore(requestToTime);
    }

    private List<Flight> getAvailableFlights(Order order) {
        List<Flight> flights = flightService.findFlightListByDate(order.getDepartureDate());
        List<Flight> result = new ArrayList<>();

        for (Flight flight : flights) {
            int freeSeats = carriageService.getTotalFreeSeats(flight.getId());
            if (freeSeats > 0) {
                result.add(flight);
            }
        }
        return result;
    }
}