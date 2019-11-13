
# User Guide:

This application is implemented in Spring Boot as a Restful web server which exposes the following endpoints:

Get /zipcoderanges which will return a json formatted object with ranges of zip codes to which deliveries can not be made, example:

    GET http://localhost:8080/zipcoderanges
    
    returns - 200

            [
                {
                    "range": [
                        10001,
                        20000
                    ]
                },
                {
                    "range": [
                        501,
                        999
                    ]
                }
            ]


Get /zipcoderanges/{zipcode}   which will return a range of zipcodes in which the argument zipcode is included, indicating it is prohibited,
or a HTTP 404 reponse, indicating it is not. Example:

        GET http://localhost:8080/zipcoderanges/9999
        
        returns - 404
        
        "Zip code is not in a range"


Post /zipcoderanges  which must send a json formatted body of a range of zip codes to be included in the prohibited list. If the range to be added intersects
existing ranges of zip codes, they will be merged. The range of zip codes in which the new range is contained will be returned as a json object. Example:

        POST http://localhost:8080/zipcoderanges
        
        with body 
        
            {
                "lowZipCode": 998,
                "highZipCode": 10002
            }
            
        returns - 201
        
            {
                "range": [
                    501,
                    20000
                ]
            }

The range to be added has to specify the numerically lower zip code first with both values not less than 501 (Holtsville, New York) and 
not higher than 99950 (Ketchikan, AK). Example:

        POST http://localhost:8080/zipcoderanges
        
        with body 
        
            {
                "lowZipCode": 99999,
                "highZipCode": 99998
            }
            
        returns - 422
        
        "Invalid range of zip codes"

Delete /zipcoderanges/{zipcode}   which will delete all existing zip code ranges. Example:

        DELETE http://localhost:8080/zipcoderanges
        
        returns - 204
        
        