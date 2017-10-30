package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;
import java.util.List;

/**
 *
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryDaoTest extends AbstractTestNGSpringContextTests {

    private static final String CATEGORY_NAME = "Electronics";
    private static final String CATEGORY_DESCRIPTION = "Some description.";

    @Autowired
    private CategoryDao categoryDao;
    private Category category;

    @BeforeMethod
    public void setup() {
        category = new Category();
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void create_null_throwsException() {
        categoryDao.create(null);
    }

    @Test
    public void create_category_stores() {
        Assert.assertEquals(category.getId(), 0);

        categoryDao.create(category);

        Assert.assertTrue(category.getId() != 0);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void create_nullName_throwsException() {
        category.setName(null);
        categoryDao.create(category);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void create_nullDescription_throwsException() {
        category.setDescription(null);
        categoryDao.create(category);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void create_nonUniqueName_throwsException() {
        categoryDao.create(category);

        Category nonUniqueCategory = new Category();
        nonUniqueCategory.setName(CATEGORY_NAME);
        nonUniqueCategory.setDescription("Description.");

        categoryDao.create(nonUniqueCategory);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void delete_null_throwsException() {
        categoryDao.delete(null);
    }

    @Test
    public void delete_category_removes() {
        categoryDao.create(category);
        Category storedCategory = categoryDao.findById(category.getId());
        Assert.assertNotNull(storedCategory);

        categoryDao.delete(category);

        storedCategory = categoryDao.findById(category.getId());
        Assert.assertNull(storedCategory);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findById_negativeId_throwsException() {
        categoryDao.findById(-5L);
    }

    @Test
    public void findById_categoryId_retrieves() {
        categoryDao.create(category);

        Category storedCategory = categoryDao.findById(category.getId());

        Assert.assertEquals(storedCategory.getName(), CATEGORY_NAME);
        Assert.assertEquals(storedCategory.getDescription(), CATEGORY_DESCRIPTION);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findCategoryByName_null_throwsException() {
        categoryDao.findCategoryByName(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findCategoryByName_emptyName_throwsException() {
        categoryDao.findCategoryByName("");
    }

    @Test
    public void findCategoryByName_electronics_retrieves() {
        categoryDao.create(category);

        Category storedCategory = categoryDao.findCategoryByName(CATEGORY_NAME);

        Assert.assertEquals(storedCategory.getId(), category.getId());
        Assert.assertEquals(storedCategory.getDescription(), CATEGORY_DESCRIPTION);
    }

    @Test
    public void findAll_electronics_retrievesList() {
        categoryDao.create(category);

        Category bag = new Category();
        bag.setName("Bag");
        bag.setDescription(CATEGORY_DESCRIPTION);
        categoryDao.create(bag);

        List<Category> storedCategories = categoryDao.findAll();

        Assert.assertEquals(storedCategories.size(), 2);
    }
}