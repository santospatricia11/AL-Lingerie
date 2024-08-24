package back.ecommerce_AL_Lingerie.back.service.faq;
import back.ecommerce_AL_Lingerie.back.dto.FAQDto;
import back.ecommerce_AL_Lingerie.back.model.FAQ;
import back.ecommerce_AL_Lingerie.back.model.Product;
import back.ecommerce_AL_Lingerie.back.repository.FAQRepository;
import back.ecommerce_AL_Lingerie.back.repository.ProductRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FAQServiceImpl implements FAQService {


	private final FAQRepository faqRepository;

	private final ProductRepository productRepository;

	public FAQDto postFAQ(Long productId, FAQDto faqDto) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(optionalProduct.isPresent()) {
			FAQ faq = new FAQ();
			faq.setQuestion(faqDto.getQuestion());
			faq.setAnswer(faqDto.getAnswer());
			faq.setProduct(optionalProduct.get());

			FAQ savedFAQ = faqRepository.save(faq);
			log.info("FAQ posted successfully for product with ID: {}", productId);
			return savedFAQ.getFAQDto();
		}

		log.warn("Failed to post FAQ. Product with ID {} not found.", productId);
		return null;
	}

}
