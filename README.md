This app produces random values for energy 
consumption by reading them from a csv file (sensor.csv)
that acts as a user's home sensor and then sends them
to the main backend app using a rabbitmq queue. 
(change the details of the queue in application.properties file).

To start the message sending run "SenderApplication".

