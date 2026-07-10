package dev.sorn.autoworker;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dev.sorn.autoworker.Envelope.Builder.envelope;
import static dev.sorn.autoworker.Id.id;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class EnvelopeTest implements EnvelopeTestData {

    @Nested
    class NullRejection {

        @Test
        void rejects_null_correlation_id() {
            // given
            var envelope = envelope()
                .correlationId(null)
                .payload(anObjectValue())
                .metadata(anObjectValue());

            // when // then
            thenThrownBy(envelope::build)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'correlationId' is required");
        }

        @Test
        void rejects_null_payload() {
            // given
            var envelope = envelope()
                .correlationId(Id.id())
                .payload(null)
                .metadata(anObjectValue());

            // when // then
            thenThrownBy(envelope::build)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'payload' is required");
        }

        @Test
        void rejects_null_metadata() {
            // given
            var envelope = envelope()
                .correlationId(Id.id())
                .payload(anObjectValue())
                .metadata(null);

            // when // then
            thenThrownBy(envelope::build)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'metadata' is required");
        }

    }

}
