package demo.controller;

import demo.domain.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class DemoController {
    private static List<Shop> shops = Arrays.asList(
            new Shop(1, "火币", "火的力量"),
            new Shop(2, "水币", "水的力量"),
            new Shop(3, "风币", "风的力量"),
            new Shop(4, "土币", "土的力量")

    );

    @RequestMapping("")
    public String main() {
        return "demo.html";
    }

    // e.g. GET http://localhost:8080/shop/1
    @GetMapping("shop/{id}")
    public @ResponseBody Shop getShop(@PathVariable("id") int id) {
        return shops.get(id - 1);
    }
    @GetMapping("shop/all")
    public @ResponseBody Iterable<Shop> getAll() {
        return shops;
    }
}
