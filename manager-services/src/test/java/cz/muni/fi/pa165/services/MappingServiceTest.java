package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.enums.ItemColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class MappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MappingService mappingService;

    private Item notebook;
    private List<Category> categories;

    @BeforeClass
    public void init() {
        categories = new ArrayList<>();

        Category electronics = new Category();
        electronics.setId(1);
        electronics.setName("Electronics");
        electronics.setDescription("Various types of electronics");

        categories.add(electronics);

        Category bag = new Category();
        bag.setId(2);
        bag.setName("Bag");
        bag.setDescription("Items for stuff.");

        categories.add(bag);

        notebook = new Item();
        notebook.setId(1);
        notebook.setName("Notebook");
        notebook.setDescription("DELL laptop");
        notebook.setColor(ItemColor.BLUE);
        notebook.setCategory(electronics);
        notebook.setDepth(1);
        notebook.setWeight(BigDecimal.ONE);
        notebook.setHeight(1);
        notebook.setWidth(1);
        notebook.setPhotoUri("test");
    }

    @Test
    public void mapTo_itemWithCategory_maps() {
        ItemDTO item = mappingService.mapTo(notebook, ItemDTO.class);

        Assert.assertEquals(item.getId(), notebook.getId());
        Assert.assertEquals(item.getName(), notebook.getName());
        Assert.assertEquals(item.getDescription(), notebook.getDescription());
        assertCategories(item.getCategory(), notebook.getCategory());
        Assert.assertEquals(item.getDepth(), notebook.getDepth());
        Assert.assertEquals(item.getHeight(), notebook.getHeight());
        Assert.assertEquals(item.getWidth(), notebook.getWidth());
        Assert.assertEquals(item.getWeight(), notebook.getWeight());
        Assert.assertEquals(item.getPhotoUri(), notebook.getPhotoUri());
    }

    @Test
    public void mapToCollection_categories_maps() {
        List<CategoryDTO> categoryList = mappingService.mapTo(categories, CategoryDTO.class);

        Assert.assertTrue(categoryList.size() == 2);

        for (int i = 0; i < categoryList.size(); i++) {
            assertCategories(categoryList.get(i), categories.get(i));
        }
    }

    private void assertCategories(CategoryDTO categoryDTO, Category category) {
        Assert.assertEquals(categoryDTO.getId(), category.getId());
        Assert.assertEquals(categoryDTO.getName(), category.getName());
        Assert.assertEquals(categoryDTO.getDescription(), category.getDescription());
    }
}