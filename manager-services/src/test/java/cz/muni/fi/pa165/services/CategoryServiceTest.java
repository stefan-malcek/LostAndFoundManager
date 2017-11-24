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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CategoryServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CategoryDao categoryDao;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    private long electronicsId = 5;
    private Category electronics;

    @BeforeMethod
    public void createCategories() {
        electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Various lost electronic devices.");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void create_electronics_callsCategoryDaoMethodOnce() {
        categoryService.create(electronics);

        verify(categoryDao, times(1)).create(electronics);
    }

    @Test
    public void findById_electronicsId_retrievesCategory() {
        when(categoryDao.findById(electronicsId)).thenReturn(electronics);

        Category category = categoryService.findById(electronicsId);

        Assert.assertEquals(category.getName(), electronics.getName());
        Assert.assertEquals(category.getDescription(), electronics.getDescription());
    }
}