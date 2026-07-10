package dio.marketplace.ticketing.domain;

public class SeatNotFundException extends RuntimeException {
    public SeatNotFundException(EventId eventId, SeatId seatId) {
        super("Seat with id" + seatId + "not found");
    }
}
