package com.example.echo_api.validation.sequence;

import jakarta.validation.GroupSequence;

@GroupSequence({ BasicCheck.class, AdvancedCheck.class })
public interface ValidationOrder {
}
