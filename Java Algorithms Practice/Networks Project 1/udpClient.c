//Winfrey, Joshua
//George, Hebron
//Project 1
//09/26/2014



//==================================================== file = udpClient.c =====
//=  A message "client" program to demonstrate sockets programming            =
//=============================================================================
//=  Notes:                                                                   =
//=    1) This program conditionally compiles for Winsock and BSD sockets.    =
//=       Set the initial #define to WIN or BSD as appropriate.               =
//=    2) This program needs udpServer to be running on another host.         =
//=       Program udpServer must be started first.                            =
//=    3) This program assumes that the IP address of the host running        =
//=       udpServer is defined in "#define IP_ADDR"                           =
//=    4) The steps #'s correspond to lecture topics.                         =
//=---------------------------------------------------------------------------=
//=  Example execution: (udpServer and udpClient running on host 127.0.0.1)   =
//=    Received from server: This is a reply message from SERVER to CLIENT    =
//=---------------------------------------------------------------------------=
//=  Build: bcc32 ucpClient.c or cl udpClient.c wsock32.lib for Winsock       =
//=         gcc udpClient.c -lsocket -lnsl for BSD                            =
//=---------------------------------------------------------------------------=
//=  Execute: udpClient                                                       =
//=---------------------------------------------------------------------------=
//=  Author: Ken Christensen                                                  =
//=          University of South Florida                                      =
//=          WWW: http://www.csee.usf.edu/~christen                           =
//=          Email: christen@csee.usf.edu                                     =
//=---------------------------------------------------------------------------=
//=  History:  KJC (08/02/08) - Genesis (from client.c)                       =
//=            KJC (09/09/09) - Minor clean-up                                =
//=            KJC (09/22/13) - Minor clean-up to fix warnings                =
//=============================================================================
#define  WIN                // WIN for Winsock and BSD for BSD sockets

//----- Include files ---------------------------------------------------------
#include <stdio.h>          // Needed for printf()
#include <string.h>         // Needed for memcpy() and strcpy()
#include <stdlib.h>         // Needed for exit()
#include <time.h>
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

//----- Defines ---------------------------------------------------------------
#define  PORT_NUM           1050  // Port number used
#define  IP_ADDR      "127.0.0.1" // IP address of server1 (*** HARDWIRED ***)

//===== Main program ==========================================================
int main()
{
#ifdef WIN
	WORD wVersionRequested = MAKEWORD(1, 1);       // Stuff for WSA functions
	WSADATA wsaData;                              // Stuff for WSA functions
#endif
	int                  client_s;        // Client socket descriptor
	struct sockaddr_in   server_addr;     // Server Internet address
	int                  addr_len;        // Internet address length
	char                 out_buf[4096];   // Output buffer for data
	char                 in_buf[4096];    // Input buffer for data
	int                  retcode;         // Return code

	enum  CLIENTSTATES {
		unreg,	//1 = unregistered state
		penreg,	//2 = PendingRegistration state
		reg,		//3 = Registered State
		nackpend, //4 = NACK Pending State
		unregpend	//5 = Pending Unregistered state
	};

#ifdef WIN
	// This stuff initializes winsock
	WSAStartup(wVersionRequested, &wsaData);
#endif

	//Variables
	const char *REGISTER = "REGISTER";
	const char *REGISTERSUCCES = "REGISTERSUCCES";

	const char *UNREGISTER = "UNREGISTER";
	const char *UNREGISTERSUCCESS = "UNREGISTERSUCCESS";

	const char *NACK = "NACK";
	const char *ACK = "ACK";

	enum CLIENTSTATES currentState;

	int roundTripTime = 1;// one second. This is an approximation of 2 * t_fr

	clock_t RegisterRetryTimer = clock();
	clock_t RegisterRetryTimerDiff;
	int RegisterRetryTimerINTERVAL = roundTripTime;//seconds
	int RegisterRetryCount = 0;
	int RegisterRetryCountMax = 3;

	clock_t MessageTimer = clock();
	clock_t MessageTimerDiff;
	int MessageTimerINTERVAL = 10 + roundTripTime; // seconds. This is the amount given in the requirements.


	clock_t retryTimer = clock();
	clock_t retryTimerDiff;
	int retryTimerINTERVAL = roundTripTime; // one second. This is an approximation of 2 * t_fr
	int retryTimerCount = 0;
	int retryTimerCountMax = 3;

	clock_t UnregisterTimer = clock();
	clock_t UnregisterTimerDiff;
	int UnregisterCount = 0;
	int UnregisterCountMax = 3;
	int UnregisterTimerINTERVAL = roundTripTime;



	// >>> Step #1 <<<
	// Create a socket
	//   - AF_INET is Address Family Internet and SOCK_DGRAM is datagram
	client_s = socket(AF_INET, SOCK_DGRAM, 0);
	if (client_s < 0)
	{
		printf("*** ERROR - socket() failed \n");
		Sleep(3000);
		exit(-1);
	}

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

	iResult = ioctlsocket(client_s, FIONBIO, &iMode);
	if (iResult != NO_ERROR)
		printf("ioctlsocket failed with error: %ld\n", iResult);


#endif

#ifdef BSD

	fcntl(server_addr, F_SETFL, O_NONBLOCK);  // set to non-blocking

#endif


	// >>> Step #2 <<<
	// Fill-in server1 socket's address information
	server_addr.sin_family = AF_INET;                 // Address family to use
	server_addr.sin_port = htons(PORT_NUM);           // Port num to use
	server_addr.sin_addr.s_addr = inet_addr(IP_ADDR); // IP address to use


	// >>> Step #3 <<<
	// Now send the message to server.  The "+ 1" is for the end-of-string
	// delimiter

	//reset
	currentState = unreg;

	while (1){
		if (currentState == unreg){
			printf("Current State: Unregistered.\n");
			//RegisterTimer Expires
			//-------------------------- > Pending Registration
			//send REGISTER Message
			//reset RegisterRetryTimer
			RegisterRetryTimerDiff = (clock() - RegisterRetryTimer) / CLOCKS_PER_SEC;
			if (RegisterRetryTimerDiff / RegisterRetryTimerINTERVAL > 1){//timer expired
				printf("RegisterTimer Expired.\n");
				// Assign a message to buffer out_buf
				strcpy(out_buf, REGISTER);//A Register Message

				printf("Sent:");
				printf(out_buf);
				printf("\n");
				retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
					(struct sockaddr *)&server_addr, sizeof(server_addr));
				if (retcode < 0)
				{
					printf("*** ERROR - sendto() failed \n");
					Sleep(3000);
					//exit(-1);
				}
				RegisterRetryTimer = clock();//reset timer

				//unreg- > penreg
				currentState = penreg;
				//break; //Doing this breaks our main loop....Not good.
			}//end if for timer
		}//end if unreg state

		if (currentState == penreg){
			printf("Current State: Pending Registration\n");
			//PendingRegistion State
			//Reset the following variables when entering this state:
			//clock_t RegisterRetryTimer = clock();
			//clock_t RegisterRetryTimerDiff;
			//int RegisterRetryTimerINTERVAL = roundTripTime;//seconds
			//int RegisterRetryCount = 0;
			//int RegisterRetryCountMax = 3;
			RegisterRetryTimer = clock();
			RegisterRetryCount = 0;


			//Loop till state change.....
			while (currentState == penreg){
				// Receive REGISTERED Message
				// ------------------------------------------------------>Registered
				// reset MessageTimer
				// reset RetryCount

				//non-blocking check for input messages.
				//Check for a message
				addr_len = sizeof(server_addr);
				//in_buf[0] = '\0';
				memset(&in_buf[0], 0, sizeof(in_buf));
				if (retcode = recvfrom(client_s, in_buf, sizeof(in_buf), 0, (struct sockaddr *)&server_addr, &addr_len) < 0){
					if (WSAGetLastError() != WSAEWOULDBLOCK){
						//printf("recvfrom() failed. No incomming connections.");
						printf("");
						//exit(-1);
					}
					else
					{
						//printf(".");
						//printf("Still have not received packet...Waiting and then trying again\n");
						//Sleep(200);  /* Sleep for 2 milliseconds */
					}
				}//end recvfrom
				if (strcmp(in_buf, REGISTERSUCCES) == 0){
					printf("Received from server: %s \n", in_buf);
					//We have found a match
					RegisterRetryTimer = clock();
					RegisterRetryCount = 0;
					currentState = reg;
					break;
				}


				// RegisterRetryTimer expires
				// ------------------------------------------------------>PendingRegistration
				// send REGISTER message
				// increment RegisterRetryCount
				// Reset RegisterRetryTimer
				RegisterRetryTimerDiff = (clock() - RegisterRetryTimer) / CLOCKS_PER_SEC;
				if (RegisterRetryTimerDiff / RegisterRetryTimerINTERVAL > 1){//timer expired
					printf("RegisterTimer Expired.\n");
					// Assign a message to buffer out_buf
					strcpy(out_buf, REGISTER);//A Register Message

					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}
					RegisterRetryTimer = clock();//reset timer
					RegisterRetryCount++;
				}//end RegistryTimer expired section


				// RegisterRetryCount == RegisterRetryCountMax
				// ------------------------------------------------------->UnRegistered
				// reset registerTimer
				// reset registerRetryCount
				if (RegisterRetryCount >= RegisterRetryCountMax){
					RegisterRetryTimer = clock();
					RegisterRetryCount = 0;
					currentState = unreg;
					printf("Unable to register with %d retries.\n", RegisterRetryCountMax);
					break;
				}//end max count reached

			}//end loop of penreg state
		}//end if penreg state


		if (currentState == reg){
			printf("Current State: Registered.\n");
			//Reset when get in state.
			MessageTimer = clock();

			//loop looking for each of these.
			while (currentState = reg){

				//Receive MESSAGE
				//---------------------------------------------------------->Registered State
				//deliver_Data(MESSAGE)
				//reset MessageTimer
				//send ACK
				addr_len = sizeof(server_addr);
				//in_buf[0] = '\0';//clear buffer
				memset(&in_buf[0], 0, sizeof(in_buf));
				if (retcode = recvfrom(client_s, in_buf, sizeof(in_buf), 0, (struct sockaddr *)&server_addr, &addr_len) < 0){
					if (WSAGetLastError() != WSAEWOULDBLOCK){
						//printf("recvfrom() failed. No incomming connections.");
						printf(".");
						Sleep(200);
						//exit(-1);
					}
					else
					{
						//printf(".");
						//printf("Still have not received packet...Waiting and then trying again\n");
						//Sleep(200);  /* Sleep for 2 milliseconds */
					}
				}//end recvfrom

				if (in_buf[0] != '\0'){
					MessageTimer = clock();
					//We got something.
					// Output the received message
					printf("Received from server: %s \n", in_buf);
					in_buf[0] = '\0';//clear buffer

					retryTimer = clock();
					//send ACK
					strcpy(out_buf, ACK);//An UnRegister Message
					//send UnregisterMessage
					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}
					
				}

				//MessageTimer expires
				//-------------------------------------------------------->NACK Pending
				//send NACK
				//reset retryTimer()
				MessageTimerDiff = (clock() - MessageTimer) / CLOCKS_PER_SEC;
				if (MessageTimerDiff / MessageTimerINTERVAL > 1){
					//Send a NACK
					strcpy(out_buf, NACK);
					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}
					currentState = nackpend;
					retryTimer = clock();
					retryTimerCount = 0;
					break;
				}//end of timer check



			}//end reg loop
		}//end reg state


		if (currentState == nackpend){
			printf("Current State: NackPending.\n");
			//NACK Pending State
			//The following variables should be reset when entering this state:
			//
			//clock_t retryTimer = clock();
			//clock_t retryTimerDiff;
			//int retryTimerINTERVAL = roundTripTime; // one second. This is an approximation of 2 * t_fr
			//int retryTimerCount = 0;
			//int retryTimerCountMax = 3;
			retryTimer = clock();
			retryTimerCount = 0;

			//Loop through these
			while (currentState == nackpend){
				//	retryTimer expires
				//	-------------------------------------------------------->NACK Pending
				//	send NACK
				//	increment retryCount

				retryTimerDiff = (clock() - retryTimer) / CLOCKS_PER_SEC;
				if (retryTimerDiff / retryTimerINTERVAL > 1){
					//send NACK
					strcpy(out_buf, NACK);//An UnRegister Message
					//send UnregisterMessage
					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}
					retryTimer = clock();
					retryTimerCount++;
				}//end retryTimer section



				//	retryCount == retryCountMax
				//	-------------------------------------------------------->Pending Unregistered
				//	reset MessageTimer
				//	reset RetryCount
				//	reset UnRegisterTimer
				//	reset UnRegisterCount
				if (retryTimerCount >= retryTimerCountMax){
					//Max retries for MESSAGE reached.
					MessageTimer = clock();
					retryTimerCount = 0;
					UnregisterTimer = clock();
					UnregisterCount = 0;
					currentState = unregpend;
					break;
				}



				//	Receive MESSAGE
				//	--------------------------------------------------------->Registered State
				//	reset MessageTimer
				//	reset RetryCount
				//	send ACK
				//in_buf[0] = '\0';//clear buffer
				memset(&in_buf[0], 0, sizeof(in_buf));
				addr_len = sizeof(server_addr);
				if (retcode = recvfrom(client_s, in_buf, sizeof(in_buf), 0, (struct sockaddr *)&server_addr, &addr_len) < 0){
					if (WSAGetLastError() != WSAEWOULDBLOCK){
						//printf("recvfrom() failed. No incomming connections.");
						printf(".");
						//exit(-1);
					}
					else
					{
						//printf(".");
						//printf("Still have not received packet...Waiting and then trying again\n");
						//Sleep(200);  /* Sleep for 2 milliseconds */
					}
				}//end recvfrom

				if (in_buf[0] != '\0'){
					//We got something.
					// Output the received message
					printf("Received from server: %s \n", in_buf);
					in_buf[0] = '\0';//clear buffer
					

					//send ACK
					strcpy(out_buf, ACK);//An UnRegister Message
					//send UnregisterMessage
					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}
					MessageTimer = clock();
					retryTimerCount = 0;
					currentState = reg;
					break; 
				}//end if Message received section
			}//end nackpend loop
		}//end nackpend state


		if (currentState == unregpend){
			printf("Current State: Unregistration Pending.\n");
			//loop through state
			while (currentState == unregpend){
				//UnRegisterTimer Expires
				//	------------------------------------------------------->Pending UnRegister State
				//	send UNREGISTER
				//	increment unRegisterRetryCount
				UnregisterTimerDiff = (clock() - UnregisterTimer) / CLOCKS_PER_SEC;
				if (UnregisterTimerDiff / UnregisterTimerINTERVAL > 1){
					strcpy(out_buf, UNREGISTER);//An UnRegister Message
					//send UnregisterMessage
					printf("Sent:");
					printf(out_buf);
					printf("\n");
					retcode = sendto(client_s, out_buf, (strlen(out_buf) + 1), 0,
						(struct sockaddr *)&server_addr, sizeof(server_addr));
					if (retcode < 0)
					{
						printf("*** ERROR - sendto() failed \n");
						Sleep(3000);
						//exit(-1);
					}

					UnregisterCount++;
				}//end timer check

				//	Receive UNREGISTERED Message
				//	------------------------------------------------------->UnRegistered State
				//	reset registerTimer
				//	reset UnRegisterCount
				//in_buf[0] = '\0';//clear buffer
				memset(&in_buf[0], 0, sizeof(in_buf));
				addr_len = sizeof(server_addr);
				if (retcode = recvfrom(client_s, in_buf, sizeof(in_buf), 0, (struct sockaddr *)&server_addr, &addr_len) < 0){
					if (WSAGetLastError() != WSAEWOULDBLOCK){
						//printf("recvfrom() failed. No incomming connections.");
						printf(".");
						//exit(-1);
					}
					else
					{
						//printf(".");
						//printf("Still have not received packet...Waiting and then trying again\n");
						//Sleep(200);  /* Sleep for 2 milliseconds */
					}
				}//end recvfrom
				if (strcmp(in_buf, UNREGISTERSUCCESS) == 0){
					// Output the received message
					printf("Received from server: %s \n", in_buf);
					currentState = unreg;
					break;
				}//end checking for unregistersucces message.


				//	UnRegisterCount == UnRegisterCountMax
				//	-------------------------------------------------------->UnRegistered State
				//	reset registerTimer
				//	reset UnRegisterCount

				if (UnregisterCount == UnregisterCountMax){
					//Give up, the server is not responding.
					RegisterRetryTimer = clock();
					RegisterRetryCount = 0;
					UnregisterCount = 0;
					currentState = unreg;
					break;
				}


			}//end loop of state
		}//end unregpend State
	}//end of all of the states


		printf("SHUTING DOWN\N");
		Sleep(10000);



		// >>> Step #5 <<<
		// Close all open sockets
#ifdef WIN
		retcode = closesocket(client_s);
		if (retcode < 0)
		{
			printf("*** ERROR - closesocket() failed \n");
			Sleep(3000);
			exit(-1);
		}
#endif
#ifdef BSD
		retcode = close(client_s);
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
		return(0);
	}
