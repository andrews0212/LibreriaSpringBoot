package org.example.libreriakamel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Hola {
    @RequestMapping("/hola")
    public String hola(){
        return "Hola";
    }
}
