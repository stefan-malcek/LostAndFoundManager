package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.services.CategoryService;
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

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CategoryFacadeTest extends AbstractTestNGSpringContextTests {

    //TODO: fix mocking the service in the facade. Spring uses a real implementation and not a mock.
    @Mock
    private CategoryService categoryService;

    @Autowired
    @InjectMocks
    private CategoryFacade categoryFacade;

    private long electronicsId = 5;
    private Category electronics;
    private CategoryCreateDTO electronicsDto;

    @BeforeMethod
    public void createCategories() {
        electronics = new Category();
        electronics.setId(electronicsId);
        electronics.setName("Electronics");
        electronics.setDescription("Various lost electronic devices.");

        electronicsDto = new CategoryCreateDTO();
        electronicsDto.setName("Electronics");
        electronicsDto.setDescription("Various lost electronic devices.");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void update_electronics_updates(){
        electronicsId = categoryFacade.createCategory(electronicsDto);

        CategoryDTO category = new CategoryDTO();
        category.setId(electronicsId);
        category.setName("test");
        category.setDescription("Hello");

        categoryFacade.updateCategory(category);

        CategoryDTO retrieved = categoryFacade.getCategoryById(electronicsId);

        Assert.assertEquals(retrieved.getName(), "test");
    }

    @Test
    public void getCategoryById_electronicsId_retrievesElectronics(){
        when(categoryService.findById(electronicsId)).thenReturn(electronics);

        CategoryDTO category = categoryFacade.getCategoryById(electronicsId);

        Assert.assertEquals(category.getId(), electronics.getId());
        Assert.assertEquals(category.getName(), electronics.getName());
        Assert.assertEquals(category.getDescription(), electronics.getDescription());
    }

}
