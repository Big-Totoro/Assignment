# math-parser-service

## Description

*math-parser-service* is Spring-boot REST service to parse and calculate mathematical expressions for the following small set of the operations: "+", "-", "*", "/", "(", ")", "^". And provides some statistic about the calculated expressions.

### Provides the following functionality

- `/parser/v1/parse?expression={expression}` - parses the expression and returns the result of the calculation or an error. 
  Example `/parser/v1/parse?expression=(-7*8+9-(9/4.5))^2`. Please be informed that the URL should be properly encoded before execution. Please use one of the online URL Encode services like [www.urlencoder.org](https://www.urlencoder.org/). So the request should be encoded to the `/parser/v1/parse?expression=%28-7%2A8%2B9-%289%2F4.5%29%29%5E2`
  
- `/stats/v1/expressions/amount/date/{date}` - returns amount of the calculated expressions on the date. `{date}` is ISO-DATE format.
  Example `/stats/v1/expressions/amount/date/2019-09-24`.
  
- `/stats/v1/expressions/amount/operation/{operation}` - returns amount of the calculated expressions for the specific operation. Where {operation} set is "+" - plus, "-" - minus, "*" - multiply, "/" - div, "(" - lp, ")" - rp, "^" - pow. 
  Example `/stats/v1/expressions/amount/operation/minus`
  
- `/stats/v1/expressions/date/{date}` - returns a list of the calculated expressions for the specific date. `{date}` is ISO-DATE format. Example `/stats/v1/expressions/date/2019-09-24`.

- `/stats/v1/expressions/operation/{operation}` - returns a list of the calculated expressions for the specific operation. Where {operation} set is "+" - plus, "-" - minus, "*" - multiply, "/" - div, "(" - lp, ")" - rp, "^" - pow. 
  Example `/stats/v1/expressions/operation/multiply`

- `/stats/v1/number/popular` - returns the most popular number across all calculated expressions.

The *math-parser-service* uses __parser module__ to parse and calculate arithmetic expression. 
The parser correspons to the context free-grammar as following

- expr: term ((PLUS | MINUS) term)*
- term: factor ((MUL | DIV) factor)*
- factor: exp ^ exp | exp
- exp: (expr) | NUM

## Build and run

Please use `Assignment$ mvn clean install` command to build all modules and then go to 'math-parser-service' folder to run the command `./mvnw spring-boot:run` 

## Pivotal Cloud Service

I use trial period of [Pivotal Web Services](https://run.pivotal.io/) to deploy my microservice .war file and play a bit with it. All technical details are in the manifest.yml file. And I'm just going to provide the link to the *math-parser-service* endpoints here.

https://bootcamp-customers-sleepy-bongo.cfapps.io/parser/v1/parse?expression=100-50

https://bootcamp-customers-sleepy-bongo.cfapps.io/stats/v1/expressions/amount/date/2019-09-24

https://bootcamp-customers-sleepy-bongo.cfapps.io/stats/v1/expressions/amount/operation/minus

https://bootcamp-customers-sleepy-bongo.cfapps.io/stats/v1/expressions/date/2019-09-24

https://bootcamp-customers-sleepy-bongo.cfapps.io/stats/v1/expressions/operation/plus

https://bootcamp-customers-sleepy-bongo.cfapps.io/stats/v1/number/popular
