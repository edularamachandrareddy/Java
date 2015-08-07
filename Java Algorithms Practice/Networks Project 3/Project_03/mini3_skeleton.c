//================================================ file = mini3_skeleton.c =====
//=  Skeleton for miniproject #3 for fall 2014 - Students should use this      =
//==============================================================================
//=  Notes:                                                                    =
//=----------------------------------------------------------------------------=
//=  Example execution:                                                        =
//=----------------------------------------------------------------------------=
//=  Build:                                                                    =
//=----------------------------------------------------------------------------=
//=  Execute:                                                                  =
//=----------------------------------------------------------------------------=
//=  Author:                                                                   =
//=----------------------------------------------------------------------------=
//=  History:                                                                  =
//==============================================================================
//----- Includes ---------------------------------------------------------------
#include "csim.h"       // Needed for CSIM stuff
// Add additional as needed
#include <math.h>		// Used for the power function.
#include <stdbool.h>	// I like boolean values.
#include <stdlib.h> 	// atof

//----- Defines ----------------------------------------------------------------
#define    SIM_TIME       3600.0  // Total simulation time in seconds
#define     PKT_LEN         1500  // Packet length in bytes
#define SAMPLE_TIME     100.0e-3  // Link hold time in seconds
#define      THRESH       1.0e-3  // Delay threshold in seconds
#define  LINK1_RATE      100.0e6  // Data rate of link #1
#define  LINK2_RATE        1.0e9  // Data rate of link #2
#define LINK1_POWER          1.0  // Power draw of link #1
#define LINK2_POWER         10.0  // Power draw of link #2
// Add additional as needed
//#define COV					2.0
#define COV_ALMOST_ONE       1.000000001;
#define MIN_ON_TIME			 0.1

//----- Globals ----------------------------------------------------------------
FACILITY Server1;       // Declaration of CSIM Server facility #1
FACILITY Server2;       // Declaration of CSIM Server facility #2
TABLE    DelayTable;    // Delay table
// Add additional as needed
TABLE    PowerDrawTable;
TABLE    DelayTableSampled;
TABLE    Q_LENGTH_SERVER1;
TABLE    Q_LENGTH_SERVER2;
TABLE    Q_LENGTH_TOTAL;
TABLE    Q_LENGTH_TOTAL_SAMPLED;
TABLE    Q_LENGTH_SERVER1_SAMPLED;
TABLE    Q_LENGTH_SERVER2_SAMPLED;
TABLE    UTILIZATION_TOTAL;
TABLE    UTILIZATION_TOTAL_SAMPLED;
TABLE    UTILIZATION_SERVER1;
TABLE    UTILIZATION_SERVER2;
TABLE    UTILIZATION_SERVER1_SAMPLED;
TABLE    UTILIZATION_SERVER2_SAMPLED;

int      PktCount;
int      OverCount;
double   mu1;          // Mean service rate (cust/sec)
double   mu2;          // Mean service rate (cust/sec)
double   COV;
double   OFFERED_LOAD_PERCENTAGE;
int      count;
double   onTime;
bool     isSwitchable;
int      currentLink;




//----- Prototypes -------------------------------------------------------------
void generate(double lambda, double cov);     // Packet generator
void linkDecision();		  // Link decision
void queue1(double service_time, double orgTime);      // Single server queue for link #1
void queue2(double service_time, double orgTime);      // Single server queue for link #2
// Add additional as needed
void collectStatistics(double orgTime, double powerDraw);

//==============================================================================
//==  Main program                                                            ==
//==============================================================================
void sim(int argc, char *argv[])
{
	// Your code here
	double   lambda;      // Mean arrival rate (cust/sec)
	double   rho;         // Theoretical utilization
	/* Maximum rates possible for the outgoing links. The units for this are
	*  Packets per second. The formula for offered load or utilization is 1/lambda
	*  This translate to rate/packet_length_in_Bits.  	*/
	double   offered_load_link1_100 = LINK1_RATE / (PKT_LEN * 8);
	double   offered_load_link2_100 = LINK2_RATE / (PKT_LEN * 8);

	//Collect arguments from the command line, offered load and cov values.
	//int i;
	//for (i = 1; i < argc; i++) {
	//first variable is the offered load
	double result = atof(argv[1]);
	if (result != 0 && result != HUGE_VAL){
		if (result <= 0.15 && result >= 0.01){
			OFFERED_LOAD_PERCENTAGE = result;
		}
		else{
			printf("Offered load value is outside of the range from 1%% to 15%%. Offered load is set to 1%%.\n");
			OFFERED_LOAD_PERCENTAGE = 0.01;
		}
	}
	else{
		if (result != 0){
			OFFERED_LOAD_PERCENTAGE = result;
		}
		else{
			printf("Incorrect value. Use a double next time. Offered load is set to 1%%.\n");
			OFFERED_LOAD_PERCENTAGE = 0.01;
		}
	}

	//second variable is the cov value
	result = atof(argv[2]);
	if (result != 0 && result != HUGE_VAL){
		if (result != 1.0){
			COV = result;
		}
		else{
			printf("COV has been set to almost one.\n");
			COV = COV_ALMOST_ONE;
		}
	}
	else{
		printf("ERROR: COV has been set to almost one.\n");
		COV = COV_ALMOST_ONE;
	}
	//}

	// Create the simulation
	create("sim");

	// CSIM initializations
	Server1 = facility("Server1");
	Server2 = facility("Server2");
	DelayTable = table("DelayTable");
	PowerDrawTable = table("PowerDrawTable");
	DelayTableSampled = table("DelayTableSampled");
	table_moving_window(DelayTableSampled, SAMPLE_TIME);
	Q_LENGTH_SERVER1 = table("Q_LENGTH_SERVER1");
	Q_LENGTH_SERVER2 = table("Q_LENGTH_SERVER2"); 
	Q_LENGTH_SERVER1_SAMPLED = table("Q_LENGTH_SERVER1_SAMPLED");
	table_moving_window(Q_LENGTH_SERVER1_SAMPLED, SAMPLE_TIME);
	Q_LENGTH_SERVER2_SAMPLED = table("Q_LENGTH_SERVER2_SAMPLED");
	table_moving_window(Q_LENGTH_SERVER2_SAMPLED, SAMPLE_TIME);
	Q_LENGTH_TOTAL = table("Q_LENGTH_TOTAL");
	Q_LENGTH_TOTAL_SAMPLED = table("Q_LENGTH_TOTAL_SAMPLED");
	table_moving_window(Q_LENGTH_TOTAL_SAMPLED, SAMPLE_TIME);
	UTILIZATION_TOTAL = table("UTILIZATION_TOTAL");
	UTILIZATION_TOTAL_SAMPLED = table("UTILIZATION_TOTAL_SAMPLED");
	UTILIZATION_SERVER1 = table("UTILIZATION_SERVER1");
	UTILIZATION_SERVER2 = table("UTILIZATION_SERVER2");
	UTILIZATION_SERVER1_SAMPLED = table("UTILIZATION_SERVER1_SAMPLED");
	table_moving_window(UTILIZATION_SERVER1_SAMPLED, SAMPLE_TIME);
	UTILIZATION_SERVER2_SAMPLED = table("UTILIZATION_SERVER2_SAMPLED");
	table_moving_window(UTILIZATION_SERVER2_SAMPLED, SAMPLE_TIME);


	// Parameter initializations
	lambda = OFFERED_LOAD_PERCENTAGE * offered_load_link2_100;	//Testing 1% offered load.
	mu1 = offered_load_link1_100;				//testing...
	mu2 = offered_load_link2_100;				//testing...
	PktCount = 0;
	OverCount = 0;
	count = 0;
	onTime = clock;
	currentLink = 2;

	// Output begin-of-simulation banner
	printf("*** BEGIN SIMULATION *** \n");

	// Initiate generate function and hold for SIM_TIME
	generate(lambda, COV);
	hold(SIM_TIME);


	// Compute theoretical utilization
	rho = lambda / mu1;

	// Output results
	printf("============================================================= \n");
	printf("==  CSIM dual links simulation (miniproject #3 fall 2014)  == \n");
	printf("============================================================= \n");
	printf("= Offered load       = %f %%       \n", 1.0);//Using Theoretical at the moment.
	printf("= Cov                = %f          \n", COV);
	printf("= Sampling time      = %f ms       \n", SAMPLE_TIME);
	printf("= Delay threshold    = %f ms       \n", (THRESH * 1000));
	printf("============================================================= \n");
	printf("= Total CPU time     = %f s        \n", cputime());//from mm1_csim.c
	printf("= Total sim time     = %f s        \n", clock);//from mm1_csim.c
	printf("= Total completions  = %d pkt      \n", (completions(Server1) + completions(Server2)));
	printf("=------------------------------------------------------------ \n");
	printf("=- Overall results                                         -- \n");
	printf("=------------------------------------------------------------ \n");
	printf("= Mean delay         = %f ms      \n", table_mean(DelayTable) * 1000.0);	//seconds to ms
	printf("= Over delay thresh  = %f %%      \n", ((double)OverCount / (double)PktCount));
	printf("= Average power draw = %f W       \n", table_sum(PowerDrawTable) / table_sum(DelayTable));		//Try the sum instead of mean.
	printf("=------------------------------------------------------------ \n");
	printf("=- Results for Link #1 (100 Mb/s)                          -- \n");
	printf("=------------------------------------------------------------ \n");
	printf("= Completions        = %l pkt      \n", completions(Server1));
	printf("= Utilization        = %f %%       \n", 100.0 * util(Server1));
	printf("= Mean num in system = %f pkt      \n", qlen(Server1));
	printf("= Mean response time = %f ms       \n", (resp(Server1) * 1000.0));	//seconds to ms
	printf("= Mean service time  = %f ms       \n", (serv(Server1) * 1000.0));	//seconds to ms
	printf("= Mean throughput    = %f pkt/s    \n", tput(Server1));
	printf("=------------------------------------------------------------ \n");
	printf("=- Results for Link #2 (1 Gb/s)                            -- \n");
	printf("=------------------------------------------------------------ \n");
	printf("= Completions        = %l pkt      \n", completions(Server2));
	printf("= Utilization        = %f %%       \n", 100.0 * util(Server2));
	printf("= Mean num in system = %f pkt      \n", qlen(Server2));
	printf("= Mean response time = %f ms       \n", (resp(Server2)* 1000.0));	//seconds to ms
	printf("= Mean service time  = %f ms       \n", (serv(Server2)* 1000.0));	//seconds to ms
	printf("= Mean throughput    = %f pkt/s    \n", tput(Server2));
	/*
	printf("=------------------------------------------------------------ \n");
	printf("=- Variables for Debugging                                 -- \n");
	printf("=------------------------------------------------------------ \n");
	printf("=  lambda            = %f pkt/second\n", lambda);
	printf("=  mu1               = %f pkt/second\n", mu1);
	printf("=  mu2               = %f pkt/second\n", mu2);
	*/

	//report(DelayTable);
	//report(PowerDrawTable);

	printf("============================================================= \n");
}

//==============================================================================
//==  Process to generate packets                                             ==
//==============================================================================
void generate(double lambda, double cov)
{
	// Your code here
	double   interarrival_time;    // Interarrival time to next send
	//double   service_time;         // Service time for this customer

	cov = 1.00000000001;//testing Poisson at the moment.

	create("generate");

	// Loop forever to create customers
	while (1)
	{
		// Pull an exponentially distributed interarrival time and hold for it
		//interarrival_time = exponential(1.0 / lambda);
		interarrival_time = hyperx((1.0 / lambda), pow((cov / lambda), 2.0));

		hold(interarrival_time);
		linkDecision();
	}
}

//==============================================================================
//==  Process for making link decision                                        ==
//==============================================================================
void linkDecision()
{
	// Your code here
	create("linkDecision");

	double service_time;
	double cov = 1.00000000001;

	//Determine if switchable
	if ((clock - onTime) >= MIN_ON_TIME){
		isSwitchable = true;
	}


	//if Switchable 
	if (isSwitchable){

		//Consider switching links if need be.

		//count = 0;

		/* The instructions state that the switch should be able to smartly
		*  use the slow link when all that is offered load can fit on it.
		*
		*/
		if (table_mean(DelayTableSampled) < THRESH &&
			table_mean(UTILIZATION_SERVER1_SAMPLED) <= table_mean(UTILIZATION_TOTAL) &&
			table_mean(Q_LENGTH_SERVER1_SAMPLED) <= table_mean(Q_LENGTH_SERVER1) &&
			table_sum(Q_LENGTH_SERVER1_SAMPLED) <= 1250000 &&
			table_mean(Q_LENGTH_TOTAL_SAMPLED) <= table_mean(Q_LENGTH_TOTAL)
			){

			//if switching up date the clock with a new time and currentLink.
			if (currentLink == 1){
				//no switching is happening.
			}
			else{
				//update with new on time
				currentLink = 1;
				onTime = clock;
			}

			// Pull an exponential service time and then send the customer to the queue
			service_time = hyperx((1.0 / mu1), pow((cov / mu1), 2.0));
			//service_time = exponential(1.0 / mu1);
			queue1(service_time, clock);
		}
		else{
			
			//if switching up date the clock with a new time and currentLink.
			if (currentLink == 2){
				//no switching is happening.
			}
			else{
				//update with new on time
				currentLink = 2;
				onTime = clock;
			}

			
			// Pull an exponential service time and then send the customer to the queue
			service_time = hyperx((1.0 / mu2), pow((cov / mu2), 2.0));
			//service_time = exponential(1.0 / mu1);
			queue2(service_time, clock);

		}
	}
	//else stay with last option.
	else{
		if (currentLink == 1){
			service_time = hyperx((1.0 / mu1), pow((cov / mu1), 2.0));
			//service_time = exponential(1.0 / mu1);
			queue1(service_time, clock);
		}
		else{
			// Pull an exponential service time and then send the customer to the queue
			service_time = hyperx((1.0 / mu2), pow((cov / mu2), 2.0));
			//service_time = exponential(1.0 / mu1);
			queue2(service_time, clock);
		}
	}
	count++;
}

//==============================================================================
//==  Process for 100 Mb/s link                                               ==
//==============================================================================
void queue1(double service_time, double orgTime)
{
	// Your code here
	create("queue1");

	// Reserve, hold, and release server
	reserve(Server1);
	hold(service_time);
	release(Server1);

	//Collect statistics
	collectStatistics(orgTime, LINK1_POWER);
}

//==============================================================================
//==  Process for 1 Gb/s link                                                 ==
//==============================================================================
void queue2(double service_time, double orgTime)
{
	// Your code here
	create("queue2");

	// Reserve, hold, and release server
	reserve(Server2);
	hold(service_time);
	release(Server2);

	//Collect statistics
	collectStatistics(orgTime, LINK2_POWER);
}


void collectStatistics(double orgTime, double powerDraw){
	record((clock - orgTime), DelayTable);
	record((clock - orgTime), DelayTableSampled);

	record(qlen(Server1), Q_LENGTH_SERVER1);
	record(qlen(Server1), Q_LENGTH_SERVER1_SAMPLED);
	record(qlen(Server2), Q_LENGTH_SERVER2);
	record(qlen(Server2), Q_LENGTH_SERVER2_SAMPLED);

	record((qlen(Server1) + qlen(Server2)), Q_LENGTH_TOTAL);
	record((qlen(Server1) + qlen(Server2)), Q_LENGTH_TOTAL_SAMPLED);

	record(util(Server1), UTILIZATION_SERVER1);
	record(util(Server2), UTILIZATION_SERVER2);
	record(util(Server1), UTILIZATION_SERVER1_SAMPLED);
	record(util(Server2), UTILIZATION_SERVER2_SAMPLED);
	record((util(Server1) + util(Server2)), UTILIZATION_TOTAL);
	record((util(Server1) + util(Server2)), UTILIZATION_TOTAL_SAMPLED);



	/* We only count power when the device is servicing packets. The sleep mode
	*  allows us to not  count the time not spent processing packets. */
	record((clock - orgTime) * powerDraw, PowerDrawTable);

	PktCount++;
	if ((clock - orgTime) > THRESH){
		OverCount++;
	}
}
