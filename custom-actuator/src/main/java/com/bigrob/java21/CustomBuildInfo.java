package com.bigrob.java21;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CustomBuildInfo implements InfoContributor {

    private final Supplier<String> versionNumberSupplier;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("model.version", versionNumberSupplier.get());
    }

}