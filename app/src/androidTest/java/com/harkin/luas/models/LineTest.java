package com.harkin.luas.models;

import com.harkin.luas.network.models.Line;

import junit.framework.TestCase;

// Created by Intercom on 04/05/15.
// Copyright (c) 2015 Intercom. All rights reserved.
public class LineTest extends TestCase {
    public void testToLine() throws Exception {
        assertEquals(Line.RED, Line.toLine("red"));
        assertEquals(Line.RED, Line.toLine("rEd"));
        assertEquals(Line.GREEN, Line.toLine("GREEN"));
        assertEquals(Line.UNKNOWN, Line.toLine("blue"));
        assertEquals(Line.UNKNOWN, Line.toLine(null));
    }
}