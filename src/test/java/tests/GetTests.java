package tests;

import com.epam.api.configuration.ConfigutarionManager;
import com.epam.api.controllers.RequestController;
import com.epam.api.models.Pet;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.epam.api.enums.PetStatus.AVAILABLE;
import static com.epam.api.enums.PetStatus.SOLD;

public class GetTests {
    RequestSpecification requestSpecification = ConfigutarionManager.getConfig();
    RequestController requestController = new RequestController(requestSpecification);

    @Test
    public void getPetByStatusTest() {
        Pet[] pets = requestController.getPetByStatus(AVAILABLE, SOLD)
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets.length).isGreaterThan(0);
    }

    @Test
    public void getEmptyStatus() {
        Pet[] pets = requestController.getPetByStatus()
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }

    @Test
    public void getPetByInvalidStatus() {
        Pet[] pets = requestController.getPetByStatus("dead")
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }
}
