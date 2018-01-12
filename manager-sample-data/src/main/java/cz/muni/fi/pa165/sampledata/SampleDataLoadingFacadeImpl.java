package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.ItemColor;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.services.CategoryService;
import cz.muni.fi.pa165.services.EventService;
import cz.muni.fi.pa165.services.ItemService;
import cz.muni.fi.pa165.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Loads some sample data to populate the LostAndFoundManager database.
 *
 * @author Adam Bananka
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private EventService eventService;

    @Override
    public void loadData() {
        Category electro = category("Electronics", "Smartphones, laptops and other electronic devices.");
        Category jewelry = category("Jewelry", "Rings, necklaces...all shiny stuff.");
        Category clothes = category("Clothes", "Hats, jackets...anything you wear.");
        Category cars = category("Cars", "Yep, cars.");
        Category animals = category("Animals", "Alive or dead.");
        Category others = category("Others", "Everything else ...");
        log.info("Loaded L&F categories.");

        User admin = user("admin", "admin@lostandfound.com", "javajesuper", UserRole.ADMINISTRATOR);
        User jon = user("jonSnow", "youknow@nothing.ws", "lordCommander", UserRole.MEMBER);
        User walt = user("waltWhite", "heisenberg@bluestuff.com", "methBoss737", UserRole.MEMBER);
        User barry = user("barryAllen", "flash@starlabs.com", "runBarryRun", UserRole.MEMBER);
        User deadpool = user("deadpool", "deadpool@unicorns.com", "deadpool", UserRole.MEMBER);
        log.info("Loaded L&F users.");

        Item laptop = item("MacBook", electro, ItemColor.GRAY, "MacBook Pro 2017 13\"", BigDecimal.valueOf(1.4), 2, 30, 21, null, null);
        Item phone = item("Huawei P10", electro, ItemColor.BLUE, "Huawei P10 64GB", BigDecimal.valueOf(0.15), 15, 7, 1, "p10.jpg", null);
        Item hat = item("Hat", clothes, ItemColor.BLACK, "Ordinary round black hat.", BigDecimal.valueOf(0.3), 15, 25, 30, "hat.jpg", null);
        Item batmobile = item("Bat mobile", cars, ItemColor.BLACK, "ehm ... ordinary black car cough", BigDecimal.valueOf(2500), 150, 250, 300, "batmobile.jpg", null);
        Item hand = item("Hand", others, ItemColor.WHITE, "missing hand", BigDecimal.valueOf(1), 200, 50, 30, "hand.jpg", null);
        Item sword = item("Longclaw", jewelry, ItemColor.GRAY, "Valyrian steel sword...please, return fast, White Walkers are coming!", BigDecimal.valueOf(3.5), 125, 15, 4, null, daysBeforeNow(0));
        Item unicorn = item("Unicorn", jewelry, ItemColor.WHITE, "Fabulous unicorn.", BigDecimal.valueOf(0.05), 5, 4, 3, null, null);
        Item snake = item("Snake", animals, ItemColor.BLACK, "2 meters long anaconda", BigDecimal.valueOf(5), 15, 200, 15, null, null);
        Item skoda = item("Skoda 120", cars, ItemColor.BROWN, "Classic car", BigDecimal.valueOf(800), 200, 500, 300, null, null);

        log.info("Loaded L&F items.");

        Event ev0 = event(laptop, null, null, null, barry, daysBeforeNow(4), "Botanicka 38, Brno");
        Event ev1 = event(phone, walt, daysBeforeNow(3), "Klusackova 21, Brno", null, null, null);
        Event ev2 = event(hat, walt, daysBeforeNow(3), "Kounicova 10, Teplice", null, null, null);
        Event ev3 = event(sword, jon, daysBeforeNow(6), "Klacelova 51, Karlove Vary", barry, daysBeforeNow(0), "Cejl 10, Praha");
        Event ev4 = event(hand, deadpool, daysBeforeNow(0), "Botanicka 68, Brno", null, null, null);
        Event ev5 = event(batmobile, deadpool, daysBeforeNow(2), "Cejl 1, Praha", walt, daysBeforeNow(1), "Cejl 2, Praha");
        Event ev6 = event(unicorn, jon, daysBeforeNow(10), "Novakova 1, Nymburk", null, null, null);
        Event ev7 = event(snake, deadpool, daysBeforeNow(7), "Křídlovecká 21, Holešov", null, null, null);
        Event ev8 = event(skoda, walt, daysBeforeNow(100), "Štefánikova 97, Karviná", null, null, null);
        log.info("Loaded L&F events.");
    }

    private Category category(String name, String description) {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription(description);
        categoryService.create(cat);
        return cat;
    }

    private User user(String name, String email, String password, UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUserRole(role);
        userService.register(user, password);
        return user;
    }

    private Item item(String name, Category category, ItemColor color, String description, BigDecimal weight, int height, int width, int depth, String photoUri, Date returned) {
        Item item = new Item();
        item.setName(name);
        item.setCategory(category);
        item.setColor(color);
        item.setDescription(description);
        item.setWeight(weight);
        item.setWidth(width);
        item.setHeight(height);
        item.setDepth(depth);
        item.setPhotoUri(photoUri);
        item.setReturned(returned);
        itemService.create(item);
        return item;
    }

    private Event event(Item item, User owner, Date dateOfLoss, String placeOfLoss, User finder, Date dateOfFind, String placeOfFind) {
        Event event = new Event();
        event.setItem(item);
        event.setOwner(owner);
        event.setDateOfLoss(dateOfLoss);
        event.setPlaceOfLoss(placeOfLoss);
        event.setFinder(finder);
        event.setDateOfFind(dateOfFind);
        event.setPlaceOfFind(placeOfFind);
        eventService.createEvent(event);
        return event;
    }

    private static Date daysBeforeNow(int days) {
        return Date.from(ZonedDateTime.now().minusDays(days).toInstant());
    }
}
