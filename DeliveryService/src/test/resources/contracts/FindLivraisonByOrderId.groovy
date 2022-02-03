import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return 200 and check body"
    request{
        method GET()
        headers {
		contentType(applicationJson())
	}
        url("/api/livraison/order/1") 
    }
    response {
        status 200
        headers {
		contentType(applicationJson())
	}
        body(file("ResponseByOrderId.json"))
    }
}

