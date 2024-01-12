package com.java.eshop.eshop.repositories;

import com.java.eshop.eshop.common.Status;
import com.java.eshop.eshop.dto.OrderDTO;
import com.java.eshop.eshop.dto.OrderProductDTO;
import com.java.eshop.eshop.dto.OrderReportDTO;
import com.java.eshop.eshop.dto.OrderShopDTO;
import com.java.eshop.eshop.model.OrderEntity;

import com.java.eshop.eshop.model.QClientEntity;
import com.java.eshop.eshop.model.QOrderEntity;
import com.java.eshop.eshop.model.QOrderProductEntity;
import com.java.eshop.eshop.model.QProviderEntity;
import com.java.eshop.eshop.model.QShopEntity;
import com.java.eshop.eshop.model.QShopProductEntity;
import com.querydsl.core.group.Group;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.java.eshop.eshop.model.QOrderEntity.orderEntity;
import static com.querydsl.core.group.GroupBy.groupBy;

@Repository
public class QdslOrderRepository extends QuerydslRepositorySupport {

    public QdslOrderRepository() {
        super(OrderEntity.class);
    }

    public List<OrderShopDTO> findOrderDetailsByOrderId(Long orderId) {
        QOrderProductEntity orderProductEntity = QOrderProductEntity.orderProductEntity;
        QShopProductEntity shopProductEntity = QShopProductEntity.shopProductEntity;

        return from(orderProductEntity)
                .leftJoin(orderProductEntity.shopProduct, shopProductEntity)
                .where(orderProductEntity.order.id.eq(orderId))
                .transform(GroupBy.groupBy(shopProductEntity.shop.id).as(Projections.bean(OrderShopDTO.class,
                                shopProductEntity.shop.id.as("shopId"),
                                shopProductEntity.shop.name.as("name"),
                                GroupBy.list(Projections.bean(OrderProductDTO.class,
                                        orderProductEntity.id.as("productId"),
                                        orderProductEntity.quantity.as("quantity"),
                                        orderProductEntity.unitCost.as("unitCost"),
                                        shopProductEntity.name.as("name")
                                )).as("productsList")
                        ))
                )
                .values().stream().toList();
    }

    public List<OrderDTO> findOrdersByClientAndDateRanges(Long clientId, Date initialDate, Date finishDate) {
        return from(orderEntity)
                .where(orderEntity.client.id.eq(clientId))
                .where(orderEntity.status.eq(Status.ACTIVE.value))
                .where(orderEntity.orderCreationDate.between(initialDate, finishDate))
                .select((Projections.bean(OrderDTO.class,
                                orderEntity.id,
                                orderEntity.client.id.as("clientId"),
                                orderEntity.totalCost
                        ))
                )
                .stream().toList();
    }

    public List<OrderReportDTO> findOrdersReportData() {
        QClientEntity clientEntity = QClientEntity.clientEntity;
        QProviderEntity providerEntity = QProviderEntity.providerEntity;
        return from(orderEntity)
                .leftJoin(orderEntity.client, clientEntity)
                .leftJoin(orderEntity.provider, providerEntity)
                .select(Projections.bean(OrderReportDTO.class,
                        clientEntity.name.as("clientName"),
                        providerEntity.name.as("providerName"),
                        orderEntity.totalCost.as("cost")
                ))
                .stream().toList();
    }

}
