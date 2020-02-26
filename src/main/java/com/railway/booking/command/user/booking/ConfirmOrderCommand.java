package com.railway.booking.command.user.booking;

import com.railway.booking.command.Command;
import com.railway.booking.dto.FlightDto;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.entity.Carriage;
import com.railway.booking.entity.Flight;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.Seat;
import com.railway.booking.entity.SeatStatus;
import com.railway.booking.service.BillService;
import com.railway.booking.service.CarriageService;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.SeatService;
import com.railway.booking.service.StationService;
import com.railway.booking.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class ConfirmOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ConfirmOrderCommand.class);

    private final StationService stationService;
    private final OrderService orderService;
    private final BillService billService;
    private final TariffService tariffService;
    private final CarriageService carriageService;
    private final SeatService seatService;

    public ConfirmOrderCommand(StationService stationService, OrderService orderService,
                               BillService billService, TariffService tariffService,
                               CarriageService carriageService, SeatService seatService) {
        this.stationService = stationService;
        this.orderService = orderService;
        this.billService = billService;
        this.tariffService = tariffService;
        this.carriageService = carriageService;
        this.seatService = seatService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String trainCode = request.getParameter("trainCode");
        List<FlightDto> trains = (List<FlightDto>) request.getSession().getAttribute("trains");
        Order order = (Order) request.getSession().getAttribute("order");
        FlightDto flightDto = getFlightDto(trains, trainCode);

        if (flightDto == null) {
            request.setAttribute("trainCodeError", true);
            return "/view/chooseTrain.jsp";
        }
        boolean isSuccessful = false;
        try {
            Flight flight = flightDto.getFlight();

            Integer orderId = orderService.saveOrder(order);

            Carriage carriage = getFreeCarriage(flight);
            int seatNumber = getSeatNumber(carriage);

            int distance = stationService.getDistanceBetweenStations(flight.getTrainId(), order.getDepartureStation(), order.getDestinationStation());
            BigDecimal rate = tariffService.getRate(carriage.getType());

            BigDecimal price = rate.multiply(BigDecimal.valueOf(distance));

            Bill bill = Bill.builder().withOrderId(orderId)
                    .withBillStatus(BillStatus.INVOICED)
                    .withPrice(price)
                    .build();

            billService.saveBill(bill);
            Seat seat = Seat.builder().withNumber(seatNumber)
                    .withCarriageId(carriage.getId())
                    .withBillId(orderId)
                    .withSeatStatus(SeatStatus.RESERVED).build();
            seatService.save(seat);

            isSuccessful = true;
        } catch (Exception e) {
            LOGGER.error("Failed to book ticket", e);
        }
        if (!isSuccessful) {
            request.setAttribute("trainCodeError", true);
            return "/view/chooseTrain.jsp";
        }
        return "/view/confirmOrder.jsp";
    }

    private Carriage getFreeCarriage(Flight flight) {
        List<Carriage> carriages = carriageService.findCarriageByFlight(flight.getId());

        for (Carriage carriage : carriages) {
            int capacity = carriage.getCapacity();
            int reserved = carriageService.getAmountReservedInSingleCarriage(carriage);
            if (capacity - reserved > 0) {

                return carriage;
            }
        }
        return null;
    }

    private int getSeatNumber(Carriage carriage) {
        int reserved = carriageService.getAmountReservedInSingleCarriage(carriage);
        return reserved + 1;
    }

    private FlightDto getFlightDto(List<FlightDto> trains, String trainCode) {
        for (FlightDto flightDto : trains) {
            if (trainCode != null && trainCode.equalsIgnoreCase(flightDto.getCodeTrain())) {
                return flightDto;
            }
        }
        return null;
    }
}