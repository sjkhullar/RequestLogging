
The project willl log the requests coming to BookingController resource.

We have provide three public api 

1.) Default Home Page which is residing under "/hotel/" path.
2.) GET Request (residing under "/hotel/book" path) : which will retrive the customer information based upon the input supplied.
    We are expecting id of customer record. which can be used to fetch the information from db (if required).
	But we have generated the required customer information at controller itself (by generating UUID string and sending it back)
3.) Post Request (residing under "/hotel/book" path) : Post request will post the customer information like name, city, purpose, idproof
													   etc to the server module.

We have created one LoggingHandler aspect which will intercepts all the above three requests.
The LoggingHandler aspect have mainly four methods
1.) logBefore		 : logBefore method will be executed once it satisfied its pointcut expression. logBefore has pointcut expression 
					   @Before("controller() && allMethod() && args(..,request)"), 
					   which implies it will be executed for 
					   1.) Controller module
					   2.) All Method of module
					   3.) Method taking HttpServletRequest as last parameters
	So, Joining above three points, In controller module there are only two methods which are taking HttpServletRequest as last parameters, so
	GET and Post methods of "/book" resouce of BookingController will be intercepted by logBefore and will log all the information.
	
2.) logAfter : It has pointcut expression"@AfterReturning(pointcut = "controller() && allMethod()", returning = "result")", Which means
			   it will be executed for all resources of BookingController name ("/","/book" (GET,Post))
3.) logAfterThrowing : logAfterThrowing will be executed if any method of BookingController modules throws any exception.
4.) logAround        : logAround will be executed for all methods of BookingController controller module. We have used it to calculate the execution
					   time of particular method.
					   

Our server application is deployed in local machine so "localhost" is our machine name and at port 9095. 
Please change your ip and port to access the resources of BookingController.

The URL to access the resource should look like.

http://<IP Address or machine name>:<port number>/log/hotel/

Here are the execution results:

Default Home Page

http://localhost:9095/log/hotel/

Logs are :

INFO : org.learn.log.BookingController - Welcome home! The client locale is en_US.
DEBUG: org.learn.log.aspect.LoggingHandler - Method org.learn.log.BookingController.home () execution time : 1 ms
DEBUG: org.learn.log.aspect.LoggingHandler - Method Return value : hotel


Post request
http://localhost:9095/log/hotel/book

Logs are :

DEBUG: org.learn.log.aspect.LoggingHandler - Entering in Method :  bookHotel
DEBUG: org.learn.log.aspect.LoggingHandler - Class Name :  org.learn.log.BookingController
DEBUG: org.learn.log.aspect.LoggingHandler - Arguments :  [Scott, Customer CityName, Personal, Security Number, {}, org.apache.catalina.connector.RequestFacade@7886596e]
DEBUG: org.learn.log.aspect.LoggingHandler - Target class : org.learn.log.BookingController
DEBUG: org.learn.log.aspect.LoggingHandler - Start Header Section of request 
DEBUG: org.learn.log.aspect.LoggingHandler - Method Type : POST
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: host Header Value : localhost:9095
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: connection Header Value : keep-alive
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: content-length Header Value : 0
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: user-agent Header Value : Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: cache-control Header Value : no-cache
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: origin Header Value : chrome-extension://mkhojklkhkdaghjjfdnphfphiaiohkef
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept Header Value : */*
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept-encoding Header Value : gzip, deflate
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept-language Header Value : en-US,en;q=0.8
DEBUG: org.learn.log.aspect.LoggingHandler - Request Path info :/hotel/book
DEBUG: org.learn.log.aspect.LoggingHandler - End Header Section of request 
DEBUG: org.learn.log.aspect.LoggingHandler - Method org.learn.log.BookingController.bookHotel () execution time : 148 ms
DEBUG: org.learn.log.aspect.LoggingHandler - Method Return value : customerDetails


GET Request

http://localhost:9095/log/hotel/book?id=1

Logs are :

DEBUG: org.learn.log.aspect.LoggingHandler - Entering in Method :  bookHotel
DEBUG: org.learn.log.aspect.LoggingHandler - Class Name :  org.learn.log.BookingController
DEBUG: org.learn.log.aspect.LoggingHandler - Arguments :  [1, {}, org.apache.catalina.connector.RequestFacade@777d0dd6]
DEBUG: org.learn.log.aspect.LoggingHandler - Target class : org.learn.log.BookingController
DEBUG: org.learn.log.aspect.LoggingHandler - Start Header Section of request 
DEBUG: org.learn.log.aspect.LoggingHandler - Method Type : GET
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept Header Value : image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept-language Header Value : en-US
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: ua-cpu Header Value : AMD64
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: accept-encoding Header Value : gzip, deflate
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: user-agent Header Value : Mozilla/5.0 (Windows NT 6.2; Win64; x64; Trident/7.0; MASPJS; rv:11.0) like Gecko
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: host Header Value : localhost:9095
DEBUG: org.learn.log.aspect.LoggingHandler - Header Name: connection Header Value : Keep-Alive
DEBUG: org.learn.log.aspect.LoggingHandler - Request Path info :/hotel/book
DEBUG: org.learn.log.aspect.LoggingHandler - End Header Section of request 
DEBUG: org.learn.log.aspect.LoggingHandler - Method org.learn.log.BookingController.bookHotel () execution time : 8 ms
DEBUG: org.learn.log.aspect.LoggingHandler - Method Return value : customerDetails