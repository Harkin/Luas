package com.harkin.luas.models;

import com.harkin.luas.network.models.Direction;

import junit.framework.TestCase;

// Created by Intercom on 04/05/15.
// Copyright (c) 2015 Intercom. All rights reserved.
public class DirectionTest extends TestCase {
    public void testToDirection() throws Exception {
        assertEquals(Direction.INBOUND, Direction.toDirection("inbound"));
        assertEquals(Direction.OUTBOUND, Direction.toDirection("outbound"));
        assertEquals(Direction.UNKNOWN, Direction.toDirection("out_bound"));
        assertEquals(Direction.UNKNOWN, Direction.toDirection(null));
    }
}