package rest.api;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import rest.utilities.TestDataLoader;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetRequests {

    private static TestDataLoader data = TestDataLoader.load("tests-data");;

    public static List<ValidatableResponse> getSearchValidatedResponses() {
        List<ValidatableResponse> responses = new ArrayList<>();
        data.getSearchValues().forEach(searchValue -> {
            responses.add(
                given()
                    .spec(new RequestSpecBuilder()
                        .setBaseUri(data.getBaseUri())
                        .setBasePath(data.getBasePath().get("search").concat(data.getCurrentSite()))
                        .addQueryParam(data.getQueryParams().get("search"), searchValue)
                        .build())
                .when()
                    .get(Endpoints.SEARCH.value())
                .then()
                    .spec(new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build())
            );
        });
        return responses;
    }

    public static List<ValidatableResponse> getItemValidatedResponses(List<String> productIds) {
        List<ValidatableResponse> responses = new ArrayList<>();
        productIds.forEach(productId -> {
            responses.add(
                given()
                    .spec(new RequestSpecBuilder()
                        .setBaseUri(data.getBaseUri())
                        .build())
                .when()
                    .get(Endpoints.ITEMS.value().concat(productId))
                .then()
                    .spec(new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build())
            );
        });
        return responses;
    }
}
