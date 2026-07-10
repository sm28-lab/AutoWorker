package dev.sorn.autoworker;

import static dev.sorn.autoworker.Envelope.Builder.envelope;
import static dev.sorn.autoworker.Id.id;

public interface EnvelopeTestData extends ValueTestData {

    default Envelope.Builder anEnvelope() {
        return envelope()
            .correlationId(Id.id())
            .payload(anObjectValue())
            .metadata(anObjectValue());
    }

}
