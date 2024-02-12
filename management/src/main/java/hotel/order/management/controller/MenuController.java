package hotel.order.management.controller;

import hotel.order.management.model.Menu;
import hotel.order.management.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public Flux<Menu> getAllMenus() {
        return menuService.getAllMenus()
                .delayElements(Duration.ofSeconds(2))
                .doOnError(throwable -> logger.error("Error occurred while fetching all menus", throwable));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Menu>> getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id)
                .map(menu -> ResponseEntity.ok().body(menu))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(throwable -> logger.error("Error occurred while fetching menu by id: {}", id, throwable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Menu> createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu)
                .doOnSuccess(createdMenu -> logger.info("Menu created successfully: {}", createdMenu))
                .doOnError(throwable -> logger.error("Error occurred while creating menu", throwable));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Menu>> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        return menuService.updateMenu(id, updatedMenu)
                .map(menu -> ResponseEntity.ok().body(menu))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(throwable -> logger.error("Error occurred while updating menu with id: {}", id, throwable));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnSuccess(voidResponseEntity -> logger.info("Menu with id {} deleted successfully", id))
                .doOnError(throwable -> logger.error("Error occurred while deleting menu with id: {}", id, throwable));
    }
}
