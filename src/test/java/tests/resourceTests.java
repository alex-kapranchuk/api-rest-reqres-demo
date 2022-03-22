package tests;

import model.response.ResourceData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import spec.Specifications;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class resourceTests {

    /**
     * Check sorted list by year
     */

    private final static String URL = "https://reqres.in/";

    @Test
    @DisplayName("Sorted list by year")
    public void checkSortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        List<ResourceData> data = given()
                .when()
                .get("/api/unknown")
                .then()
                .log().status()
                .extract().body().jsonPath().getList("data", ResourceData.class);

        List<Integer> dataYears = data.stream().map(ResourceData::getYear).collect(Collectors.toList());
        List<Integer> sortedDataYears = dataYears.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(dataYears, sortedDataYears, "Years are not sorted by year");
    }

    /**
     * Check data of single resource
     */

    @Test
    @DisplayName("Check single value")
    public void checkSingleValueTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        ResourceData resourceData = given()
                .when()
                .get("api/unknown/2")
                .then()
                .log().status()
                .extract().body().jsonPath().getObject("data", ResourceData.class);

        assertThat(resourceData.getColor().startsWith("#"));
        assertThat(resourceData.getYear().toString().startsWith("20"));
        Assert.assertEquals("equals", "2", resourceData.getId().toString());
    }

    /**
     * Negative test resource list are not found
     */

    @Test
    @DisplayName("Negative test of resource")
    public void checkNegativeResourceTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(404));
        List<ResourceData> data = given()
                .when()
                .get("/api/unknown/23")
                .then()
                .log().status()
                .extract().body().jsonPath().getList("data", ResourceData.class);
    }
}
