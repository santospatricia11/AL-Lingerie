package back.ecommerce_AL_Lingerie.back.service.category;


import back.ecommerce_AL_Lingerie.back.dto.CategoryDto;
import back.ecommerce_AL_Lingerie.back.model.Category;
import back.ecommerce_AL_Lingerie.back.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	public Category createCategory(CategoryDto categoryDto) {
		log.info("Creating a new category: {}", categoryDto.getName());
		return categoryRepository.save(
				Category.builder()
						.name(categoryDto.getName())
						.description(categoryDto.getDescription())
						.build()
		);
	}

	public List<Category> getAllCategory() {
		log.info("Fetching all categories.");
		return categoryRepository.findAll();
	}
}
