package dio.marketplace.ticketing.infrastructure.event;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.common.infrastructure.event.dto.EventUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public class TicketingEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TicketingEventListener.class);

    @EventListener
    @Async
    public void handle(CustomerCreated event) {
        logger.info("CustomerCreated received {}", event);
    }

    @EventListener
    @Async
    public void handle(EventUpdate event) {
        logger.info("EventUpdate received {}", event);
    }
}
