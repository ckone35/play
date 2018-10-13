package fr.ckone35.play.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class GreetingController {


    @Value("${external.url}")
    private String externalUrl;

    @GetMapping("/hello")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(externalUrl, String.class);

        model.addAttribute("code", response.getStatusCode().value());

        model.addAttribute("url", externalUrl);
        System.out.println(response.getBody());
        model.addAttribute("length", response.getBody().length());
        model.addAttribute("name", name);

        try {
            model.addAttribute("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException("impossible to retrieve hostname", e);
        }

        return "greeting";
    }

}