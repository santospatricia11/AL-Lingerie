package back.ecommerce_AL_Lingerie.back.service.admin.adminOrder;

import back.ecommerce_AL_Lingerie.back.dto.AnalyticsResponse;
import back.ecommerce_AL_Lingerie.back.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();

    Long getTotalOrdersForMonths(int month, int year);

    Long getTotalEarningsForMonth(int month, int year);
}
