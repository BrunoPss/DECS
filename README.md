# DECS

DECS is a Distributed Evolutionary Computing System developed by Bruno Guiomar
for his final degree thesis.\
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

`<address>`

## Tests

There are some simple tests included on this project. They are placed on the 'test' folder
and can be executed with the following command.

`mvn test`

## Default User

DECS implements a simple login interface which manages the user access to the system.
There is a default user configured for development purposes with the following credentials:

User: `user`\
Password: `user`

## Other Information
### Author
**Name:** Bruno Guiomar\
**Email:** [Bruno.guiomar33@gmail.com](mailto:bruno.guiomar33@gmail.com?subject=DECS%20Inquire)\
**GitHub:** [BrunoPss](https://github.com/BrunoPss)

### Repository
[**DECS**](https://github.com/BrunoPss/DECS)\
[**DECS-Slave**](https://github.com/BrunoPss/DECS-Slave)

### Further Documentation
DECS JavaDocs can be found under this [**link**](https://brunopss.github.io/DECS/).

### Licence
This software is licensed under the GPL-2.0 License.
