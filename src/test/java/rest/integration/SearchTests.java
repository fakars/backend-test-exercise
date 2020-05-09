package rest.integration;

import com.jayway.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import rest.api.GetRequests;

import static org.hamcrest.Matchers.*;
import static rest.utilities.ApiUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("regression")
@Tag("search")
public class SearchTests {

	private List<ValidatableResponse> searchResponses;
	private List<ValidatableResponse> itemsResponses;
	private List<Map<String, String>> searchProducts = new ArrayList<>();
	private List<String> searchProductIds = new ArrayList<>();
	private final Logger LOG = Logger.getLogger(SearchTests.class.getName());

	@BeforeAll
	void beforeTests() {
		searchResponses = GetRequests.getSearchValidatedResponses();
		searchResponses.forEach(response -> {
			searchProducts.add(randomObjectFromList(getListFromResponse(response, "results")));
		});
		searchProducts.forEach(product -> {
			searchProductIds.add(product.get("id"));
		});
		itemsResponses = GetRequests.getItemValidatedResponses(searchProductIds);
	}

	@Test
	@Order(1)
	@DisplayName("Response headers value validation")
	void searchHeadersValuesTest() {
		searchResponses.forEach(response -> {
			response
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Content-Type")
				.header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
				.header("ETag", notNullValue())
				.header("Cache-Control", "max-age=300, stale-while-revalidate=150, stale-if-error=600")
				.header("Content-Encoding", "gzip")
				.header("Connection", "keep-alive")
				.header("Transfer-Encoding", "chunked");
		});
	}

	@Test
	@Order(2)
	@DisplayName("Verify response body main structure")
	void searchStatusAndBodyTest() {
		searchResponses.forEach(response -> {
			response.assertThat()
			.body(
				"site_id", instanceOf(String.class),
				"query", instanceOf(String.class),
				"paging", instanceOf(Map.class),
				"results", instanceOf(List.class),
				"secondary_results", instanceOf(List.class),
				"related_results", instanceOf(List.class),
				"sort", instanceOf(Map.class),
				"available_sorts", instanceOf(List.class),
				"filters", instanceOf(List.class),
				"available_filters", instanceOf(List.class)
			);
		});
	}

	@Test
	@Order(3)
	@DisplayName("Validate results array length doesn't exceed paging limit")
	void searchResultsTest() {
		searchResponses.forEach(response -> {
			List<String> resultsArr = response.extract().jsonPath().getList("results");
			if(resultsArr.size() != 0) {
				response.assertThat()
					.body(
						"paging.limit", greaterThanOrEqualTo(resultsArr.size())
					);
			} else {
				LOG.warning("No products found for given query string");
			}
		});
	}

	@Test
	@Order(3)
	@DisplayName("Validate product in search results matches /items response")
	void searchResultsMatchesItemResultsTest() {
		int index = 0;
		for(ValidatableResponse response : itemsResponses) {
			response.assertThat()
				.body(
					"id", equalTo(searchProductIds.get(index)),
					"seller_id.id", equalTo(searchProducts.get(index).get("seller.id")),
					"title", equalTo(searchProducts.get(index).get("title")),
					"price", equalTo(searchProducts.get(index).get("price")),
					"accepts_mercadopago", equalTo(searchProducts.get(index).get("accepts_mercadopago")),
					"currency_id", equalTo(searchProducts.get(index).get("currency_id")),
					"shipping.free_shipping", equalTo(
							Boolean.parseBoolean(
								StringUtils
									.substringBetween(
										searchProducts.get(index).toString(), "free_shipping=", ",")))
				);
			index++;
		}
	}
}
