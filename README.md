This project is zjz's garduation project

2019/11/20 Algorithm changed：
1. Try to add request to bucket
    1.1 Add successfully, consume request at a constant rate
    1.2 Add failed, go to 2

2. Try to take an token(The token will be added at a constant rate)
    2.1 Take successfully, handle the request
    2.2 Take failed, return an error response to client

2019/11/20 What need to do:
1. Use front end to test API
2. Read constant from Config file
3. Add some comment