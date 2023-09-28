package com.example.andersontimetracking.interfaces;

import java.sql.Connection;

public interface Connector {
    Connection getConnection();
}
