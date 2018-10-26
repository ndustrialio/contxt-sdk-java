package com.ndustrialio.contxt.service;

/**
 * ServiceOptions allows configuring options used for service calls.
 *
 * Example:
 *
 * ```
 * ServiceOptions options = ServiceOptions.Builder()
 *     .connectionTimeout(60000)
 *     .readTimeout(60000)
 *     .build();
 * ```
 */
public class ServiceOptions {
    private final int _connectionTimeout;
    private final int _readTimeout;

    /**
     * Creates a ServiceOptions object. Private so it can only be used by the builder.
     *
     * @param connectionTimeout the timeout value used when an HTTP call is connecting
     * @param readTimeout the timeout value used when an HTTP call is reading output
     */
    private ServiceOptions(final int connectionTimeout, final int readTimeout) {
        this._connectionTimeout = connectionTimeout;
        this._readTimeout = readTimeout;
    }

    /**
     * Creates an instance of a builder for specifying ServiceOptions properties using method chaining.
     *
     * @return a new instance of a ServiceOptionsBuilder
     */
    public static ServiceOptionsBuilder Builder() {
        return new ServiceOptionsBuilder();
    }

    /**
     * The timeout value used when an HTTP call is connecting.
     *
     * @return an int representing the connection timeout in milliseconds
     */
    public int getConnectionTimeout() {
        return _connectionTimeout;
    }

    /**
     * The timeout value used when an HTTP call is reading output.
     *
     * @return an int representing the read timeout in milliseconds
     */
    public int getReadTimeout() {
        return _readTimeout;
    }

    /**
     * A builder class for creating an immutable ServiceOptions object using method chaining.
     */
    public static class ServiceOptionsBuilder {
        private int _connectionTimeout = 30000;
        private int _readTimeout = 30000;

        /**
         * Creates a ServiceOptions object with the values we've set
         *
         * @return an immutable ServiceOptions object with the values we've set
         */
        public ServiceOptions build() {
            return new ServiceOptions(
                    _connectionTimeout,
                    _readTimeout
            );
        }

        /**
         * Sets the connection timeout field. A value of 0 represents no timeout. The default value is 30 seconds.
         *
         * @param connectionTimeout the timeout value in milliseconds
         * @return this builder
         */
        public ServiceOptionsBuilder connectionTimeout(int connectionTimeout) {
            this._connectionTimeout = connectionTimeout;

            return this;
        }

        /**
         * Sets the read timeout field. A value of 0 represents no timeout. The default value is 30 seconds.
         *
         * @param readTimeout the timeout value in milliseconds
         * @return this builder
         */
        public ServiceOptionsBuilder readTimeout(int readTimeout) {
            this._readTimeout = readTimeout;

            return this;
        }
    }
}
