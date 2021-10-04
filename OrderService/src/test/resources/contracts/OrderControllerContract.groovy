import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return 201 when creating new Order"
    request{
        method POST()
        headers {
		contentType(applicationJson())
	}
        url("/api/orders") {
            body(file("request.json"))
        }
    }
    response {
        status 201
        body(file("PostResponse.json"))
    }
}

