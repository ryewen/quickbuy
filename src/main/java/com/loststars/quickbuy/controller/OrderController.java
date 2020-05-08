package com.loststars.quickbuy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loststars.quickbuy.error.BusinessException;
import com.loststars.quickbuy.error.EmBusinessError;
import com.loststars.quickbuy.response.CommonReturnType;
import com.loststars.quickbuy.service.OrderService;
import com.loststars.quickbuy.service.model.OrderModel;
import com.loststars.quickbuy.service.model.UserModel;

@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/createorder")
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam("itemId") Integer itemId, @RequestParam(name = "promoId", required = false) Integer promoId, @RequestParam("amount") Integer amount, HttpServletRequest requset) throws BusinessException {
        if (itemId == null || amount == null) throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        OrderModel orderModel = new OrderModel();
        UserModel userModel = (UserModel) requset.getSession().getAttribute("LOGIN_USER");
        if (userModel == null) throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        orderModel.setItemId(itemId);
        orderModel.setUserId(userModel.getId());
        orderModel.setAmount(amount);
        orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.createSuccess(null);
    }
}
