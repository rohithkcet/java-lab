import java.util.Scanner;
class EbReading{
	int ConsumerNumber;
	String name,ConnectionType;
	int PreviousMeter,CurrentMeter;
	int price=0;
	void display(){
		int unit= CurrentMeter -PreviousMeter;
		if(ConnectionType.equalsIgnoreCase("domestic"))
		{	
			if(unit<=100 && unit>=0)
			{
				price = 0;
			}
			else if(unit>=101 && unit<=200)
			{
				price = (100*0)+(unit-100)*2;
			}
			else if(unit>=201 && unit <=500)
			{
				price = (100*0)+(100*2)+(unit-200)*4;
			}
			else{
				price = (100*0)+(100*2)+(300*4)+(unit-500)*6;
			}
		}
		else if(ConnectionType.equalsIgnoreCase("commercial"))
		{
			if(unit<=100 && unit>=0)
			{
				price = unit*2;
			}
			else if(unit>=101 && unit<=200)
			{
				price = (100*2)+(unit-100)*4;
			}
			else if(unit>=201 && unit <=500)
			{
				price = (100*2)+(100*4)+(unit-200)*6;
			}
			else{
				price = (100*2)+(100*4)+(300*6)+(unit-500)*7;
			}
		}

		System.out.println("Amount to be paid: Rs."+price);
	}

}

public class EbBill
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int unit;
		EbReading eb1 = new EbReading();
		System.out.println("Enter Consumer Number: ");
		eb1.ConsumerNumber = sc.nextInt();
		System.out.println("Enter the name: ");
		eb1.name = sc.next();
		System.out.println("Enter connnection type : ");
		eb1.ConnectionType = sc.next();
		System.out.println("Enter the previous and current meter :");
		eb1.PreviousMeter = sc.nextInt();
		eb1.CurrentMeter = sc.nextInt();
		eb1.display();
		
	}
}