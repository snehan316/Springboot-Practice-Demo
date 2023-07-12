# Springboot-Practice-Demo
Practice app to brush up on Springboot concepts

#Some Notes\
----Spring 3 works only with Java 17 or greater\
----when returning a Class object directly, remember to use new keyword eg: return new HelloWorldBean("hi");\
----How does bean object converted to json response?\
	---@RestController -> @responsebody + @controller  --> jackson autoconfigured for json responses (jackson-hhtp-message-converters)\
----who configures error mapping ? -> error-mvc-autoconfiguration\
----Use ResponseEntity to send back right HTTP status codes (Can also send location in headers)\
	---URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();\
	---return ResponseEntity.created(location).build();\

----Creating a custome exception class \
	---@ResponseStatus(code=HttpStatus.NOT_FOUND)\
		public class UserNotFoundException extends RuntimeException {\

			public UserNotFoundException(String message) {\
			super(message);\
		}\
	}\


------ResponseEntityExceptionHandler class is used by Spring for all exception handling formatting. For custom format of exceptions, we can extend this Class and write our method (override handleException method of ResponseEntityExceptionHandler class). Add @ControllerAdvice on our custom exception class\



------Validation - \

------content negotitation - how does the consumer want the response (response format) - xml or json --> use Accept header in request to specify this\
      --- just add jackson-dataformat-xml dependency \
	  


------Internationalization (i18n) -->  which language does the consumer want the response - english, french..etc -> use Accept-Language header in request to specify this (eg: en = English, nl=Dutch, fr=French)\



-----HATEOS - perform actions using links --> Important things to remember : EntityModel, WebMvcLinkBuilder (linkTo and methodOn methods )\

-----Serialization - Converting object to Stream  (eg: Object to JSON) -> Most popular JSON Serialization in Java is Jackson. \
1.You can customize the names of variables in the object using some Jackson annotations -> eg: @JsonProperty("user_name")\
2. You can return only some selected fields -> called filtering\
   2 types of filtering:\
   1. Static filtering: Same filtering for a bean across all REST Apis -> @JsonIgnoreProperties(class level), @JsonIgnore(variable level)\
   2. Dynamic filtering: Customized filtering for a bean for specific REST Apis -> @JsonFilter with FilterProvider (MappingJacksonValue Class)\
   
   
-----SpringBoot Actuator -> For Metrics, HealthCheck .. etc -> add the dependency\

-----ManyToOne and OneToMany Mapping, Lazy fetch, MappedBy .. etc\

-----Spring Security -> basic = add the dependency, customised = Add configurations\
     --> SecurityFilterChain\
	     1. All the requests should be authenticated\
		 2. If a request is not authenticated, a web page must be shown \
		 3. Disable CSRF -> Or else POST, PUT won't work \
		
