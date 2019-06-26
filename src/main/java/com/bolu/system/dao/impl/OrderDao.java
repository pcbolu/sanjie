package com.bolu.system.dao.impl;


import com.bolu.system.bo.Order;
import com.bolu.system.dao.IOrderDao;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends BaseDao<Order> implements IOrderDao {
}
