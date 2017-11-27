package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.facade.CategoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CategoryFacade categoryFacade;

    private long electronicsId;
    private String electronics = "Electronics";
    private CategoryCreateDTO electronicsCreateDTO;

    @BeforeClass
    public void setup() {
        electronicsCreateDTO = new CategoryCreateDTO();
        electronicsCreateDTO.setName(electronics);
        electronicsCreateDTO.setDescription("Various lost electronic devices.");
    }

    @BeforeMethod
    public void init() {
        electronicsId = categoryFacade.createCategory(electronicsCreateDTO);
    }

    @Test
    public void create_electronics_stores() {
        CategoryCreateDTO toyCreateDTO = new CategoryCreateDTO();
        toyCreateDTO.setName("Toy");
        toyCreateDTO.setDescription("Children stuff");

        categoryFacade.createCategory(toyCreateDTO);
    }

    @Test
    public void update_category_updates() {
        CategoryDTO category = new CategoryDTO();
        category.setId(electronicsId);
        category.setName("test");
        category.setDescription("Hello");

        categoryFacade.updateCategory(category);
        CategoryDTO retrieved = categoryFacade.getCategoryById(electronicsId);

        Assert.assertEquals(retrieved.getName(), "test");
        Assert.assertEquals(retrieved.getDescription(), "Hello");
    }

    @Test
    public void remove_bagId_deletes() {
        categoryFacade.remove(electronicsId);

        CategoryDTO retrieved = categoryFacade.getCategoryById(electronicsId);
        Assert.assertNull(retrieved);
    }

    @Test
    public void getCategoryById_electronicsId_retrieves() {
        CategoryDTO retrieved = categoryFacade.getCategoryById(electronicsId);

        Assert.assertEquals(retrieved.getName(), electronicsCreateDTO.getName());
        Assert.assertEquals(retrieved.getDescription(), electronicsCreateDTO.getDescription());
    }

    @Test
    public void findByName_electronics_retrieves() {
        CategoryDTO retrieved = categoryFacade.findByName(electronics);

        Assert.assertEquals(retrieved.getId(), electronicsId);
        Assert.assertEquals(retrieved.getDescription(), electronicsCreateDTO.getDescription());
    }

    @Test
    public void getAllCategories_list_retrievesCategories() {
        List<CategoryDTO> categories = categoryFacade.getAllCategories();

        Assert.assertTrue(categories.size() == 1);
    }
}