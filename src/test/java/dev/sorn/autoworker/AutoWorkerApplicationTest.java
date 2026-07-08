package dev.sorn.autoworker;

import org.junit.jupiter.api.Test;

import static dev.sorn.autoworker.AutoWorkerApplication.main;

class AutoWorkerApplicationTest {

    @Test
    void starts_application_with_no_args() {
        // then
        main(new String[]{});
    }

}
