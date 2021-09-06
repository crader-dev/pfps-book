# Identifying algebras
_(Source: Chapter 4. page 80)_
- GET /brands
- POST /brands
- GET /categories
- POST /categories
- GET /items
- GET /items?brand={name}
- POST /items
- PUT /items
- GET /cart
- POST /cart
- PUT /cart
- DELETE /cart/{itemId}
- GET /orders
- GET /orders/{orderId}
- POST /checkout
- POST /auth/users
- POST /auth/login
- POST /auth/logout

# Deviations from PFPS
- Renamed Quantity to ItemQuantity, to disambiguate with Squants.
- Moved PaymentId into Payment file, instead of Order. 

# Notes / Observations
## During Chapter 4
- [This blog post](https://blog.softwaremill.com/a-simple-trick-to-improve-type-safety-of-your-scala-code-ba80559ca092)
  explores refined types & newtypes from PFPS Ch.1 in a bit more detail.
  - **TODO:** Read more on refined/new types
- What other ways could ItemCreate and ItemUpdate be differentiated from themselves/Item?
- Squants has a really nice approach to Money conversion rates (i.e. separating volatile rates, via MoneyContext, from
  boilerplate conversion logic)
- It seems like Cart, CartItem. CartTotal, Order, etc. could all be simplified.
- In ShoppingCart, single item removal is provided but not modify/add - why?
  - It seems like multiple partial modifications (i.e. PUT) outside of deletions would have to be synchronized upstream.
  - Without more context (as of Ch.4), this seems like an unnecessary break in separation of concerns.
- The Statuses in HealthCheck seems out of place, the domain code shouldn't know about how things are being persisted
  (i.e., RedisStatus, PostgresStatus).
  - Instead, could there be a general ServiceStatus trait that gets extended in another module/package? Does this work
    with newtypes?
  - **TODO:** Investigate later
- We define JSON encoders tightly with their respective domain objects, but is that necessary? (i.e. Status)
  - This is a somewhat common pattern and is usually reasonable, but it does tie serialization to a single JSON format.
  - What would this code look like if we separated json (and potentially other) encoders into a separate, common module
    so that they are still generally available but are also pluggable and separate from the domain objects.
  - **TODO:** Investigate later

# Potential Expansions
_(which may or may not break the book's assumptions)_
- Allow multiple Categories per Item
