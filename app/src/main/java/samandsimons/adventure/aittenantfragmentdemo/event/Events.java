package samandsimons.adventure.aittenantfragmentdemo.event;

import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;

/**
 * Created by samgrund on 12/10/16.
 */
public class Events {

    public static class ConfirmedConnectionEvent {
        private Connection confirmed;

        public ConfirmedConnectionEvent(Connection confirmed) {
            this.confirmed = confirmed;
        }

        public Connection getConfirmed() {
            return confirmed;
        }
    }

    public static class PendingConnectionEvent {
        private Connection pending;

        public PendingConnectionEvent(Connection pending) {
            this.pending = pending;
        }

        public Connection getPending() {
            return pending;
        }
    }

    public static class RequestedConnectionEvent {
        private Connection requested;

        public RequestedConnectionEvent(Connection requested) {
            this.requested = requested;
        }

        public Connection getRequested() {
            return requested;
        }
    }

    public static class MessageEvent {
        private Message message;

        public MessageEvent(Message message) {
            this.message = message;
        }

        public Message getMessage() {
            return message;
        }
    }

    public static class PaymentEvent {
        private Payment payment;

        public PaymentEvent(Payment payment) {
            this.payment = payment;
        }

        public Payment getPayment() {
            return payment;
        }
    }

    public static class EventEvent {
        private Event event;

        public EventEvent(Event event) {
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }
    }


}
