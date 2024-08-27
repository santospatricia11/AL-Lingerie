package back.ecommerce_AL_Lingerie.back.services.admin.category;


import back.ecommerce_AL_Lingerie.back.dto.CategoryDto;
import back.ecommerce_AL_Lingerie.back.model.Category;

import java.util.List;

public interface CategoryService {
	 Category createCategory(CategoryDto categoryDto);
	 List<Category> getAllCategory();
}
