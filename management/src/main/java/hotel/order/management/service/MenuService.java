package hotel.order.management.service;
// MenuService.java
import hotel.order.management.model.Menu;
import hotel.order.management.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Flux<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Mono<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Mono<Menu> createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Mono<Menu> updateMenu(Long id, Menu updatedMenu) {
        return menuRepository.findById(id)
                .flatMap(existingMenu -> {
                    existingMenu.setName(updatedMenu.getName());
                    existingMenu.setPrice(updatedMenu.getPrice());
                    existingMenu.setDescription(updatedMenu.getDescription());
                    return menuRepository.save(existingMenu);
                });
    }

    public Mono<Void> deleteMenu(Long id) {
        return menuRepository.deleteById(id);
    }
}

