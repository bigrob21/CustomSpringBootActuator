package com.bigrob.java21;

public interface Model {
    String versionNumber = "1-beta";

    default String getVersion() {
        return versionNumber;
    }
}
