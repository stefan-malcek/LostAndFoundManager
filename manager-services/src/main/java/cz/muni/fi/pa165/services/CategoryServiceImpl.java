package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.entities.Category;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stefan Malcek
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Inject
	private CategoryDao categoryDao;

	@Override
	public Category findById(Long id) {
		return categoryDao.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public void create(Category category) {
		categoryDao.create(category);
	}

	@Override
	public void remove(Category c) {
		categoryDao.delete(c);
	}

	@Override
	public Category findByName(String categoryName) {
		return categoryDao.findCategoryByName(categoryName);
	} 
}