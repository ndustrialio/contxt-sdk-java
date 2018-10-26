package com.ndustrialio.contxt.service;

public class ServiceOptions {
    private final int _connectionTimeout;
    private final int _readTimeout;

    private ServiceOptions(final int connectionTimeout, final int readTimeout) {
        this._connectionTimeout = connectionTimeout;
        this._readTimeout = readTimeout;
    }

    public static ServiceOptionsBuilder Builder() {
        return new ServiceOptionsBuilder();
    }

    public int getConnectionTimeout() {
        return _connectionTimeout;
    }

    public int getReadTimeout() {
        return _readTimeout;
    }

    private static class ServiceOptionsBuilder {
        private int _connectionTimeout = 30;
        private int _readTimeout = 30;

        public ServiceOptions build() {
            return new ServiceOptions(
                    _connectionTimeout,
                    _readTimeout
            );
        }

        public ServiceOptionsBuilder connectionTimeout(int connectionTimeout) {
            this._connectionTimeout = connectionTimeout;

            return this;
        }

        public ServiceOptionsBuilder readTimeout(int readTimeout) {
            this._readTimeout = readTimeout;

            return this;
        }
    }
}
