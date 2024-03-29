package fh.se.car.rental.fh.messaging.common.config;

public class MessagingConfig {
    public static final String EXCHANGE_NAME = "CarNowExchange";

    //ROUTING KEYS
    public static final String SIGN_UP = "SIGN_UP_2_MESSAGES";
    public static final String LOGGING = "LOGGING";
    public static final String CAR_UPDATE_KEY = "CAR_UPDATE";
    public static final String SO_UPDATE = "SO_UPDATE";

    //QUEUES
    public static final String LOGGING_KEY = "LOGGING_Key";
    public static final String SIGN_UP_2_USER_MANAGEMENT = "SIGN_UP_2_USER_MANAGEMENT";
    public static final String SIGN_UP_2_AUTHENTICATION = "SIGN_UP_2_AUTHENTICATION";
    public static final String SALES_ORDER_UPDATE = "SALES_ORDER_UPDATE";
    public static final String CAR_UPDATE = "CAR_UPDATE";
}
