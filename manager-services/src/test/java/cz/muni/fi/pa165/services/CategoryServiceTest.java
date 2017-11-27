package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.entities.Category;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CategoryServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CategoryDao categoryDao;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    private long electronicsId = 5;
    private String categoryName = "Electronics";
    private Category electronics;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() {
        electronics = new Category();
        electronics.setId(electronicsId);
        electronics.setName(categoryName);
        electronics.setDescription("Various lost electronic devices.");
    }

    @Test
    public void create_electronics_callsCategoryDaoMethodOnce() {
        categoryService.create(electronics);

        verify(categoryDao, times(1)).create(electronics);
    }

    @Test
    public void remove_electronics_callCategoryDaoMethodOnce(){
        categoryService.remove(electronics);

        verify(categoryDao, times(1)).delete(electronics);
    }

    @Test
    public void findById_electronicsId_retrievesCategory() {
        when(categoryDao.findById(electronicsId)).thenReturn(electronics);

        Category category = categoryService.findById(electronicsId);

        Assert.assertEquals(category.getName(), electronics.getName());
        Assert.assertEquals(category.getDescription(), electronics.getDescription());
        verify(categoryDao, times(1)).findById(electronicsId);
    }

    @Test
    public void findByName_electronics_retrievesCategory(){
        when(categoryDao.findCategoryByName(categoryName)).thenReturn(electronics);

        Category category = categoryService.findByName(categoryName);

        Assert.assertEquals(category.getId(), electronics.getId());
        Assert.assertEquals(category.getDescription(), electronics.getDescription());
        verify(categoryDao, times(1)).findCategoryByName(categoryName);
    }

    @Test
    public void findByName_list_retrievesList(){
        List<Category> categories = new ArrayList<>();
        categories.add(electronics);

        Category bag = new Category();
        bag.setId(2);
        bag.setName("Bag");
        bag.setDescription("Items for stuff.");

        categories.add(bag);

        when(categoryDao.findAll()).thenReturn(categories);

        List<Category> retrieved = categoryService.findAll();

        Assert.assertTrue(retrieved.size() == 2);
        verify(categoryDao, times(1)).findAll();
    }
}