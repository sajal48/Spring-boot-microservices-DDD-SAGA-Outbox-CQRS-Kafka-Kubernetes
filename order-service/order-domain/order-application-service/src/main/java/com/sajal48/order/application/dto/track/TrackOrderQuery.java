package com.sajal48.order.application.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
@Getter
@Builder
@AllArgsConstructor
public class TrackOrderQuery {
	@NotNull
	private final UUID orderTrackingId;
}
