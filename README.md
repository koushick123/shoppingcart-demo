<h3>Shopping Cart Demo application</h3>
<br>
This is a Shopping cart application that contains 3 microservices and a eureka registration service. It can be used to add a new customer, add inventory and checkout an order for a customer.
<br>
The following technologies were used:
<ul>
  <li><b>Java 1.8</b></li>
  <li><b>Spring Boot 2.0.1</b></li>
  <li><b>Maven 3.6.1</b></li>
  <li><b>Netflix Eureka (for inter-service communication)</b></li>
  <li><b>H2 in-memory DB</b></li>
</ul>
Application Microservices:<br>
<ul>
  <li><b>Customer Service</b><br>&nbsp;&nbsp;1. Adds a customer.<br>&nbsp;&nbsp;2. Checks for existing customer.<br>&nbsp;&nbsp;3. Gets customer details.</li>
  <li><b>Inventory Service</b><br>&nbsp;&nbsp;1. Adds an inventory.<br>&nbsp;&nbsp;2. Checks for existing inventory.</li>
  <li><b>CheckoutOrder Service</b><br>&nbsp;&nbsp;1. Checks out order for a customer.<br>&nbsp;&nbsp;2. Fetches all checkedout orders for a particular customer.</li>
</ul>
BDD Test Case depicts below scenarios:<br>
<ul>
  <li>Checkout an order for an existing customer</li>
  <li>Checkout an order for a non-existing customer</li>
</ul>
The application has been deployed onto Docker Hub, where 4 container images have been pushed for each of the 4 microservices (registration, customer, inventory and checkout) onto koushick123/shoppingcart-demo. <br>To run this application in a containerized version, after you build and run the registration service locally, use docker command <b>docker inspect &lt;registration_container_id&gt;</b> to obtain the IP address of the eureka registration server.<br>
<br>Use the above IP address to update the below files, before you start the rest of the services so that they are able to register themselves with Eureka registration service.<br>
<ul>
  <li><b>customer-server.yml</b></li>
  <li><b>inventory-server.yml</b></li>
  <li><b>checkoutorder-server.yml</b></li>
</ul>
under<br> eureka:<br>
&nbsp;  client:<br>
&nbsp;&nbsp;    serviceUrl:<br>
&nbsp;&nbsp;&nbsp;      defaultZone: http://&lt;IP Address&gt;:8761/eureka
<br>
<h4><b>NOTE: Above steps are not necessary if the application needs to be run directly</b></h4>
