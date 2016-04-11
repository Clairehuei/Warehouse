package com.fabric.warehouse;


public class Config {

    /**
     * The version number of API.
     */
    public static final String API_VERSION = "v1";

    /**
     * External System Id is a id provided by CCE according to type of application
     */
    public static final String EXTERNAL_SYSTEM_ID = "100002";

    /**
     * Define the max amount of price for a single order
     */
    public static final int MAX_AMOUNT_OF_PRICE = 1000000000;

    /**
     * The seed used to encrypt the password.
     */
    public static final String SEED = "CCEAPITAIPEIV001";
}
