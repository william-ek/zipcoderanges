# Problem Statement:
 
BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:
 
[94133,94133] [94200,94299] [94600,94699]
 
Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.
 
Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.
 
PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.
 
NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices
 
EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]
Then the output should be = [94133,94133] [94200,94299] [94600,94699]
 
If the input = [94133,94133] [94200,94299] [94226,94399]
Then the output should be = [94133,94133] [94200,94399]

# APPROACH:

We’ll use the ranges of zip codes as keys to a Map whose values are the same range of zip codes. This is because a test for a range being already in the Map will take some legerdemain so we won’t be able to tell what zip code range is in the Map by it simply being equal; we will find a key equal that is not identical (e.g. zip code range 10001, 10005 is equal to 10000, 20000 because 10000, 20000 contains it, but they are not identical so we’ll need the value pointed to but this key). Let’s take a look at the possibilities: We can have two ranges that do not intersect, when the second range is added the result is two separate ranges.  When, however, the range to be added has an intersecting end point or end points, a new range will be created with the lowest and highest zip codes in the combined range.  The only thing we need is an Object that encapsulates a single range of zip codes and implements comparable, so we can give the caller of compareTo our view of what equal is and a collection that will listen to what compareTo has to say. The object is ZipCodeRange and the collection is a TreeMap which is extended with a custom class ZipCodeRangesMap. The compareTo will declare an object equal if there are any intersecting points in the range, however, because a key can be equal and not identical, there may be more than one equal objects in the map. This is why a Map was chosen as the fastest way to determine the actual value of the key is to have a value that is identical to the key; the value can be retrieved. 


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
        
        