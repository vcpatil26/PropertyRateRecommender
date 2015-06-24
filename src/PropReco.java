
import java.io.*;
import java.lang.Math.*;
import java.util.*;

public class PropReco{

public static void main(String[] args) {
 
	PropReco obj = new PropReco();
	obj.run();
 
  }

 public void run() {
 
	String csvFile = "C:\\Users\\Varun\\Desktop\\data.csv";					//Data file including the case base
	String results = "C:\\Users\\Varun\\Desktop\\results.txt";	
	String cvsSplitBy = ",";
	Scanner sc=null;
	
	int[][] test = new int[105][7];
    String [][] testStr = new String[105][7];
    int[][] euclidean=new int[105][2];
    float[] userInp=new float [7];

	try {
 
		sc = new Scanner(new File(csvFile));					//Storing the data into a 2D array
		int row=0;
		 while (sc.hasNextLine())
	        {
	            String line = sc.nextLine();
	            String arr[] = line.split(cvsSplitBy);
	            for(int j=0;j<arr.length;j++){
	            	testStr[row][j]=arr[j];
	            }
	            row++;
	        } 
		 
			for(int i=0;i<105;i++){
				for(int j=0;j<7;j++){
					test[i][j]= Integer.parseInt(testStr[i][j]);
				}
			}
			
		 	int[] userIn= new int[6];						//Storing user inputs in an array
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter your parameters(Zip, Area(Sq. feet), Per Capita Income, type of house, Year of Construction, Locality indicator):");
			System.out.println("Enter the Zip code for the house:");
			userIn[0] = scan.nextInt();
			System.out.println("Enter the desired area for the house(in sq. feet):");
			userIn[1] = scan.nextInt();
			System.out.println("Enter the per capita income for the house (USD):");
			userIn[2] = scan.nextInt();
			System.out.println("Enter the desired type of the house: \nEnter 0 for an efficiency apartment\nEnter 1 for an apartment with 2 rooms\nEnter 2 for an apartment with 2 rooms and a fire place\nEnter 3 for an apartment with three rooms\nEnter 4 for townhouse");
			userIn[3] = scan.nextInt();
			System.out.println("Enter the year of construction desired(YYYY):");
			userIn[4] = scan.nextInt();
			System.out.println("Enter the desired locality:(0-5)");
			userIn[5] = scan.nextInt();
				
				
				/*for (int j=0;j<6;j++)
			{System.out.print("Pincode: +userIn[j]);}*/
	
				for(int i=0; i<105;i++){
					int d = (test[i][0]-userIn[0])*(test[i][0]-userIn[0]) + 
							(test[i][1]-userIn[1])*(test[i][1]-userIn[1]) + 
							(test[i][2]-userIn[2])*(test[i][2]-userIn[2]) + 
							(test[i][3]-userIn[3])*(test[i][3]-userIn[3]) + 
							(test[i][4]-userIn[4])*(test[i][4]-userIn[4]) + 
							(test[i][5]-userIn[5])*(test[i][5]-userIn[5]);
					d= (int) Math.sqrt(d);
					euclidean[i][0]=d;
					euclidean[i][1]=test[i][6];
				}
				
			
				int t=0;
				int t1=0;
	            for(int x=0;x<105-1;x++){
	            	for(int y=1; y<105-x;y++){
	            		if(euclidean[y-1][0] > euclidean[y][0])
	            		{
	            			t=euclidean[y-1][0];
	            			t1=euclidean[y-1][1];
	            			euclidean[y-1][0]=euclidean[y][0];
	            			euclidean[y-1][1]=euclidean[y][1];
	            			euclidean[y][0]=t;
	            			euclidean[y][1]=t1;
	            		}
	            		
	            	}
	            }
	            
				boolean flag=false;
	            float sum=0;
	            float avg=0;
	            int count=0;
	            //Filtering the matched case base
	            for(int i=0;i<10;i++){
	            	if(euclidean[i][0]==0)
	            	{	sum = sum +euclidean[i][1];
            		count++;
            		break;
	            	
	             }
	            	
	            	else if (euclidean[i][0]<100)
	            	{ 
	            		sum = sum +euclidean[i][1];
	            		count++;
	            		break;
	            		
	            	}
	            	
	            	else if (euclidean[i][0]<500){
	            			sum = sum +euclidean[i][1];
		            		count++;
		            		break;
	            		}
	            	else{
	            		for (int j=0; j<10; j++){
	            			sum = sum +euclidean[j][1];
		            		count++;
		            		break;}
	            			
	            	}
	            }
	            //System.out.println(sum);
	            while(sum>0 && count>0 && flag==false){
	            	avg=sum/count;
	            	System.out.println("The approximate rent will be: USD " + avg);
	            	//System.out.println("In the while loop");
	            	flag=true;
	            }
	            
	            for(int i=0;i<10;i++){
	            	System.out.println(euclidean[i][0] + "," + euclidean[i][1]);
	            }
	            
	            userInp[0]=userIn[0];
	            userInp[1]=userIn[1];
	            userInp[2]=userIn[2];
	            userInp[3]=userIn[3];
	            userInp[4]=userIn[4];
	            userInp[5]=userIn[5];
	            userInp[6]=avg;
	            
	            String data = Arrays.toString(userInp);
	            
	    		/*File file =new File("javaio-appendfile.txt");
	 
	    		if file doesnt exists, then create it
	    		if(!file.exists()){
	    			file.createNewFile();
	    		}*/
	 
	    		//true = append file
	    		FileWriter fileWritter = new FileWriter(results,true);
	    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	        bufferWritter.write(data);
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
	            
		}
	  catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  catch (IOException e) {
			e.printStackTrace();
		}
	  catch (Exception e){
		  System.out.println("The input is not valid. Enter a integer.");
		  System.exit(-1);
	  }
		finally {
			if (sc != null) {
				try {
					sc.close();
				} 
				finally{}
				
			}
		}
	}
}