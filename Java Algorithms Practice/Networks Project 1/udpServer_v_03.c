//Winfrey, Joshua
//George, Hebron
//Project 1
//09/26/2014



//==================================================== file = udpServer.c =====
//=  A message "server" program to demonstrate sockets programming            =
//=============================================================================
//=  Notes:                                                                   =
//=    1) This program conditionally compiles for Winsock and BSD sockets.    =
//=       Set the initial #define to WIN or BSD as appropriate.               =
//=    2) This program serves a message to program udpClient running on       =
//=       another host.                                                       =
//=    3) The steps #'s correspond to lecture topics.                         =
//=---------------------------------------------------------------------------=
//=  Example execution: (udpServer and udpClient running on host 127.0.0.1)   =
//=    Waiting for recvfrom() to complete...                                  =
//=    IP address of client = 127.0.0.1  port = 55476)                        =
//=    Received from client: Test message from CLIENT to SERVER               =
//=---------------------------------------------------------------------------=
//=  Build: bcc32 udpServer.c or cl udpServer.c wsock32.lib for Winsock       =
//=         gcc udpServer.c -lsocket -lnsl for BSD                            =
//=---------------------------------------------------------------------------=
//=  Execute: udpServer                                                       =
//=---------------------------------------------------------------------------=
//=  Author: Ken Christensen                                                  =
//=          University of South Florida                                      =
//=          WWW: http://www.csee.usf.edu/~christen                           =
//=          Email: christen@csee.usf.edu                                     =
//=---------------------------------------------------------------------------=
//=  History:  KJC (08/02/08) - Genesis (from server1.c)                      =
//=            KJC (09/07/09) - Minor clean-up                                =
//=            KJC (09/22/13) - Minor clean-up to fix warnings                =
//=============================================================================
#define  WIN                // WIN for Winsock and BSD for BSD sockets

//----- Include files --------------------------------------------------------
#include <stdio.h>          // Needed for printf()
#include <string.h>         // Needed for memcpy() and strcpy()
#include <stdlib.h>         // Needed for exit()
#include <time.h>
#include <fcntl.h>
#ifdef WIN
#include <windows.h>      // Needed for all Winsock stuff
#endif
#ifdef BSD
#include <sys/types.h>    // Needed for sockets stuff
#include <netinet/in.h>   // Needed for sockets stuff
#include <sys/socket.h>   // Needed for sockets stuff
#include <arpa/inet.h>    // Needed for sockets stuff
#include <fcntl.h>        // Needed for sockets stuff
#include <netdb.h>        // Needed for sockets stuff
#endif

//----- Defines --------------------------------------------------------------
#define  PORT_NUM   1050    // Arbitrary port number for the server

//Variables
struct listOfClients{
	struct sockaddr_in someClient;
	struct listOfClients *next;
	clock_t lastContactTime;
};

const char *REGISTER = "REGISTER";
const char *UNREGISTER = "UNREGISTER";
const char *REGISTERSUCCES = "REGISTERSUCCES";
const char *UNREGISTERSUCCESS = "UNREGISTERSUCCESS";
const char *NACK = "NACK";
const char *ACK = "ACK";
double packeLoss = 0.05;//expected packet loss

//===== Main program =========================================================
int main()
{
#ifdef WIN
	WORD wVersionRequested = MAKEWORD(1, 1);       // Stuff for WSA functions
	WSADATA wsaData;                              // Stuff for WSA functions
#endif
	char MESSAGE[10]; //this will be an increasing number later

	int                  server_s;        // Server socket descriptor
	struct sockaddr_in   server_addr;     // Server Internet address
	struct sockaddr_in   client_addr;     // Client Internet address
	struct in_addr       client_ip_addr;  // Client IP address
	int                  addr_len;        // Internet address length
	char                 out_buf[4096];   // Output buffer for data
	char                 in_buf[4096];    // Input buffer for data
	int                  retcode;         // Return code
	int					 sentMessageCounter = 0;

	_itoa(sentMessageCounter, MESSAGE, 10);
	//sprintf(&MESSAGE, "%d\0", sentMessageCounter);

	struct listOfClients *listOfClients = NULL;// = malloc(sizeof(struct listOfClients));
	listOfClients = malloc(sizeof(struct listOfClients));
	int numberOfClients = 0;
	struct listOfClients *listOfClientsIterator;
	clock_t messageTimer = clock();
	clock_t diffMessageTimer;
	clock_t staleConnectionTimer = clock();
	clock_t	diffStaleConnectionTimer;
	int INTERVAL = 10;//seconds
	int staleConnectionTimerINTERVAL = 20;//seconds
	int TIMEOUT = 30;//seconds
	srand(time(NULL));
	float r;


#ifdef WIN
	// This stuff initializes winsock
	WSAStartup(wVersionRequested, &wsaData);
#endif

	// >>> Step #1 <<<
	// Create a socket
	//   - AF_INET is Address Family Internet and SOCK_DGRAM is datagram
	server_s = socket(AF_INET, SOCK_DGRAM, 0);

	if (server_s < 0)
	{
		printf("*** ERROR - socket() failed \n");
		Sleep(3000);
		exit(-1);
	}

	// >>> Step #2 <<<
	// Fill-in my socket's address information
	server_addr.sin_family = AF_INET;                 // Address family to use
	server_addr.sin_port = htons(PORT_NUM);           // Port number to use
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);  // Listen on any IP address

#ifdef WIN
	//non blocking info on sockets
	//http://msdn.microsoft.com/en-us/library/windows/desktop/ms738573(v=vs.85).aspx
	int iResult;
	u_long iMode = 1;
	//-------------------------
	// Set the socket I/O mode: In this case FIONBIO
	// enables or disables the blocking mode for the 
	// socket based on the numerical value of iMode.
	// If iMode = 0, blocking is enabled; 
	// If iMode != 0, non-blocking mode is enabled.

	iResult = ioctlsocket(server_s, FIONBIO, &iMode);
	if (iResult != NO_ERROR)
		printf("ioctlsocket failed with error: %ld\n", iResult);


#endif

#ifdef BSD

	fcntl(server_addr, F_SETFL, O_NONBLOCK);  // set to non-blocking

#endif

	retcode = bind(server_s, (struct sockaddr *)&server_addr,
		sizeof(server_addr));
	if (retcode < 0)
	{
		printf("*** ERROR - bind() failed \n");
		Sleep(3000);
		exit(-1);
	}

	//This is where the protocol should start its idle state.
	while (1){
		//printf("numberOfClients: %d\n", numberOfClients);
		// >>> Step #3 <<<
		// Wait to receive a message from client
		// This is a blocking wait I may need a thread to always listen.

		addr_len = sizeof(client_addr);

		//MSDN recvfrom reference
		//http://msdn.microsoft.com/en-us/library/windows/desktop/ms740120(v=vs.85).aspx

		//Check for a message
		if (retcode = recvfrom(server_s, in_buf, sizeof(in_buf), 0, (struct sockaddr *)&client_addr, &addr_len) < 0){
			if (WSAGetLastError() != WSAEWOULDBLOCK){
				printf("recvfrom() failed. No incomming connections.");
				//exit(-1);
			}
			else
			{
				//printf(".");
				//printf("Still have not received packet...Waiting and then trying again\n");
				//Sleep(200);  /* Sleep for 2 milliseconds */
			}
		}

		//Receive REGISTER Message
		//--------------------------------------------->Idle
		//addToListOfClients()
		//send REGISTERED Message

		if (strcmp(in_buf, REGISTER) == 0){
			//// Copy the four-byte client IP address into an IP address structure
			printf("Register Message from ");
			memcpy(&client_ip_addr, &client_addr.sin_addr.s_addr, 4);
			printf("IP address of client = %s  port = %d) \n", inet_ntoa(client_ip_addr),
				ntohs(client_addr.sin_port));
			//printf("Adding client to list of registered clients.\n");
			//Add to the list of clients

			//printf("if check: %d\n", (numberOfClients > 1));

			//look for the client....
			int tempReg = 1;
			listOfClientsIterator = listOfClients;
			while (tempReg <= numberOfClients){
				if (inet_ntoa((&(listOfClientsIterator->someClient))->sin_addr) ==
					inet_ntoa(client_addr.sin_addr)
					//Need to check the port number also
					&& (&(listOfClientsIterator->someClient))->sin_port ==
					client_addr.sin_port){

					printf("This client has already registered.\n");
					listOfClientsIterator->lastContactTime = clock();
					tempReg = -1;
					break;
				}
				else{//keep looking
					listOfClientsIterator = listOfClientsIterator->next;//Go to the next client
				}
				tempReg++;
			}

			if (tempReg < 0){
				break;
			}

			//Add the client if not already added.
			if (numberOfClients > 0){
				//We need a new node.
				struct listOfClients *newClient = malloc(sizeof(struct listOfClients));
				//If more than one, we have to find the end of our linked list.
				//printf("We have more than one registered client\n");
				int temp = 1;
				listOfClientsIterator = listOfClients;//start at root
				while (temp < numberOfClients){
					//printf("On client %d\n", temp);
					listOfClientsIterator = listOfClientsIterator->next;
					temp++;
				}
				//printf("Second to last client found.\n");
				//need to have the next to last point to a new object

				//listOfClientsIterator->someClient = &client_addr;
				//add new node to end of list
				listOfClientsIterator->next = newClient;
				//Update new node
				listOfClientsIterator = listOfClientsIterator->next;
				
				listOfClientsIterator->someClient.sin_addr.S_un.S_addr = (client_addr.sin_addr.S_un.S_addr);
				listOfClientsIterator->someClient.sin_addr.S_un.S_un_b = client_addr.sin_addr.S_un.S_un_b;
				listOfClientsIterator->someClient.sin_addr.S_un.S_un_w = client_addr.sin_addr.S_un.S_un_w;

				listOfClientsIterator->someClient.sin_family = client_addr.sin_family;
				listOfClientsIterator->someClient.sin_port = client_addr.sin_port;

				//listOfClientsIterator->someClient = client_addr;

				listOfClientsIterator->next = NULL;
				listOfClientsIterator->lastContactTime = clock();
				printf("Client added.\n");

			}
			else{//For the case when there are no registered clients
				listOfClients = malloc(sizeof(struct listOfClients));
				listOfClients->someClient.sin_addr.S_un = client_addr.sin_addr.S_un;
				listOfClients->someClient.sin_family = client_addr.sin_family;
				listOfClients->someClient.sin_port = client_addr.sin_port;
				listOfClients->next = NULL;
				listOfClients->lastContactTime = clock();
				printf("1st Client added.\n");
			}
			numberOfClients = numberOfClients + 1;

			// Send to the client using the server socket
			strcpy(out_buf, REGISTERSUCCES);
			retcode = sendto(server_s, out_buf, (strlen(out_buf) + 1), 0,
				(struct sockaddr *)&client_addr, sizeof(client_addr));
			if (retcode < 0)
			{
				printf("*** ERROR - sendto() failed \n");
				Sleep(3000);
				exit(-1);
			}
			//strcpy(out_buf, NULL);
			//in_buf[0] = '\0';
			memset(&in_buf[0], 0, sizeof(in_buf));
			//printf("End of Register Message Block.\n");
		}


		//Receive UNREGISTER Message
		//--------------------------------------------->Idle
		//removeClient()
		//send UNREGISTERED Message
		if (strcmp(in_buf, UNREGISTER) == 0){
			printf("UnRegister Message from: ");
			memcpy(&client_ip_addr, &client_addr.sin_addr.s_addr, 4);
			printf("IP address of client = %s  port = %d) \n", inet_ntoa(client_ip_addr),
				ntohs(client_addr.sin_port));

			//Remove the client from the list of clients
			if (numberOfClients == 1){
				if (inet_ntoa((&(listOfClients->someClient))->sin_addr) ==
					inet_ntoa(client_addr.sin_addr)
					//Need to check the port number also
					&& (&(listOfClients->someClient))->sin_port ==
					client_addr.sin_port
					){
					//printf("We have found the only client to unregister.\n");
					listOfClients = NULL;
					//listOfClients->someClient = NULL;
					numberOfClients--;
				}
				//else case we ignore the unregister message
			}
			else if (numberOfClients > 2){
				//Start at the begining.
				struct listOfClients *previous = listOfClients;
				//Begining plus one
				listOfClientsIterator = listOfClients->next;
				int tempCount = 1;
				//loop through all of the clients looking for a match
				while (tempCount <= numberOfClients){
					if (tempCount == 1){//check the first node
						//lots of nodes starting at the begining.
						if (listOfClientsIterator->next != NULL && previous->next != NULL){
							if (inet_ntoa((&(previous->someClient))->sin_addr) ==
								inet_ntoa(client_addr.sin_addr)
								//Need to check the port number also
								&& (&(previous->someClient))->sin_port ==
								client_addr.sin_port
								){
								//printf("IP and Port number match.\n");
								//printf("First client in the list is getting unregistered.\n");
								listOfClients = previous->next;
								previous = listOfClients;
								listOfClientsIterator = previous->next;
								numberOfClients--;
								//don't increment temp because we are still on the first one.
							}
							else//Keep searching
								tempCount++;
						}
						else if (previous->next == NULL){
							//Single node left
							if (inet_ntoa((&(previous->someClient))->sin_addr) ==
								inet_ntoa(client_addr.sin_addr)
								//Need to check the port number also
								&& (&(previous->someClient))->sin_port ==
								client_addr.sin_port
								){
								previous = NULL;
								listOfClients = NULL;
								numberOfClients--;
							}
						}
						//only two node case
						else if (inet_ntoa((&(previous->someClient))->sin_addr) ==
							inet_ntoa(client_addr.sin_addr)
							//Need to check the port number also
							&& (&(previous->someClient))->sin_port ==
							client_addr.sin_port){
							//printf("IP and Port number match.\n");
							//printf("First client in the list is getting unregistered.\n");
							listOfClients = previous->next;

							//We have only one more client
							listOfClientsIterator = NULL;
							numberOfClients--;
							//don't increment temp because we are still on the first one.
						}
						else
							tempCount++;
					}
					//TempCount larger than one
					else
						if (inet_ntoa((&(listOfClientsIterator->someClient))->sin_addr) ==
							inet_ntoa(client_addr.sin_addr)
							//Need to check the port number also
							&& (&(listOfClientsIterator->someClient))->sin_port ==
							client_addr.sin_port){

						//printf("We have found the client to unregister.\n");
						//check to see if the current node is the last one
						if (tempCount < numberOfClients){
							previous->next = listOfClientsIterator->next;
							listOfClientsIterator = previous->next;
						}
						else{//last one in the linked list
							//deallocate last node
							//printf("UnRegistering last node in the list.\n");
							free(previous->next);
							previous->next = NULL;
						}
						numberOfClients--;
						}
						else{//keep searching
							previous = previous->next;
							listOfClientsIterator = listOfClientsIterator->next;
							tempCount++;//each loop we need to increment
						}
				}
			}
			// Send to the client using the server socket
			strcpy(out_buf, UNREGISTERSUCCESS);
			retcode = sendto(server_s, out_buf, (strlen(out_buf) + 1), 0,
				(struct sockaddr *)&client_addr, sizeof(client_addr));
			printf("Sent: ");
			printf(UNREGISTERSUCCESS);
			printf("\n");
			if (retcode < 0)
			{
				printf("*** ERROR - sendto() failed \n");
				Sleep(3000);
				//exit(-1);
			}
			memset(&in_buf[0], 0, sizeof(in_buf));
		}



		//Receive NACK Message
		//----------------------------------------------->Idle
		//sendMessageToNACKClient()
		if (strcmp(in_buf, NACK) == 0){
			printf("NACK received from ");
			memcpy(&client_ip_addr, &client_addr.sin_addr.s_addr, 4);
			printf("IP address of client = %s  port = %d) \n", inet_ntoa(client_ip_addr),
				ntohs(client_addr.sin_port));
			listOfClientsIterator = listOfClients;
			while (&(listOfClientsIterator->someClient) != NULL && numberOfClients > 0){
				if (inet_ntoa((&(listOfClientsIterator->someClient))->sin_addr) ==
					inet_ntoa(client_addr.sin_addr)
					//Need to check the port number also
					&& (&(listOfClientsIterator->someClient))->sin_port ==
					client_addr.sin_port){
					//printf("We have found the NACK client.\n");

					//update last contact time of client
					listOfClientsIterator->lastContactTime = clock();

					//send a Message to the client
					strcpy(out_buf, MESSAGE);
					retcode = sendto(server_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&client_addr, sizeof(client_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						//Sleep(3000);
						//exit(-1);
					}
					listOfClientsIterator = listOfClientsIterator->next;//Go to the next client
				}
				else{//keep looking
					listOfClientsIterator = listOfClientsIterator->next;//Go to the next client
				}
			}
			//in_buf[0] = '\0';
			memset(&in_buf[0], 0, sizeof(in_buf));
		}


		//Receive ACK Message
		//------------------------------------------------>Idle
		//refreshLastContactTimeOfClient()
		if (strcmp(in_buf, ACK) == 0){
			printf("ACK received from ");
			memcpy(&client_ip_addr, &client_addr.sin_addr.s_addr, 4);
			printf("IP address of client = %s  port = %d) \n", inet_ntoa(client_ip_addr),
				ntohs(client_addr.sin_port));
			listOfClientsIterator = listOfClients;
			int tempACK = 1;
			while (tempACK <= numberOfClients){
				if (inet_ntoa((&(listOfClientsIterator->someClient))->sin_addr) ==
					inet_ntoa(client_addr.sin_addr)
					//Need to check the port number also
					&& (&(listOfClientsIterator->someClient))->sin_port ==
					client_addr.sin_port){

					//printf("We have found the client who sent the ACK.\n");
					listOfClientsIterator->lastContactTime = clock();
					tempACK = numberOfClients;
				}
				else{//keep looking
					listOfClientsIterator = listOfClientsIterator->next;//Go to the next client
				}
				tempACK++;
			}
			//in_buf[0] = '\0';
			memset(&in_buf[0], 0, sizeof(in_buf));
		}



		//messageTimer Expires
		//--------------------------------------------->Idle
		//sendMessageToListOfClients()
		//resetMessageTimer()
		diffMessageTimer = (clock() - messageTimer) / CLOCKS_PER_SEC;

		if ((diffMessageTimer / INTERVAL) > 1){//timer expired
			sentMessageCounter++;
			_itoa(sentMessageCounter, MESSAGE, 10);
			printf("MESSAGE:");
			printf(MESSAGE);
			printf("\nMessageTimer expired\n");
			//Send to list of clients
			listOfClientsIterator = listOfClients;
			int temp = 1;
			if (numberOfClients > 0){
				while (temp <= numberOfClients){
					printf("Sending Messages to client %d ", temp);
					//printf("%d\n", listOfClientsIterator->someClient.sin_addr);

					memcpy(&client_addr.sin_addr, &listOfClientsIterator->someClient.sin_addr, sizeof(listOfClientsIterator->someClient.sin_addr));
					memcpy(&client_addr.sin_family, &listOfClientsIterator->someClient.sin_family, sizeof(listOfClientsIterator->someClient.sin_family));
					memcpy(&client_addr.sin_port, &listOfClientsIterator->someClient.sin_port, sizeof(listOfClientsIterator->someClient.sin_port));

					//client_addr.sin_addr.s_addr = listOfClientsIterator->someClient.sin_addr.s_addr;
					//client_addr.sin_family = listOfClientsIterator->someClient.sin_family;
					//client_addr.sin_port = listOfClientsIterator->someClient.sin_port;

					//// Copy the four-byte client IP address into an IP address structure
					memcpy(&client_ip_addr, &client_addr.sin_addr.s_addr, 4);
					printf("IP address of client = %s  port = %d) \n", inet_ntoa(client_ip_addr),
						ntohs(client_addr.sin_port));
					//printf("Copied client_addr\n");

					// Send to the client using the server socket
					strcpy(out_buf, MESSAGE);
					r = ((float)rand()) / ((float)RAND_MAX);
					//printf("Random number: %f\n", r);
					if (r > packeLoss){
						retcode = sendto(server_s, out_buf, (strlen(out_buf) + 1), 0,
							(struct sockaddr *)&(client_addr), sizeof(client_addr));
						printf("Sent: ");
						printf(out_buf);
						printf("\n");
						if (retcode < 0)
						{
							printf("*** ERROR - sendto() failed \n");
							//Sleep(3000);
							//exit(-1);
						}
					}//No packet loss
					else{
						printf("Lost Packet.\n");
					}
					listOfClientsIterator = listOfClientsIterator->next;//Go to the next client
					temp++;
					//Sleep(1000);//Maybe we are going to fast for the send operation.
				}
				//out_buf[0] = '\0';
				memset(&out_buf[0], 0, sizeof(out_buf));
			}
			else{
				printf("No registered clients\n");
			}
			messageTimer = clock();//reset timer
		}


		//StaleConnection Timer Expires
		//------------------------------------------------>Idle
		//removeClient(getClientsWithLastTimeGreaterThan(timeout))
		diffStaleConnectionTimer = (clock() - staleConnectionTimer) / CLOCKS_PER_SEC;
		if ((diffStaleConnectionTimer / staleConnectionTimerINTERVAL) > 1){
			printf("StaleConnectionTimer expired.\n");
			//Taken from UnRegister section....
			//Remove the client from the list of clients
			if (numberOfClients == 1){
				if (
					(clock() - listOfClients->lastContactTime) / CLOCKS_PER_SEC > TIMEOUT
					){
					printf("We have found the only stale client.\n");
					listOfClients = NULL;
					//listOfClients->someClient = NULL;
					numberOfClients--;
				}
				//else case we ignore the unregister message
			}
			else if (numberOfClients > 2){
				//Start at the begining.
				struct listOfClients *previous = listOfClients;
				//Begining plus one
				listOfClientsIterator = listOfClients->next;
				int tempCount = 1;
				//loop through all of the clients looking for a match
				while (tempCount <= numberOfClients){
					if (tempCount == 1){//check the first node
						//lots of nodes starting at the begining.
						if (listOfClientsIterator != NULL &&listOfClientsIterator->next != NULL && previous->next != NULL){
							if (
								(clock() - previous->lastContactTime) / CLOCKS_PER_SEC > TIMEOUT
								){
								//printf("IP and Port number match.\n");
								printf("First client in the list is stale.\n");
								listOfClients = previous->next;
								previous = listOfClients;
								listOfClientsIterator = previous->next;
								numberOfClients--;
								//don't increment temp because we are still on the first one.
							}
							else//Keep searching
								tempCount++;
						}
						else if (previous->next == NULL){
							//Single node left
							if ((clock() - previous->lastContactTime) / CLOCKS_PER_SEC > TIMEOUT

								){
								printf("Last client is stale.\n");
								previous = NULL;
								listOfClients = NULL;
								numberOfClients--;
							}
						}
						//only two node case
						else if (
							(clock() - previous->lastContactTime) / CLOCKS_PER_SEC > TIMEOUT
							){
							//printf("IP and Port number match.\n");
							printf("Found a stale client.\n");
							listOfClients = previous->next;

							//We have only one more client
							listOfClientsIterator = NULL;
							numberOfClients--;
							//don't increment temp because we are still on the first one.
						}
						else
							tempCount++;
					}
					//TempCount larger than one
					else
						if (
							(clock() - listOfClientsIterator->lastContactTime) / CLOCKS_PER_SEC > TIMEOUT
							){

						printf("We have found the a stale client.\n");
						//check to see if the current node is the last one
						if (tempCount < numberOfClients){
							previous->next = listOfClientsIterator->next;
							listOfClientsIterator = previous->next;
						}
						else{//last one in the linked list
							//deallocate last node
							printf("UnRegistering last node in the list is stale.\n");
							free(previous->next);
							previous->next = NULL;
						}
						numberOfClients--;
						}
						else{//keep searching
							previous = previous->next;
							listOfClientsIterator = listOfClientsIterator->next;
							tempCount++;//each loop we need to increment
						}

				}
			}

			//reset Timer
			staleConnectionTimer = clock();
		}//end staleConnectionTimer Section


	}//end idle loop



	// >>> Step #5 <<<
	// Close all open sockets
#ifdef WIN
	retcode = closesocket(server_s);
	if (retcode < 0)
	{
		printf("*** ERROR - closesocket() failed \n");
		Sleep(3000);
		exit(-1);
	}
#endif
#ifdef BSD
	retcode = close(server_s);
	if (retcode < 0)
	{
		printf("*** ERROR - close() failed \n");
		Sleep(3000);
		exit(-1);
	}
#endif

#ifdef WIN
	// This stuff cleans-up winsock
	WSACleanup();
#endif

	// Return zero and terminate
	Sleep(10000);
	return(0);
}
