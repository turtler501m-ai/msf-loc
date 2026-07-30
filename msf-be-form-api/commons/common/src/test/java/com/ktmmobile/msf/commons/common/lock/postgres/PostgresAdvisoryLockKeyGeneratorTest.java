package com.ktmmobile.msf.commons.common.lock.postgres;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostgresAdvisoryLockKeyGeneratorTest {

    @Test
    void generateReturnsStableKey() {
        long first = PostgresAdvisoryLockKeyGenerator.generate("msf-sample-table:1:2:3");
        long second = PostgresAdvisoryLockKeyGenerator.generate("msf-sample-table:1:2:3");

        assertThat(first).isEqualTo(second);
    }

    @Test
    void generateRequiresLockName() {
        assertThatThrownBy(() -> PostgresAdvisoryLockKeyGenerator.generate(" "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("lockName");
    }
}
