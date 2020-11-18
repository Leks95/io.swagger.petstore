package wb.go;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class TestCard {

    String baseUri = "https://nginx.wbpay.svc.k8s.dldevel";
    String allCards = "/api/v1/get_cards/";
    String cardForLink = "/link_card/";
    boolean isEnabled = false;

    @Test
    public void getAllCards(){
        int merchant = 1;
        int user = 44;
        String expires_at = "1605695389";
        String signature =
                "8104ec8f457cb9d3d0b4aba00f13ae84abb825f62ac27a8d992b11dfda536a68a22045cf9e7588f7172c609c50ae78daf656c5ccea67c0d4b77f39f15cd37ef6";

        String cardData = String.format("merchant/%s/user/%s/expires_at/%s/signature/%s",
                merchant, user, expires_at, signature
        );
        System.out.println(baseUri + allCards + cardData);
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .baseUri(baseUri)
                .basePath(allCards + cardData)
                .get();
        System.out.println(response.asString());
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void linkingCard(){
        int merchant = 1;
        int user = 44;
        int sum = 100;
        int currency = 643;
        String expires_at = "1605638855";
        String transaction = "9b2a8ae7-903f-4ae5-9bbe-c706907e6b1c";
        boolean use_3ds = false;
        String success_url = "aHR0cHM6Ly95YW5kZXgucnUvP2lkPTEyMw==";
        String fail_url = "aHR0cHM6Ly9yYW1ibGVyLnJ1Lz9pZD0zMjE=";
        String signature =
                "032faee3806893cc804257662c5b94358cf7569b01367f7eed8fd63cb1da8d60e45012a438fff29e3accd35738c70a678ba6b54a3ce2df2938c417699493e161";

        String cardData = String.format("merchant/%s/user/%s/sum/%s/currency/%s/transaction/%s/use_3ds/%s/success_url/%s" +
                "/fail_url/%s/expires_at/%s/signature/%s",
                merchant, user, sum, currency, transaction, use_3ds, success_url, fail_url, expires_at, signature);

        Response response = RestAssured.given()
                .urlEncodingEnabled(isEnabled)
                .relaxedHTTPSValidation()
                .baseUri(baseUri)
                .basePath(cardForLink + cardData)
                .get();
        System.out.println(response.asString());
        Assert.assertEquals(true, response.asString().contains("Обработка платежа"));
        Assert.assertEquals(200, response.getStatusCode());

    }
}

