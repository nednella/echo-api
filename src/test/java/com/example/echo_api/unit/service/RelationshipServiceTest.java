package com.example.echo_api.unit.service;

import org.junit.jupiter.api.BeforeAll;

import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Profile;

public class RelationshipServiceTest {

    private static Profile source;
    private static Profile target;

    @BeforeAll
    static void setup() {
        Account sourceAcc = new Account("source", "test");
        source = new Profile(sourceAcc);

        Account targetAcc = new Account("target", "test");
        target = new Profile(targetAcc);
    }

}
