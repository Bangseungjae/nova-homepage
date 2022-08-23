package nova.novahomepage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class NovaHomepageApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaHomepageApplication.class, args);
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}

	@PersistenceContext
	private EntityManager entityManager;
}
