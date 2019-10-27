package com.harkin.luas;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class PresenterTest {


    @Test
    public void blh() throws Exception {
        InstrumentationRegistry.getTargetContext();
        assertThat(0).isEqualTo(9);
    }
}