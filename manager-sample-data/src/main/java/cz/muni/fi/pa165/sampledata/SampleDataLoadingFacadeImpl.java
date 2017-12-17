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
import java.security.spec.ECField;
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
        log.info("Loaded L&F categories.");

        User admin = user("admin", "admin@lostandfound.com", "javajesuper", UserRole.ADMINISTRATOR);
        User jon = user("jonSnow", "youknow@nothing.ws", "lordCommander", UserRole.MEMBER);
        User walt = user("waltWhite", "heisenberg@bluestuff.com", "methBoss737", UserRole.MEMBER);
        User barry = user("barryAllen", "flash@starlabs.com", "runBarryRun", UserRole.MEMBER);
        log.info("Loaded L&F users.");

        Item laptop = item("MacBook", electro, ItemColor.GRAY, "MacBook Pro 2017 13\"", BigDecimal.valueOf(1.4), 2, 30, 21, "https://www.paklap.pk/media/wysiwyg/MQD32_Pakistan_.png", null);
        Item phone = item("Huawei P10", electro, ItemColor.BLUE, "Huawei P10 64GB", BigDecimal.valueOf(0.15), 15, 7, 1, "https://cdn.alza.cz/ImgW.ashx?fd=f3&cd=HU2900a3b", null);
        Item hat = item("Hat", clothes, ItemColor.BLACK, "Ordinary round black hat.", BigDecimal.valueOf(0.3), 15, 25, 30, "https://www.villagehatshop.com/photos/product/standard/4511390S163373/all/stovepipe-wool-felt-top-hat.jpg", null);
        Item sword = item("Longclaw", jewelry, ItemColor.GRAY, "Valyrian steel sword...please, return fast, White Walkers are coming!", BigDecimal.valueOf(3.5), 125, 15, 4, "http://www.darkknightarmoury.com/images/Product/large/Game-11.png", daysBeforeNow(0));
        log.info("Loaded L&F items.");

        Event ev0 = event(laptop, null, null, null, barry, daysBeforeNow(4), "Botanicka");
        Event ev1 = event(phone, walt, daysBeforeNow(3), "Klusackova", null, null, null);
        Event ev2 = event(hat, walt, daysBeforeNow(3), "Kounicova", null, null, null);
        Event ev3 = event(sword, jon, daysBeforeNow(6), "Klacelova", barry, daysBeforeNow(0), "Cejl");
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
