# The task
Imagine you're building an URL shortener as a potential next-big-thing product. To test it, we would like to first have an MVP with only basic functionality.

However, if everything goes according to plan, we will invest more effort into this so even the MVP should be a modern, nicely written, maintainable and production ready API.

For the MVP we don't want any kind of user registration or security. We only care that we provide a proper REST API that will enable other users/ clients to send a URL and will receive some kind of identifier/ hash to which this URL corresponds in our system. And the other way around of course, meaning that a user should be able to resolve the full URL.

To speed up the development, we won't be writing everything on our own, so please think of libraries/ components you could use to fulfill the task.

# Helpful info:
- include .git folder in your solution so we can, with the help of the Git history, see and understand your thinking process
- production ready API can mean a lot of things to different people. We're interested to see what you think a quality production ready API solution looks like. Imagine that the moment you deliver the solution, we'll deploy it to all our environments, consisting of multiple instances. So, provide data in a way that allows customization for dedicated environments
- when designing the solution please think about global scalability: what endpoints will be used the most, what might be a bottle neck, what could be a possible solution to identify problems

Have fun with our coding challenge. If you have any questions, don’t hesitate to ask.


# Research
- should generate a short url from a long one
  - however, for the MVP only an identifier/hash is needed
- read/write ratio could be around 1:100
- design issues:
  - must be fault-tolerant to malicious input
  - load balancing *
  - horizontal scalability is important
  - should use caching for faster accessibility
  - security/authentication *
  - include logging and monitoring for proactive troubleshooting *
  - add analytics *
- features
  - endpoint for creating identifier for a given URL
  - endpoint for retrieval of full URL for a given identifier
  - endpoint(s) for analytics *
- algorithm
  - 4 char substring of md5 should suffice
  - collisions have to be mitigated
- operations
  - service-external databases or key-value stores (caching) have to be set up
  - backup- and restore concept needed
  
*) out of scope for the MVP

# TODO list
- bootstrap project
- create endpoint for getting identifier/hash by URL
- implement algorithm and persistence
- create endpoint for getting URL by identifier/hash & implement URL retrieval
- staging: environment configuration
- scalability: using an external database or some kind of distributed key-value map
