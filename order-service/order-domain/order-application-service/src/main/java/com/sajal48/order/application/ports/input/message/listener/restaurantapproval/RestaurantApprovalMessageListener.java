package com.sajal48.order.application.ports.input.message.listener.restaurantapproval;

import com.sajal48.order.application.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalMessageListener {
	void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
	void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
