package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.DateUtil;
import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.common.Status;
import com.java.eshop.eshop.dto.OrderDTO;
import com.java.eshop.eshop.dto.OrderReportDTO;
import com.java.eshop.eshop.model.ClientEntity;
import com.java.eshop.eshop.model.OrderEntity;
import com.java.eshop.eshop.model.OrderProductEntity;
import com.java.eshop.eshop.model.ProviderEntity;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import com.java.eshop.eshop.repositories.OrderProductRepository;
import com.java.eshop.eshop.repositories.OrderRepository;
import com.java.eshop.eshop.repositories.QdslOrderRepository;
import com.java.eshop.eshop.repositories.ShopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopProductRepository shopProductRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private QdslOrderRepository qdslOrderRepository;

    /**
     * Metodo para crear una order
     * @param orderDTO
     * @return
     */
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Valido que exista el producto en la tienda
        verifyShopAndProductExistance(orderDTO);
        // 2. Creo order
        OrderEntity newOrder = orderRepository.save(OrderEntity.builder()
                .totalCost(orderDTO.getTotalCost())
                .status(Status.ACTIVE.value)
                .orderCreationDate(new Date())
                .provider(ProviderEntity.builder().id(orderDTO.getProviderId()).build())
                .client(ClientEntity.builder().id(orderDTO.getClientId()).build())
                .build());
        // 3. Creo order product
        orderDTO.getShopList().forEach(shop -> shop.getProductsList().forEach(product -> {
            orderProductRepository.save(OrderProductEntity.builder()
                    .quantity(product.getQuantity())
                    .unitCost(product.getUnitCost())
                    .shopProduct(ShopProductEntity.builder().id(product.getProductId()).build())
                    .order(newOrder)
                    .build());
            // Resto stock
            shopProductRepository.findById(product.getProductId())
                    .ifPresent(productEntity -> {
                        productEntity.setStock(productEntity.getStock() - product.getQuantity());
                        shopProductRepository.save(productEntity);
                    });
        }));
        orderDTO.setId(newOrder.getId());
        return orderDTO;
    }

    /**
     * Metodo para validar que el producto exista en la tienda y tenga stock suficiente
     * @param orderDTO
     */
    public void verifyShopAndProductExistance(OrderDTO orderDTO) {
        List<Long> productsWithNoStock = new ArrayList<>();
        orderDTO.getShopList().forEach(shop -> {
            // Verifico que exista la tienda
            shopService.findShopEntityById(shop.getShopId());
            // Verifico que haya stock
            shop.getProductsList().forEach(product ->
                    shopProductRepository.findByIdAndShop(product.getProductId(), ShopEntity.builder().id(shop.getShopId()).build())
                            .ifPresentOrElse(productEntity -> {
                                if (productEntity.getStock() == 0 || productEntity.getStock() < product.getQuantity()) {
                                    productsWithNoStock.add(productEntity.getId());
                                }
                            }, () -> {
                                throw new ShopException("Product with id " + product.getProductId() +
                                        " doesn't exist in shop with id " + shop.getShopId());
                            })
            );
        });
        // 2. Si no hay stock para los productos devuelvo mensaje
        if (!productsWithNoStock.isEmpty()) {
            throw new ShopException("There is no stock for products with id: " + productsWithNoStock +
                    ", check for stock and try again");
        }
    }

    /**
     * Metodo para obtener las ordenes del cliente en un rango de fechas
     * @param clientId
     * @param initialDate
     * @param finishDate
     * @return
     */
    public List<OrderDTO> findOrdersByClientAndDateRanges(Long clientId, Long initialDate, Long finishDate) {
        // Obtener ordenes
        return qdslOrderRepository.findOrdersByClientAndDateRanges(clientId, DateUtil.getInitialDate(initialDate),
                        DateUtil.getFinishDate(finishDate))
                .stream().peek(orderDTO ->
                        orderDTO.setShopList(qdslOrderRepository.findOrderDetailsByOrderId(orderDTO.getId()))
                ).toList();
    }

    /**
     * Elimina una orden, retorna true si fue existoso, caso contrario un mensaje
     * @param orderId
     * @return
     */
    public Boolean deleteOrder(Long orderId){
        orderRepository.findById(orderId)
                .ifPresentOrElse(orderEntity -> {
                    orderEntity.setStatus(Status.INACTIVE.value);
                    orderRepository.save(orderEntity);
                    // Se podría borrar tambien con:
//                    orderRepository.delete(orderEntity);
                }, () -> {
                    throw new ShopException("Product with id " + orderId + " does not exist");
                });
        return true;
    }

    public List<OrderReportDTO> gerOrdersReport(){
        return qdslOrderRepository.findOrdersReportData();
    }

}
