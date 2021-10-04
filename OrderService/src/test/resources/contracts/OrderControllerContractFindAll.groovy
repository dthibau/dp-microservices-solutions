import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return 201 when creating new Order"
    request{
        method GET()
        headers {
		contentType(applicationJson())
	}
        url("/api/orders/") 
    }
    response {
        status 200
        headers {
		contentType(applicationJson())
	}
        body(file("GetResponse.json"))
    }
}

