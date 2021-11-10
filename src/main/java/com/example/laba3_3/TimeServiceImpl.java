package com.example.laba3_3;

import java.sql.Timestamp;

public class TimeServiceImpl implements ITimeService {
    @Override
    public Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}
