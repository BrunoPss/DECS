# DECS

DECS is a Distributed Evolutionary Computing System developed by Bruno Guiomar
for his bachelor's thesis.\
This system follows a Coordinator - Slave architecture where there is one Coordinator
in the network and multiple Slaves that process the tasks delegated by the coordinator.\
This repository only contains the source code for the system Coordinator.\
The Slave source code can be found in a separate repository (DECS-Slave).

## Running the application

This project follows the standard Maven structure and this module (Coordinator)
should be started before any Slave. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux). This will start a web server accessible
from any browser connected to the local network.
In order to interact with the Coordinator, you should access the URL
http://<coordinator-address>:8080. For development purposes you can access the Coordinator
at http://localhost:8080.

Parameters
<address>