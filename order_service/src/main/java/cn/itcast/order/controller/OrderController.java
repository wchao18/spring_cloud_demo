package cn.itcast.order.controller;

import cn.itcast.order.command.OrderCommand;
import cn.itcast.order.entity.Product;
import cn.itcast.order.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RestTemplate restTemplate;


	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public Product getById(@PathVariable Long id) {
        return productFeignClient.findById(id);
	}

    @RequestMapping(value = "/query/{id}",method = RequestMethod.GET)
    public Product queryById(@PathVariable Long id) {
        return productFeignClient.findById(id);
    }

    /**
     * 线程隔离修改
     * @param id
     * @return
     */
    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable Long id) {
        return new OrderCommand(restTemplate, id).execute();
    }


}
