// VALMIK KALATHIA cs610 9400 prp

import java.io.*;
import java.lang.*;
import static java.lang.Math.*;
import java.util.*;

public class hits9400 {

	private final double error_rate = 0.00001;
	private int edges_count;
	public static hits9400 obj = new hits9400();

	public static void main(String[] args) {

		// Check number of arguments passed is 0 or not. If it is zero then print error message and exit
		if(args.length !=3 ) {
			System.out.println("Error! Usage: java hits9400 iterations initialValue inputfilename.");
			System.out.println("All 3 arguments are mandatory.");
			System.exit(1);
		}

		// Store number of iterations we are going to perform from command line argument
		int iterations = Integer.parseInt(args[0]);
		//System.out.println("# of iterations : " + iterations);

		// Store initial value from command line argument
		int initialValue = Integer.parseInt(args[1]);
		//System.out.println("initialValue is : " + initialValue );

		if (!(initialValue >= -2 && initialValue <= 1 )) {
			System.out.println("Invalid Initial Value. It must be either -2 or -1 or 0 or 1.");
			System.exit(1);
		}

		// Store file name from the command line argument
		String fileName = args[2];
		//System.out.println("Filename is : " + fileName);

		// Extract values from file and create Adjacencey Matrix Array
		try{
			Scanner scan = new Scanner(new File(fileName));
			int e = scan.nextInt(); // Total # of edges
			int v = scan.nextInt(); // Total # of vertices
			//System.out.println("# of edges : " + e + " # of vertices : " + v);
			obj.setEdgesCount9400(e);

			// Create Array to store all the edges
			int [][] edgesArr = new int [e][2];
			if (scan.hasNextInt()){
				for (int i = 0 ; i < e; i++) {
					for (int j = 0 ; j<2; j++) {
						edgesArr[i][j] = scan.nextInt(); 
					}
				}
			}

        	//print Edges Array values for testing 
			/*System.out.println("Edges Array Values "); 
			for (int i = 0 ; i < e ; i++) {
				for (int j = 0 ; j < 2 ; j++) {
					System.out.print(" " + edgesArr[i][j] + " "); 
				}
				System.out.println("");
			}*/

        	//Create Adjacency Matrix with all value as 0 in the first place
			int [][] adjMatArr = new int[e][e];
			for(int i = 0 ; i < e ; i++){
				for(int j = 0 ; j < e ; j++){
					adjMatArr[i][j] = 0;
				}
			}

       		//Print Adjacency Matrix with 0 Values for testing
			/*System.out.println("Adjacency Matrix ");
			for (int i = 0 ; i < e; i++){
				for (int j = 0 ; j<e; j++){
					System.out.print(" "+ adjMatArr[i][j] + " "); 
				}
				System.out.println("");
			}*/

       		//Update Adjacency Matrix list for all edges we have
			for (int i=0; i<e; i++){
				int x = edgesArr[i][0];
				int y = edgesArr[i][1];
				adjMatArr[x][y]=1;
			}

        	//Print Adjacency Matrix with Updated Values for testing
			/*System.out.println("Adjacency Matrix Updated");
			for (int i = 0 ; i < e; i++){
				for (int j = 0 ; j<e; j++){
					System.out.print(" "+ adjMatArr[i][j] + " "); 
				}
				System.out.println("");
			}*/

			//Transpose of Adjacency Matrix
			int [][] tr_adjMatArr = new int [e][e];
			for (int i = 0; i < e; i++){
				for (int j = 0; j < e; j++){
					tr_adjMatArr[i][j] = adjMatArr[j][i];
				}
			}

            // Print Transpose of Adjacency Matrix for Testing 
			/*System.out.println("Transpose of Adjacency Matrix");
			for (int i = 0 ; i < e; i++){
				for (int j = 0 ; j<e; j++){
					System.out.print(" "+ tr_adjMatArr[i][j] + " "); 
				}
				System.out.println("");
			}*/

			double [] auth0 = new double[e];
			double [] hub0 = new double[e];

			switch(initialValue){
				case 0:
				for (int i=0; i<e; i++){
					auth0[i] = 0;
					hub0[i] = 0;
				}
				break;
				case 1:
				for (int i=0; i<e; i++){
					auth0[i] = 1.0;
					hub0[i] = 1.0;
				}
				break;
				case -1:
				for (int i=0; i<e; i++){
					auth0[i] = 1.0/e;
					hub0[i] = 1.0/e;
				}
				break;			
				case -2:
				for (int i=0; i<e; i++){
					auth0[i] = 1.0/Math.sqrt(e);
					hub0[i] = 1.0/Math.sqrt(e);
				}
				break;
			}

			// Print value of Auth0 Matrix for testing
			/*System.out.println("Auth0");
			for (int i=0; i<e; i++){
				System.out.println(auth0[i]);
			} 

			// Print value of Hub0 Matrix for testing
			System.out.println("Hub0");
			for (int i=0; i<e; i++){
				System.out.println(hub0[i]);
			} */

			double [] auth = new double[e];
			double [] hub = new double[e];
			double [] authprev = new double[e];
			double [] hubprev = new double[e];
			double auth_scale = 0.0;
			double sum_of_auth_square = 0.0;
			double hub_scale = 0.0;
			double sum_of_hub_square  = 0.0; 

			// Initialization Part
			for(int i=0; i<e; i++) {
				auth[i] = auth0[i];
				hub[i] = hub0[i];
				authprev[i] = auth[i]; 
				hubprev[i] = hub[i];
			}

       		// Build Base Case
       		if(e<10){
				System.out.print("Base:    0 :");
				for (int i=0; i<e; i++){
					System.out.printf(" A/H[%d]=%.6f/%.6f",i,Math.round(auth0[i]*1000000.0)/1000000.0,Math.round(hub0[i]*1000000.0)/1000000.0); 
				}
			}

       		// Update and print Iterations value for Auth/Hub Matrix 
			if(iterations==0 && e<=10){
				int i=0;
				do{
					for(int j=0; j<e; j++){
						authprev[j]=auth[j];
						hubprev[j]=hub[j];
					}

       			// Update Auth Matrix Values
					for (int j=0; j<e; j++){
						auth[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(tr_adjMatArr[k][l] == 1){
								auth[k] = auth[k] + hub[l];
							}
						}
					}

   				// Update Hub Matrix Values
					for (int j=0; j<e; j++){
						hub[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(adjMatArr[k][l] == 1){
								hub[k] = hub[k] + auth[l];
							}
						}
					}

   				// Scaling of Auth Matrix Values
					auth_scale = 0.0;
					sum_of_auth_square = 0.0;
					for(int j=0; j<e; j++) {
						sum_of_auth_square = sum_of_auth_square + (auth[j]*auth[j]);    
					}
					auth_scale = Math.sqrt(sum_of_auth_square); 
					for(int k=0; k<e; k++) {
						auth[k] = auth[k]/auth_scale;
					} 

				// Scaling of Hub Matrix Values
					hub_scale = 0.0;
					sum_of_hub_square = 0.0;
					for(int j=0; j<e ; j++) {
						sum_of_hub_square = sum_of_hub_square + (hub[j]*hub[j]);    
					}
					hub_scale = Math.sqrt(sum_of_hub_square); 
					for(int k=0; k<e; k++) {
						hub[k] = hub[k]/hub_scale;
					}
					i++;
					System.out.println();
					System.out.print("Iter:    " + i + " :");
					for(int r=0; r<e; r++) {
						System.out.printf(" A/H[%d]=%.6f/%.6f",r,Math.round(auth[r]*1000000.0)/1000000.0,Math.round(hub[r]*1000000.0)/1000000.0); 
					}
				} while( converge9400(auth,authprev)==false ||  converge9400(hub,hubprev)==false);
				System.out.println("");
			}
       		// consider case for iterator value is not 0 and # of edges is less than 10
			else if(iterations!=0 && e<=10){
				for (int i=0; i<iterations; i++){

       				// Update Auth Matrix Values
					for (int j=0; j<e; j++){
						auth[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(tr_adjMatArr[k][l] == 1){
								auth[k] = auth[k] + hub[l];
							}
						}
					}

       				// Update Hub Matrix Values
					for (int j=0; j<e; j++){
						hub[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(adjMatArr[k][l] == 1){
								hub[k] = hub[k] + auth[l];
							}
						}
					}

       				// Scaling of Auth Matrix Values
					auth_scale = 0.0;
					sum_of_auth_square = 0.0;
					for(int j=0; j<e; j++) {
						sum_of_auth_square = sum_of_auth_square + (auth[j]*auth[j]);    
					}
					auth_scale = Math.sqrt(sum_of_auth_square); 
					for(int k=0; k<e; k++) {
						auth[k] = auth[k]/auth_scale;
					} 

 					// Scaling of Hub Matrix Values
					hub_scale = 0.0;
					sum_of_hub_square = 0.0;
					for(int j=0; j<e ; j++) {
						sum_of_hub_square = sum_of_hub_square + (hub[j]*hub[j]);    
					}
					hub_scale = Math.sqrt(sum_of_hub_square); 
					for(int k=0; k<e; k++) {
						hub[k] = hub[k]/hub_scale;
					}

					System.out.println("");
					System.out.print("Iter:    " + (i+1) + " :");
					for(int p=0; p<e; p++) {
						System.out.printf(" A/H[%d]=%.6f/%.6f",p,Math.round(auth[p]*1000000.0)/1000000.0,Math.round(hub[p]*1000000.0)/1000000.0); 
					}
				}
				System.out.println("");
			}

       		// If the graph has more than 10 edges, then value of #iterations will be 0 and initialvalue will be 1
			if (e>10){
				iterations = 0;
				for (int i=0; i<e; i++){
					auth[i] = 1.0/e;
					hub[i] = 1.0/e;
					authprev[i] = auth[i];
					hubprev[i] = hub[i];				
				}

				int i=0;
				do{
					for(int j=0; j<e; j++){
						authprev[j]=auth[j];
						hubprev[j]=hub[j];
					}

       				// Update Auth Matrix Values
					for (int j=0; j<e; j++){
						auth[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(tr_adjMatArr[k][l] == 1){
								auth[k] = auth[k] + hub[l];
							}
						}
					}

   					// Update Hub Matrix Values
					for (int j=0; j<e; j++){
						hub[j]=0.0;
					}
					for (int k=0; k<e; k++){
						for (int l=0; l<e; l++){
							if(adjMatArr[k][l] == 1){
								hub[k] = hub[k] + auth[l];
							}
						}
					}

   					// Scaling of Auth Matrix Values
					auth_scale = 0.0;
					sum_of_auth_square = 0.0;
					for(int j=0; j<e; j++) {
						sum_of_auth_square = sum_of_auth_square + (auth[j]*auth[j]);    
					}
					auth_scale = Math.sqrt(sum_of_auth_square); 
					for(int k=0; k<e; k++) {
						auth[k] = auth[k]/auth_scale;
					} 

					// Scaling of Hub Matrix Values
					hub_scale = 0.0;
					sum_of_hub_square = 0.0;
					for(int j=0; j<e ; j++) {
						sum_of_hub_square = sum_of_hub_square + (hub[j]*hub[j]);    
					}
					hub_scale = Math.sqrt(sum_of_hub_square); 
					for(int k=0; k<e; k++) {
						hub[k] = hub[k]/hub_scale;
					}
					i++;
				}while( converge9400(auth,authprev)==false ||  converge9400(hub,hubprev)==false);
				System.out.println("Iter:    "+i);
				for(int s=0; s<e; s++) {
					System.out.printf(" A/H[%d]=%.6f/%.6f\n",s,Math.round(auth[s]*1000000.0)/1000000.0,Math.round(hub[s]*1000000.0)/1000000.0); 
				}
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

	public static int getEdgesCount9400() {
		return obj.edges_count;
	}

	public static void setEdgesCount9400(int num) {
		obj.edges_count = num;
	}

	// Converge Function to check if Auth or Hub values are converged or not
	public static boolean converge9400(double[] p, double[] q)
	{
		int e = obj.getEdgesCount9400();
		for(int i = 0 ; i < e; i++) {
			if ( abs(p[i] - q[i]) > obj.error_rate ) 
				return false;
		}
		return true;
	}
}