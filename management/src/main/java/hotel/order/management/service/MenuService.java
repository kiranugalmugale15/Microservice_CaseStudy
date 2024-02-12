package hotel.order.management.service;

import hotel.order.management.model.Menu;
import hotel.order.management.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Flux<Menu> getAllMenus() {
        return menuRepository.findAll()
                .doOnError(throwable -> logger.error("Error occurred while fetching all menus", throwable));
    }

    public Mono<Menu> getMenuById(Long id) {
        return menuRepository.findById(id)
                .doOnError(throwable -> logger.error("Error occurred while fetching menu by id: {}", id, throwable));
    }

    public Mono<Menu> createMenu(Menu menu) {
        return menuRepository.save(menu)
                .doOnSuccess(savedMenu -> logger.info("Menu created successfully: {}", savedMenu))
                .doOnError(throwable -> logger.error("Error occurred while creating menu", throwable));
    }

    public Mono<Menu> updateMenu(Long id, Menu updatedMenu) {
        return menuRepository.findById(id)
                .flatMap(existingMenu -> {
                    existingMenu.setName(updatedMenu.getName());
                    existingMenu.setPrice(updatedMenu.getPrice());
                    existingMenu.setDescription(updatedMenu.getDescription());
                    return menuRepository.save(existingMenu);
                })
                .doOnError(throwable -> logger.error("Error occurred while updating menu with id: {}", id, throwable));
    }

    public Mono<Void> deleteMenu(Long id) {
        return menuRepository.deleteById(id)
                .doOnSuccess(unused -> logger.info("Menu with id {} deleted successfully", id))
                .doOnError(throwable -> logger.error("Error occurred while deleting menu with id: {}", id, throwable));
    }
}

