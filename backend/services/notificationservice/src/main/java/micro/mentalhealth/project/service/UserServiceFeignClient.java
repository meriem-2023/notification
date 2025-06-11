package micro.mentalhealth.project.service;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

import micro.mentalhealth.project.model.RecipientType;
import micro.mentalhealth.project.dto.ContactInfo; // Importe la classe ContactInfo

@FeignClient(name = "user-service", url = "${user.service.url:http://localhost:8081}") // 'user-service' est le nom du microservice dans Eureka/Consul
public interface UserServiceFeignClient {

    @GetMapping("/api/users/{userId}/contact-info")
    ContactInfo getContactInfo(
            @PathVariable("userId") UUID userId,
            @RequestParam("type") RecipientType type);
}