package back.ecommerce_AL_Lingerie.back.service.faq;


import back.ecommerce_AL_Lingerie.back.dto.FAQDto;

public interface FAQService {
	FAQDto postFAQ(Long productId, FAQDto faqDto);
}
