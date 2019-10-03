// VALMIK KALATHIA cs610 9400 prp

import java.io.*;
import java.lang.*;
import static java.lang.Math.*;
import java.util.*;

public class pgrk9400 {

	private final static double d = 0.85;
	private final double error_rate = 0.00001;
	private int edges_count;
	public static pgrk9400 obj = new pgrk9400();

	public static void main(String[] args) {

		// Check number of arguments passed is 0 or not. If it is zero then print error message and exit
		if(args.length !=3 ) {
			System.out.println("Error! Usage: java pgrk9400 iterations initialValue inputfilename.");
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

			// C[i] array represents # outgoing links of page "Ti"
			int [] C = new int [e];
			for(int i=0; i<e; i++){
				C[i]=0;
				for(int j=0; j<e; j++){
					C[i] = C[i] + adjMatArr[i][j];
				}
			}

			double [] Src= new double[e];
			double[] D = new double[e];
			boolean flag = true;

			switch(initialValue){
				case 0:
				for(int i=0; i<e; i++){
					Src[i]=0;
				}
				break;
				case 1:
				for(int i=0; i<e; i++){
					Src[i]=1;
				}
				break;
				case -1:
				for(int i=0; i<e; i++){
					Src[i]=1.0/e;
				}
				break;
				case -2:
				for(int i=0; i<e; i++){
					Src[i]=1.0/Math.sqrt(e);
				}
				break;
			}

			// Build Base Case
			if (e<10) {
				System.out.print("Base    : 0");
				for(int i=0; i<e; i++) {
					System.out.printf(" :P[" + i + "]=%.6f",Math.round(Src[i]*1000000.0)/1000000.0);
				}				
			}

			// consider case for iterator value as 0 and # of edges is less than 10
			if (iterations==0 && e<=10) {
				int x = 0;
				do {
					if(flag == true) {
						flag = false;
					}
					else{
						for(int i=0; i<e; i++) {
							Src[i] = D[i];
						}
					}

					for(int i=0; i<e; i++) {
						D[i] = 0.0;
					}

					for(int i=0; i<e; i++) {
						for(int j=0; j<e; i++)
						{
							if(tr_adjMatArr[i][j] == 1) {
								D[i] = D[i] + (Src[j]/C[j]);
							}
						}
					}

               		//Compute and print pagerank of all pages
					System.out.println(); 
					System.out.print("Iter    : " + (x+1));
					for(int k= 0; k<e; k++) {
						D[k] = ( d*D[k] ) + ( (1-d) / e) ;
						System.out.printf(" :P[" + k + "]=%.6f",Math.round(D[k]*1000000.0)/1000000.0);
					}
					x++;  
				} while (converge9400(Src, D) != true);  
				System.out.println(); 
			}
			// consider case for iterator value is not 0 and # of edges is less than 10
			else if (iterations!=0 & e<=10){
				for(int i=0; i <iterations; i++){
					for(int j=0; j<e; j++) {
						D[j] = 0.0;
					}

					for(int k=0; k<e; k++) {
						for(int l=0; l<e; l++)
						{
							if(tr_adjMatArr[k][l] == 1) {
								D[k] = D[k] + (Src[l]/C[l]);
							} 
						}
					}

					System.out.println();
					System.out.print("Iter    : " + (i+1));
					for(int t=0; t<e; t++) {
						D[t] = ( d*D[t] ) + ( (1-d) / e);
						System.out.printf(" :P[" + t + "]=%.6f",Math.round(D[t]*1000000.0)/1000000.0);
					}

					for(int g=0; g<e; g++) {
						Src[g] = D[g]; 
					} 
				}
				System.out.println();
			}

			// If the graph has more than 10 edges, then value of #iterations will be 0 and initialvalue will be 1
			if (e>10){
				iterations = 0;
				for (int i=0; i<e; i++){
					Src[i] = 1.0/e;
				}
				int i=0;
				do{
					if(flag == true){
						flag = false;
					}
					else{
						for(int j=0; j<e; j++){
							Src[j] = D[j];
						}
					}

					for(int l=0; l<e; l++){
						D[l] = 0.0;
					}

					for(int m=0; m<e; m++){
						for (int n=0; n<e; n++){
							if(tr_adjMatArr[m][n] == 1){
								D[m] = D[m] + (Src[n]/C[n]);
							}
						}
					}

					for (int p=0; p<e; p++){
						D[p] = ( d * D[p] ) + ( (1-d) / e );
					}
					i++;
				} while (converge9400(Src,D) != true);

				System.out.println("Iter: " + i);
				for(int y=0; y<e; y++){
					System.out.printf(" P[" + y + "] = %.6f\n",Math.round(D[y]*1000000.0)/1000000.0);
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