----------------------
Install dependencies using 'mvn install'
----------------------
Execute tests from command line running 'mvn test'
-------------------------
Design of the solution

The processing of the messages was built as a batch, receiving a batch of pending messages and storing their data.
For the test messages, I created a MessageProducerMock class which builds a set of messages randomly, for the different types
and their corresponding attributes.
For the solution itself, MessageProcessor is the entry of the system. Its method processMessages
receives the existing messages and internaly resolves the storing and report generation. Every 10 messages the application
creates a report on console indicating for each product the amount of units sold and the total. Every 50 messages the pending 
operations, which are stored in a queue inside a class called PendingAdjustments, get processed and the queue is cleared.
SalesStore class is the actual class holding the data and the pending "Adjustments" or operations comming in
message type 3.



-------------------------
config.properties file contains configuration properties:
nLogging: Number of sales before the system creates a report
nAdjustments Number of sales before the system performs the pending adjustment operations
productTypes Some mock product types for the system


--------------
Possible improvements:

Since the test was specific about being single threaded I made no further focus on concurrency, synchronization or thread management.
In a multithreaded environment however, a producer-consumer design would be a better solution, with a queue being filled
by a producer and used by a consumer.
