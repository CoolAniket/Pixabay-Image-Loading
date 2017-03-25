package com.tutorial.exercise.Utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Aniketrao Rane on 10-02-2017.
 */

public class Provider {

    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance() {
        return BUS;
    }

    private Provider() {
        // No instances.
    }

}