package myretail;

import myretail.model.ProductDetails;
import myretail.router.ProductDetailsApi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyRetailApplicationTests {

	WebTestClient testClient;

	@Autowired
	ProductDetailsApi productDetailsApi;

	@org.junit.Before
    public void setUp() {
        testClient = WebTestClient.bindToRouterFunction(productDetailsApi.composedRoutes()).build();
    }

	@Test
	public void testGetProductDetails() {
		//given(productDetailsService.getProductDetails("1234")).willReturn(Mono.just(productDetails));
		ProductDetails productDetails = testClient.get()
				.uri("http://localhost:8080/rest/products/1234")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(ProductDetails.class)
				.returnResult()
				.getResponseBody();
		Assert.assertEquals(new Long(1234), Long.valueOf(productDetails.getProductId()));
	}
}
