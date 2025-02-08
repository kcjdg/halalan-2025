package ph.dgtech.halalan.voter.profile.stubs;

import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class AddressClientStub {

    public static void stubValidateAddress(AddressInfo address) {
        stubFor(post(urlEqualTo("/address/validate"))
                .withRequestBody(equalToJson(createJson(address)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(createJson(address))));
    }

    public static void stubAddressNotFound(AddressInfo address) {
        stubFor(post(urlEqualTo("/address/validate"))
                .withRequestBody(equalToJson(createJson(address)))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "status": "Failure",
                                    "message": "Address not found",
                                    "errors": [
                                        "Combination of address is not valid"
                                    ]
                                }
                                """)));
    }

    public static void stubInternalServer(AddressInfo address) {
        stubFor(post(urlEqualTo("/address/validate"))
                .withRequestBody(equalToJson(createJson(address)))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "statusCode":500,
                                  "title":"Internal Server Error",
                                  "details":"Cannot verify address. Try again"
                                }
                                """)));
    }

    private static String createJson(AddressInfo address) {
        return """
                {
                  "region": "%s",
                  "province": "%s",
                  "municipality": "%s",
                  "barangay": "%s"
                }
                """.formatted(address.region(), address.province(), address.municipality(), address.barangay());
    }
}
