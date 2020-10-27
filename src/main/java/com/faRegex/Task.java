package com.faRegex;

import com.faRegex.core.ComportamentalFANetwork;

public abstract class Task {
    private ComportamentalFANetwork compFAN;

    public Task(ComportamentalFANetwork compFAN) {
        this.compFAN = compFAN;
    }

    public ComportamentalFANetwork getCompFAN() {
        return compFAN;
    }

    public abstract void build();

}
